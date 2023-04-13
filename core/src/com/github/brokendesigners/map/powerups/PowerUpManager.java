package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.CustomerManager;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.utils.Timer;

public class PowerUpManager {

    Player player;
    MainGame game;
    CustomerManager customerManager;
    ArrayList<PowerUp> activePowerUps;
    Timer timer;

    public PowerUpManager(Player player, MainGame game, CustomerManager customerManager){
        this.player = player;
        this.game = game;
        this.customerManager = customerManager;
        activePowerUps = new ArrayList<>();
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                activePowerUps.add(spawnRandomPowerUp());
            }
        },0f,15f);
        timer.start();
    }


    public void handlePowerUps(){
        //Check for collisions with powerups and then activate them
        for(PowerUp powerUp: new ArrayList<>(activePowerUps)){
            if(player.getPlayerRectangle().overlaps(powerUp.getSprite().getBoundingRectangle())){
                powerUp.usePowerUp();
                activePowerUps.remove(powerUp);
            }
        }
    }

    private PowerUp spawnRandomPowerUp(){
        Random rnd = new Random();
        int powerUpOption = rnd.nextInt(5);
        Vector2 spawnPoint = getPowerUpSpawnPoint();

        switch (powerUpOption){
            case 0:
                return new CarryCapacityPowerUp(spawnPoint, player, game,this);
            case 1:
                return new CustomerWaitTimePowerUp(spawnPoint, player, game, customerManager,this);
            case 2:
                return new DoubleMoneyPowerUp(spawnPoint, player, game,this);
            case 3:
                return new PrepTimePowerUp(spawnPoint, player, game,this);
            default:
                return new SpeedPowerUp(spawnPoint, player, game,this);
        }
    }

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
}
