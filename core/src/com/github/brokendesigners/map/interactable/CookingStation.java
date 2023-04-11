package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;
import com.badlogic.gdx.utils.Timer.Task;


public class CookingStation extends Station implements IFailable {

    static final String[] Cookables = {"Patty","Bun", "Tomato"};
    static final String[] Flippables = {"Patty"};

    public boolean canBurn;

    public boolean needsInput;

    public Bubble cookingBubble, attentionBubble;

    public CookingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cooking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.cookingBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.gearAnimation);
        this.attentionBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.attentionAnimation);
        stationUseTime = 4f;
        canBurn = false;
        needsInput = false;
    }

    Task burnFood = new Task() {
        @Override
        public void run() {
            if(canBurn){
                hand = ItemRegister.itemRegister.get("Waste");
                attentionBubble.setVisible(false);
                canBurn = false;
            }
        }
    };

    public void startFoodBurning(){
        if(hand == null){
            canBurn = false;
            attentionBubble.setVisible(false);
            burnFood.cancel();
        }
        if(!canBurn) return;
        final Timer timer = new Timer();
        if(!burnFood.isScheduled()){
            timer.scheduleTask(new Task() {
                @Override
                public void run() {
                    attentionBubble.setVisible(true);
                }
            }, 2f);
            timer.scheduleTask(burnFood, 7f);
        }
    }

    //Cooking Operation
    @Override
    public boolean action(final Player player) {

        // if player is holding something, station is not already in use and item
        // in hand has not already been cooked
        if(inuse || hand == null || hand.Cooking) return false;
        if (Applicable(Cookables, "Cooking_Station", hand.getName())) {
            inuse = true;
            player.disableMovement();
            player.hand.disable_hand_ability();
            cookingBubble.setVisible(true);
            if (!requiresFlipping(player)){
                finishSuccessfulOperation(player, stationUseTime);
            }
        } else {
            System.out.println("Incorrect Item");
            failure.play();
        }
        return false;
    }

    private boolean requiresFlipping(final Player player){
        if(!Applicable(Flippables, "Cooking_Station", hand.getName())) return false;
        Timer timer = new Timer();
        //Wait half of stationUseTime
        timer.scheduleTask(new Task() {
            @Override
            public void run() {
                needsInput = true;
                cookingBubble.setVisible(false);
                attentionBubble.setVisible(true);
            }
        }, stationUseTime/2f);
        timer.scheduleTask(new Task() {
            @Override
            public void run() {
                boolean wasSuccessful = !needsInput ? finishSuccessfulOperation(player, stationUseTime/2f) : finishFailedOperation(player, stationUseTime/2f);
            }
        }, 3f);
        return true;
    }

    @Override
    public void handleStationInteraction() {
        startFoodBurning();
        if(needsInput && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            needsInput = false;
            attentionBubble.setVisible(false);
            cookingBubble.setVisible(true);
        }
    }

    @Override
    public boolean finishSuccessfulOperation(final Player player, float endingCookTime) {
        burnFood.cancel();
        Timer timer = new Timer();
        Task finishCooking = new Task() {
            @Override
            public void run() {
                hand = ItemRegister.itemRegister.get("Cooked_" + hand.getName());
                generalFinish(player);
                canBurn = true;
            }
        };
        timer.scheduleTask(finishCooking, endingCookTime);
        return true;
    }

    @Override
    public boolean finishFailedOperation(final Player player, float endingCookTime) {
        Timer timer = new Timer();
        Task finishCooking = new Task() {
            @Override
            public void run() {
                hand = ItemRegister.itemRegister.get("Waste");
                generalFinish(player);
                canBurn = false;
            }
        };
        timer.scheduleTask(finishCooking, endingCookTime);
        return false;
    }

    @Override
    public void generalFinish(Player player) {
        hand.Cooking = true;
        System.out.println("Hand: " + hand.getName());
        cookingBubble.setVisible(false);
        player.enableMovement();
        player.hand.enable_hand_ability();
        inuse = false;
        needsInput = false;
        attentionBubble.setVisible(false);
    }
}
