package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Items {
    public static HashMap<String, Item> itemMap = new HashMap<String, Item>();

    public static boolean addItem(String ID, Item item){
        itemMap.put(ID, item);
        return true;

    }

}
