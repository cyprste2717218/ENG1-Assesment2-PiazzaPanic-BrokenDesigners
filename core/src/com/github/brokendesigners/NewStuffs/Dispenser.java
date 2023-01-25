package com.github.brokendesigners.NewStuffs;

public class Dispenser{
    private String Ingredient;

    public Dispenser(String T)
    {
        // this.Ingredient = new Item(T);
        this.Ingredient = T;
    }

    public SpecialItem Interact() //When you go up to the station, an ingredient of this type is dropped.
    {
        return new SpecialItem(this.Ingredient);
    }

}