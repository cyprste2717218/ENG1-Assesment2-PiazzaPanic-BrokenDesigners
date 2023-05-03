package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;
/**
 * Created class

 The ExitGameButton class represents a button that exits the game when clicked.
 */
public class ExitGameButton extends Button{
    /**
     * Constructs an ExitGameButton with the given parameters.
     *
     * @param rectangle the rectangle bounds of the button
     * @param selectedTexture the texture to display when the button is selected
     * @param unselectedTexture the texture to display when the button is unselected
     * @param menuScreen the MenuScreen that the button belongs to
     */
    public ExitGameButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
    }
    /**
     * Exits the game when the button is clicked.
     *
     * @return true, indicating that the button task has been performed
     */
    @Override
    public boolean performTask() {
        System.out.println("Exiting Game");
        Gdx.app.exit();
        return true;
    }
}
