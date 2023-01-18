package com.github.brokendesigners;

import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.Items;
import com.github.brokendesigners.item.NuclearWeapon;
import com.github.brokendesigners.item.Void;

public class ItemInitialiser {

    public ItemInitialiser(){

    }
    public void initialise(){
        Item testItem = new NuclearWeapon();
        Items.addItem("wmd", testItem);

        Item testItem2 = new Void();
        Items.addItem("void", testItem2);
    }
}
