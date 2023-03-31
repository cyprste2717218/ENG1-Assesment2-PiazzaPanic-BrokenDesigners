package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;

public class SpeedPowerUp extends PowerUp{

    public SpeedPowerUp(Vector3 worldPosition, float width, float height, Player player, MainGame game, float time) {
        super(worldPosition, width, height, player, game, time);
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
