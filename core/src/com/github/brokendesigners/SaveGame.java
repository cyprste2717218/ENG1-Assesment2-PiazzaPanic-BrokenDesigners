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

/**

 The SaveGame class is responsible for saving the current state of the game to a Preferences object, which can be
 accessed later to resume the game. It saves information about the current match, the kitchen, the chefs, and the customers.
 It also provides utility methods for saving specific objects such as stations, chefs, and customers.
 */
public class SaveGame {

    Preferences pref;
    Match match;
    ArrayList<Player> chefs;
    Kitchen kitchen;
    CustomerManager customerManager;
    MainGame game;
    String stack = " item-stack";
    /**
     * Constructs a new SaveGame object with the given match, kitchen, players, customers, and game.
     *
     * @param match the current match being played
     * @param kitchen the kitchen of the match
     * @param players the players in the match
     * @param customers the customers in the match
     * @param game the MainGame object representing the game being played
     */

    public SaveGame(Match match, Kitchen kitchen, ArrayList<Player> players, CustomerManager customers, MainGame game){
        this.match = match;
        chefs = players;
        this.kitchen = kitchen;
        this.customerManager = customers;
        this.game = game;
        pref = Gdx.app.getPreferences("Game_Data");
    }
    /**
     * Saves the current state of the game to a Preferences object.
     *
     * @return true if the save was successful, false otherwise
     */
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
    /**
     * Prints debug information about the save to the console.
     *
     * @param pref the Preferences object representing the save data
     */
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
    /**
     * Saves the state of the kitchen stations to the Preferences object.
     *
     * @return true if the save was successful, false otherwise
     */
    private boolean saveStations(){

        String temp = "";
        ArrayList<? extends Station> stations = kitchen.getKitchenStations();
        pref.putInteger("stationCount", stations.size());
        for(Station station : stations){
            String stationID = "Station " + stations.indexOf(station);
            if(station instanceof AssemblyStation){
                AssemblyStation assemble = (AssemblyStation)station;
                temp = (assemble.getHand().getHeldItems() == null) ? "" : itemArrToString(assemble.getHand().getHeldItems());
                System.out.println(temp);
            }
            else{
                temp = (station.getItem() == null)? "" : station.getItem().toString();
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
    /**

     Converts an ArrayList of Item objects to a String representation.
     @param arr The ArrayList of Item objects to be converted to a String.
     @return A String representation of the ArrayList of Item objects.
     */

    private String itemArrToString(ArrayList<Item> arr){
        String output = "[";
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i) != null){
                output += arr.get(i).name + (i == arr.size() - 1 ? "" : ", ");
            }
        }
        output += "]";
        return output;
    }
    /**

     Saves the state of chefs to shared preferences.
     @return true if saving the chefs' state was successful, false otherwise.
     */
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
    /**

     Saves the state of customers to shared preferences.
     @return true if saving the customers' state was successful, false otherwise.
     */
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
            pref.putString(customerN + stack, customer.getDesiredMeal().toString());
            pref.putInteger(customerN + " CustomerStation", customerManager.getCustomerStations().indexOf(customer.getStation()));
            pref.putLong(customerN + " time spent waiting", TimeUtils.timeSinceMillis(customer.getWaitingStartTime()));
        }
        return true;
    }
}
