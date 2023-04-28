package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.powerups.CustomerWaitTimePowerUp;
import com.github.brokendesigners.map.powerups.PowerUpManager;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.character.CustomerTestingUtils;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CustomerWaitTimePowerUpTests {

    @Test
    public void testActivate(){
        CustomerWaitTimePowerUp customerWaitTimePowerUp = createCustomerWaitTimePowerup();
        CustomerManager customerManager = customerWaitTimePowerUp.getCustomerManager();
        Customer customer = CustomerTestingUtils.createTestCustomer();
        customerManager.getCustomers().add(customer);
        assertTrue(customer.getCustomerWaitTime() == 60000L);
        customerWaitTimePowerUp.activate();
        assertTrue(customer.getCustomerWaitTime() == 70000L);
        customerWaitTimePowerUp.activate();
        assertTrue(customer.getCustomerWaitTime() == 80000L);
    }

    private CustomerWaitTimePowerUp createCustomerWaitTimePowerup(){
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        Player player = new Player(new Vector2(0,0));
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        PowerUpManager powerUpManager = new PowerUpManager(player, match, customerManager, KitchenTestUtils.createMockedKitchen(match));
        return new CustomerWaitTimePowerUp(new Vector2(0,0), player, match, customerManager, powerUpManager, true);
    }
}
