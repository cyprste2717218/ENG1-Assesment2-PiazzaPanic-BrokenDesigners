package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.Player;

public class BinStation extends Station{
	/**
	 * Creates a new Bin Station with a given interaction area.
	 *
	 * @param interactionArea the Rectangle that defines the interaction area of this Bin Station.
	 */
	public BinStation(Rectangle interactionArea) {
		super(interactionArea,"bin_station");
	}
	public BinStation()	{

	}
	/**
	 * Attempts to drop off the item held by the player's hand at this Bin Station.
	 * If the player's hand is empty, this method will return false and nothing will happen.
	 * Otherwise, the item will be dropped off and a sound effect will play.
	 *
	 * @param player the Player object representing the player interacting with this Bin Station.
	 * @return true if the item was successfully dropped off, false otherwise.
	 */
	@Override
	public boolean dropOff(Player player){
		if (player.hand.isEmpty()){
			return false;
		}
		player.hand.drop();
		put_down.play();
		return true;
	}

	@Override
	public boolean pickUp(Player player) {
		return false;
	}

}
