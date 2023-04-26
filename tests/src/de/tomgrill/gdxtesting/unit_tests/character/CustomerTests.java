package de.tomgrill.gdxtesting.unit_tests.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.Match;
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
import org.mockito.Mockito;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CustomerTests {


    //Check customer constructors work properly
    @Test
    public void testCustomerSpawn(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        assertTrue(customer.spawn());
        assertTrue(customer.isVisible());
        assertEquals(customer.getPhase(), CustomerPhase.MOVING_TO_STATION);
    }

    @Test
    public void testMovingToStationPhase(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.MOVING_TO_STATION);
        while(customer.getWorldPosition().x != 10 || customer.getWorldPosition().y != 5){
            assertFalse(Objects.equals(customer.getWorldPosition(), new Vector2(10, 5)));
            customer.update();
        }
        assertTrue(customer.getWorldPosition().x == 10);
        assertTrue(customer.getWorldPosition().y == 5);
        customer.update();
        assertEquals(customer.getPhase(), CustomerPhase.WAITING);
    }

    @Test
    public void testWaitingPhase(){
        //Tests for item correctness will be done in the ServingStationTests class
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.WAITING);
        assertEquals(customer.waitingStartTime, -1L);
        customer.update();
        assertEquals(customer.waitingStartTime, TimeUtils.millis(), 10L);
        assertFalse(customer.getPhase() == CustomerPhase.LEAVING);
        customer.waitingStartTime = customer.customerWaitTime;
        customer.update();
        assertEquals(customer.getPhase(), CustomerPhase.LEAVING);
    }

    @Test
    public void testLeavingPhase(){
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customer.spawn();
        customer.setPhase(CustomerPhase.LEAVING);
        customer.worldPosition = new Vector2(10,5);
        while(customer.getWorldPosition().x != 2 || customer.getWorldPosition().y != 0){
            assertFalse(Objects.equals(customer.getWorldPosition(), new Vector2(2, 0)));
            customer.update();
        }
        assertTrue(customer.getWorldPosition().x == 2);
        assertTrue(customer.getWorldPosition().y == 0);
        customer.update();
        assertTrue(customer.getMatch().getCustomersSoFar() == 1);
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




    //Work on testing different customer phases

    //Can we test spawn function?

}
