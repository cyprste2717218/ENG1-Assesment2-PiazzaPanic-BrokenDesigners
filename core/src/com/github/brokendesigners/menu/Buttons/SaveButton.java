package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.menu.MenuScreen;
/**
 * Created class

 A button that saves the game when clicked.
 */
public class SaveButton extends Button{
    /**
     * Constructor for the SaveButton class.
     *
     * @param rectangle         The rectangular bounds of the button.
     * @param selectedTexture   The texture to be used when the button is selected.
     * @param unselectedTexture The texture to be used when the button is unselected.
     * @param menuScreen        The MenuScreen instance that the button belongs to.
     */
    public SaveButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }
    /**
     * Saves the game when the button is clicked and updates the gameSaved flag.
     *
     * @return True, indicating that the task has been performed.
     */
    @Override
    public boolean performTask() {
        if(MainGame.saveGame.save()){
            menuScreen.gameSaved = true;
        }
        return true;
    }
}
