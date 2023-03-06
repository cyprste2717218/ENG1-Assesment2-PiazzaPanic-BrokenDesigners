package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

public class ExitHowToPlayButton extends Button{

    boolean howToPlay;

    public ExitHowToPlayButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }

    @Override
    public boolean performTask() {
        System.out.println("Returning to main menu");
        menuScreen.howToScreen = false;
        menuScreen.playOptions = false;
        menuScreen.selectedButton = 0;
        return true;
    }
}
