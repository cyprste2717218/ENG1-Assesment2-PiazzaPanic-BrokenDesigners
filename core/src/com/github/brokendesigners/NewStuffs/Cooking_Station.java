package com.github.brokendesigners.NewStuffs;

public class Cooking_Station extends Station {

    static final String[] Cookables = {"Patty","Bun","Cut_Bun"};
    static final String[] Flippables = {"Patty"};

    public Cooking_Station()
    {
        super("Cooking_Station");
    }

    //Cooking Operation
    public void Interact()
    {
        if(this.storing == true) //only if a value is held
        {
            if(Applicable(Cookables,"Cooking_Station",stored.getName())==true)
            {
                //when cooking check for flip
                if(Applicable(Flippables,"Cooking_Station",stored.getName())==false) //Cooking something that does not require a flip
                {
                    stored.Cooked = true;
                    stored.setName("Cooked_"+stored.getName());
                }
                else
                {
                    stored.Cooking = true;
                }
            }
            else //If operation should not be able to preformed, stops item being valid for other operations
            {
                stored.setName("Waste_"+stored.getName());
                stored.Waste = true;
            }
        }
    }

    //Flipping
    public void Flipping() //Once key to flup is pushed
    {
        if(this.storing == true) //only if a value is held
        {
            if(Applicable(Flippables,"Cooking_Station",stored.getName())==true)
            {
                //This is once it is cooked
                if (stored.Cooking == true) //only cooked if it has been cooking
                {
                    stored.setName("Cooked_"+stored.getName());
                    stored.Cooked = true;
                    stored.Cooking = false;
                }
            }
            //Does not matter if you flip a non-flippable
        }
    }
    
}
