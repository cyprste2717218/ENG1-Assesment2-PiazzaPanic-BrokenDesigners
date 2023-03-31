package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;

import java.util.ArrayList;

public class CustomerWaitTimePowerUp extends PowerUp{

    private CustomerManager customerManager;

    public CustomerWaitTimePowerUp(Vector3 worldPosition, float width, float height, Player player, MainGame game, CustomerManager customerManager, float time) {
        super(worldPosition, width, height, player, game, time);
        this.customerManager = customerManager;
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
