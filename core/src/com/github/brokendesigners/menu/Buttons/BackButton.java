package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;
/**

 A class that represents a back button in the menu screen.
 Extends the abstract Button class.
 */
public class BackButton extends Button{

    boolean howToPlay;
    /**
     * Constructs a BackButton object.
     * @param rectangle the rectangle representing the button's dimensions.
     * @param selectedTexture the texture representing the button when selected.
     * @param unselectedTexture the texture representing the button when unselected.
     * @param menuScreen the MenuScreen object this button belongs to.
     */

    public BackButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }
    /**
     * Defines the action to be performed when the button is activated.
     * Sets various boolean values in the menu screen to false and resets the selected button to the first button.
     * @return true
     */
    @Override
    public boolean performTask() {
        System.out.println("Returning to main menu");
        menuScreen.howToScreen = false;
        menuScreen.playOptions = false;
        menuScreen.isDifficultyScreen = false;
        menuScreen.selectedButton = 0;
        return true;
    }
}
