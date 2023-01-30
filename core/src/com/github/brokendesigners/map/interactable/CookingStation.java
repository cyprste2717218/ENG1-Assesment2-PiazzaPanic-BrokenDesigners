package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;

public class CookingStation extends Station {

    static final String[] Cookables = {"Patty","Bun"};
    static final String[] Flippables = {"Patty"};

    public CookingStation(Vector2 objectPosition, float width, float height, float handX, float handY){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cooking_Station");
        this.handPosition = new Vector2(handX, handY);
    }

    //Cooking Operation
    @Override
    public boolean action(Player player)
    {
        if (this.inuse == true) {
            return false;
        } else{
            this.inuse = true;
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
                    hand =  ItemRegister.itemRegister.get("Cooked_"+hand.getName());
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
