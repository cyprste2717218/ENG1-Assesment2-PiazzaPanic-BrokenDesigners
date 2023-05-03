package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;
/**
 * Created class

 The ScenarioModeButton class extends the Button class and represents a button for selecting scenario mode in the main menu.
 */
public class ScenarioModeButton extends Button{
    /**
     * Constructor for creating a new ScenarioModeButton object with the specified parameters.
     *
     * @param rectangle         the rectangular shape of the button
     * @param selectedTexture   the texture of the button when it is selected
     * @param unselectedTexture the texture of the button when it is unselected
     * @param menuScreen        the menu screen on which the button is displayed
     */
    public ScenarioModeButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        setRendered(false);
    }

    /**
     * Method to perform the task associated with this button.
     * This method sets the appropriate menu screen flags for selecting scenario mode.
     *
     * @return true if the task was successfully performed, false otherwise
     */
    @Override
    public boolean performTask() {
        menuScreen.playOptions = false;
        menuScreen.isEndless = false;
        menuScreen.isDifficultyScreen = true;
        return true;
    }
}
