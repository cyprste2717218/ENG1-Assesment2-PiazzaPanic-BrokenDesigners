package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.Buttons.HowToPlayButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class HowToPlayButtonTests {
    
    @Test
    public void testRenderedAtStart(){
        HowToPlayButton howToPlayButton = createHowToPlayButton();
        assertTrue(howToPlayButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        HowToPlayButton howToPlayButton = createHowToPlayButton();
        MenuScreen menuScreen = howToPlayButton.getMenuScreen();
        menuScreen.howToScreen = false;
        howToPlayButton.performTask();
        assertTrue(menuScreen.howToScreen);
    }

    private HowToPlayButton createHowToPlayButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new HowToPlayButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }
}
