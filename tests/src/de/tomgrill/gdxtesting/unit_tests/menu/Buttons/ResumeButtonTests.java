package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.Buttons.EndlessModeButton;
import com.github.brokendesigners.menu.Buttons.ResumeButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ResumeButtonTests {

    @Test
    public void testRenderedAtStart(){
        ResumeButton resumeButton = createResumeButton(ButtonTestUtils.createMenuScreen());
        assertTrue(resumeButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        OrthographicCamera cam = new OrthographicCamera();
        PiazzaPanic panic = new PiazzaPanic();
        MenuScreen menuScreen = Mockito.mock(MenuScreen.class, Mockito.withSettings().useConstructor(cam, panic, true));
        ResumeButton resumeButton = createResumeButton(menuScreen);
        menuScreen.active = true;
        menuScreen.gameSaved = true;
        resumeButton.performTask();
        assertFalse(menuScreen.active);
        assertFalse(menuScreen.gameSaved);
        Mockito.verify(menuScreen).unpauseGame();
    }

    private ResumeButton createResumeButton(MenuScreen menuScreen){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new ResumeButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, menuScreen);
    }
}
