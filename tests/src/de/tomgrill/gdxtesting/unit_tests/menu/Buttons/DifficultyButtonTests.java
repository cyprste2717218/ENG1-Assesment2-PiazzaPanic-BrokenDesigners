package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.menu.Buttons.BackButton;
import com.github.brokendesigners.menu.Buttons.DifficultyButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class DifficultyButtonTests {

    @Test
    public void testRenderedAtStart(){
        DifficultyButton difficultyButton = createDifficultyButton(DifficultyLevel.MEDIUM);
        assertFalse(difficultyButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        DifficultyButton easyButton = createDifficultyButton(DifficultyLevel.EASY);
        DifficultyButton mediumButton = createDifficultyButton(DifficultyLevel.MEDIUM);
        DifficultyButton hardButton = createDifficultyButton(DifficultyLevel.HARD);

        MenuScreen menuScreen = easyButton.getMenuScreen();
        menuScreen.active = true;
        menuScreen.tryActivateGame = false;
        menuScreen.setDifficultyLevel(DifficultyLevel.HARD);

        easyButton.performTask();
        assertFalse(menuScreen.active);
        assertTrue(menuScreen.tryActivateGame);
        assertEquals(menuScreen.getDifficulty(), DifficultyLevel.EASY);

        menuScreen = mediumButton.getMenuScreen();
        menuScreen.active = true;
        menuScreen.tryActivateGame = false;
        menuScreen.setDifficultyLevel(DifficultyLevel.EASY);

        mediumButton.performTask();
        assertFalse(menuScreen.active);
        assertTrue(menuScreen.tryActivateGame);
        assertEquals(menuScreen.getDifficulty(), DifficultyLevel.MEDIUM);

        menuScreen = hardButton.getMenuScreen();
        menuScreen.active = true;
        menuScreen.tryActivateGame = false;
        menuScreen.setDifficultyLevel(DifficultyLevel.EASY);

        hardButton.performTask();
        assertFalse(menuScreen.active);
        assertTrue(menuScreen.tryActivateGame);
        assertEquals(menuScreen.getDifficulty(), DifficultyLevel.HARD);

    }


    public DifficultyButton createDifficultyButton(DifficultyLevel difficultyLevel){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new DifficultyButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, ButtonTestUtils.createMenuScreen(), difficultyLevel);
    }
}
