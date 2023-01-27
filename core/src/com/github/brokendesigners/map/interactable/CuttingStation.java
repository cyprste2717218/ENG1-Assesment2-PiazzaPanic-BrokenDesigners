package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;

public class CuttingStation extends Station {

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion","Bun"};

    public CuttingStation(Vector3 objectPosition, float width, float height, float handX, float handY){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cutting_Station");
    }

    //Cutting Operation
    @Override
    public boolean action(Player player)
    {
        if(Applicable(Cuttables,"Cutting_Station",hand.getName())==true)
        {
            hand =  ItemRegister.itemRegister.get("Cut_"+hand.getName());
            return true;
        }
        else //If operation should not be able to preformed, stops item being valid for other operations
        {
            hand =  ItemRegister.itemRegister.get("Waste");
            return true;
        }
    }
    
}
