package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.*;
import com.github.brokendesigners.map.powerups.PowerUpManager;
import com.github.brokendesigners.map.powerups.PrepTimePowerUp;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PrepTimePowerUpTests {

    @Test
    public void testActivateAndDeactivate(){
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        Kitchen kitchen = KitchenTestUtils.createMockedKitchen(match);
        createUnMockedStations(kitchen);
        PrepTimePowerUp prepTimePowerUp = createPrepTimePowerUp(match, kitchen);

        assertEquals(10f, kitchen.getAssembly().get(0).getStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getCookings().get(0).getStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getBakings().get(0).getStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getCuttings().get(0).getStationUseTime(), 0.0);
        assertEquals(5f, kitchen.getCounters().get(0).getStationUseTime(), 0.0);
        prepTimePowerUp.activate();
        assertEquals(5f, kitchen.getAssembly().get(0).getStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getCookings().get(0).getStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getBakings().get(0).getStationUseTime(), 0.0);
        assertEquals(1f, kitchen.getCuttings().get(0).getStationUseTime(), 0.0);
        assertEquals(2.5f, kitchen.getCounters().get(0).getStationUseTime(), 0.01);
        prepTimePowerUp.deactivate();
        assertEquals(10f, kitchen.getAssembly().get(0).getStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getCookings().get(0).getStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getBakings().get(0).getStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getCuttings().get(0).getStationUseTime(), 0.0);
        assertEquals(5f, kitchen.getCounters().get(0).getStationUseTime(), 0.0);
    }


    private void createUnMockedStations(Kitchen kitchen){
        kitchen.getKitchenStations().clear();
        kitchen.getAssembly().clear();
        kitchen.getCookings().clear();
        kitchen.getBakings().clear();
        kitchen.getCuttings().clear();
        kitchen.getCounters().clear();


        AssemblyStation assemblyStation = new AssemblyStation();
        kitchen.getKitchenStations().add(assemblyStation);
        kitchen.getAssembly().add(assemblyStation);

        CookingStation cookingStation = new CookingStation();
        kitchen.getKitchenStations().add(cookingStation);
        kitchen.getCookings().add(cookingStation);

        CuttingStation cuttingStation = new CuttingStation();
        kitchen.getKitchenStations().add(cuttingStation);
        kitchen.getCuttings().add(cuttingStation);

        CounterStation counterStation = new CounterStation();
        kitchen.getKitchenStations().add(counterStation);
        kitchen.getCounters().add(counterStation);

        BakingStation bakingStation = new BakingStation();
        kitchen.getKitchenStations().add(bakingStation);
        kitchen.getBakings().add(bakingStation);
    }

    private PrepTimePowerUp createPrepTimePowerUp(Match match, Kitchen kitchen){
        Player player = new Player(new Vector2(0,0));
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        PowerUpManager powerUpManager = new PowerUpManager(player, match, customerManager, kitchen);
        return new PrepTimePowerUp(new Vector2(0,0), player, match, powerUpManager, true, kitchen);
    }
}
