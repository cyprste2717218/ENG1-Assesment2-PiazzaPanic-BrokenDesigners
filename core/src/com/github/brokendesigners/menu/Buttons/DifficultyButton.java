package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.menu.MenuScreen;

/**
 * Created class

 Represents a button that allows the user to select a difficulty level for the game.
 It extends the abstract class Button and overrides the performTask() method to set the game's difficulty level and
 activate the game.
 */

public class DifficultyButton extends Button{

    private DifficultyLevel buttonDifficulty;

    /**
     * Constructor for DifficultyButton class
     * @param rectangle The button's rectangular bounds
     * @param selectedTexture Texture displayed when button is selected
     * @param unselectedTexture Texture displayed when button is not selected
     * @param menuScreen The menu screen where the button is being displayed
     * @param difficultyLevel The difficulty level associated with the button
     */
    public DifficultyButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen, DifficultyLevel difficultyLevel) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        buttonDifficulty = difficultyLevel;
        setRendered(false);
    }

    /**
     * Overrides the performTask method from the Button class. Sets the difficulty level and activates the game.
     * @return True if task is completed successfully, false otherwise
     */
    @Override
    public boolean performTask() {
        menuScreen.setDifficultyLevel(buttonDifficulty);
        System.out.println(menuScreen.getDifficulty().name());
        menuScreen.active = false;
        menuScreen.tryActivateGame = true;
        return true;
    }
}
