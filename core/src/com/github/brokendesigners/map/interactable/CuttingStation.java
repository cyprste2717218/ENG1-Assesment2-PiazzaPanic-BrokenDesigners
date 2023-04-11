package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.item.ItemRegister;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

public class CuttingStation extends Station {
    //and in global variable
    //make match in constructor (CuttingStation
    //Multiple the time with match.getDifficulty.getSpeedMultiplier
    //Add match to the kitchen class to all cooking stations look at screenshot

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion","Bun"};
    Bubble bubble;

    Match match;

    public CuttingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, Match match){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cutting_Station");
        this.match = match;
        this.handPosition = new Vector2(handX, handY);
        this.bubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
            Animations.cuttingAnimation);

    }

    //Cutting Operation
    @Override
    public boolean action(final Player player) {
        if (this.inuse == false && this.hand != null) {
            if (Applicable(Cuttables, "Cutting_Station", hand.getName()) == true) {
                this.inuse = true;
                player.disableMovement();
                player.hand.disable_hand_ability();
                this.bubble.setVisible(true);
                Timer timer = new Timer();
                Task task = new Task() {
                    @Override
                    public void run() {
                        hand = ItemRegister.itemRegister.get("Cut_" + hand.getName());
                        bubble.setVisible(false);
                        player.enableMovement();
                        player.hand.enable_hand_ability();
                        inuse = false;
                    }
                };
                timer.scheduleTask(task, 4f * match.getDifficultyLevel().getSpeedMultiplier());
                return true;
            } else {
                System.out.println("Incorrect Item");
                failure.play();
            }
        }
        return false;
    }
    
}
