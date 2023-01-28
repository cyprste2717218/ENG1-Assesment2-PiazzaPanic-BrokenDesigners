package com.github.brokendesigners.item;

import java.util.HashMap;

public class ItemRegister {
    public static HashMap<String, Item> itemRegister = new HashMap<>();

    public static boolean addItem(String ID, Item item){
        itemRegister.put(ID, item);
        return true;

    }
    public static void dispose(){
        for (String key : itemRegister.keySet()){
            itemRegister.get(key).texture.dispose();

        }

    }

}
