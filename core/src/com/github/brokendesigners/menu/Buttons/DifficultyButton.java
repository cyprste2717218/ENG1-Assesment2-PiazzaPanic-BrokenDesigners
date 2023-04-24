package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.menu.MenuScreen;

public class DifficultyButton extends Button{

    private DifficultyLevel buttonDifficulty;
    public DifficultyButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen, DifficultyLevel difficultyLevel) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        buttonDifficulty = difficultyLevel;
        setRendered(false);
    }

    @Override
    public boolean performTask() {
        menuScreen.setDifficultyLevel(buttonDifficulty);
        System.out.println(menuScreen.getDifficulty().name());
        menuScreen.active = false;
        menuScreen.tryActivateGame = true;
        return true;
    }
}
