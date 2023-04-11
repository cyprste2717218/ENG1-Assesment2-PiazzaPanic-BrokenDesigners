package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

public class BakingStation extends Station implements IFailable {

    static final String[] Bakeables = {"Pizza","JacketPotato"};
    Bubble bakingBubble, attentionBubble;
    boolean canBurn;

    public BakingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Baking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.bakingBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.gearAnimation);
        this.attentionBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.attentionAnimation);
        stationUseTime = 4f;
        canBurn = false;
    }

    Timer.Task burnFood = new Timer.Task() {
        @Override
        public void run() {
            if(canBurn){
                hand = ItemRegister.itemRegister.get("Waste");
                attentionBubble.setVisible(false);
                canBurn = false;
            }
        }
    };

    @Override
    public boolean action(final Player player) {
        // if player is holding something, station is not already in use and item
        // in hand has not already been cooked
        if(inuse || hand == null || hand.Baking) return false;

        if (Applicable(Bakeables, "Baking_Station", hand.getName())) {
            this.inuse = true;
            this.bakingBubble.setVisible(true);
            finishSuccessfulOperation(player, 4f);
            return true;
        } else {
            return finishFailedOperation(player, stationUseTime);
        }
    }

    @Override
    public void handleStationInteraction() {
        if(hand == null){
            canBurn = false;
            attentionBubble.setVisible(false);
        }
        if(!canBurn) return;
        final Timer timer = new Timer();
        if(!burnFood.isScheduled()){
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    attentionBubble.setVisible(true);
                }
            }, 2f);
            timer.scheduleTask(burnFood, 7f);
        }
    }

    @Override
    public boolean finishSuccessfulOperation(Player player, float endingStationTime) {
        Timer timer = new Timer();
        Timer.Task finishBaking = new Timer.Task() {
            @Override
            public void run() {
                hand = ItemRegister.itemRegister.get("Baked_" + hand.getName());
                hand.Baking = true;
                System.out.println("Hand: "+ hand.getName());
                bakingBubble.setVisible(false);
                inuse = false;
                canBurn = true;
            }
        };
        timer.scheduleTask(finishBaking, endingStationTime);
        return true;
    }

    @Override
    public boolean finishFailedOperation(Player player, float endingStationTime) {
        System.out.println("Incorrect Item");
        failure.play();
        return false;
    }

    @Override
    public void generalFinish(Player player) {

    }
}

