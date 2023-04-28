package de.tomgrill.gdxtesting.unit_tests.map.interactable;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.BinStation;

import org.junit.Test;
import org.mockito.Mockito;

import static com.badlogic.gdx.Gdx.app;
import static org.junit.Assert.*;

public class BinStationTests {
    @Test
    public void testItemRemoval()   {
        Item Pizza = Mockito.mock(Item.class);
        Pizza.name = "Pizza";
        ItemRegister.addItem("Pizza", Pizza);
        Pizza.Baking = false;
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.hand.give(Pizza);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        BinStation fakeBin = new BinStation();
        fakeBin.put_down = fakeSound;
        assertTrue(fakeBin.dropOff(fakePlayer));
    }
    @Test
    public void testEmptyHandInteraction()   {
        Player fakePlayer = new Player(new Vector2(5,5));

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Sound fakeSound = Mockito.mock(Sound.class);
        BinStation fakeBin = new BinStation();
        fakeBin.put_down = fakeSound;
        assertFalse(fakeBin.dropOff(fakePlayer));
    }
}
