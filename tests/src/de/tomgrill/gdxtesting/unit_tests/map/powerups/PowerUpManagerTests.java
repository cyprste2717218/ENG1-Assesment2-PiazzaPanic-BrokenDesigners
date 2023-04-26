package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.powerups.PowerUp;
import com.github.brokendesigners.map.powerups.PowerUpManager;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.utils.Timer;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PowerUpManagerTests {

    private PowerUpManager createPowerUpManager(){

        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        Player player = new Player(new Vector2(0,0));
        return new PowerUpManager(player, match, customerManager);
    }
}
