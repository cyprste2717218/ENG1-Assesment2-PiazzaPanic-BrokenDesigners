package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.menu.MenuScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LoadGame {
    FileHandle prefsFile = Gdx.files.internal(System.getProperty("user.home") + "/.prefs/Game_Data");
    Preferences pref = Gdx.app.getPreferences("Game_Data");

    private MenuScreen menuScreen;
    public boolean loadFailed;

    //Match data
    private Match match;
    private int elapsedTime;

    //Chef data
    private float[] chefX = new float[3];
    private float[] chefY = new float[3];
    private boolean[] chefLocked = new boolean[3];
    private ArrayList<Item>[] chefItemStacks = new ArrayList[3];
    private int selectedChef;

    //Customer Data
    private int customerSize;
    private ArrayList<Item> customerOrders = new ArrayList<>();
    private ArrayList<Boolean> customerIsServed = new ArrayList<>();
    private ArrayList<Integer> customerStationIndex = new ArrayList<>();
    private ArrayList<Float> customerX = new ArrayList<>();
    private ArrayList<Float> customerY = new ArrayList<>();
    private ArrayList<CustomerPhase> customerPhases = new ArrayList<>();
    private ArrayList<Long> customerTimeSpentWaiting = new ArrayList<>();
    private long customerManagerTimeSinceSpawn;

    //Station Data
    private Kitchen kitchen;
    private int stationCount;
    private ArrayList<Boolean> stationLocked = new ArrayList<>();
    private ArrayList<ArrayList<Item>> assemblerItems = new ArrayList<>();
    private ArrayList<Item> stationHand = new ArrayList<>();
    private ArrayList<Boolean> servingCustomer = new ArrayList<>();

    public LoadGame(Kitchen kitchen, MenuScreen menuScreen){
        this.kitchen = kitchen;
        this.menuScreen = menuScreen;
        load();
    }

    public void load(){
        if(prefsFile.exists()){
            if(loadMatch() && loadChefs() && loadCustomers() && loadStations()){
                debugLoading();
            }
            loadFailed = false;
        }
        else{
            menuScreen.isLoading = false;
            loadFailed = true;
        }
    }

    public void debugLoading(){
        System.out.println("LOADING");
        System.out.println("LOADING MATCH");
        System.out.println("Money: " + pref.getString("Money"));
        System.out.println("Game_Mode: " + pref.getString("Game_Mode"));
        System.out.println("Elapsed_Time: " + pref.getInteger("Elapsed_Time"));
        System.out.println("Reputation Points: " + pref.getInteger("Reputation Points"));
        System.out.println("Customers served: " + pref.getInteger("Customers served"));
        System.out.println("Customers so far: " + pref.getInteger("Customers so far"));
        System.out.println("MATCH LOADED");
        System.out.println(" ");

        System.out.println("LOADING CHEFS");
        for(int i = 0; i < 3; i++){
            String chefId = "Chef " + i;
            System.out.println(chefId + " position x-coordinate: " + getChefX()[i]);
            System.out.println(chefId + " position y-coordinate: " + getChefY()[i]);
            System.out.println(chefId + " locked: " + getChefLocked()[i]);
            System.out.println(chefId + " item stack: " + getChefItemStacks()[i].toString());
        }
        System.out.println("Selected Chef: " + selectedChef);
        System.out.println("CHEFS LOADED");
        System.out.println(" ");

        System.out.println("LOADING CUSTOMERS");
        for(int i = 0; i < getCustomerSize(); i++){

        }
        System.out.println("CUSTOMERS LOADED");

        System.out.println("LOADING COMPLETE");
    }

    public boolean loadMatch(){
        GameMode gameMode = Objects.equals(pref.getString("Game_Mode"), GameMode.SCENARIO.name()) ? GameMode.SCENARIO : GameMode.ENDLESS;
        int points = pref.getInteger("Reputation Points");
        float money = pref.getFloat("Money");
        int cusServed = pref.getInteger("Customers served");
        int cusSoFar = pref.getInteger("Customers so far");
        DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(pref.getString("Difficulty Level"));
        match = new Match(gameMode, points, money, cusServed, cusSoFar, difficultyLevel);
        elapsedTime = pref.getInteger("Elapsed_Time");
        return true;
    }

    public boolean loadChefs(){
        for(int i = 0; i < 3; i++){
            String chef = "Chef" + i + " ";
            getChefX()[i] = pref.getFloat(chef + "position x-coordinate");
            getChefY()[i] = pref.getFloat(chef + "position y-coordinate");
            getChefLocked()[i] = pref.getBoolean(chef + "locked");
            getChefItemStacks()[i] =  stringToItemArray(pref.getString(chef + "item-stack"));
        }
        selectedChef = pref.getInteger("chef selected");
        return true;
    }

    public boolean loadCustomers(){
        customerManagerTimeSinceSpawn = pref.getLong("CustomerManager time spent waiting");
        customerSize = pref.getInteger("Customer size");
        for(int i = 0; i < getCustomerSize(); i++){
            String customer = "Customer" + i + " ";
            getCustomerOrders().add(ItemRegister.itemRegister.get(pref.getString(customer + "item-stack")));
            getCustomerStationIndex().add(pref.getInteger(customer + "CustomerStation"));
            getCustomerIsServed().add(pref.getBoolean(customer + "beenServed"));
            getCustomerPhases().add(CustomerPhase.valueOf(pref.getString(customer + "phase")));
            getCustomerX().add(pref.getFloat(customer + "position x-coordinate"));
            getCustomerY().add(pref.getFloat(customer + "position y-coordinate"));
            getCustomerTimeSpentWaiting().add(pref.getLong(customer + "time spent waiting"));
        }
        return true;
    }

    public boolean loadStations(){
        stationCount = pref.getInteger("stationCount");
        for(int i = 0; i < stationCount; i++){
            String stationID = "Station " + i + " ";
            String stationName = pref.getString(stationID + "name");

            getStationHand().add(ItemRegister.itemRegister.get(pref.getString(stationID + "item-stack")));
            getStationLocked().add(pref.getBoolean(stationID + "locked"));

            if(Objects.equals(stationName, "Assembly_Station")){
                String str = pref.getString(stationID + "item-stack");
                ArrayList<Item> arr = stringToItemArray(str);
                getAssemblerItems().add(arr);
            }
            else if(Objects.equals(stationName, "Customer_Station")){
                customerIsServed.add(pref.getBoolean(stationID + "serving customer"));
            }
        }
        return true;
    }

    private ArrayList<String> stringToArray(String input){
        input = input.replace("[", "");
        input = input.replace("]", "");
        return new ArrayList<String>(Arrays.asList(input.split(", ")));
    }

    private ArrayList<Item> stringToItemArray(String input){
        ArrayList<String> itemStrings = stringToArray(input);
        ArrayList<Item> items = new ArrayList<>();
        for(String item: itemStrings){
            items.add(ItemRegister.itemRegister.get(item));
            if(item == ""){
                return new ArrayList<Item>();
            }
        }
        return items;
    }

    public Match getMatch() {
        return match;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public float[] getChefX() {
        return chefX;
    }

    public float[] getChefY() {
        return chefY;
    }

    public boolean[] getChefLocked() {
        return chefLocked;
    }

    public ArrayList<Item>[] getChefItemStacks() {
        return chefItemStacks;
    }

    public int getSelectedChef() {
        return selectedChef;
    }

    public int getCustomerSize() {
        return customerSize;
    }

    public ArrayList<Item> getCustomerOrders() {
        return customerOrders;
    }

    public ArrayList<Integer> getCustomerStationIndex() {
        return customerStationIndex;
    }

    public ArrayList<Float> getCustomerX() {
        return customerX;
    }

    public ArrayList<Float> getCustomerY() {
        return customerY;
    }

    public ArrayList<CustomerPhase> getCustomerPhases() {
        return customerPhases;
    }

    public ArrayList<Boolean> getCustomerIsServed() {
        return customerIsServed;
    }

    public ArrayList<Long> getCustomerTimeSpentWaiting() {
        return customerTimeSpentWaiting;
    }

    public long getCustomerManagerTimeSinceSpawn() {
        return customerManagerTimeSinceSpawn;
    }

    public ArrayList<Boolean> getStationLocked() {
        return stationLocked;
    }

    public ArrayList<ArrayList<Item>> getAssemblerItems() {
        return assemblerItems;
    }

    public ArrayList<Item> getStationHand() {
        return stationHand;
    }

    public ArrayList<Boolean> getServingCustomer() {
        return servingCustomer;
    }
}
