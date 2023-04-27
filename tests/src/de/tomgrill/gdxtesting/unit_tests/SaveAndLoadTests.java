package de.tomgrill.gdxtesting.unit_tests;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.SaveGame;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.map.Kitchen;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.character.CustomerTestingUtils;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class SaveAndLoadTests {

    @Test
    public void testSave(){
        Match match = new Match(GameMode.ENDLESS, 4, 5.00f, 6, 7, DifficultyLevel.MEDIUM, 8);
        SaveGame saveGame = createSaveGame(match);
        saveGame.save();



    }

    private SaveGame createSaveGame(Match match){
        Kitchen kitchen = KitchenTestUtils.createMockedKitchen(match);
        ArrayList<Player> players = createPlayerList();
        MainGame mainGame = createTestMainGame(match);
        return new SaveGame(match, kitchen, players, CustomerTestingUtils.createCustomerManager(8, match), mainGame);
    }

    private ArrayList<Player> createPlayerList(){
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player(new Vector2(0,0));
        player1.locked = true;
        players.add(player1);

        Player player2 = new Player(new Vector2(5.12f,6.32f));
        player2.locked = false;
        players.add(player2);

        Player player3 = new Player(new Vector2(20,11));
        player3.locked = false;
        players.add(player3);
        return players;
    }

    private MainGame createTestMainGame(Match match){
        OrthographicCamera camera = new OrthographicCamera();
        return new MainGame(camera, camera, match, KitchenTestUtils.createMockedKitchen(match));
    }

    private void testMatchSaved(){

    }

    private void testPlayersSaved(){

    }

    private void testStationsSaved(){

    }
}
