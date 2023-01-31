package com.github.brokendesigners.item;

import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class ItemRegister {
    public static HashMap<String, Item> itemRegister = new HashMap<>();

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
