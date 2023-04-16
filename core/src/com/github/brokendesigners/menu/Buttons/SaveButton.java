package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

public class SaveButton extends Button{

    public SaveButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }

    @Override
    public boolean performTask() {
//        menuScreen.gameSaved = true;
        return true;
    }
}
