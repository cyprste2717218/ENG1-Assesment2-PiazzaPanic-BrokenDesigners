package com.github.brokendesigners.NewStuffs;

public class Counter_Station extends Station
{
    public Counter_Station()
    {
        super("Counter_Station");
    }

    public Item formPatty() //Turn meat into patty
    {
        if(stored.getName()=="Meat")
        { 
            stored.setName("Patty");
        }
        return stored;

    }
    
}
