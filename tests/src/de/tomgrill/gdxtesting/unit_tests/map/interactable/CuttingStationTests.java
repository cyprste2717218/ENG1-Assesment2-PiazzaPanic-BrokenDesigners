package de.tomgrill.gdxtesting.unit_tests.map.interactable;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CookingStation;
import com.github.brokendesigners.map.interactable.CuttingStation;
import org.junit.Test;
import org.mockito.Mockito;

import static com.badlogic.gdx.Gdx.app;
import static org.junit.Assert.*;

public class CuttingStationTests {
    @Test
    public void testIncorrectItem() {
        Item Burger = Mockito.mock(Item.class);
        ItemRegister.addItem("Burger", Burger);
        Burger.Cooking = false;
        Player fakePlayer = new Player(new Vector2(5, 5));

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CuttingStation fakeCutting = new CuttingStation();
        fakeCutting.hand = Burger;
        fakeCutting.locked = false;
        fakeCutting.failure = fakeSound;
        assertFalse(fakeCutting.action(fakePlayer));
    }
    @Test
    public void testCorrectItem_Tomato() {
        Item Tomato = Mockito.mock(Item.class);
        ItemRegister.addItem("Tomato", Tomato);
        Tomato.Cooking = false;

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CuttingStation fakeCutting = new CuttingStation();
        fakeCutting.locked = false;
        fakeCutting.failure = fakeSound;
        assertTrue(fakeCutting.Applicable(fakeCutting.getCuttables(), "Cutting_Station", "Tomato"));
    }
    @Test
    public void testCorrectItem_Lettuce() {
        Item Lettuce = Mockito.mock(Item.class);
        ItemRegister.addItem("Lettuce", Lettuce);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CuttingStation fakeCutting = new CuttingStation();
        fakeCutting.locked = false;
        fakeCutting.failure = fakeSound;
        assertTrue(fakeCutting.Applicable(fakeCutting.getCuttables(), "Cutting_Station", "Lettuce"));
    }
    @Test
    public void testCorrectItem_Onion() {
        Item Onion = Mockito.mock(Item.class);
        ItemRegister.addItem("Onion", Onion);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CuttingStation fakeCutting = new CuttingStation();
        fakeCutting.locked = false;
        fakeCutting.failure = fakeSound;
        assertTrue(fakeCutting.Applicable(fakeCutting.getCuttables(), "Cutting_Station", "Onion"));
    }
    @Test
    public void testCorrectItem_Potato() {
        Item Potato = Mockito.mock(Item.class);
        ItemRegister.addItem("Potato", Potato);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CuttingStation fakeCutting = new CuttingStation();
        fakeCutting.locked = false;
        fakeCutting.failure = fakeSound;
        assertTrue(fakeCutting.Applicable(fakeCutting.getCuttables(), "Cutting_Station", "Potato"));
    }
    @Test
    public void testFailedOperation()   {
        Item Potato = Mockito.mock(Item.class);
        ItemRegister.addItem("Potato", Potato);
        Potato.name = "Potato";
        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Player fakePlayer = new Player(new Vector2(5,5));

        Sound fakeSound = Mockito.mock(Sound.class);
        CuttingStation fakeCutting = new CuttingStation();
        fakeCutting.locked = false;
        fakeCutting.failure = fakeSound;
        fakeCutting.finishFailedOperation(fakePlayer, 5);
        assertEquals(ItemRegister.itemRegister.get("Waste"),fakeCutting.hand);

    }
}
