package com.github.brokendesigners.map.interactable;

import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;

public class CuttingStation extends Station {

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion","Bun"};

    public CuttingStation()
    {
        super("Cutting_Station");
    }

    //Cutting Operation
    public void Interact()
    {
        if(Applicable(Cuttables,"Cutting_Station",hand.getName())==true)
        {
            hand =  ItemRegister.itemRegister.get("Cut_"+hand.getName());
        }
        else //If operation should not be able to preformed, stops item being valid for other operations
        {
            hand =  ItemRegister.itemRegister.get("Waste");
        }
    }
    
}
