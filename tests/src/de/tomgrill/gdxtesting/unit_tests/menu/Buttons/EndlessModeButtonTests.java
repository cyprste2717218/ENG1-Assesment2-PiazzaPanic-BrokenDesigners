package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.BackButton;
import com.github.brokendesigners.menu.Buttons.EndlessModeButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class EndlessModeButtonTests {

    @Test
    public void testRenderedAtStart(){
        EndlessModeButton endlessModeButton = createEndlessModeButton();
        assertFalse(endlessModeButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        EndlessModeButton endlessModeButton = createEndlessModeButton();
        MenuScreen menuScreen = endlessModeButton.getMenuScreen();
        menuScreen.playOptions = true;
        menuScreen.isEndless = false;
        menuScreen.isDifficultyScreen = false;
        endlessModeButton.performTask();
        assertFalse(menuScreen.playOptions);
        assertTrue(menuScreen.isEndless);
        assertTrue(menuScreen.isDifficultyScreen);
    }

    private EndlessModeButton createEndlessModeButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new EndlessModeButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }
}
