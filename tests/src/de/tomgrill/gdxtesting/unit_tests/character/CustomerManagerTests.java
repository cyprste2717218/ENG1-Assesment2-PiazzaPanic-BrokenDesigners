
package de.tomgrill.gdxtesting.unit_tests.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CustomerManagerTests {

    //Test customer spawning - need proper station testing implementation first
    //Test timer - need proper station testing implementation first

    @Test
    public void testGetMeal(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        for(int i = 0; i < 1000; i++){
            String foodName = customerManager.getMeal();
            assertTrue(foodName == "Salad" || foodName == "Burger" || foodName == "Baked_Pizza" || foodName == "Baked_JacketPotato" || foodName == "VoidItem");
        }
    }

    @Test
    public void testScenarioComplete(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        Match match = customerManager.getMatch();
        match.setGameMode(GameMode.SCENARIO);

        assertFalse(customerManager.isComplete());
        match.setReputationPoints(-5);
        assertFalse(customerManager.isComplete());
        match.setReputationPoints(5);
        assertFalse(customerManager.isComplete());

        for(int i = 0; i < customerManager.getCustomerNumber(); i++){
            assertFalse(customerManager.isComplete());
            match.incrementCustomersSoFar();
            System.out.println(match.getCustomersSoFar());
        }
        assertTrue(customerManager.isComplete());
    }

    @Test
    public void testEndlessComplete(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        Match match = customerManager.getMatch();
        match.setGameMode(GameMode.ENDLESS);
        match.setReputationPoints(5);
        assertFalse(customerManager.isComplete());
        match.setReputationPoints(0);
        assertTrue(customerManager.isComplete());
        match.setReputationPoints(1);
        assertFalse(customerManager.isComplete());

        for(int i = 0; i < customerManager.getCustomerNumber(); i++){
            match.incrementCustomersSoFar();
        }
        assertFalse(customerManager.isComplete());
    }

    @Test
    public void testTimeToString(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        assertEquals(customerManager.timeToString(0),"0:00");
        assertEquals(customerManager.timeToString(60),"1:00");
        assertEquals(customerManager.timeToString(30),"0:30");
        assertEquals(customerManager.timeToString(180),"3:00");
        assertEquals(customerManager.timeToString(194),"3:14");
        assertEquals(customerManager.timeToString(2),"0:02");
        assertEquals(customerManager.timeToString(3601),"60:01");
        assertEquals(customerManager.timeToString(-1),"");
    }

    @Test
    public void testEnd(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        customerManager.getCustomers().add(CustomerTestingUtils.createTestCustomer());
        customerManager.getCustomers().add(CustomerTestingUtils.createTestCustomer());
        customerManager.getCustomers().add(CustomerTestingUtils.createTestCustomer());
        assertEquals(customerManager.getCustomers().size(), 3);
        customerManager.end();
        assertEquals(customerManager.getCustomers().size(), 0);
    }

    @Test
    public void testSpawnCustomers(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        SpriteBatch hud_batch = Mockito.mock(SpriteBatch.class);
        customerManager.update(hud_batch);

        assertEquals(customerManager.getCustomers().size(),0);
        customerManager.getCustomerStations().add(CustomerTestingUtils.createTestCustomerStation());
        customerManager.setSpawningTime(TimeUtils.millis() - 11000L);
        customerManager.update(hud_batch);
        assertEquals(1, customerManager.getCustomers().size());
        customerManager.getCustomerStations().clear();
        customerManager.getCustomers().clear();
        for(int i = 0; i < 4; i++){
            customerManager.getCustomerStations().add(CustomerTestingUtils.createTestCustomerStation());
        }
        customerManager.setSpawningTime(TimeUtils.millis() - 11000L);
        customerManager.update(hud_batch);
        assertTrue(customerManager.getCustomers().size() > 0);
    }

    @Test
    public void testHandleCustomerPhases(){
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager();
        SpriteBatch hud_batch = Mockito.mock(SpriteBatch.class);
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.setPhase(CustomerPhase.DESPAWNING);
        customerManager.getCustomers().add(customer);

        int currentReputationPoints = customer.getMatch().getReputationPoints();
        int currentCustomersServed = customer.getMatch().getCustomersServed();
        customer.setBeenServed(true);
        customerManager.update(hud_batch);
        assertEquals(currentCustomersServed + 1, customerManager.getMatch().getCustomersServed());
        assertSame(CustomerPhase.UNLOADING, customer.getPhase());
        assertFalse(customer.isVisible());
        assertFalse(customer.getStation().isServingCustomer());

        customer.setPhase(CustomerPhase.DESPAWNING);
        customer.setBeenServed(false);
        customerManager.update(hud_batch);
        assertEquals(currentReputationPoints - 1, customerManager.getMatch().getReputationPoints());
        assertSame(CustomerPhase.UNLOADING, customer.getPhase());
        assertFalse(customer.isVisible());
        assertFalse(customer.getStation().isServingCustomer());

        Customer customer1 = Mockito.mock(Customer.class);
        Mockito.when(customer1.getPhase()).thenReturn(CustomerPhase.SPAWNING);
        Mockito.when(customer1.isVisible()).thenReturn(false);
        customerManager.getCustomers().clear();
        customerManager.getCustomers().add(customer1);
        customerManager.update(hud_batch);
        Mockito.verify(customer1).spawn();
    }
}
