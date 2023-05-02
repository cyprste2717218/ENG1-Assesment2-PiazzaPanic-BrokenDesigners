package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.renderer.BubbleRenderer;

public class CustomerStation extends CounterStation{

	public Vector2 customerPosition;
	public boolean servingCustomer;

	/**
	 * Creates a new CustomerStation with a specific position, width, height, hand position, bubbleRenderer, and match.
	 *
	 * @param objectPosition The position of the CustomerStation.
	 * @param width The width of the CustomerStation.
	 * @param height The height of the CustomerStation.
	 * @param handX The x position of the hand.
	 * @param handY The y position of the hand.
	 * @param bubbleRenderer The BubbleRenderer used to render speech bubbles.
	 * @param match The current Match.
	 */
	public CustomerStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, Match match) {
		super(objectPosition, width, height, handX, handY, bubbleRenderer, match);
		this.customerPosition = new Vector2(handX - (32 * Constants.UNIT_SCALE), handY);
		System.out.println(customerPosition);
		System.out.println(this.handPosition);
		servingCustomer = false;
		station_name = "Customer_Station";
	}
	/**
	 * Returns true if the CustomerStation is currently serving a customer, false otherwise.
	 *
	 * @return True if the CustomerStation is currently serving a customer, false otherwise.
	 */
	public boolean isServingCustomer(){
		return servingCustomer;
	}
	/**
	 * Sets whether or not the CustomerStation is currently serving a customer.
	 *
	 * @param free True if the CustomerStation is not currently serving a customer, false otherwise.
	 */
	public void setServingCustomer(boolean free){
		this.servingCustomer = free;
	}
	/**
	 * Returns the position of the customer.
	 *
	 * @return The position of the customer.
	 */
	public Vector2 getCustomerPosition(){
		return customerPosition;

	}
	/**
	 * Returns the Item currently in the CustomerStation's hand.
	 *
	 * @return The Item currently in the CustomerStation's hand.
	 */
	public Item getItemInHand(){
		return this.hand;
	}
	/**
	 * Attempts to drop off the Item in the Player's hand to the CustomerStation.
	 *
	 * @param player The Player attempting to drop off the Item.
	 * @return True if the drop off was successful, false otherwise.
	 */
	@Override
	public boolean dropOff(Player player){
		if (this.hasEmptyHand()){
			this.hand = player.hand.drop();
			return true;
		} else {
			return false;
		}
	}


}
