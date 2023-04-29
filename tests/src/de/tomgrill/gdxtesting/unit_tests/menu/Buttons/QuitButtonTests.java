package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.QuitButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class QuitButtonTests {

    @Test
    public void testUnrenderedAtStart(){
        QuitButton quitButton = createQuitButton();
        assertFalse(quitButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        QuitButton quitButton = createQuitButton();
        MenuScreen menuScreen = quitButton.getMenuScreen();
        menuScreen.gameSaved = true;
        menuScreen.complete = true;
        menuScreen.cont = true;
        menuScreen.panic.startGame();
        quitButton.performTask();
        assertNull(menuScreen.panic.getGame());
        assertFalse(menuScreen.gameSaved);
        assertFalse(menuScreen.complete);
        assertFalse(menuScreen.cont);

    }

    private QuitButton createQuitButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new QuitButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }

}
