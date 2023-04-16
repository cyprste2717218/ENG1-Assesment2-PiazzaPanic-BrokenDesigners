package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.*;
import com.github.brokendesigners.menu.MenuScreen;
import com.github.brokendesigners.renderer.CustomerRenderer;

import java.time.LocalTime;
import java.util.ArrayList;

public class SaveGame {

    Preferences pref = Gdx.app.getPreferences("Game_Data");
    Match match;
    ArrayList<Player> chefs;
    Kitchen kitchen;
    CustomerManager customerManager;

    String stack = " item-stack";

    public SaveGame(Match match, Kitchen kitchen, ArrayList<Player> players, CustomerManager customers){
        this.match = match;
        chefs = players;
        this.kitchen = kitchen;
        this.customerManager = customers;
    }

    public boolean save(){
        pref.putFloat("Money", (float)match.getMoneyDouble());

        pref.putString("Game_Mode", match.getGameMode().name());
        pref.putInteger("Elapsed_Time", customerManager.getElapsedTime());

        pref.putInteger("Reputation Points", match.getReputationPoints());
        pref.putInteger("Customers served", match.getCustomersServed());
        pref.putInteger("Customers so far", match.getCustomersSoFar());

        if(saveChefs() && saveStations() && saveCustomers()){
            pref.flush();
            return true;
        }
        return false;
    }

    private boolean saveStations(){

        String temp;

        ArrayList<AssemblyStation> assembly = kitchen.getAssembly();
        pref.putInteger("Assembly_Station size", assembly.size());
        for (AssemblyStation assemble : assembly){
            temp = (assemble.getItems()==null)? "" : assemble.getItems().toString();
            pref.putString(assemble.getStation_name() + " " + assembly.indexOf(assemble) + stack, temp);
        }

        ArrayList<CookingStation> cookings = kitchen.getCookings();
        pref.putInteger("Cooking_Station size", cookings.size());
        for (CookingStation cooking : cookings){
            temp = (cooking.hand == null)? "" : cooking.hand.toString();
            pref.putString(cooking.getStation_name() + " " + cookings.indexOf(cooking) + stack, temp);
        }

        ArrayList<BakingStation> bakings = kitchen.getBakings();
        pref.putInteger("Baking_Station size", bakings.size());
        for (BakingStation baking : bakings){
            temp = (baking.hand == null)? "" : baking.hand.toString();
            pref.putString(baking.getStation_name() + " " + bakings.indexOf(baking) + stack, temp);
        }

        ArrayList<CuttingStation> cuttings = kitchen.getCuttings();
        pref.putInteger("Cutting_Station size", cuttings.size());
        for (CuttingStation cutting : cuttings){
            temp = (cutting.hand == null)? "" : cutting.hand.toString();
            pref.putString(cutting.getStation_name() + " " + cuttings.indexOf(cutting) + stack, temp);
        }

        ArrayList<CounterStation> counters = kitchen.getCounters();
        pref.putInteger("Counter_Station size", counters.size());
        for (CounterStation counter : counters){
            temp = (counter.hand == null)? "" : counter.hand.toString();
            pref.putString(counter.getStation_name() + " " + counters.indexOf(counter) + stack, temp);
        }

        return true;
    }

    private boolean saveChefs(){
//        pref.putInteger("Chefs size", chefs.size());
        String chefN;
        for(Player chef : chefs){
            chefN = "Chef" + chefs.indexOf(chef);
            pref.putFloat(chefN + " position x-coordinate", chef.worldPosition.x);
            pref.putFloat(chefN + " position y-coordinate", chef.worldPosition.y);
            pref.putString(chefN + stack, chef.hand.heldItems.toString());
            pref.putBoolean(chefN + " selected", chef.isSelected());
        }
        return true;
    }

    private boolean saveCustomers(){
        ArrayList<Customer> customers = customerManager.getCustomers();
        pref.putInteger("Customer size", customers.size());
        String customerN;
        for (Customer customer : customers){
            customerN = "Customer" + customers.indexOf(customer);
            pref.putFloat(customerN + " position x-coordinate", customer.worldPosition.x);
            pref.putFloat(customerN + " position y-coordinate", customer.worldPosition.y);
            pref.putString(customerN + " phase", customer.getPhase().toString());
            pref.putString(customerN + stack, customer.getDesiredMeal().toString());
        }
        return true;
    }
}
