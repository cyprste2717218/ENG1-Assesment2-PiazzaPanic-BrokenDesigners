package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.item.Item;

public class CustomerStation extends CounterStation{

	public Vector3 customerPosition;
	public Customer servedCustomer;


	public CustomerStation(Vector3 objectPosition, float width, float height, float handX, float handY) {
		super(objectPosition, width, height, handX, handY);
		this.customerPosition = new Vector3(handX - (32 * Constants.UNIT_SCALE), handY, 0);
		System.out.println(customerPosition);
		System.out.println(this.handPosition);
	}

	public boolean isFree(){
		if (servedCustomer == null){
			return true;
		}
		else {
			return false;
		}
	}
	public Vector3 getCustomerPosition(){
		return customerPosition;

	}

	public Item getItemInHand(){
		return this.hand;
	}


}
