package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.powerups.DoubleMoneyPowerUp;
import com.github.brokendesigners.map.powerups.PowerUpManager;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class DoubleMoneyPowerUpTests {

    @Test
    public void testActivateAndDeactivate(){
        DoubleMoneyPowerUp doubleMoneyPowerUp = createDoubleMoneyPowerup();
        Match match = doubleMoneyPowerUp.getMatch();
        assertTrue(match.getMoneyDouble() == 0);
        assertFalse(match.hasMoneyPower);
        doubleMoneyPowerUp.activate();
        assertTrue(match.hasMoneyPower);
        match.addMoney("Pizza", 0,0);
        assertTrue(match.getMoneyDouble() == 30);
        match.subtractMoney(30);
        doubleMoneyPowerUp.deactivate();
        assertFalse(match.hasMoneyPower);
        match.addMoney("Pizza", 0,0);
        assertTrue(match.getMoneyDouble() == 15);
    }


    private DoubleMoneyPowerUp createDoubleMoneyPowerup(){
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        Player player = new Player(new Vector2(0,0));
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        PowerUpManager powerUpManager = new PowerUpManager(player, match, customerManager, KitchenTestUtils.createMockedKitchen(match));
        return new DoubleMoneyPowerUp(new Vector2(0,0), player, match, powerUpManager, true);
    }
}
