package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;

public class DoubleMoneyPowerUp extends PowerUp{
    public DoubleMoneyPowerUp(Vector3 worldPosition, float width, float height, Player player, MainGame game, float time) {
        super(worldPosition, width, height, player, game, time);
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
