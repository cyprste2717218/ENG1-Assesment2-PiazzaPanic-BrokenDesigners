package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;

public class CarryCapacityPowerUp extends PowerUp{

    Hand hand;
    public CarryCapacityPowerUp(Vector3 worldPosition, float width, float height, Player player, MainGame game, float time) {
        super(worldPosition, width, height, player, game, time);
        hand = player.hand;
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
