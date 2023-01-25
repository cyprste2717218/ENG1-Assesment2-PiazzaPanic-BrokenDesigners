package com.github.brokendesigners.NewStuffs;

import javax.print.event.PrintJobAttributeEvent;

public class Item {
    private String name;
    public Boolean Cut;
    public Boolean Cooking;
    public Boolean Cooked;
    public Boolean Waste;

    public Item(String n)
    {
        this.name = n;
        this.Cut = false;
        this.Cooking = false;
        this.Cooked = false;
        this.Waste = false;
    }

    //getname
    public String getName()
    {
        return this.name;
    }
    public void setName(String x)
    {
        this.name = x;
    }

}