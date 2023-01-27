package com.github.brokendesigners.map.interactable;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;


public class CounterStation extends Station {

	Vector3 handPosition;


	public CounterStation(Rectangle interactionArea, float handX, float handY) {

		super(interactionArea,"Counter_Station");

		handPosition = new Vector3(handX, handY, 0);


	}
	public CounterStation(Vector3 objectPosition, float width, float height, float handX, float handY){
		super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Counter_Station");
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


	public Item formPatty() //Turn meat into patty
	{
		if(hand.getName()=="Meat")
		{
			hand =  ItemRegister.itemRegister.get("Patty");
		}
		return hand;

	}

	@Override
	public boolean action(Player player){
		if (hand == null){
			return false;
		} else if (hand.equals(ItemRegister.itemRegister.get("Meat"))) {
			this.dumpHand();
			this.hand = ItemRegister.itemRegister.get("Patty");
			return true;

		}
		return false;
	}



}
