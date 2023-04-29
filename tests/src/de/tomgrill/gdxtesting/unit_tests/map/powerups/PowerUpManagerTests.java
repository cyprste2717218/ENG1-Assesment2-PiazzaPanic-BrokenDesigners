package de.tomgrill.gdxtesting.unit_tests.map.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.powerups.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.utils.Timer;
import org.mockito.Mockito;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PowerUpManagerTests {

    @Test
    public void testSpawnPowerUp(){
        PowerUpManager powerUpManager = createPowerUpManager();
        assertEquals(powerUpManager.getActivePowerUps().size(), 0);
        for(int i = 0; i < 10; i++){
            powerUpManager.spawnPowerUp.run();
            assertEquals(powerUpManager.getActivePowerUps().size(), i + 1);
            PowerUp powerUp = powerUpManager.getActivePowerUps().get(i);
            assertTrue(powerUp instanceof CarryCapacityPowerUp ||
                    powerUp instanceof CustomerWaitTimePowerUp ||
                    powerUp instanceof DoubleMoneyPowerUp ||
                    powerUp instanceof PrepTimePowerUp ||
                    powerUp instanceof SpeedPowerUp);
            assertTrue(powerUp.getSpawnPoint().equals(new Vector2(18, 34)) ||
                    powerUp.getSpawnPoint().equals(new Vector2(38, 26)) ||
                    powerUp.getSpawnPoint().equals(new Vector2(58, 34)) ||
                    powerUp.getSpawnPoint().equals(new Vector2(38, 46)));
        }
    }

    @Test
    public void testHandlePowerUps(){
        PowerUpManager powerUpManager = createPowerUpManager();
        powerUpManager.spawnPowerUp.run();
        PowerUp powerUp = powerUpManager.getActivePowerUps().get(0);
        Vector2 intersectPoint = powerUp.getSpawnPoint();

        Sprite sprite = Mockito.mock(Sprite.class);
        Mockito.when(sprite.getBoundingRectangle()).thenReturn(new Rectangle(intersectPoint.x - 5, intersectPoint.y - 5,
                intersectPoint.x + 5, intersectPoint.y + 5));
        powerUp.setSprite(sprite);
        Player player = new Player(intersectPoint);
        powerUpManager.setPlayer(player);
        assertEquals(powerUpManager.getActivePowerUps().size(), 1);
        powerUpManager.handlePowerUps();
        assertEquals(powerUpManager.getActivePowerUps().size(), 0);
    }

    private PowerUpManager createPowerUpManager(){
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        CustomerManager customerManager = new CustomerManager(5, new Vector2(0,0), match);
        Player player = new Player(new Vector2(0,0));
        return new PowerUpManager(player, match, customerManager, true, KitchenTestUtils.createMockedKitchen(match));
    }
}
