package de.tomgrill.gdxtesting.unit_tests;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MainGameTests {

    //TODO: end()

    @Test
    public void testSetSelectedPlayer(){
        MainGame mainGame = createTestMainGame();
        Player player1 = new Player(new Vector2(0,0));
        Player player2 = new Player(new Vector2(0,0));
        Player player3 = new Player(new Vector2(0,0));

        mainGame.getPlayerList().add(player1);
        mainGame.getPlayerList().add(player2);
        mainGame.getPlayerList().add(player3);

        mainGame.setSelectedPlayer(0);
        assertEquals(0, mainGame.getSelectedPlayerIndex());
        mainGame.setSelectedPlayer(1);
        assertEquals(1, mainGame.getSelectedPlayerIndex());
        mainGame.setSelectedPlayer(2);
        assertEquals(2, mainGame.getSelectedPlayerIndex());
    }

    @Test
    public void testInitialisePlayers(){
        MainGame mainGame = createTestMainGame();
        mainGame.initialisePlayers(false, null, true);
        assertEquals(mainGame.getPlayerList().size(), 3);
        assertEquals(0, mainGame.getSelectedPlayerIndex());
        assertFalse(mainGame.getPlayerList().get(0).isLocked());
        assertTrue(mainGame.getPlayerList().get(1).isLocked());
        assertTrue(mainGame.getPlayerList().get(2).isLocked());
        assertEquals(mainGame.getPlayerList().size(), 3);
        assertEquals(mainGame.getLockedPlayerList().size(),2);
        assertEquals(mainGame.getPlayerList().get(0).getWorldPosition(), new Vector2(5, 2));
        assertEquals(mainGame.getPlayerList().get(1).getWorldPosition(), new Vector2(11, 11));
        assertEquals(mainGame.getPlayerList().get(2).getWorldPosition(), new Vector2(20, 20));
    }

    private MainGame createTestMainGame(){
        OrthographicCamera cam = new OrthographicCamera();
        Match match = new Match(GameMode.ENDLESS, DifficultyLevel.MEDIUM, 5);
        return new MainGame(cam, cam, match, KitchenTestUtils.createMockedKitchen(match));
    }

}
