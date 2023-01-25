package com.github.brokendesigners.NewStuffs;

public class Dispenser{
    private String Ingredient;

    public Dispenser(String T)
    {
        // this.Ingredient = new Item(T);
        this.Ingredient = T;
    }

    public Item Interact() //When you go up to the station, an ingredient of this type is dropped.
    {
        return new Item(this.Ingredient);
    }

}