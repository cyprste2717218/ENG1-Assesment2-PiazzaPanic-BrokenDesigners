package com.github.brokendesigners.NewStuffs;

public class Cutting_Station extends Station {

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion","Bun"};

    public Cutting_Station()
    {
        super("Cutting_Station");
    }

    //Cutting Operation
    public void Interact()
    {
        stored.Cut = true;
        if(Applicable(Cuttables,"Cutting_Station",stored.getName())==true)
        {
            stored.setName("Cut_"+stored.getName());
        }
        else //If operation should not be able to preformed, stops item being valid for other operations
        {
            stored.setName("Waste_"+stored.getName());
            stored.Waste = true;
        }
    }
    
}
