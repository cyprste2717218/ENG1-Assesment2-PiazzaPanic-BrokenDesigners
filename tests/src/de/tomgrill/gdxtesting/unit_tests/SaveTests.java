package de.tomgrill.gdxtesting.unit_tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.*;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.item.TestItem;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.AssemblyStation;
import com.github.brokendesigners.map.interactable.CookingStation;
import com.github.brokendesigners.map.interactable.CustomerStation;
import com.github.brokendesigners.map.interactable.Station;
import de.tomgrill.gdxtesting.GdxTestRunner;
import de.tomgrill.gdxtesting.unit_tests.character.CustomerTestingUtils;
import de.tomgrill.gdxtesting.unit_tests.map.KitchenTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(GdxTestRunner.class)
public class SaveTests {

    private Preferences pref;
    @Before
    public void testSave(){
        pref = Gdx.app.getPreferences("Game_Data");
        Match match = new Match(GameMode.ENDLESS, 4, 5.00f, 6, 7, DifficultyLevel.MEDIUM, 8);
        SaveGame saveGame = createSaveGame(match);
        saveGame.save();
    }

    @Test
    public void testSavedMatch(){
        assertEquals(5.00f, pref.getFloat("Money"),0.0);
        assertEquals("ENDLESS", pref.getString("Game_Mode"));
        assertEquals(0, pref.getInteger("Elapsed_Time"));
        assertEquals(4, pref.getInteger("Reputation Points"));
        assertEquals(6, pref.getInteger("Customers served"));
        assertEquals(7, pref.getInteger("Customers so far"));
        assertEquals("MEDIUM", pref.getString("Difficulty Level"));
        assertEquals(8, pref.getInteger("CustomerNumber"));
    }

    @Test
    public void testSavedChefs(){
        assertEquals(0, pref.getFloat("Chef0 position x-coordinate"), 0.0f);
        assertEquals(0, pref.getFloat("Chef0 position y-coordinate"), 0.0f);
        assertEquals("[]", pref.getString("Chef0 item-stack"));
        assertTrue(pref.getBoolean("Chef0 locked"));

        assertEquals(5.12f, pref.getFloat("Chef1 position x-coordinate"), 0.01f);
        assertEquals(6.32, pref.getFloat("Chef1 position y-coordinate"), 0.01f);
        assertEquals("[]", pref.getString("Chef1 item-stack"));
        assertFalse(pref.getBoolean("Chef1 locked"));

        assertEquals(20, pref.getFloat("Chef2 position x-coordinate"), 0.0f);
        assertEquals(11, pref.getFloat("Chef2 position y-coordinate"), 0.0f);
        assertEquals("[Tomato, Cheese]", pref.getString("Chef2 item-stack"));
        assertFalse(pref.getBoolean("Chef2 locked"));

        assertEquals(0, pref.getInteger("chef selected"));
    }

    @Test
    public void testSavedCustomers(){
        assertEquals(2, pref.getInteger("Customer size"));
        assertEquals(TimeUtils.timeSinceMillis(5000L), pref.getLong("CustomerManager time spent waiting"), 50);

        assertEquals(5f, pref.getFloat("Customer0 position x-coordinate"), 0.0f);
        assertEquals(10f, pref.getFloat("Customer0 position y-coordinate"), 0.0f);
        assertEquals("WAITING", pref.getString("Customer0 phase"));
        assertFalse(pref.getBoolean("Customer0 beenServed"));
        assertEquals("Burger", pref.getString("Customer0 item-stack"));
        assertEquals(0, pref.getInteger("Customer0 CustomerStation"));
        assertEquals(TimeUtils.timeSinceMillis(10000L), pref.getLong("Customer0 time spent waiting"), 50);

        assertEquals(12f, pref.getFloat("Customer1 position x-coordinate"), 0.0f);
        assertEquals(21f, pref.getFloat("Customer1 position y-coordinate"), 0.0f);
        assertEquals("LEAVING", pref.getString("Customer1 phase"));
        assertTrue(pref.getBoolean("Customer1 beenServed"));
        assertEquals("Pizza", pref.getString("Customer1 item-stack"));
        assertEquals(1, pref.getInteger("Customer1 CustomerStation"));
        assertEquals(TimeUtils.timeSinceMillis(20000L), pref.getLong("Customer1 time spent waiting"), 50);
    }

    @Test
    public void testSavedStations(){
        assertEquals(3, pref.getInteger("stationCount"));

        assertFalse(pref.getBoolean("Station 0 locked"));
        assertEquals("Customer_Station", pref.getString("Station 0 name"));
        assertTrue(pref.getBoolean("Station 0 serving customer"));
        assertEquals("Lettuce", pref.getString("Station 0 item-stack"));

        assertTrue(pref.getBoolean("Station 1 locked"));
        assertEquals("Cooking_Station", pref.getString("Station 1 name"));
        assertEquals("", pref.getString("Station 1 item-stack"));

        assertFalse(pref.getBoolean("Station 2 locked"));
        assertEquals("Assembly_Station", pref.getString("Station 2 name"));
        assertEquals("[Onion, Potato]", pref.getString("Station 2 item-stack"));
    }

    public static SaveGame createSaveGame(Match match){
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        ArrayList<Player> players = createPlayerList();
        MainGame mainGame = createTestMainGame(match);
        CustomerManager customerManager = CustomerTestingUtils.createCustomerManager(8, match);
        createTestCustomers(customerManager, match);
        createKitchenStations(kitchen);
        return new SaveGame(match, kitchen, players, customerManager, mainGame);
    }

    private static ArrayList<Player> createPlayerList(){
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player(new Vector2(0,0));
        player1.locked = true;
        players.add(player1);

        Player player2 = new Player(new Vector2(5.12f,6.32f));
        player2.locked = false;
        players.add(player2);

        Player player3 = new Player(new Vector2(20,11));
        player3.locked = false;
        player3.hand.heldItems = new ArrayList<>(Arrays.asList(new TestItem("Tomato"), new TestItem("Cheese")));
        players.add(player3);
        return players;
    }

    private static void createTestCustomers(CustomerManager customerManager, Match match){
        CustomerStation customerStation1 = CustomerTestingUtils.createTestCustomerStation();
        customerManager.getCustomerStations().add(customerStation1);
        Customer customer1 = new Customer(customerStation1,
                new TestItem("Burger"), new Vector2(5,10), match);
        customer1.setPhase(CustomerPhase.WAITING);
        customer1.setBeenServed(false);
        customer1.setWaitingStartTime(10000L);
        customerManager.getCustomers().add(customer1);

        CustomerStation customerStation2 = CustomerTestingUtils.createTestCustomerStation();
        customerManager.getCustomerStations().add(customerStation2);
        Customer customer2 = new Customer(customerStation2,
                new TestItem("Pizza"), new Vector2(12,21), match);
        customer2.setPhase(CustomerPhase.LEAVING);
        customer2.setBeenServed(true);
        customer2.setWaitingStartTime(20000L);
        customerManager.getCustomers().add(customer2);
        customerManager.setSpawningTime(5000L);
    }

    private static void createKitchenStations(Kitchen kitchen){
        ArrayList<Station> stations = new ArrayList<>();
        //Customer, Cooking and Assembly Stations
        CustomerStation customerStation = Mockito.mock(CustomerStation.class);
        Mockito.when(customerStation.isServingCustomer()).thenReturn(true);
        Mockito.when(customerStation.isLocked()).thenReturn(false);
        Mockito.when(customerStation.getStation_name()).thenReturn("Customer_Station");
        TestItem lettuceItem = new TestItem("Lettuce");
        Mockito.when(customerStation.getItem()).thenReturn(lettuceItem);
        stations.add(customerStation);

        CookingStation cookingStation = Mockito.mock(CookingStation.class);
        Mockito.when(cookingStation.isLocked()).thenReturn(true);
        Mockito.when(cookingStation.getStation_name()).thenReturn("Cooking_Station");
        TestItem nullItem = null;
        Mockito.when(cookingStation.getItem()).thenReturn(nullItem);
        stations.add(cookingStation);

        AssemblyStation assemblyStation = Mockito.mock(AssemblyStation.class);
        Mockito.when(assemblyStation.isLocked()).thenReturn(false);
        Mockito.when(assemblyStation.getStation_name()).thenReturn("Assembly_Station");
        Hand hand = new Hand();
        hand.heldItems = new ArrayList<>(Arrays.asList(new TestItem("Onion"), new TestItem("Potato")));
        Mockito.when(assemblyStation.getHand()).thenReturn(hand);
        stations.add(assemblyStation);

        Mockito.when(kitchen.getKitchenStations()).thenReturn(stations);
    }

    private static MainGame createTestMainGame(Match match){
        OrthographicCamera camera = new OrthographicCamera();
        return new MainGame(camera, camera, match, KitchenTestUtils.createMockedKitchen(match));
    }
}
