package com.github.brokendesigners;

import com.github.brokendesigners.enums.GameMode;
import java.util.Formatter;
import java.util.Random;

public class Match {

    public static boolean moneyPower = false; // if money power is used, then effects how much is added in 'addMoney'.

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
        if (moneyPower) {money += value*2;}
        else {money += value;}

    }

    public void addTip() { // potentially add functionality to base this value off of time spent on order
        Random random = new Random();

        double tip = random.nextDouble();
        money += tip;
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
