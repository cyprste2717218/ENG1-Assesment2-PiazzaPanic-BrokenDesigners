package de.tomgrill.gdxtesting.unit_tests.map.interactable;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;

import com.github.brokendesigners.map.interactable.CookingStation;
import org.junit.Test;
import org.mockito.Mockito;

import static com.badlogic.gdx.Gdx.app;
import static org.junit.Assert.*;

public class CookingStationTests {
    @Test
    public void testIncorrectItem() {
        Item Burger = Mockito.mock(Item.class);
        ItemRegister.addItem("Burger", Burger);
        Burger.Cooking = false;
        Player fakePlayer = new Player(new Vector2(5, 5));

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CookingStation fakeCooking = new CookingStation(null);
        fakeCooking.hand = Burger;
        fakeCooking.locked = false;
        fakeCooking.failure = fakeSound;
        assertFalse(fakeCooking.action(fakePlayer));
    }

    @Test
    public void testCorrectItem_Patty() {
        Item Patty = Mockito.mock(Item.class);
        ItemRegister.addItem("Patty", Patty);
        Patty.Cooking = false;

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CookingStation fakeCooking = new CookingStation(null);
        fakeCooking.locked = false;
        fakeCooking.failure = fakeSound;
        assertTrue(fakeCooking.Applicable(fakeCooking.getCookables(), "Cooking_Station", "Patty"));
    }
    @Test
    public void testCorrectItem_Bun() {
        Item Bun = Mockito.mock(Item.class);
        ItemRegister.addItem("Bun", Bun);
        Bun.Cooking = false;

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CookingStation fakeCooking = new CookingStation(null);
        fakeCooking.locked = false;
        fakeCooking.failure = fakeSound;
        assertTrue(fakeCooking.Applicable(fakeCooking.getCookables(), "Cooking_Station", "Bun"));
    }
    @Test
    public void testCorrectItem_Tomato() {
        Item Tomato = Mockito.mock(Item.class);
        ItemRegister.addItem("Tomato", Tomato);
        Tomato.Cooking = false;

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        CookingStation fakeCooking = new CookingStation(null);
        fakeCooking.locked = false;
        fakeCooking.failure = fakeSound;
        assertTrue(fakeCooking.Applicable(fakeCooking.getCookables(), "Cooking_Station", "Tomato"));
    }
    @Test
    // Testing the burn function if the customer fails to remove their item from the station
    public void testBurntItem()    {
        Item Patty = Mockito.mock(Item.class);
        ItemRegister.addItem("Patty", Patty);
        Patty.Baking = false;
        Item Waste = Mockito.mock(Item.class);
        Waste.name = "Waste";
        ItemRegister.addItem("Waste", Waste);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        Bubble fakeBubble = Mockito.mock(Bubble.class);

        CookingStation fakeCooking = new CookingStation(fakeBubble);
        fakeCooking.hand = Patty;
        fakeCooking.locked = false;
        fakeCooking.failure = fakeSound;
        fakeCooking.burnFood.run();
        assertEquals(ItemRegister.itemRegister.get("Waste"),fakeCooking.hand);
    }
}
