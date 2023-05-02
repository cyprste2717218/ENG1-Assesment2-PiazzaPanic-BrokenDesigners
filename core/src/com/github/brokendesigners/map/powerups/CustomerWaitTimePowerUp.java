package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;

import java.util.ArrayList;

/**

 This class represents a power up that increases the wait time of all customers in the game when activated.
 It extends the abstract class PowerUp, and overrides the activate and deactivate methods to provide
 custom functionality specific to this power up.
 */
public class CustomerWaitTimePowerUp extends PowerUp{

    private CustomerManager customerManager;
    /**
     * Constructs a CustomerWaitTimePowerUp with the specified parameters. It also initializes the sprite of the power up.
     *
     * @param worldPosition The position of the power up in the game world.
     * @param player The player who collects the power up.
     * @param match The match instance.
     * @param customerManager The manager responsible for handling the customers in the game.
     * @param powerUpManager The manager responsible for handling the power ups in the game.
     * @param testing A boolean flag indicating if the game is in testing mode.
     */
    public CustomerWaitTimePowerUp(Vector2 worldPosition, Player player, Match match, CustomerManager customerManager, PowerUpManager powerUpManager, boolean testing) {
        super(worldPosition, player, match, 30f, powerUpManager);
        this.customerManager = customerManager;
        if(testing) return;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Customer.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned customer wait time power up");
    }
    /**
     * Increases the wait time of all customers in the game.
     *
     * It gets a list of all the customers from the customer manager and iterates through them,
     * adding a fixed amount of wait time to each customer.
     */
    @Override
    public void activate() {
        ArrayList<Customer> customers = customerManager.getCustomers();
        for(Customer customer : customers){
            customer.setCustomerWaitTime(customer.getCustomerWaitTime() + 10000L);
        }
    }
    /**
     * This method is not implemented because there is no need to undo the effect of the power up.
     */
    @Override
    public void deactivate() {
    }
    /**
     * Returns the customer manager of this power up.
     *
     * @return The customer manager instance.
     */
    public CustomerManager getCustomerManager(){
        return customerManager;
    }
}
