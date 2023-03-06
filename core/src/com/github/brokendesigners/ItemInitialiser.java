package com.github.brokendesigners;

import com.github.brokendesigners.item.*;

/*
* Initialises Items into the ItemRegister
*
*/
public class ItemInitialiser {

    public ItemInitialiser(){

    }
    public void initialise(){
        ItemRegister.addItem("void", new VoidItem());
        ItemRegister.addItem("grapes", new grapes());

        ItemRegister.addItem("Onion", new Onion());
        ItemRegister.addItem("Lettuce", new Lettuce());
        ItemRegister.addItem("Tomato", new Tomato());
        ItemRegister.addItem("Bun", new Bun());
        ItemRegister.addItem("Meat", new Meat());
        ItemRegister.addItem("Potato", new Potato());
        ItemRegister.addItem("Dough", new Dough());
        ItemRegister.addItem("Cheese", new Cheese());

        ItemRegister.addItem("Patty", new Patty());
        ItemRegister.addItem("Base", new Base());

        ItemRegister.addItem("Pizza", new Pizza());
        ItemRegister.addItem("JacketPotato", new JacketPotato());

        ItemRegister.addItem("Cut_Lettuce", new Cut_Lettuce());
        ItemRegister.addItem("Cut_Tomato", new Cut_Tomato());
        ItemRegister.addItem("Cut_Onion", new Cut_Onion());
        ItemRegister.addItem("Cut_Potato", new Cut_Potato());

        ItemRegister.addItem("Cooked_Patty", new Cooked_Patty());
        ItemRegister.addItem("Cooked_Bun", new Cooked_Bun());
        ItemRegister.addItem("Cooked_Tomato", new Cooked_Tomato());

        ItemRegister.addItem("Salad", new Salad());
        ItemRegister.addItem("Burger", new Burger());
        ItemRegister.addItem("Baked_Pizza", new Baked_Pizza());
        ItemRegister.addItem("Baked_JacketPotato", new Baked_JacketPotato());

        ItemRegister.addItem("Waste", new Waste());


    }
}
