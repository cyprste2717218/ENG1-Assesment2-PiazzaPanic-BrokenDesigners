package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.Buttons.LoadButton;
import com.github.brokendesigners.menu.Buttons.ResumeButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class LoadButtonTests {

    @Test
    public void testRenderedAtStart(){
        LoadButton loadButton = createLoadButton(ButtonTestUtils.createMenuScreen());
        assertTrue(loadButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        OrthographicCamera cam = new OrthographicCamera();
        PiazzaPanic panic = new PiazzaPanic();
        MenuScreen menuScreen = new MenuScreen(cam, panic, true);
        panic.setMenu(menuScreen);
        panic.startGame();
        LoadButton loadButton = createLoadButton(menuScreen);

        menuScreen.isLoading = false;
        menuScreen.tryActivateGame = false;
        loadButton.performTask();
        assertNull(panic.getGame());
        assertTrue(menuScreen.isLoading);
        assertTrue(menuScreen.tryActivateGame);
    }

    private LoadButton createLoadButton(MenuScreen menuScreen){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new LoadButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, menuScreen);
    }
}


