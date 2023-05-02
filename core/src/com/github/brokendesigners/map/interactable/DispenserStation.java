package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import java.lang.reflect.InvocationTargetException;
/**

 Represents a dispenser station, which extends the Station class. Dispenser stations allow a player to pick up a specified item.
 */
public class DispenserStation extends Station {
	/**
	 * The item that is dispensed by the station.
	 */
	protected final Item dispenserItem;
	/**
	 * Creates a new DispenserStation object with a specified world position, width, height, and item to be dispensed.
	 * @param worldPosition the world position of the dispenser station
	 * @param width the width of the dispenser station
	 * @param height the height of the dispenser station
	 * @param dispenserItem the item to be dispensed by the station
	 */
	public DispenserStation(Vector2 worldPosition, float width, float height, Item dispenserItem){
		super(new Rectangle(worldPosition.x, worldPosition.y, width, height),"dispenser_station");
		this.dispenserItem = dispenserItem;
	}
	/**
	 * Creates a new DispenserStation object with the specified item to be dispensed.
	 * @param dispenserItem the item to be dispensed by the station
	 */
	public DispenserStation(Item dispenserItem)	{
		this.station_name = "dispenser_station";
		this.dispenserItem = dispenserItem;
	}
	/**
	 * Allows the player to pick up the item that the dispenser station is dispensing.
	 * @param player the player picking up the item
	 * @return true if the player successfully picks up the item, false otherwise
	 */
	@Override
	public boolean pickUp(Player player) {
		if (player.hand.isFull())	{
			failure.play();
			return false;
		}
		player.hand.give(dispenserItem);
		System.out.println("HAND==="+player.hand.getHeldItems());

		pick_up.play();

		return true;
	}
	/**
	 * Does not allow the player to drop off items at a dispenser station.
	 * @param player the player attempting to drop off an item
	 * @return always returns false
	 */
	@Override
	public boolean dropOff(Player player)
	{
		return false;
	}

}
