package com.github.brokendesigners;

import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.character.Customer;

import java.util.Formatter;

/**
 * Created class
 *
 */

public class Match {

    public Match(GameMode gameMode, DifficultyLevel difficultyLevel, int customerNumber){
        this.gameMode = gameMode;
        money = 0.00;
        reputationPoints = 3;
        customersServed = 0;
        customersSoFar = 0;
        this.difficultyLevel = difficultyLevel;
        this.customerNumber = customerNumber;
    }

    public Match(GameMode gameMode, int reputationPoints, float money, int customersServed, int customersSoFar, DifficultyLevel difficultyLevel, int customerNumber){
        this.gameMode = gameMode;
        this.reputationPoints = reputationPoints;
        this.money = money;
        this.customersServed = customersServed;
        this.customersSoFar = customersSoFar;
        this.difficultyLevel = difficultyLevel;
        this.customerNumber = customerNumber;
    }
    public boolean hasMoneyPower = false; // if money power is used, then effects how much is added in 'addMoney'.
    private GameMode gameMode;
    private int customerNumber;
    private int reputationPoints;
    private int customersServed;
    private int customersSoFar;
    private double money;
    private DifficultyLevel difficultyLevel;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getReputationPoints() {
        return reputationPoints;
    }

    public void setReputationPoints(int reputationPoints) {
        this.reputationPoints = reputationPoints;
    }

    public void incrementReputationPoints(){
        reputationPoints++;
    }
    public void decrementReputationPoints() {reputationPoints--;}
    public DifficultyLevel getDifficultyLevel(){return difficultyLevel;}
    public void setDifficultyLevel(DifficultyLevel difficultyLevel){this.difficultyLevel = difficultyLevel;}

    public void failedOrder() {money += 0;}


    /*
     *  Methodology:
     *
     * each meal that can be ordered has a different max wait time customers are willing to wait,
     * the method will give a tip if the order is served before 75% of max customer waiting time for the order is elapsed,
     * with:
     *  - customer waiting time being between 75% and 50% of max customer wait time inclusive getting a 10% tip of order total
     *  - customer waiting time being between 50% and 25% of max customer wait time inclusive getting a 20% tip of order total
     *  - customer waiting time being between 0% and 25% of max customer waiting time inclusive getting a 50% tip of order total
     *
     *
     *  */

    /**
     * Adds money to the player's account based on the meal type served and the customer's wait time.
     *
     * @param mealBeenServed The type of meal that was served to the customer.
     * @param customerWaitingStartTime The time at which the customer started waiting for their order.
     * @param customerMaxWaitTime The maximum amount of time the customer is willing to wait for their order.
     */

    public void addMoney(String mealBeenServed, long customerWaitingStartTime, long customerMaxWaitTime) {

        //note: infrastruture mostly set up to account for meal difficulty in profit yield for customer order
        // just needs mealBeenServed argument to be placed into method below as needed


        double orderTotal = 0;
        double orderTip = 0;

        switch(mealBeenServed) {
            case "Salad":
                orderTotal = 7;
                break;
            case "Burger":
                orderTotal = 12;
                break;
            case "Baked_Pizza":
                orderTotal = 15;
                break;
            case "Baked_JacketPotato":
                orderTotal = 10;
            default:
                orderTotal += 0; //test
                System.out.println("Not a valid meal name");

        }
        orderTotal = hasMoneyPower ? orderTotal * 2: orderTotal;
        money += orderTotal;
        // test print to see meal argument passed
        // System.out.println("meal served: " + mealBeenServed);


        // determining amount of tip

        float customerWaitTimeFraction = TimeUtils.timeSinceMillis(customerWaitingStartTime)/1000f;

        if(customerWaitTimeFraction >= 0.75f * customerMaxWaitTime){
            money += 0;
            System.out.println("Too long wait time so no tip");
        } else if (customerWaitTimeFraction >= 0.5f * customerMaxWaitTime) {
            orderTip = orderTotal*0.1;
            System.out.println("Order time between 75% and 50%");
        }
        else if(customerWaitTimeFraction >= 0.25f * customerMaxWaitTime){
            orderTip = orderTotal*0.2;
            System.out.println("Order time between 50% and 25%");
        }
        else{
            orderTip = orderTotal*0.5;
            System.out.println("Order time less than 25%");
        }
        money += orderTip;
        System.out.println("order tip amount: £" + orderTip);
    }

    /**
     * Subtracts the specified amount of money from the player's account.
     *
     * @param value The amount of money to subtract from the player's account.
     */

    public void subtractMoney(double value) {
        money -= value;
    }
    /**

     Returns a formatted string representation of the current money amount with two decimal places.
     @return the formatted money amount as a string
     */

    public String getMoney() {

        Formatter formatter = new Formatter();
        formatter.format("%.2f", money);
        return formatter.toString();
    }
    public double getIntMoney()  {
        return money;
    }

    public double getMoneyDouble(){
        return money;
    }

    public int getCustomersServed() {
        return customersServed;
    }
    public int getCustomersSoFar() {return customersSoFar;}
    public void incrementCustomersSoFar(){customersSoFar++;}

    public void setCustomersServed(int customersServed) {
        this.customersServed = customersServed;
    }

    public void incrementCustomersServed(){
        customersServed++;
    }


    public int getCustomerNumber() {
        return customerNumber;
    }
}
