package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.menu.MenuScreen;

/**
 * Created class

 A button that modifies the number of customers in the scenario when clicked.
 */
public class ScenarioCustomerCountButton extends Button{

    int customerCountModifier;
    /**
     * Creates a new instance of ScenarioCustomerCountButton with the given parameters.
     * @param rectangle the rectangular bounds of the button.
     * @param selectedTexture the texture to display when the button is selected.
     * @param unselectedTexture the texture to display when the button is unselected.
     * @param menuScreen the MenuScreen associated with this button.
     * @param customerCountModifier the value by which to modify the number of customers.
     */
    public ScenarioCustomerCountButton(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen, int customerCountModifier) {
        super(rectangle, selectedTexture, unselectedTexture, menuScreen);
        this.customerCountModifier = customerCountModifier;
    }
    /**
     * Modifies the number of customers in the scenario by the customerCountModifier value when the button is clicked.
     * If the customer count is already at the minimum (1) and the customerCountModifier value is negative, nothing happens.
     * @return true if the customer count is modified, false otherwise.
     */
    @Override
    public boolean performTask() {
        if(menuScreen.customerCount > 1 || customerCountModifier > 0){
            menuScreen.customerCount += customerCountModifier;
            return true;
        }
        return false;
    }
}
