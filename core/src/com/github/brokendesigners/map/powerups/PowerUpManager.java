package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.utils.Timer;

public class PowerUpManager {

    Player player;
    Match match;
    CustomerManager customerManager;
    ArrayList<PowerUp> activePowerUps;
    Timer timer;
    boolean taskWasActivated;


    public PowerUpManager(Player player, Match match, CustomerManager customerManager){
        this.player = player;
        this.match = match;
        this.customerManager = customerManager;
        activePowerUps = new ArrayList<>();
        timer = new Timer();
        taskWasActivated = false;
        //Repeatedly spawns a new power up every 15 seconds
        timer.scheduleTask(spawnPowerUp,0f,15f);
        timer.start();
    }

    public Timer.Task spawnPowerUp = new Timer.Task() {
        @Override
        public void run() {
            taskWasActivated = true;
            activePowerUps.add(spawnRandomPowerUp());
        }
    };

    public void handlePowerUps(){
        //Check for collisions with powerups and then activate them
        for(PowerUp powerUp: new ArrayList<>(activePowerUps)){
            if(player.getPlayerRectangle().overlaps(powerUp.getSprite().getBoundingRectangle())){
                powerUp.usePowerUp();
                activePowerUps.remove(powerUp);
            }
        }
    }

    //Spawns a random powerup into the map
    private PowerUp spawnRandomPowerUp(){
        Random rnd = new Random();
        int powerUpOption = rnd.nextInt(5);
        Vector2 spawnPoint = getPowerUpSpawnPoint();
        switch (powerUpOption){
            case 0:
                return new CarryCapacityPowerUp(spawnPoint, player, match,this);
            case 1:
                return new CustomerWaitTimePowerUp(spawnPoint, player, match, customerManager,this);
            case 2:
                return new DoubleMoneyPowerUp(spawnPoint, player, match,this);
            case 3:
                return new PrepTimePowerUp(spawnPoint, player, match,this);
            default:
                return new SpeedPowerUp(spawnPoint, player, match,this);
        }
    }

    //Gets a random spawn point for the power up to be placed
    private Vector2 getPowerUpSpawnPoint(){
        Random rnd = new Random();
        int spawnPointOption = rnd.nextInt(4);

        switch (spawnPointOption){
            case 0:
                return new Vector2(18,34);
            case 1:
                return new Vector2(38,26);
            case 2:
                return new Vector2(58,34);
            default:
                return new Vector2(38,46);
        }
    }

    public ArrayList<PowerUp> getActivePowerUps(){
        return activePowerUps;
    }

    public void setPlayer(Player p){
        player = p;
    }

    public boolean getTaskWasActivated(){
        return taskWasActivated;
    }

    public void setTaskWasActivated(boolean wasActivated){
        taskWasActivated = wasActivated;
    }
}
