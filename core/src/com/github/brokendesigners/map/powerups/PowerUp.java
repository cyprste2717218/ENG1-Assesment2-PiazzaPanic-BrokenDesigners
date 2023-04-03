package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.map.KitchenCollisionObject;

public abstract class PowerUp {

    protected Timer timer;
    protected Player player;
    protected MainGame game;
    protected Match match;
    protected float time;
    protected Sprite sprite;
    private PowerUpManager powerUpManager;
    public PowerUp(Vector2 worldPosition, Player player, MainGame game, float time, final PowerUpManager powerUpManager) {
        timer = new Timer();
        this.player = player;
        this.game = game;
        match = game.match;
        this.time = time;
        this.powerUpManager = powerUpManager;
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                powerUpManager.getActivePowerUps().remove(this);
                System.out.println("Power up deleted");
            }
        }, 15f);
    }
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
    public abstract void activate();
    public abstract void deactivate();

    public Sprite getSprite(){
        return sprite;
    }

}
