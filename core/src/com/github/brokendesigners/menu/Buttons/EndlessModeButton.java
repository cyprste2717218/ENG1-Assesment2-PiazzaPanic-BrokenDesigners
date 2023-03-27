package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

public class EndlessModeButton extends Button{
    public EndlessModeButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }

    @Override
    public boolean performTask() {
        menuScreen.isEndless = true;
        menuScreen.active = false;
        menuScreen.tryActivateGame = true;
        return true;
    }
}
