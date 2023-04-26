package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.powerups.PowerUpManager;
import com.github.brokendesigners.map.powerups.PrepTimePowerUp;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PrepTimePowerUpTests {

    @Test
    public void testActivateAndDeactivate(){
        PrepTimePowerUp prepTimePowerUp = createPrepTimePowerUp();
        prepTimePowerUp.activate();
        prepTimePowerUp.deactivate();
    }


    private PrepTimePowerUp createPrepTimePowerUp(){
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        Player player = new Player(new Vector2(0,0));
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        PowerUpManager powerUpManager = new PowerUpManager(player, match, customerManager);
        return new PrepTimePowerUp(new Vector2(0,0), player, match, powerUpManager, true);
    }
}
