package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.map.interactable.Station;

import java.util.ArrayList;

public class PrepTimePowerUp extends PowerUp{
    public PrepTimePowerUp(Vector2 worldPosition, Player player, MainGame game, PowerUpManager powerUpManager) {
        super(worldPosition, player, game, 15f, powerUpManager);
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Cooking.png")));
        sprite.setScale(0.125f);
        System.out.println("Spawned prep time power up");
    }

    @Override
    public void activate() {
        ArrayList<? extends Station> stations = game.getKitchen().getKitchenStations();
        for(Station station: stations){
            station.stationUseTime /= 2;
        }
    }

    @Override
    public void deactivate() {
        ArrayList<? extends Station> stations = game.getKitchen().getKitchenStations();
        for(Station station: stations){
            station.stationUseTime *= 2;
        }
    }
}