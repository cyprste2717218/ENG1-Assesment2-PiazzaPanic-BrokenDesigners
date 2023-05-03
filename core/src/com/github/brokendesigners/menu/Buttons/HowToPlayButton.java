package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

/**
 * Created class

 A button that allows the user to navigate to the How To Play screen in the main menu.
 */
public class HowToPlayButton extends Button{
    /**
     * Constructs a new HowToPlayButton.
     *
     * @param rectangle The rectangle representing the button's boundaries.
     * @param selectedTexture The texture used when the button is selected.
     * @param unselectedTexture The texture used when the button is unselected.
     * @param menuScreen The menu screen that the button is on.
     */
    public HowToPlayButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }
    /**
     * Sets the menu screen's howToScreen boolean to true, indicating that the user wants to view the How To Play screen.
     *
     * @return true, always.
     */
    @Override
    public boolean performTask() {
        menuScreen.howToScreen = true;
        return true;
    }
}
