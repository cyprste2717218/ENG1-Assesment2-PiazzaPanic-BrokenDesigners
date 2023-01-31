package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.github.brokendesigners.item.Item;
import java.util.ArrayList;

public class Hand{

	public ArrayList<Item> heldItems;

	public Hand(){
		this.heldItems = new ArrayList<Item>(3);
	}

	public void give(Item item){

		if (this.heldItems.size() == 3){
			return;
		}
		this.heldItems.add(item);
	}

	public Item drop(Item item){
		this.heldItems.remove(item);
		return(item);
	}

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

	public boolean isEmpty(){
		return this.heldItems.isEmpty();
	}

	public boolean isFull(){
		if (this.heldItems.size() == 3){
			return true;
		}
		else{
			return false;
		}
	}
	public ArrayList<Item> getHeldItems(){
		return heldItems;
	}


}