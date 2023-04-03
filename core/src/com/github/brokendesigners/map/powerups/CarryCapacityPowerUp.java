package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;

public class CarryCapacityPowerUp extends PowerUp{

    Hand hand;
    public CarryCapacityPowerUp(Vector2 worldPosition, Player player, MainGame game, PowerUpManager powerUpManager) {
        super(worldPosition, player, game, 30f, powerUpManager);
        hand = player.hand;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/burger.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned carry capacity power up");
    }

    @Override
    public void activate() {
        hand.currentHandSize = hand.baseHandSize * 2;
    }

    @Override
    public void deactivate() {
        hand.currentHandSize = hand.baseHandSize;
    }
}
