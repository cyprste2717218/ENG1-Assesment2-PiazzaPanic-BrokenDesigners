package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;
/**

 A button representing the "Play" option in the main menu screen
 */
public class PlayButton extends Button{
    /**
     * Creates a new PlayButton with the given properties
     *
     * @param rectangle The rectangle defining the size and position of the button
     * @param selectedTexture The texture to use when the button is selected
     * @param unselectedTexture The texture to use when the button is unselected
     * @param menuScreen The menu screen instance that contains this button
     */
    public PlayButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }
    /**
     * Performs the task associated with the button
     *
     * @return true to indicate that the button has been clicked and its task has been performed
     */
    @Override
    public boolean performTask() {
        menuScreen.playOptions = true;
        menuScreen.loadingFailed = false;
        return true;
    }
}
