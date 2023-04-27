package de.tomgrill.gdxtesting.unit_tests.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.ItemInitialiser;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.*;
import com.github.brokendesigners.renderer.BubbleRenderer;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

public class KitchenTestUtils {

    public static Kitchen createMockedKitchen(Match match){
        Kitchen kitchen = Mockito.mock(Kitchen.class);

        ArrayList<Station> stations = new ArrayList<>();
        ArrayList<AssemblyStation> assemblyStations = new ArrayList<>();
        ArrayList<CuttingStation> cuttingStations = new ArrayList<>();
        ArrayList<CookingStation> cookingStations = new ArrayList<>();
        ArrayList<BakingStation> bakingStations = new ArrayList<>();
        ArrayList<CounterStation> counterStations = new ArrayList<>();
        ArrayList<CustomerStation> customerStations = new ArrayList<>();

        //Assembly Stations
        AssemblyStation assemblyStation1 = createMockedAssemblyStation(new ArrayList<>(), true);
        stations.add(assemblyStation1);
        assemblyStations.add(assemblyStation1);

        ArrayList<Item> assembly2Items = new ArrayList<>();
        assembly2Items.add(ItemRegister.itemRegister.get("Burger"));
        assembly2Items.add(ItemRegister.itemRegister.get("Bun"));
        AssemblyStation assemblyStation2 = createMockedAssemblyStation(assembly2Items, true);
        stations.add(assemblyStation2);
        assemblyStations.add(assemblyStation2);

        //Cutting Stations
        CuttingStation cuttingStation1 = createMockedCuttingStation(ItemRegister.itemRegister.get("Tomato"),true);
        CuttingStation cuttingStation2 = createMockedCuttingStation(ItemRegister.itemRegister.get("Lettuce"),false);
        cuttingStations.add(cuttingStation1);
        cuttingStations.add(cuttingStation2);
        stations.add(cuttingStation1);
        stations.add(cuttingStation2);

        //Cooking Stations
        CookingStation cookingStation1 = createMockedCookingStation(null, true);
        CookingStation cookingStation2 = createMockedCookingStation(ItemRegister.itemRegister.get("Tomato"), false);
        cookingStations.add(cookingStation1);
        cookingStations.add(cookingStation2);
        stations.add(cookingStation1);
        stations.add(cookingStation2);

        //Baking Stations
        BakingStation bakingStation1 = createMockedBakingStation(null,true);
        BakingStation bakingStation2 = createMockedBakingStation(ItemRegister.itemRegister.get("Bun"),false);
        bakingStations.add(bakingStation1);
        bakingStations.add(bakingStation2);
        stations.add(bakingStation1);
        stations.add(bakingStation2);

        //Counter Stations
        CounterStation counterStation1 = createMockedCounterStation(null);
        CounterStation counterStation2 = createMockedCounterStation(ItemRegister.itemRegister.get("Lettuce"));
        counterStations.add(counterStation1);
        counterStations.add(counterStation2);
        stations.add(counterStation1);
        stations.add(counterStation2);


        //Customer Stations
        CustomerStation customerStation1 = createMockedCustomerStation(true);
        CustomerStation customerStation2 = createMockedCustomerStation(false);
        customerStations.add(customerStation1);
        customerStations.add(customerStation2);
        stations.add(customerStation1);
        stations.add(customerStation2);

        Mockito.when(kitchen.getAssembly()).thenReturn(assemblyStations);
        Mockito.when(kitchen.getBakings()).thenReturn(bakingStations);
        Mockito.when(kitchen.getKitchenStations()).thenReturn(stations);
        Mockito.when(kitchen.getCookings()).thenReturn(cookingStations);
        Mockito.when(kitchen.getCounters()).thenReturn(counterStations);
        Mockito.when(kitchen.getCustomerStations()).thenReturn(customerStations);
        Mockito.when(kitchen.getCuttings()).thenReturn(cuttingStations);

        ArrayList<Vector2> playerSpawns = new ArrayList<>();
        playerSpawns.add(new Vector2(5,2));
        playerSpawns.add(new Vector2(11,11));
        playerSpawns.add(new Vector2(20,20));
        Mockito.when(kitchen.getPlayerSpawnPoints()).thenReturn(playerSpawns);
        return kitchen;
    }

    //We save & load data about the inventory and locked status of assembly, cutting, cooking, counter, customer and baking
    public static AssemblyStation createMockedAssemblyStation(ArrayList<Item> items, boolean locked){
        AssemblyStation assemblyStation = Mockito.mock(AssemblyStation.class);
        Mockito.when(assemblyStation.isLocked()).thenReturn(locked);
        Mockito.when(assemblyStation.getStation_name()).thenReturn("Assembly_Station");

        Hand hand = Mockito.mock(Hand.class);
        Mockito.when(hand.getHeldItems()).thenReturn(items);

        Mockito.when(assemblyStation.getHand()).thenReturn(hand);
        Mockito.when(assemblyStation.getHand().getHeldItems()).thenReturn(items);
        return assemblyStation;
    }

    public static CuttingStation createMockedCuttingStation(Item item, boolean locked){
        CuttingStation cuttingStation = Mockito.mock(CuttingStation.class);
        Mockito.when(cuttingStation.isLocked()).thenReturn(locked);
        Mockito.when(cuttingStation.getItem()).thenReturn(item);
        Mockito.when(cuttingStation.getStation_name()).thenReturn("Cutting_Station");
        return cuttingStation;
    }

    public static CookingStation createMockedCookingStation(Item item, boolean locked){
        CookingStation cookingStation = Mockito.mock(CookingStation.class);
        Mockito.when(cookingStation.isLocked()).thenReturn(locked);
        Mockito.when(cookingStation.getItem()).thenReturn(item);
        Mockito.when(cookingStation.getStation_name()).thenReturn("Cooking_Station");
        return cookingStation;
    }

    public static BakingStation createMockedBakingStation(Item item, boolean locked){
        BakingStation bakingStation = Mockito.mock(BakingStation.class);
        Mockito.when(bakingStation.isLocked()).thenReturn(locked);
        Mockito.when(bakingStation.getItem()).thenReturn(item);
        Mockito.when(bakingStation.getStation_name()).thenReturn("Baking_Station");
        return bakingStation;
    }

    public static CounterStation createMockedCounterStation(Item item){
        CounterStation counterStation = Mockito.mock(CounterStation.class);
        Mockito.when(counterStation.isLocked()).thenReturn(false);
        Mockito.when(counterStation.getItem()).thenReturn(item);
        Mockito.when(counterStation.getStation_name()).thenReturn("Counter_Station");
        return counterStation;
    }

    public static CustomerStation createMockedCustomerStation(boolean isServingCustomer){
        CustomerStation customerStation = Mockito.mock(CustomerStation.class);
        Mockito.when(customerStation.isLocked()).thenReturn(false);
        Mockito.when(customerStation.getItem()).thenReturn(null);
        Mockito.when(customerStation.isServingCustomer()).thenReturn(isServingCustomer);
        Mockito.when(customerStation.getStation_name()).thenReturn("Customer_Station");
        return customerStation;
    }
}
