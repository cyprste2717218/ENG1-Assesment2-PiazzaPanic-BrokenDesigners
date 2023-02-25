package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

public class ExitGameButton extends Button{

    public ExitGameButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }

    @Override
    public boolean performTask() {
        System.out.println("Exiting Game");
        Gdx.app.exit();
        return true;
    }
}
