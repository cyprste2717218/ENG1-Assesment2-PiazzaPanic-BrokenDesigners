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

	Sound pick_up = Gdx.audio.newSound(Gdx.files.internal("assets/audio/pick_up.wav"));
	Sound put_down = Gdx.audio.newSound(Gdx.files.internal("assets/audio/put_down.wav"));

	public void give(Item item){

		if (this.heldItems.size() == 3){
			return;
		}
		pick_up.play();
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

			put_down.play();

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

	public void dispose () {
		pick_up.dispose();
		put_down.dispose();
	}
}