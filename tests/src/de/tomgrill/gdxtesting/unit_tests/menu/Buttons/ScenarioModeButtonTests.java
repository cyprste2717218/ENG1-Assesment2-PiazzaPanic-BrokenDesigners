package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.ScenarioModeButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ScenarioModeButtonTests {

    @Test
    public void testRenderedAtStart(){
        ScenarioModeButton scenarioModeButton = createScenarioModeButton();
        assertFalse(scenarioModeButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        ScenarioModeButton scenarioModeButton = createScenarioModeButton();
        MenuScreen menuScreen = scenarioModeButton.getMenuScreen();
        menuScreen.playOptions = true;
        menuScreen.isDifficultyScreen = false;
        menuScreen.isEndless = true;
        scenarioModeButton.performTask();
        assertFalse(menuScreen.playOptions);
        assertTrue(menuScreen.isDifficultyScreen);
        assertFalse(menuScreen.isEndless);
    }

    private ScenarioModeButton createScenarioModeButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new ScenarioModeButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }
}
