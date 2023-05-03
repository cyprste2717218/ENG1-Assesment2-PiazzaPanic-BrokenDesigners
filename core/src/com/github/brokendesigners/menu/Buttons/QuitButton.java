package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.MenuScreen;
/**
 * Created class
 *
 * A button used to quit the game and return to the main menu.
 */
public class QuitButton extends Button{
    /**

     Represents a button that returns the user to the main menu when clicked.
     @param rectangle the rectangular bounds of the button
     @param selectedTexture the texture to be displayed when the button is selected
     @param unselectedTexture the texture to be displayed when the button is unselected
     @param menuScreen the menu screen instance to which the button belongs
     */
    public QuitButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }
    /**

     Returns the user to the main menu and resets certain flags in the menu screen.
     @return true always
     */
    @Override
    public boolean performTask() {
        System.out.println("Returning to main menu");
        menuScreen.setGameNull();
        menuScreen.gameSaved = false;
        menuScreen.complete = false;
        menuScreen.cont = false;
        return true;
    }
}
