package com.github.brokendesigners;

import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.item.NuclearWeapon;
import com.github.brokendesigners.item.Void;

public class ItemInitialiser {

    public ItemInitialiser(){

    }
    public void initialise(){
        Item testItem = new NuclearWeapon();
        ItemRegister.addItem("wmd", testItem);

        Item testItem2 = new Void();
        ItemRegister.addItem("void", testItem2);

        Item testItem3 = new Void();
        ItemRegister.addItem("grapes", testItem3);

    }
}
