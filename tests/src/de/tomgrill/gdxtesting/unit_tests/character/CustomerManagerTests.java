package de.tomgrill.gdxtesting.unit_tests.character;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CustomerManagerTests {

    //Test customer spawning - need proper station testing implementation first
    //Test timer - need proper station testing implementation first

    @Test
    public void testGetMeal(){
        CustomerManager customerManager = createCustomerManager();
        for(int i = 0; i < 1000; i++){
            String foodName = customerManager.getMeal();
            assertTrue(foodName == "Salad" || foodName == "Burger" || foodName == "VoidItem");
        }
    }

    @Test
    public void testIsComplete(){
        CustomerManager customerManager = createCustomerManager();
        Match match = customerManager.getMatch();
        //Scenario
        match.setGameMode(GameMode.SCENARIO);
        for(int i = 0; i < customerManager.getCustomerNumber(); i++){
            assertFalse(customerManager.isComplete());
            match.incrementCustomersSoFar();
            System.out.println(match.getCustomersSoFar());
        }
        assertTrue(customerManager.isComplete());
        match.setReputationPoints(-5);
        assertTrue(customerManager.isComplete());
        match.setReputationPoints(5);
        assertTrue(customerManager.isComplete());

        //Endless
        match.setGameMode(GameMode.ENDLESS);
        match.setReputationPoints(5);
        assertFalse(customerManager.isComplete());
        match.setReputationPoints(-5);
        assertTrue(customerManager.isComplete());
    }

    @Test
    public void testTimeToString(){
        CustomerManager customerManager = createCustomerManager();
        assertEquals(customerManager.timeToString(0),"0:00");
        assertEquals(customerManager.timeToString(60),"1:00");
        assertEquals(customerManager.timeToString(30),"0:30");
        assertEquals(customerManager.timeToString(180),"3:00");
        assertEquals(customerManager.timeToString(194),"3:14");
        assertEquals(customerManager.timeToString(2),"0:02");
        assertEquals(customerManager.timeToString(3601),"60:01");
        assertEquals(customerManager.timeToString(-1),"");
    }



    private CustomerManager createCustomerManager(){
        return new CustomerManager(5, new Vector2(0,0), new Match(GameMode.SCENARIO, DifficultyLevel.MEDIUM, 5));
    }


}
