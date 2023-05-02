package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;
/**

 A button that triggers loading the saved game state.
 */
public class LoadButton extends Button{
    /**
     * Constructs a new LoadButton object.
     *
     * @param rectangle The rectangle defining the bounds of the button.
     * @param selectedTexture The texture to use when the button is selected.
     * @param unselectedTexture The texture to use when the button is unselected.
     * @param menuScreen The menu screen object that this button belongs to.
     */
    public LoadButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }
    /**
     * Loads the saved game state when this button is pressed.
     *
     * @return true, indicating that the button action was performed successfully.
     */
    @Override
    public boolean performTask() {
        menuScreen.panic.setGameNull();
        menuScreen.isLoading = true;
        menuScreen.tryActivateGame=true;
        return true;
    }
}
