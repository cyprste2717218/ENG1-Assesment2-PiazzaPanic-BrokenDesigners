package de.tomgrill.gdxtesting.unit_tests;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CookingStation;
import com.github.brokendesigners.map.interactable.CuttingStation;
import com.github.brokendesigners.map.interactable.DispenserStation;
import com.github.brokendesigners.map.interactable.Station;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.app;
import static org.junit.Assert.*;

public class PlayerTests {
    @Test
    // The function, pickUp, is a boolean and returns true if it works, so we can assertTrue to test
    public void testPickUp() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.setSelected(true);
        ArrayList<Station> fakeStationList = new ArrayList<>();
        CuttingStation fakeCutting = new CuttingStation();
        fakeStationList.add(fakeCutting);
        Rectangle fakeRectangle = new Rectangle(10,10,10,10);
        fakePlayer.setPlayerRectangle(fakeRectangle);
        fakeCutting.setInteractionArea(fakeRectangle);

        assertTrue(fakePlayer.pickUp(fakeStationList));

    }
    @Test
    // Exact same code as testing pickUp, as it's simply the opposite
    public void testDropOff()   {
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.setSelected(true);
        ArrayList<Station> fakeStationList = new ArrayList<>();
        CuttingStation fakeCutting = new CuttingStation();
        fakeStationList.add(fakeCutting);
        Rectangle fakeRectangle = new Rectangle(10,10,10,10);
        fakePlayer.setPlayerRectangle(fakeRectangle);
        fakeCutting.setInteractionArea(fakeRectangle);

        assertTrue(fakePlayer.dropOff(fakeStationList));

    }
    @Test
    public void testGetInteractingStation() {
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.setSelected(true);
        ArrayList<Station> fakeStationList = new ArrayList<>();
        CuttingStation fakeCutting = new CuttingStation();
        fakeStationList.add(fakeCutting);
        Rectangle fakeRectangle = new Rectangle(10,10,10,10);
        fakePlayer.setPlayerRectangle(fakeRectangle);
        fakeCutting.setInteractionArea(fakeRectangle);

        assertEquals(fakeCutting, fakePlayer.getInteractingStation(fakeStationList));
    }
    @Test
    public void testLockPlayer()    {
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.setSelected(true);
        fakePlayer.locked = false;
        fakePlayer.moving_disabled = false;

        fakePlayer.lockPlayer();

        assertTrue(fakePlayer.isLocked());
        assertTrue(fakePlayer.moving_disabled);
    }

    @Test
    public void testUnlockPlayer()    {
        Match match = new Match(GameMode.SCENARIO,3, 100, 0, 0, DifficultyLevel.EASY,1);
        match.hasMoneyPower = false;
        Sound fakeSound = Mockito.mock(Sound.class);
        Player fakePlayer = new Player(new Vector2(5,5));
        fakePlayer.setSelected(true);
        fakePlayer.locked = true;
        fakePlayer.moving_disabled = true;
        fakePlayer.unlockFX = fakeSound;
        fakePlayer.setMatch(match);

        fakePlayer.unlockPlayer();

        assertFalse(fakePlayer.isLocked());
        assertFalse(fakePlayer.moving_disabled);
    }

}
