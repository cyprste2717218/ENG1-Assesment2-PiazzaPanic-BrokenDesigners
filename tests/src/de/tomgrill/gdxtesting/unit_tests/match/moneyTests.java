package de.tomgrill.gdxtesting.unit_tests.match;

import com.badlogic.gdx.Game;
import com.github.brokendesigners.Match;
import org.junit.Test;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;

import static org.junit.Assert.*;


public class moneyTests {
    @Test
    // Testing that the money increments by only the amount related to the item passed
    // In this case; "salad" = 7
    public void testSaladNoTip() {
        long newStartTime = TimeUtils.millis()-45000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Salad", newStartTime, 60L);
        assertEquals("7.00", match.getMoney());
    }
    @Test
    // Testing if customer served between 75% and 50% of the wait time
    // recievs a tip of 10% of item value
    public void test10Tip() {
        long newStartTime = TimeUtils.millis()-44000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Salad", newStartTime, 60L);
        assertEquals("7.70", match.getMoney());
    }
    @Test
    // Testing if customer served between 50% and 25% of the wait time
    // recievs a tip of 20% of item value
    public void test20Tip() {
        long newStartTime = TimeUtils.millis()-29000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Salad", newStartTime, 60L);
        assertEquals("8.40", match.getMoney());
    }
    @Test
    // Testing if customer served less than 25% of the wait time
    // recievs a tip of 50% of item value
    public void test50Tip() {
        long newStartTime = TimeUtils.millis()-10000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Salad", newStartTime, 60L);
        assertEquals("10.50", match.getMoney());
    }
    @Test
    // Testing that the money increments by only the amount related to the item passed
    // In this case; "burger" = 12
    public void testBurgerNoTip() {
        long newStartTime = TimeUtils.millis()-45000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Burger", newStartTime, 60L);
        assertEquals("12.00", match.getMoney());
    }
    @Test
    // Testing that the money increments by only the amount related to the item passed
    // In this case; "pizza" = 15
    public void testPizzaNoTip() {
        long newStartTime = TimeUtils.millis()-45000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Pizza", newStartTime, 60L);
        assertEquals("15.00", match.getMoney());
    }
    @Test
    // Testing that the money increments by only the amount related to the item passed
    // In this case; "Baked_JacketPotato" = 10
    public void testBaked_JacketPotatoNoTip() {
        long newStartTime = TimeUtils.millis()-45000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Baked_JacketPotato", newStartTime, 60L);
        assertEquals("10.00", match.getMoney());
    }
    @Test
    // Testing that the money increments by only the amount related to the item passed
    // In this case; Invalid Item = 0
    public void testInvalidItemNoTip() {
        long newStartTime = TimeUtils.millis()-45000L;
        Match match = new Match(GameMode.SCENARIO,3, 0, 0, 0, DifficultyLevel.EASY);
        match.hasMoneyPower = false;
        match.addMoney("Invalid", newStartTime, 60L);
        assertEquals("0.00", match.getMoney());
    }

}
