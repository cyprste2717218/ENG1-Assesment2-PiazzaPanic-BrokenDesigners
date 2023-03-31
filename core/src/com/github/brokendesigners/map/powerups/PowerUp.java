package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.map.KitchenCollisionObject;

public abstract class PowerUp extends KitchenCollisionObject {

    protected Timer timer;
    protected Player player;
    protected MainGame game;
    protected Match match;
    protected float time;
    public PowerUp(Vector3 worldPosition, float width, float height, Player player, MainGame game, float time) {
        super(worldPosition, width, height);
        timer = new Timer();
        this.player = player;
        this.game = game;
        match = game.match;
        this.time = time;
    }
    public void usePowerUp(){
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                deactivate();
            }
        }, time);
        timer.start();
        activate();
    }
    public abstract void activate();
    public abstract void deactivate();

}
