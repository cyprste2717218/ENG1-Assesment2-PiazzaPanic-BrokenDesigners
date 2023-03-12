package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;
import com.badlogic.gdx.utils.Timer.Task;


public class CookingStation extends Station {

    static final String[] Cookables = {"Patty","Bun"};
    static final String[] Flippables = {"Patty"};

    Bubble bubble;

    public CookingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cooking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.bubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.cuttingAnimation);
    }

    //Cooking Operation
    @Override
    public boolean action(final Player player) {
        
        // if player is holding something, station is not already in use and item
        // in hand has not already been cooked
        if (this.inuse == false && this.hand != null && this.hand.Cooking == false) {
            if (Applicable(Cookables, "Cooking_Station", hand.getName()) == true) {
                this.inuse = true;
                player.disableMovement();
                player.hand.disable_hand_ability();
                this.bubble.setVisible(true);
                Timer timer = new Timer();
                Task task = new Task() {
                    @Override
                    public void run() {
                        hand = ItemRegister.itemRegister.get("Cooked_" + hand.getName());
                        hand.Cooking = true;
                        System.out.println("Hand: "+ hand.getName());
                        bubble.setVisible(false);
                        player.enableMovement();
                        player.hand.enable_hand_ability();
                        inuse = false;
                    }
                };
                timer.scheduleTask(task, 4f);
                return true;

            } else {
                System.out.println("Incorrect Item");
                failure.play();
            }
        }
        return false;
    }
        /*
        if (this.inuse == true) {
            return false;
        } else{
            this.inuse = true;
            player.moving_disabled = true;
        }
        if(this.hand != null) //only if a value is held
        {
            System.out.println((Applicable(Cookables,"Cooking_Station",hand.getName())));
            if(hand.Cooking == true)
            {
                hand = Flipping();
                this.inuse = false;
                return true;
            }
            if(Applicable(Cookables,"Cooking_Station",hand.getName())==true)
            {
                //when cooking check for flip
                if(Applicable(Flippables,"Cooking_Station",hand.getName())==false) //Cooking something that does not require a flip
                {
                    this.bubble.setVisible(true);
                    Timer timer = new Timer();
                    Timer.Task task = new Timer.Task() {
                        @Override
                        public void run() {
                            hand =  ItemRegister.itemRegister.get("Cooked_"+hand.getName());
                            bubble.setVisible(false);
                            player.enableMovement();
                            interacting = false;
                            inuse = false;
                        }
                    };
                }
                else
                {
                    hand.Cooking = true;
                }
                this.inuse = false;
                return true;
            }
            else //If operation should not be able to preformed, stops item being valid for other operations
            {
                hand =  ItemRegister.itemRegister.get("Waste");
                this.inuse = false;
                return true;
            }
        }
        else
        {
            this.inuse = false;
            return false;
        }
    }
*/
    //Flipping
    private Item Flipping() //Once key to flup is pushed
    {
//        if(this.hand != null) //only if a value is held
//        {
//            if(Applicable(Flippables,"Cooking_Station",hand.getName())==true)
//            {
//                //This is once it is cooked
//                if (hand.Cooking == true) //only cooked if it has been cooking
//                {
//                    hand.Cooking = false;
//                    hand =  ItemRegister.itemRegister.get("Cooked_"+hand.getName());
//                }
//            }
//            //Does not matter if you flip a non-flippable
//        }
        this.hand.Cooking = false;
        System.out.println(hand.getName());
        hand = ItemRegister.itemRegister.get("Cooked_"+hand.getName());
        return hand;
    }
    
}
