package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;

public class PrepTimePowerUp extends PowerUp{
    public PrepTimePowerUp(Vector3 worldPosition, float width, float height, Player player, MainGame game, float time) {
        super(worldPosition, width, height, player, game, time);
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }
}
