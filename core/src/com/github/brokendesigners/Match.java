package com.github.brokendesigners;

import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import java.util.Formatter;
import java.util.Random;

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

    public void addMoney(double value) {
        money += value;
    }

    public void addTip() { // potentially add functionality to base this value off of time spent on order
        // Randomrandom = new Random();

        //double tip = random.nextDouble();
        //money += tip;

        // does this work for each customer instance?
        if (TimeUtils.timeSinceMillis(customer.waitingStartTime/1000 ) < (0.5*customer.customerWaitTime)) {

        }

    }

    public void subtractMoney(double value) {
        money -= value;
    }

    public String getMoney() {

        Formatter formatter = new Formatter();
        formatter.format("%.2f", money);
        return formatter.toString();

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
