package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
/**
 * Created Class
 *
 *
 * The PowerUp class represents a power-up object that can be collected by the player in the game.
 * It is an abstract class that provides a basic implementation of a power-up, and it should be extended
 * by specific power-up classes that provide the specific behavior for each type of power-up.
 */
public abstract class PowerUp {

    protected Timer timer;
    protected Player player;
    protected MainGame game;
    protected Match match;
    protected float time;
    protected Vector2 worldPosition;
    protected Sprite sprite;
    private PowerUpManager powerUpManager;
    /**
     * Creates a new PowerUp object with the specified parameters.
     *
     * @param worldPosition The world position where the power-up should be spawned.
     * @param player The player who collects the power-up.
     * @param match The match instance.
     * @param time The duration of the power-up effect in seconds.
     * @param powerUpManager The power-up manager that controls the collection and activation of power-ups in the game.
     */
    public PowerUp(Vector2 worldPosition, Player player, Match match, float time, final PowerUpManager powerUpManager) {
        timer = new Timer();
        this.player = player;
        this.match = match;
        this.time = time;
        this.worldPosition = worldPosition;
        this.powerUpManager = powerUpManager;
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                powerUpManager.getActivePowerUps().remove(this);
                System.out.println("Power up deleted");
            }
        }, 15f);
    }

    /**
     * Uses the power-up object by activating its effect for the specified duration.
     */
    public void usePowerUp(){
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                deactivate();
                System.out.println("Power up deactivated");
            }
        }, time);
        timer.start();
        activate();
        powerUpManager.getActivePowerUps().remove(this);
        System.out.println("PowerUp used");
    }
    /**
     * Activates the effect of the power-up object.
     */
    public abstract void activate();
    /**
     * Deactivates the effect of the power-up object.
     */
    public abstract void deactivate();
    /**
     * Returns the sprite of the power-up object.
     *
     * @return The sprite of the power-up object.
     */
    public Sprite getSprite(){
        return sprite;
    }
    /**
     * Sets the sprite of the power-up object.
     *
     * @param s The new sprite to set.
     */
    public void setSprite(Sprite s){sprite = s;}
    public Player getPlayer(){
        return player;
    }
    public Match getMatch() {
        return match;
    }
    public Vector2 getSpawnPoint(){return worldPosition;}
    public Timer getTimer(){return timer;}
}
