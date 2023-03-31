package com.github.brokendesigners.map;

import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;

public class PowerUp extends KitchenCollisionObject {
    private final String power;

    public PowerUp(Vector3 worldPosition, float width, float height, String power) {
        super(worldPosition, width, height);
        this.power = power;
    }
    public void activate () {
        switch (this.power) {
            case "speed":
                Player.speedIncrease(2);
            case "preptime":

            case "customertime":

            case "carrymore":
                // chef currently has unlimited hand already...
            case "money":
                Match.moneyPower = true;
        }
    }
}
