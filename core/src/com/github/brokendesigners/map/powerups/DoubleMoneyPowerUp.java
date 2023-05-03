package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;

/**
Created Class

 Represents a power up that doubles the amount of money earned from completed orders.
 Inherits from PowerUp class.
 */
public class DoubleMoneyPowerUp extends PowerUp{
    /**
     * Constructor for DoubleMoneyPowerUp class.
     * @param worldPosition The position in the game world to spawn the power up.
     * @param player The player that can pick up the power up.
     * @param match The match the power up is being used in.
     * @param powerUpManager The PowerUpManager that manages the power up.
     * @param testing A boolean that determines whether or not the game is in testing mode.
     */
    public DoubleMoneyPowerUp(Vector2 worldPosition, Player player, Match match, PowerUpManager powerUpManager, boolean testing) {
        super(worldPosition, player, match, 30, powerUpManager);
        if(testing) return;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Money.png")));
        sprite.setScale(0.125f);
        sprite.setPosition(worldPosition.x, worldPosition.y);
        System.out.println("Spawned double money power up");
    }
    /**
     * Activates the power up and doubles the amount of money earned from completed orders.
     */
    @Override
    public void activate() {
        match.hasMoneyPower = true;
    }

    /**
     * Deactivates the power up and sets the amount of money earned from completed orders back to normal.
     */
    @Override
    public void deactivate() {
        match.hasMoneyPower = false;
    }
}
