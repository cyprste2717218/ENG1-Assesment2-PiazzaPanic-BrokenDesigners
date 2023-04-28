package de.tomgrill.gdxtesting.unit_tests;

import com.github.brokendesigners.Match;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class DifficultyTests {

    Kitchen kitchen;
    Match match;

    @Test
    public void checkEasyAdjustedTimes(){
        match.setDifficultyLevel(DifficultyLevel.EASY);
        assertEquals(5f, kitchen.getAssembly().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getCookings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getBakings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(1f, kitchen.getCuttings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(2.5f, kitchen.getCounters().get(0).getAdjustedStationUseTime(), 0.01f);
    }

    @Test
    public void checkMediumAdjustedTimes(){
        match.setDifficultyLevel(DifficultyLevel.MEDIUM);
        assertEquals(10f, kitchen.getAssembly().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getCookings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getBakings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(2f, kitchen.getCuttings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(5f, kitchen.getCounters().get(0).getAdjustedStationUseTime(), 0.0);
    }

    @Test
    public void checkHardAdjustedTimes(){
        match.setDifficultyLevel(DifficultyLevel.HARD);
        assertEquals(20f, kitchen.getAssembly().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(8f, kitchen.getCookings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(8f, kitchen.getBakings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(4f, kitchen.getCuttings().get(0).getAdjustedStationUseTime(), 0.0);
        assertEquals(10f, kitchen.getCounters().get(0).getAdjustedStationUseTime(), 0.0);
    }

    @Before
    public void setUpEnvironment(){
        match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        kitchen = KitchenTestUtils.createMockedKitchen(match);
        createUnMockedStations(kitchen);
    }

    private void createUnMockedStations(Kitchen kitchen){
        kitchen.getKitchenStations().clear();
        kitchen.getAssembly().clear();
        kitchen.getCookings().clear();
        kitchen.getBakings().clear();
        kitchen.getCuttings().clear();
        kitchen.getCounters().clear();


        AssemblyStation assemblyStation = new AssemblyStation();
        assemblyStation.setMatch(match);
        kitchen.getKitchenStations().add(assemblyStation);
        kitchen.getAssembly().add(assemblyStation);

        CookingStation cookingStation = new CookingStation();
        cookingStation.setMatch(match);
        kitchen.getKitchenStations().add(cookingStation);
        kitchen.getCookings().add(cookingStation);

        CuttingStation cuttingStation = new CuttingStation();
        cuttingStation.setMatch(match);
        kitchen.getKitchenStations().add(cuttingStation);
        kitchen.getCuttings().add(cuttingStation);

        CounterStation counterStation = new CounterStation();
        counterStation.setMatch(match);
        kitchen.getKitchenStations().add(counterStation);
        kitchen.getCounters().add(counterStation);

        BakingStation bakingStation = new BakingStation();
        bakingStation.setMatch(match);
        kitchen.getKitchenStations().add(bakingStation);
        kitchen.getBakings().add(bakingStation);
    }

}
