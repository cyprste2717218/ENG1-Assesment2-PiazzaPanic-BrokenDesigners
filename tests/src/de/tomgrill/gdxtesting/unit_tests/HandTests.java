package de.tomgrill.gdxtesting.unit_tests;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Hand;
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

public class HandTests {
    @Test
    // testing to make sure pick_up returns false if the players hand is full
    public void testHandFull()  {
        Item Tomato = Mockito.mock(Item.class);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Hand fakeHand = new Hand();
        fakeHand.currentHandSize = 3;

        fakeHand.give(Tomato);
        fakeHand.give(Tomato);
        fakeHand.give(Tomato);
        fakeHand.give(Tomato);
        assertEquals(3, fakeHand.heldItems.size());
    }
    @Test
    public void testDropItem()  {
        Item Tomato = Mockito.mock(Item.class);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Hand fakeHand = new Hand();
        fakeHand.currentHandSize = 3;

        fakeHand.give(Tomato);

        assertEquals(Tomato, fakeHand.drop());
    }
    @Test
    public void testIsHandFull()  {
        Item Tomato = Mockito.mock(Item.class);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Hand fakeHand = new Hand();
        fakeHand.currentHandSize = 3;

        fakeHand.give(Tomato);
        fakeHand.give(Tomato);
        fakeHand.give(Tomato);


        assertTrue(fakeHand.isFull());
    }
    @Test
    public void testIsHandEmpty()  {
        Item Tomato = Mockito.mock(Item.class);

        Application fakeApp = Mockito.mock(Application.class);
        app = fakeApp;
        Hand fakeHand = new Hand();
        fakeHand.currentHandSize = 3;

        assertTrue(fakeHand.isEmpty());
    }

}
