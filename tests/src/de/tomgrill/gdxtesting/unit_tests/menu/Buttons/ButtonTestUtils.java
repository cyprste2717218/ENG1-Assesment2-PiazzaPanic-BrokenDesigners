package de.tomgrill.gdxtesting.unit_tests.menu.Buttons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.menu.MenuScreen;

public class ButtonTestUtils {

    public static MenuScreen createMenuScreen(){
        OrthographicCamera cam = new OrthographicCamera();
        PiazzaPanic panic = new PiazzaPanic();
        MenuScreen menuScreen = new MenuScreen(cam, panic, true);
        panic.setMenu(menuScreen);
        return menuScreen;
    }

    public static Rectangle createRectangle(){
        return new Rectangle(0,0,200,100);
    }
}
