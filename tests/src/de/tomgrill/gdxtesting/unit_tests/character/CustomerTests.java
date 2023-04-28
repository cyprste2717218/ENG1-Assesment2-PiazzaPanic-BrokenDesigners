package de.tomgrill.gdxtesting.unit_tests.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.enums.CustomerPhase;

import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CustomerStation;

import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class CustomerTests {


    //Check customer constructors work properly
    @Test
    public void testSpawn(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.setIsVisible(false);
        customer.setPhase(CustomerPhase.SPAWNING);
        customer.spawn();
        assertTrue(customer.isVisible());
        assertSame(customer.getPhase(), CustomerPhase.MOVING_TO_STATION);
    }

    @Test
    public void testMovingToStationPhase(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.MOVING_TO_STATION);
        while(customer.getWorldPosition().x != 10 || customer.getWorldPosition().y != 5){
            assertNotEquals(customer.getWorldPosition(), new Vector2(10, 5));
            customer.update();
        }
        assertEquals(10, customer.getWorldPosition().x, 0.0);
        assertEquals(5, customer.getWorldPosition().y, 0.0);
        customer.update();
        assertEquals(customer.getPhase(), CustomerPhase.WAITING);
    }

    @Test
    public void testWaitingPhase(){
        //Tests for item correctness will be done in the ServingStationTests class
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.WAITING);
        assertEquals(customer.getWaitingStartTime(), -1L);
        customer.update();
        assertEquals(customer.getWaitingStartTime(), TimeUtils.millis(), 10L);
        assertNotSame(customer.getPhase(), CustomerPhase.LEAVING);
        customer.setWaitingStartTime(customer.getCustomerWaitTime());
        customer.update();
        assertEquals(customer.getPhase(), CustomerPhase.LEAVING);
    }

    @Test
    public void testLeavingPhase(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.LEAVING);
        customer.setWorldPosition(new Vector2(10,5));
        while(customer.getWorldPosition().x != 2 || customer.getWorldPosition().y != 0){
            assertNotEquals(customer.getWorldPosition(), new Vector2(2, 0));
            customer.update();
        }
        assertEquals(2, customer.getWorldPosition().x, 0.0);
        assertEquals(0, customer.getWorldPosition().y, 0.0);
        customer.update();
        assertEquals(1, customer.getMatch().getCustomersSoFar());
        assertEquals(customer.getPhase(), CustomerPhase.DESPAWNING);
    }

    @Test
    public void testDespawnPhase(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.DESPAWNING);
        customer.update();
        assertFalse(customer.isVisible());
    }
}

