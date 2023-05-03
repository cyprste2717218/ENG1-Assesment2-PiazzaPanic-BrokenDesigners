package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;

/**
 * Created Class

 SpeedPowerUp class represents a power up that increases the player's movement speed.
 */

public class SpeedPowerUp extends PowerUp{
    /**
     * Constructs a SpeedPowerUp object.
     * @param worldPosition The position of the power up in the game world.
     * @param player The player that picks up the power up.
     * @param match The match the power up is in.
     * @param powerUpManager The manager that keeps track of all the power ups in the game.
     * @param testing True if the power up is being used in a testing environment.
     */
    public SpeedPowerUp(Vector2 worldPosition, Player player, Match match, PowerUpManager powerUpManager, boolean testing) {
        super(worldPosition, player, match, 15f, powerUpManager);
        if(testing) return;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Speed.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned speed power up");
    }
    /**
     * Increases the player's movement speed.
     */
    @Override
    public void activate() {
        player.setMovementSpeed(5 * Constants.UNIT_SCALE);
    }
    /**
     * Decreases the player's movement speed to its original value.
     */
    @Override
    public void deactivate() {
        player.setMovementSpeed(2 * Constants.UNIT_SCALE);
    }
}
