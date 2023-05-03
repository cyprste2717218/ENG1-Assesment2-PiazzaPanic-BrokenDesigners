package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.interactable.Station;

import java.util.ArrayList;

/**
 * Created Class

 The PrepTimePowerUp class represents a power up that decreases the preparation time required at all stations in the kitchen for a limited duration.
 */
public class PrepTimePowerUp extends PowerUp{
    private Kitchen kitchen;
    /**
     * Constructor for the PrepTimePowerUp class.
     * @param worldPosition The position of the power up in the game world.
     * @param player The player who collected the power up.
     * @param match The current match.
     * @param powerUpManager The power up manager.
     * @param testing A boolean indicating whether the game is in testing mode.
     * @param kitchen The kitchen in which the power up was spawned.
     */
    public PrepTimePowerUp(Vector2 worldPosition, Player player, Match match, PowerUpManager powerUpManager, boolean testing, Kitchen kitchen) {
        super(worldPosition, player, match, 15f, powerUpManager);
        this.kitchen = kitchen;
        if(testing) return;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Cooking.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned prep time power up");
    }
    /**
     * Activates the power up, reducing the preparation time required at all stations in the kitchen.
     */
    @Override
    public void activate() {
        ArrayList<Station> stations = kitchen.getKitchenStations();
        for(Station station: stations){
            station.stationUseTime /= 2;
        }
    }
    /**
     * Deactivates the power up, increasing the preparation time required at all stations in the kitchen back to their original values.
     */
    @Override
    public void deactivate() {
        ArrayList<Station> stations = kitchen.getKitchenStations();
        for(Station station: stations){
            station.stationUseTime *= 2;
        }
    }
}
