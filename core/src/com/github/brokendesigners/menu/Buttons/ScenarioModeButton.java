package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

public class ScenarioModeButton extends Button{
    public ScenarioModeButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }

    @Override
    public boolean performTask() {
        menuScreen.isEndless = false;
        menuScreen.active = false;
        menuScreen.tryActivateGame = true;
        return true;
    }
}
