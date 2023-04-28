package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;

import java.util.ArrayList;
/*
 * Hand stack of size 4.
 * Acts as a FIFO stack.
 */
public class Hand{

	public ArrayList<Item> heldItems;
	public int baseHandSize = 3;
	public int currentHandSize = baseHandSize;
	/*
	 * Instantiates Hand
	 */
	// a boolean to enable/disable the ability to pickup items
	// i.e. when interacting with station, cannot pickup item
	public boolean hand_ability;
	public Hand(){ // Instantiates Hand
		this.heldItems = new ArrayList<Item>(baseHandSize);
		hand_ability = true;
	}


	/*
	 * Gives item to hand. Use an ItemRegister reference for the item to be recognisable to stations.
	 */
	public void give(Item item){
		if (this.heldItems.size() <= currentHandSize && hand_ability){
			this.heldItems.add(item);

			System.out.println("held: " + this.heldItems);
		}
	}
	/*
	 * drops a specific item - not used
	 */
	public Item drop(Item item){
		this.heldItems.remove(item);
		return(item);
	}
	/*
	 * Drops the top item on the stack.
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
	/*
	 *  Returns if hand is empty
	 */
	public boolean isEmpty(){
		return this.heldItems.isEmpty();
	}
	/*
	 * Returns if hand is full
	 */
	public boolean isFull(){
		return heldItems.size() >= currentHandSize;
	}

	/*
	 * Returns array of held items - used for PlayerRenderer to render items on player.
	 */
	public ArrayList<Item> getHeldItems(){
		return heldItems;
	}
	public void disable_hand_ability()	{
		hand_ability = false;
	}
	public void enable_hand_ability()	{
		hand_ability = true;
	}

	public void setHeldItems(ArrayList<String> items){
		for(String i : items){
			heldItems.add(ItemRegister.itemRegister.get(i));
		}
	}
}