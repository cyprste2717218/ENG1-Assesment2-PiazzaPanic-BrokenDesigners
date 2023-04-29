package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.ScenarioCustomerCountButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ScenarioCustomerCountButtonTests {
    
    @Test
    public void testRenderedAtStart(){
        ScenarioCustomerCountButton scenarioCustomerCountButton = createScenarioCustomerCountButton(1);
        assertTrue(scenarioCustomerCountButton.isRendered());
        scenarioCustomerCountButton = createScenarioCustomerCountButton(-1);
        assertTrue(scenarioCustomerCountButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        ScenarioCustomerCountButton scenarioCustomerCountButton = createScenarioCustomerCountButton(1);
        MenuScreen menuScreen = scenarioCustomerCountButton.getMenuScreen();
        menuScreen.customerCount = 1;
        scenarioCustomerCountButton.performTask();
        assertEquals(menuScreen.customerCount, 2);
        scenarioCustomerCountButton.performTask();
        assertEquals(menuScreen.customerCount, 3);

        scenarioCustomerCountButton = createScenarioCustomerCountButton(-1);
        menuScreen = scenarioCustomerCountButton.getMenuScreen();
        menuScreen.customerCount = 3;
        scenarioCustomerCountButton.performTask();
        assertEquals(menuScreen.customerCount, 2);
        scenarioCustomerCountButton.performTask();
        assertEquals(menuScreen.customerCount, 1);
        scenarioCustomerCountButton.performTask();
        assertEquals(menuScreen.customerCount, 1);
    }

    private ScenarioCustomerCountButton createScenarioCustomerCountButton(int customerCountModifier){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new ScenarioCustomerCountButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen(), customerCountModifier);
    }
}
