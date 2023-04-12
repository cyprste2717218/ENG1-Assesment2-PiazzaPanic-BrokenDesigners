package com.github.brokendesigners;

import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.character.Customer;

import java.util.Formatter;

public class Match {

    public Match(GameMode gameMode){
        this.gameMode = gameMode;
        money = 0.00;
        reputationPoints = 3;
        customersServed = 0;
        customersSoFar = 0;
    }

    public Match(GameMode gameMode, int reputationPoints, int money, int customersServed, int customersSoFar){
        this.gameMode = gameMode;
        this.reputationPoints = reputationPoints;
        this.money = money;
        this.customersServed = customersServed;
        this.customersSoFar = customersSoFar;
    }
    public boolean hasMoneyPower = false; // if money power is used, then effects how much is added in 'addMoney'.
    private GameMode gameMode;

    private Customer customer;
    private int reputationPoints;
    private int customersServed;
    private int customersSoFar;
    private double money;

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
    public void addMoney(String mealBeenServed, String mealDifficulty, long customerWaitingStartTime, long customerMaxWaitTime) {

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
            case "Pizza":
                orderTotal = 15;
                break;
            default:
                orderTotal += 0; //test
                System.out.println("Not a valid meal name");

        }
        orderTotal = hasMoneyPower ? orderTotal * 2: orderTotal;
        money += orderTotal;
        // test print to see meal argument passed
        // System.out.println("meal served: " + mealBeenServed);


        // determining amount of tip

        if ((((TimeUtils.timeSinceMillis(customerWaitingStartTime))/1000) < (0.75*customerMaxWaitTime)) && (((TimeUtils.timeSinceMillis(customerWaitingStartTime))/1000) >= (0.5*customerMaxWaitTime))) {

            orderTip = orderTotal*0.1;
            System.out.println("Order time between 75% and 50%");
        } else if ((((TimeUtils.timeSinceMillis(customerWaitingStartTime))/1000) < (0.5*customerMaxWaitTime)) && (((TimeUtils.timeSinceMillis(customerWaitingStartTime))/1000) >= (0.25*customerMaxWaitTime))) {

            orderTip = orderTotal*0.2;
            System.out.println("Order time between 50% and 25%");
        } else if (((TimeUtils.timeSinceMillis(customerWaitingStartTime))/1000) < (0.25*customerMaxWaitTime)) {

            orderTip = orderTotal*0.5;
            System.out.println("Order time less than 25%");
        } else {
            money += 0;
            System.out.println("Too long wait time so no tip");

        }


        money += orderTip;
        System.out.println("order tip amount: £" + orderTip);


    }



    public void subtractMoney(double value) {
        money -= value;
    }

    public String getMoney() {

        Formatter formatter = new Formatter();
        formatter.format("%.2f", money);
        return formatter.toString();
    }
    public double getIntMoney()  {
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


    /*We need to keep track of the game mode
    Money
    Reputation points
    Customers served

    //TODO: Create these variables
    Number of chefs unlocked
    Which stations are unlocked
    Difficulty level
    */




}
