package com.github.brokendesigners.map.interactable;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import java.util.ConcurrentModificationException;

public class CounterStation extends Station {

	Vector3 handPosition;
	Item hand = null;


	public CounterStation(Rectangle interactionArea, float handX, float handY) {

		super(interactionArea);

		handPosition = new Vector3(handX, handY, 0);


	}
	public CounterStation(Vector3 objectPosition, float width, float height, float handX, float handY){
		super(new Rectangle(objectPosition.x, objectPosition.y, width, height));
		handPosition = new Vector3(handX, handY, 0);
		System.out.println(handX);
		System.out.println(handY);
	}


	public void renderCounter(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		if (!this.hasEmptyHand()) {
			spriteBatch.draw(this.hand.getTexture(), this.handPosition.x, this.handPosition.y, 16 * Constants.UNIT_SCALE, 16 * Constants.UNIT_SCALE);
		}
		spriteBatch.end();

	}
	public boolean hasEmptyHand(){
		if (this.hand == null){
			return true;
		} else {
			return false;
		}
	}

	public void dumpHand(){
		this.hand = null;
	}

	@Override
	public boolean pickUp(Player player){
		if (this.hasEmptyHand() || player.hand.isFull()){
			return false;
		} else {
			player.hand.give(hand);
			this.dumpHand();
			return true;
		}
	}
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
