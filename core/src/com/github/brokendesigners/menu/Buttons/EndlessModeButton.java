package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

/**

 This class represents a button that, when clicked, sets the game mode to endless mode and opens the difficulty screen.
 */
public class EndlessModeButton extends Button{
    /**
     * Constructs a new EndlessModeButton.
     *
     * @param rectangle the rectangle that represents the bounds of the button
     * @param selectedTexture the texture to display when the button is selected or hovered over
     * @param unselectedTexture the texture to display when the button is not selected or hovered over
     * @param menuScreen the MenuScreen to which this button belongs
     */
    public EndlessModeButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }
    /**
     * Sets the game mode to endless mode and opens the difficulty screen when the button is clicked.
     *
     * @return true always
     */
    @Override
    public boolean performTask() {
        menuScreen.playOptions = false;
        menuScreen.isEndless = true;
        menuScreen.isDifficultyScreen = true;
        return true;
    }
}
