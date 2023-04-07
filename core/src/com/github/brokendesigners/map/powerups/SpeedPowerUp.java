package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;

public class SpeedPowerUp extends PowerUp{

    public SpeedPowerUp(Vector2 worldPosition, Player player, MainGame game, PowerUpManager powerUpManager) {
        super(worldPosition, player, game, 15f, powerUpManager);
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Speed.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned speed power up");
    }

    @Override
    public void activate() {
        player.setMovementSpeed(5 * Constants.UNIT_SCALE);
    }

    @Override
    public void deactivate() {
        player.setMovementSpeed(2 * Constants.UNIT_SCALE);
    }
}
