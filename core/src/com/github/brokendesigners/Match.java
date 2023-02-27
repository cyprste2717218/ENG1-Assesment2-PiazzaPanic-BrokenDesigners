package com.github.brokendesigners;

import com.github.brokendesigners.enums.GameMode;

public class Match {

    public Match(GameMode gameMode){
        this.gameMode = gameMode;
        money = 0;
        reputationPoints = 3;
        customersServed = 0;
        customersSoFar = 0;
    }

    public Match(GameMode gameMode, int money, int reputationPoints, int customersServed, int customersSoFar){
        this.gameMode = gameMode;
        this.money = money;
        this.reputationPoints = reputationPoints;
        this.customersServed = customersServed;
        this.customersSoFar = customersSoFar;
    }

    private GameMode gameMode;
    private int money;
    private int reputationPoints;
    private int customersServed;
    private int customersSoFar;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money){
        this.money += money;
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
