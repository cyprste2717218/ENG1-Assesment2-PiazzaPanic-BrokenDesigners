package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.menu.Buttons.BackButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class BackButtonTests {

    @Test
    public void testRenderedAtStart(){
        BackButton backButton = createBackButton();
        assertFalse(backButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        BackButton backButton = createBackButton();
        MenuScreen menuScreen = backButton.getMenuScreen();
        menuScreen.howToScreen = true;
        menuScreen.playOptions = true;
        menuScreen.isDifficultyScreen = true;
        menuScreen.selectedButton = 1;
        backButton.performTask();
        assertFalse(menuScreen.howToScreen);
        assertFalse(menuScreen.playOptions);
        assertFalse(menuScreen.isDifficultyScreen);
        assertEquals(menuScreen.selectedButton, 0);
    }


    public BackButton createBackButton(){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new BackButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen());
    }
}
