package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.*;
import com.github.brokendesigners.menu.MenuScreen;
import com.github.brokendesigners.renderer.CustomerRenderer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveGame {

    Preferences pref = Gdx.app.getPreferences("Game_Data");
    Match match;
    ArrayList<Player> chefs;
    Kitchen kitchen;
    CustomerManager customerManager;
    MainGame game;

    String stack = " item-stack";

    public SaveGame(Match match, Kitchen kitchen, ArrayList<Player> players, CustomerManager customers, MainGame game){
        this.match = match;
        chefs = players;
        this.kitchen = kitchen;
        this.customerManager = customers;
        this.game = game;
    }

    public boolean save(){
        pref.clear();
        pref.putFloat("Money", (float)match.getMoneyDouble());

        pref.putString("Game_Mode", match.getGameMode().name());
        pref.putInteger("Elapsed_Time", customerManager.getElapsedTime());

        pref.putInteger("Reputation Points", match.getReputationPoints());
        pref.putInteger("Customers served", match.getCustomersServed());
        pref.putInteger("Customers so far", match.getCustomersSoFar());
        //pref.putInteger("Difficulty Level", match.getDifficultyLevel().toString());

        if(saveChefs() && saveStations() && saveCustomers()){
            pref.flush();
            debugSaving(pref);
            return true;
        }
        return false;
    }

    private void debugSaving(Preferences pref){
        System.out.println("SAVING");
        System.out.println("Money: " + pref.getString("Money"));
        System.out.println("Game_Mode: " + pref.getString("Game_Mode"));
        System.out.println("Elapsed_Time: " + pref.getInteger("Elapsed_Time"));
        System.out.println("Reputation Points: " + pref.getInteger("Reputation Points"));
        System.out.println("Customers served: " + pref.getInteger("Customers served"));
        System.out.println("Customers so far: " + pref.getInteger("Customers so far"));
        System.out.println("SAVING COMPLETE");
    }

    private boolean saveStations(){

        String temp = "";
        ArrayList<? extends Station> stations = kitchen.getKitchenStations();
        pref.putInteger("stationCount", stations.size());
        for(Station station : stations){
            String stationID = "Station " + stations.indexOf(station);
            if(station instanceof AssemblyStation){
                AssemblyStation assemble = (AssemblyStation)station;
                temp = (assemble.getHand().heldItems ==null) ? "" : itemArrToString(assemble.getHand().heldItems);
                System.out.println(temp);
            }
            else{
                temp = (station.hand == null)? "" : station.hand.toString();
                if(station instanceof CustomerStation){
                    pref.putBoolean(stationID + " serving customer", ((CustomerStation) station).isServingCustomer());
                }
            }
            pref.putString(stationID + stack, temp);
            pref.putBoolean(stationID + " locked", station.locked);
            pref.putString(stationID + " name", station.getStation_name());
        }
/*



        ArrayList<AssemblyStation> assembly = kitchen.getAssembly();
        for (AssemblyStation assemble : assembly){
            temp = (assemble.getItems()==null)? "" : assemble.getItems().toString();
            pref.putString(assemble.getStation_name() + " " + assembly.indexOf(assemble) + stack, temp);
            pref.putBoolean(assemble.getStation_name() + " " + assembly.indexOf(assemble) + "locked", assemble.locked);
        }

        ArrayList<CookingStation> cookings = kitchen.getCookings();
        for (CookingStation cooking : cookings){
            temp = (cooking.hand == null)? "" : cooking.hand.toString();
            pref.putString(cooking.getStation_name() + " " + cookings.indexOf(cooking) + stack, temp);
            pref.putBoolean(cooking.getStation_name() + " " + cookings.indexOf(cooking) + "locked", cooking.locked);
        }

        ArrayList<BakingStation> bakings = kitchen.getBakings();
        for (BakingStation baking : bakings){
            temp = (baking.hand == null)? "" : baking.hand.toString();
            pref.putString(baking.getStation_name() + " " + bakings.indexOf(baking) + stack, temp);
            pref.putBoolean(baking.getStation_name() + " " + bakings.indexOf(baking) + "locked", baking.locked);
        }

        ArrayList<CuttingStation> cuttings = kitchen.getCuttings();
        for (CuttingStation cutting : cuttings){
            temp = (cutting.hand == null)? "" : cutting.hand.toString();
            pref.putString(cutting.getStation_name() + " " + cuttings.indexOf(cutting) + stack, temp);
            pref.putBoolean(cutting.getStation_name() + " " + cuttings.indexOf(cutting) + " locked", cutting.locked);
        }

        ArrayList<CounterStation> counters = kitchen.getCounters();
        for (CounterStation counter : counters){
            temp = (counter.hand == null)? "" : counter.hand.toString();
            pref.putString(counter.getStation_name() + " " + counters.indexOf(counter) + stack, temp);
        }

        ArrayList<CustomerStation> customerStations = kitchen.getCustomerStations();
        for (CustomerStation customerStation : customerStations){
            temp = (customerStation.hand == null)? "" : customerStation.hand.toString();
            pref.putString(customerStation.getStation_name() + " " + counters.indexOf(customerStation) + stack, temp);
            pref.putBoolean(customerStation.getStation_name() + " " + stations.indexOf(customerStation) + "serving customer", customerStation.isServingCustomer());
        }*/
        return true;
    }

    private String itemArrToString(ArrayList<Item> arr){
        String output = "[";
        for(Item item: arr){
            if(item != null) output += item.name + ", ";
        }
        output += "]";
        return output;
    }

    private boolean saveChefs(){
        String chefN;
        for(Player chef : chefs){
            chefN = "Chef" + chefs.indexOf(chef);
            pref.putFloat(chefN + " position x-coordinate", chef.worldPosition.x);
            pref.putFloat(chefN + " position y-coordinate", chef.worldPosition.y);
            pref.putString(chefN + stack, chef.hand.heldItems.toString());
            pref.putBoolean(chefN + " locked", chef.isLocked());
        }
        pref.putInteger("chef selected", game.selectedPlayer);
        return true;
    }

    private boolean saveCustomers(){
        ArrayList<Customer> customers = customerManager.getCustomers();
        pref.putInteger("Customer size", customers.size());
        pref.putLong("CustomerManager time spent waiting", TimeUtils.timeSinceMillis(customerManager.spawningTime));
        String customerN;
        for (Customer customer : customers){
            customerN = "Customer" + customers.indexOf(customer);
            pref.putFloat(customerN + " position x-coordinate", customer.worldPosition.x);
            pref.putFloat(customerN + " position y-coordinate", customer.worldPosition.y);
            pref.putString(customerN + " phase", customer.getPhase().name());
            pref.putBoolean(customerN + " beenServed", customer.beenServed);
            System.out.println("Customer Phase Saving: " + customer.getPhase().name());
            pref.putString(customerN + stack, customer.getDesiredMeal().toString());
            pref.putInteger(customerN + " CustomerStation", customerManager.getCustomerStations().indexOf(customer.getStation()));
            pref.putLong(customerN + " time spent waiting", TimeUtils.timeSinceMillis(customer.waitingStartTime));
        }
        return true;
    }
}
