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
import com.github.brokendesigners.map.Kitchen;

/**

 The PowerUpManager class is responsible for spawning power-ups into the game world,
 tracking active power-ups, checking for collisions with the player, and activating/deactivating
 power-ups as needed.
 */
public class PowerUpManager {

    Player player;
    Match match;
    CustomerManager customerManager;
    ArrayList<PowerUp> activePowerUps; //A list of powerups that can currently be activated
    Timer timer;
    boolean testing;
    Kitchen kitchen;

    /**
     * Constructor for the PowerUpManager class that takes in the player, match, customer manager,
     * and kitchen objects as parameters.
     *
     * @param player the player object
     * @param match the match object
     * @param customerManager the customer manager object
     * @param kitchen the kitchen object
     */
    public PowerUpManager(Player player, Match match, CustomerManager customerManager, Kitchen kitchen){
        this.player = player;
        this.match = match;
        this.customerManager = customerManager;
        this.kitchen = kitchen;
        activePowerUps = new ArrayList<>();
        timer = new Timer();
        //Repeatedly spawns a new power up every 15 seconds
        timer.scheduleTask(spawnPowerUp,0f,15f);
        timer.start();
        testing = false;
    }
    /**
     * Constructor for the PowerUpManager class that takes in the player, match, customer manager,
     * testing, and kitchen objects as parameters.
     *
     * @param player the player object
     * @param match the match object
     * @param customerManager the customer manager object
     * @param testing a boolean value indicating whether the game is being tested
     * @param kitchen the kitchen object
     */
    public PowerUpManager(Player player, Match match, CustomerManager customerManager, boolean testing, Kitchen kitchen){
        this(player, match, customerManager, kitchen);
        this.testing = testing;
    }
    /**
     * A timer task that spawns a new power-up into the game world.
     */
    public Timer.Task spawnPowerUp = new Timer.Task() {
        @Override
        public void run() {
            activePowerUps.add(spawnRandomPowerUp());
        }
    };
    /**
     * Checks for collisions with power-ups and activates/deactivates them as needed.
     */
    public void handlePowerUps(){
        //Check for collisions with powerups and then activate them
        for(PowerUp powerUp: new ArrayList<>(activePowerUps)){
            if(player.getPlayerRectangle().overlaps(powerUp.getSprite().getBoundingRectangle())){
                powerUp.usePowerUp();
                activePowerUps.remove(powerUp);
            }
        }
    }
    /**
     * Spawns a random power-up into the game world.
     *
     * @return the spawned power-up
     */
    //Spawns a random powerup into the map
    private PowerUp spawnRandomPowerUp(){
        Random rnd = new Random();
        int powerUpOption = rnd.nextInt(5);
        Vector2 spawnPoint = getPowerUpSpawnPoint();
        switch (powerUpOption){
            case 0:
                return new CarryCapacityPowerUp(spawnPoint, player, match,this, testing);
            case 1:
                return new CustomerWaitTimePowerUp(spawnPoint, player, match, customerManager,this, testing);
            case 2:
                return new DoubleMoneyPowerUp(spawnPoint, player, match,this, testing);
            case 3:
                return new PrepTimePowerUp(spawnPoint, player, match,this, testing, kitchen);
            default:
                return new SpeedPowerUp(spawnPoint, player, match,this, testing);
        }

    }
    /**

     Gets a random spawn point for the power up to be placed on the map

     @return A Vector2 representing the position where the power up will be spawned
     */

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
    /**

     Returns the list of active power ups in the game
     @return An ArrayList of PowerUp objects representing the active power ups in the game
     */
    public ArrayList<PowerUp> getActivePowerUps(){
        return activePowerUps;
    }

    public void setPlayer(Player p){
        player = p;
    }
}
