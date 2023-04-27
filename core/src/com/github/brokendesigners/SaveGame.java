package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.*;

import java.util.ArrayList;

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
        pref.putString("Difficulty Level", match.getDifficultyLevel().toString());
        pref.putInteger("CustomerNumber", match.getCustomerNumber());

        if(saveChefs() && saveStations() && saveCustomers()){
            pref.flush();
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
            pref.putBoolean(stationID + " locked", station.isLocked());
            pref.putString(stationID + " name", station.getStation_name());
        }
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
        pref.putLong("CustomerManager time spent waiting", TimeUtils.timeSinceMillis(customerManager.getSpawningTime()));
        String customerN;
        for (Customer customer : customers){
            customerN = "Customer" + customers.indexOf(customer);
            pref.putFloat(customerN + " position x-coordinate", customer.getWorldPosition().x);
            pref.putFloat(customerN + " position y-coordinate", customer.getWorldPosition().y);
            pref.putString(customerN + " phase", customer.getPhase().name());
            pref.putBoolean(customerN + " beenServed", customer.hasBeenServed());
            System.out.println("Customer Phase Saving: " + customer.getPhase().name());
            pref.putString(customerN + stack, customer.getDesiredMeal().toString());
            pref.putInteger(customerN + " CustomerStation", customerManager.getCustomerStations().indexOf(customer.getStation()));
            pref.putLong(customerN + " time spent waiting", TimeUtils.timeSinceMillis(customer.getWaitingStartTime()));
        }
        return true;
    }
}
