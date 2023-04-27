package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;

public class DoubleMoneyPowerUp extends PowerUp{
    public DoubleMoneyPowerUp(Vector2 worldPosition, Player player, Match match, PowerUpManager powerUpManager, boolean testing) {
        super(worldPosition, player, match, 30, powerUpManager);
        if(testing) return;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Money.png")));
        sprite.setScale(0.125f);
        sprite.setPosition(worldPosition.x, worldPosition.y);
        System.out.println("Spawned double money power up");
    }

    @Override
    public void activate() {
        match.hasMoneyPower = true;
    }

    @Override
    public void deactivate() {
        match.hasMoneyPower = false;
    }
}
