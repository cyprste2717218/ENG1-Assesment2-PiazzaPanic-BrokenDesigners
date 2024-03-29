package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.menu.Buttons.SaveButton;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class SaveButtonTests {

    @Test
    public void testRenderedAtStart(){
        SaveButton paveButton = createSaveButton(ButtonTestUtils.createMenuScreen());
        assertTrue(paveButton.isRendered());
    }

    @Test
    public void testPerformedTask(){
        OrthographicCamera cam = new OrthographicCamera();
        PiazzaPanic panic = new PiazzaPanic();
        SaveButton saveButton = createSaveButton(ButtonTestUtils.createMenuScreen());
        MenuScreen menuScreen = saveButton.getMenuScreen();
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 1);
        menuScreen.panic.setGame(new MainGame(cam, cam, match, KitchenTestUtils.createMockedKitchen(match)));
        menuScreen.gameSaved = false;
        menuScreen.panic.setMenu(menuScreen);
        saveButton.performTask();
        assertTrue(menuScreen.gameSaved);
    }

    private SaveButton createSaveButton(MenuScreen menuScreen){
        Texture selectedTexture = Mockito.mock(Texture.class);
        Texture unselectedTexture = Mockito.mock(Texture.class);
        return new SaveButton(ButtonTestUtils.createRectangle(), selectedTexture, unselectedTexture, menuScreen);
    }
}
