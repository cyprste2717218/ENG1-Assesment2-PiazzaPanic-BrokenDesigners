package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.ExitGameButton;
import com.github.brokendesigners.menu.Buttons.QuitButton;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ExitGameButtonTests {

    @Test
    public void testRenderedAtStart(){
        ExitGameButton exitGameButton = createExitGameButton();
        assertTrue(exitGameButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        ExitGameButton exitGameButton = createExitGameButton();
        Application mockApp = Mockito.mock(Application.class);
        Gdx.app = mockApp;
        exitGameButton.performTask();
        Mockito.verify(mockApp).exit();
    }

    private ExitGameButton createExitGameButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new ExitGameButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }
}
