package de.tomgrill.gdxtesting.unit_tests.map.interactable;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.BakingStation;
import com.github.brokendesigners.map.interactable.Station;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.PlayerRenderer;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.badlogic.gdx.Gdx.app;
import static org.junit.Assert.*;


public class BakingStationTests {
    @Test
    public void testIncorrectItem() {
        Item Burger = Mockito.mock(Item.class);
        ItemRegister.addItem("Burger",Burger);
        Burger.Baking = false;
        Player fakePlayer = new Player(new Vector2(5,5));

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        BakingStation fakeBaking = new BakingStation(null);
        fakeBaking.hand = Burger;
        fakeBaking.locked = false;
        fakeBaking.failure = fakeSound;
        assertFalse(fakeBaking.action(fakePlayer));
    }
    @Test
    public void testCorrectItem_Pizza()   {
        Item Pizza = Mockito.mock(Item.class);
        Pizza.name = "Pizza";
        ItemRegister.addItem("Pizza", Pizza);
        Pizza.Baking = false;

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        BakingStation fakeBaking = new BakingStation(null);
        fakeBaking.locked = false;
        fakeBaking.failure = fakeSound;
        assertTrue(fakeBaking.Applicable(fakeBaking.getBakeables(), "Baking_Station", "Pizza"));
    }
    @Test
    public void testCorrectItem_JacketPotato()   {
        Item JacketPotato = Mockito.mock(Item.class);
        JacketPotato.name = "JacketPotato";
        ItemRegister.addItem("JacketPotato", JacketPotato);
        JacketPotato.Baking = false;

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        BakingStation fakeBaking = new BakingStation(null);
        fakeBaking.locked = false;
        fakeBaking.failure = fakeSound;
        assertTrue(fakeBaking.Applicable(fakeBaking.getBakeables(), "Baking_Station", "JacketPotato"));
    }
    @Test
    // Testing the burn function if the customer fails to remove their item from the station
    public void testBurntItem()    {
        Item Pizza = Mockito.mock(Item.class);
        ItemRegister.addItem("Pizza", Pizza);
        Pizza.Baking = false;
        Item Waste = Mockito.mock(Item.class);
        Waste.name = "Waste";
        ItemRegister.addItem("Waste", Waste);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        Bubble fakeBubble = Mockito.mock(Bubble.class);

        BakingStation fakeBaking = new BakingStation(fakeBubble);
        fakeBaking.hand = Pizza;
        fakeBaking.locked = false;
        fakeBaking.failure = fakeSound;
        fakeBaking.burnFood.run();
        assertEquals(ItemRegister.itemRegister.get("Waste"),fakeBaking.hand);
    }

}
