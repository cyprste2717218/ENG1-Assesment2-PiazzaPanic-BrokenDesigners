package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

public class ScenarioCustomerCountButton extends Button{

    int customerCountModifier;

    public ScenarioCustomerCountButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen, int customerCountModifier) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        this.customerCountModifier = customerCountModifier;
    }

    @Override
    public boolean performTask() {
        if(menuScreen.customerCount > 1 || customerCountModifier > 0){
            menuScreen.customerCount += customerCountModifier;
            return true;
        }
        return false;
    }
}
