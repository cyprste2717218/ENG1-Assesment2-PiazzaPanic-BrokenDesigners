package com.github.brokendesigners;

import com.github.brokendesigners.enums.GameMode;

public class Match {

    public Match(GameMode gameMode){
        money = 0;
        reputationPoints = 0;
        customersServed = 0;
    }

    public Match(GameMode gameMode, int money, int reputationPoints, int customersServed){
        this.gameMode = gameMode;
        this.money = money;
        this.reputationPoints = reputationPoints;
        this.customersServed = customersServed;
    }

    private GameMode gameMode;
    private int money;
    private int reputationPoints;
    private int customersServed;

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

    public int getCustomersServed() {
        return customersServed;
    }

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
