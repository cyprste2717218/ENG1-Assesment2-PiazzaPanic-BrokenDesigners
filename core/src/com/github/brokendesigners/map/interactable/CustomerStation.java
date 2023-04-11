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




	public CustomerStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, Match match) {
		super(objectPosition, width, height, handX, handY, bubbleRenderer, match);
		this.customerPosition = new Vector2(handX - (32 * Constants.UNIT_SCALE), handY);
		System.out.println(customerPosition);
		System.out.println(this.handPosition);
		servingCustomer = false;
	}

	public boolean isServingCustomer(){
		return servingCustomer;
	}
	public void setServingCustomer(boolean free){
		this.servingCustomer = free;
	}

	public Vector2 getCustomerPosition(){
		return customerPosition;

	}

	public Item getItemInHand(){
		return this.hand;
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
