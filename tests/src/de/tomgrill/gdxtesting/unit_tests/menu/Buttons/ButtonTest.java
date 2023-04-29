package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.Buttons.Button;
import com.github.brokendesigners.menu.MenuScreen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(GdxTestRunner.class)
public class ButtonTest {
    private Button button;
    private Rectangle rectangle;
    private Texture selectedTexture, unselectedTexture;
    private MenuScreen menuScreen;
    private OrthographicCamera camera;

    @Before
    public void setUp() {
        rectangle = new Rectangle(0, 0, 100, 50);
        selectedTexture = mock(Texture.class);
        unselectedTexture = mock(Texture.class);
        menuScreen = mock(MenuScreen.class);
        Mockito.when(menuScreen.getMenuButtons()).thenReturn(new ArrayList<Button>());
        camera = mock(OrthographicCamera.class);
        when(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)))
                .thenReturn(new Vector3(rectangle.getX(), rectangle.getY(), 0));
        button = new Button(rectangle, selectedTexture, unselectedTexture, menuScreen) {
            @Override
            public boolean performTask() {
                return true;
            }
        };
    }

    @Test
    public void testIsRendered() {
        assertTrue(button.isRendered());
        button.setRendered(false);
        assertFalse(button.isRendered());
    }

    @Test
    public void testIsHovered() {
        assertTrue(button.isHovered(camera));
        button.setRendered(false);
        assertFalse(button.isHovered(camera));
    }

    @Test
    public void testIsSelected() {
        assertFalse(button.isSelected());
        button.setSelected(true);
        assertTrue(button.isSelected());
    }

    @Test
    public void testRender() {
        SpriteBatch batch = mock(SpriteBatch.class);
        button.setSelected(false);
        button.render(batch, camera);
        assertTrue(button.isRendered());
        button.setSelected(true);
        button.render(batch, camera);
        assertTrue(button.isRendered());
        button.setRendered(false);
        button.render(batch, camera);
        assertFalse(button.isRendered());
    }
}
