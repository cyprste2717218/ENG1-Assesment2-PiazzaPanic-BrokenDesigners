package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;

import java.util.ArrayList;
/**

 A class representing the player's hand, which can hold up to 4 items.
 Implements a first-in-first-out (FIFO) stack.
 */
public class Hand{

	public ArrayList<Item> heldItems;
	public int baseHandSize = 3;
	public int currentHandSize = baseHandSize;
	/**
	 * Instantiates Hand
	 */
	// a boolean to enable/disable the ability to pickup items
	// i.e. when interacting with station, cannot pickup item
	public boolean hand_ability;
	/**
	 * Constructs a new Hand object with an empty ArrayList of items
	 * and enables the hand's ability to pick up items by default.
	 */
	public Hand(){ // Instantiates Hand
		this.heldItems = new ArrayList<Item>(baseHandSize);
		hand_ability = true;
	}


	/**
	 * Adds an item to the hand's ArrayList, as long as the hand is not full
	 * and the hand's ability to pick up items is enabled.
	 *
	 * @param item the item to be added to the hand
	 */
	public void give(Item item){
		if (this.heldItems.size() <= currentHandSize && hand_ability){
			this.heldItems.add(item);

			System.out.println("held: " + this.heldItems);
		}
	}
	/**
	 * Removes and returns the specified item from the hand's ArrayList.
	 * Not currently used in the code.
	 *
	 * @param item the item to be removed from the hand
	 * @return the removed item
	 */
	public Item drop(Item item){
		this.heldItems.remove(item);
		return(item);
	}
	/**
	 * Removes and returns the top item from the hand's ArrayList (FIFO).
	 * Returns null if the hand is empty.
	 *
	 * @return the removed item
	 */
	public Item drop() {

		if (!this.heldItems.isEmpty()) {
			int amountOfItems = this.heldItems.size();

			Item droppedItem = this.heldItems.get(
				amountOfItems - 1);
			this.heldItems.remove(amountOfItems - 1);


			return droppedItem;

		} else {
			return null;
		}
	}
	/**
	 * Returns whether the hand's ArrayList is empty.
	 *
	 * @return true if the hand is empty, false otherwise
	 */
	public boolean isEmpty(){
		return this.heldItems.isEmpty();
	}
	/**
	 * Returns whether the hand's ArrayList is full.
	 *
	 * @return true if the hand is full, false otherwise
	 */
	public boolean isFull(){
		return heldItems.size() >= currentHandSize;
	}

	/**
	 * Returns the ArrayList of items currently held in the hand.
	 * Used by PlayerRenderer to render items on the player.
	 *
	 * @return the ArrayList of held items
	 */
	public ArrayList<Item> getHeldItems(){
		return heldItems;
	}
	/**
	 * Disables the hand's ability to pick up items.
	 */
	public void disable_hand_ability()	{
		hand_ability = false;
	}
	/**
	 * Enables the hand's ability to pick up items.
	 */
	public void enable_hand_ability()	{
		hand_ability = true;
	}

	public void setHeldItems(ArrayList<String> items){
		for(String i : items){
			heldItems.add(ItemRegister.itemRegister.get(i));
		}
	}
}