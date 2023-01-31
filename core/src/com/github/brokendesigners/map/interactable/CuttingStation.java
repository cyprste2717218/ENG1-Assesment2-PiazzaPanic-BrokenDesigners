package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.item.ItemRegister;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

public class CuttingStation extends Station {

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion","Bun"};
    Bubble bubble;

    public CuttingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cutting_Station");
        this.handPosition = new Vector2(handX, handY);
        this.bubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
            Animations.cuttingAnimation);

    }

    //Cutting Operation
    @Override
    public boolean action(final Player player) {
        if (this.inuse == false) {

            if (hand == null) {
                return false;
            } else if (Applicable(Cuttables, "Cutting_Station", hand.getName()) == true && this.interacting == false) {
                this.inuse = true;
                player.moving_disabled = true;
                this.bubble.setVisible(true);
                this.interacting = true;

                Timer timer = new Timer();
                Task task = new Task() {
                    @Override
                    public void run() {
                        hand = ItemRegister.itemRegister.get("Cut_" + hand.getName());
                        bubble.setVisible(false);
                        player.enableMovement();
                        interacting = false;
                        inuse = false;
                    }
                };
                timer.scheduleTask(task, 4f);
                return true;
            } else //If operation should not be able to preformed, stops item being valid for other operations
            {
                hand = ItemRegister.itemRegister.get("Waste");
                return true;
            }
        }
        else {
            return false;
        }
    }
    
}
