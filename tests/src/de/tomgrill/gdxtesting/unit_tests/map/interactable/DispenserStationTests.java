package de.tomgrill.gdxtesting.unit_tests.map.interactable;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CookingStation;
import com.github.brokendesigners.map.interactable.CuttingStation;
import com.github.brokendesigners.map.interactable.DispenserStation;
import com.github.brokendesigners.map.interactable.Station;
import org.junit.Test;
import org.mockito.Mockito;

import static com.badlogic.gdx.Gdx.app;
import static org.junit.Assert.*;

public class DispenserStationTests {
    @Test
    // Testing to see if station dispenses correct item
    public void testCorrectItemGiven()  {
        Item Tomato = Mockito.mock(Item.class);
        ItemRegister.addItem("Tomato", Tomato);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Player fakePlayer = new Player(new Vector2(5,5));
        Sound fakeSound = Mockito.mock(Sound.class);
        DispenserStation fakeTomatoDispenser = new DispenserStation(Tomato);
        fakeTomatoDispenser.locked = false;
        fakeTomatoDispenser.pick_up = fakeSound;
        fakeTomatoDispenser.failure = fakeSound;
        fakeTomatoDispenser.pickUp(fakePlayer);
        assertEquals(Tomato, fakePlayer.getTopOfHand());

    }
    @Test
    // testing to make sure pick_up returns false if the players hand is full
    public void testHandFull()  {
        Item Tomato = Mockito.mock(Item.class);
        ItemRegister.addItem("Tomato", Tomato);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.hand.give(Tomato);
        fakePlayer.hand.give(Tomato);
        fakePlayer.hand.give(Tomato);
        Sound fakeSound = Mockito.mock(Sound.class);
        DispenserStation fakeTomatoDispenser = new DispenserStation(Tomato);
        fakeTomatoDispenser.locked = false;
        fakeTomatoDispenser.pick_up = fakeSound;
        fakeTomatoDispenser.failure = fakeSound;
        assertFalse(fakeTomatoDispenser.pickUp(fakePlayer));

    }
}
