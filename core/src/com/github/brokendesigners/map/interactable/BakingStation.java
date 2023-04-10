package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

public class BakingStation extends Station {

    static final String[] Bakeables = {"Pizza","JacketPotato"};
    Bubble bubble;

    public BakingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, boolean locked){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Baking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.bubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.gearAnimation);
        this.locked = locked;
        stationUseTime = 4f;
    }

    @Override
    public boolean action(final Player player) {
        // to unlock the station
        if (this.locked)    {
            this.unlcockStation();
            unlockFX.play();
            System.out.println("Station Unlocked");
        }
        // if player is holding something, station is not already in use and item
        // in hand has not already been cooked
        if (this.inuse == false && this.hand != null && this.hand.Baking == false) {
            if (Applicable(Bakeables, "Baking_Station", hand.getName()) == true) {
                this.inuse = true;
                this.bubble.setVisible(true);
                Timer timer = new Timer();
                Timer.Task task = new Timer.Task() {
                    @Override
                    public void run() {
                        hand = ItemRegister.itemRegister.get("Baked_" + hand.getName());
                        hand.Baking = true;
                        System.out.println("Hand: "+ hand.getName());
                        bubble.setVisible(false);
                        inuse = false;
                    }
                };
                timer.scheduleTask(task, stationUseTime);
                return true;

            } else {
                System.out.println("Incorrect Item");
                failure.play();
            }
        }
        return false;
    }
}
