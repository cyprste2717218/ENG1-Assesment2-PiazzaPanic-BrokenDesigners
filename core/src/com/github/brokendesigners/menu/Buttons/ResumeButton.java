package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

/**
 * Created class

 A button to resume the game from a paused state.
 */
public class ResumeButton extends Button{
    /**

     Constructs a new ResumeButton with the specified rectangle bounds and textures.
     @param rectangle The rectangle bounds of the button.
     @param selectedTexture The texture to use when the button is selected.
     @param unselectedTexture The texture to use when the button is not selected.
     @param menuScreen The MenuScreen instance that this button belongs to.
     */
    public ResumeButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }
    /**

     Resumes the game and sets the active and gameSaved fields of the MenuScreen instance to false.
     @return true, indicating that the task was performed successfully.
     */
    @Override
    public boolean performTask() {
        menuScreen.active = false;
        menuScreen.gameSaved = false;
        menuScreen.unpauseGame();
        return true;
    }
}
