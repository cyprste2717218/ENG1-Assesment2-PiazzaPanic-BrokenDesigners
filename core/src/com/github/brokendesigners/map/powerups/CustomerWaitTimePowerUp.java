package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;

import java.util.ArrayList;

public class CustomerWaitTimePowerUp extends PowerUp{

    private CustomerManager customerManager;

    public CustomerWaitTimePowerUp(Vector2 worldPosition, Player player, MainGame game, CustomerManager customerManager, PowerUpManager powerUpManager) {
        super(worldPosition, player, game, 30f, powerUpManager);
        this.customerManager = customerManager;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/burger.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned customer wait time power up");
    }

    @Override
    public void activate() {
        ArrayList<Customer> customers = customerManager.getCustomers();
        for(Customer customer : customers){
            customer.customerWaitTime += 10000L;
        }
    }

    @Override
    public void deactivate() {
    }
}
