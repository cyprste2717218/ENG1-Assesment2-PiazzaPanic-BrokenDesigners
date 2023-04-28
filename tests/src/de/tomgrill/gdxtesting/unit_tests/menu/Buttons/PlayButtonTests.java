package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.PlayButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PlayButtonTests {

    @Test
    public void testRenderedAtStart(){
        PlayButton playButton = createPlayButton();
        assertTrue(playButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        PlayButton playButton = createPlayButton();
        MenuScreen menuScreen = playButton.getMenuScreen();
        menuScreen.playOptions = false;
        menuScreen.loadingFailed = true;
        playButton.performTask();
        assertTrue(menuScreen.playOptions);
        assertFalse(menuScreen.loadingFailed);
    }

    private PlayButton createPlayButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new PlayButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }
}
