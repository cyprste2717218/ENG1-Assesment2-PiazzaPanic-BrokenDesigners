package com.github.brokendesigners.map.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.MainGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;

public class CarryCapacityPowerUp extends PowerUp{

    Hand hand;
    /**
     * A power-up that doubles the player's hand capacity for a limited time.
     *
     * This power-up extends the abstract class PowerUp and overrides its activate() and deactivate() methods to adjust the
     * player's hand size accordingly. It also initializes a reference to the player's hand for easy access to its properties.
     *
     * @param worldPosition The position of the power-up in the game world.
     * @param player The player who will pick up the power-up.
     * @param match The current match being played.
     * @param powerUpManager The PowerUpManager instance that spawned this power-up.
     * @param testing Whether the game is being run in a testing environment.
     */
    public CarryCapacityPowerUp(Vector2 worldPosition, Player player, Match match, PowerUpManager powerUpManager, boolean testing) {
        super(worldPosition, player, match, 30f, powerUpManager);
        hand = player.hand;
        if(testing) return;
        sprite = new Sprite(new Texture(Gdx.files.internal("items/Items.png")));
        sprite.setPosition(worldPosition.x, worldPosition.y);
        sprite.setScale(0.125f);
        System.out.println("Spawned carry capacity power up");
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
