package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.powerups.PowerUpManager;
import com.github.brokendesigners.map.powerups.PrepTimePowerUp;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PrepTimePowerUpTests {

    @Test
    public void testActivateAndDeactivate(){
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        Kitchen kitchen = KitchenTestUtils.createMockedKitchen(match);
        PrepTimePowerUp prepTimePowerUp = createPrepTimePowerUp(match, kitchen);
        //assertEquals(10f, kitchen.getAssembly().get(0).stationUseTime, 0.0);
        prepTimePowerUp.activate();
        prepTimePowerUp.deactivate();
    }


    private PrepTimePowerUp createPrepTimePowerUp(Match match, Kitchen kitchen){
        Player player = new Player(new Vector2(0,0));
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        PowerUpManager powerUpManager = new PowerUpManager(player, match, customerManager, kitchen);
        return new PrepTimePowerUp(new Vector2(0,0), player, match, powerUpManager, true, kitchen);
    }
}
