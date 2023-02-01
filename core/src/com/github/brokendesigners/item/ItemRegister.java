package com.github.brokendesigners.item;

import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
/*
 * Holds every item in the game (Ideally, you still need to add the item into the register)
 * This is so references to items can be standardised across stations - makes things easier :)
 * Like the ItemRegister in minecraft if you've ever tried making a Fabric Mod.
 *
 * The register acts much like a python dictionary - The key (String) is the name/id of the item, the item is the item itself.
 */
public class ItemRegister {
    public static HashMap<String, Item> itemRegister = new HashMap<>();
    /*
     * Adds item to the register.
     */
    public static boolean addItem(String ID, Item item){
        itemRegister.put(ID, item);
        return true;

    }
    public static void dispose(){

        for (String key : itemRegister.keySet()){
            System.out.println(key);

        }
        System.out.println("////");
        for (String key : itemRegister.keySet()){
            System.out.println(key);
            if (itemRegister.get(key).texture == null){
                System.out.println("sad :(");
                System.out.println(itemRegister.get(key));
            }
                itemRegister.get(key).dispose();

        }
    }
}
