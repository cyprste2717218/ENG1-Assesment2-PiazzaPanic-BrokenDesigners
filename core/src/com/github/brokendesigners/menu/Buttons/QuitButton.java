package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.MenuScreen;

public class QuitButton extends Button{

    public QuitButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }

    @Override
    public boolean performTask() {
        System.out.println("Returning to main menu");
        menuScreen.setGameNull();
        menuScreen.gameSaved = false;
        menuScreen.complete = false;
        menuScreen.cont = false;
        return true;
    }
}
