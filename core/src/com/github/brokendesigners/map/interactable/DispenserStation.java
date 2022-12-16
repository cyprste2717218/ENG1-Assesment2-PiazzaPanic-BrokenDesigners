package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import java.lang.reflect.InvocationTargetException;

public class DispenserStation extends Station {

	protected final Class<? extends Item> dispenserItem;


	public DispenserStation(Rectangle interactionArea, Class<? extends Item> dispensorItem) {
		super(interactionArea);
		this.dispenserItem = dispensorItem;
	}

	public DispenserStation(Vector3 worldPosition, float width, float height, Class<? extends Item> dispenserItem){
		super(new Rectangle(worldPosition.x, worldPosition.y, width, height));
		this.dispenserItem = dispenserItem;
	}


	@Override
	public boolean pickUp(Player player)
		throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		player.hand.give(this.dispenserItem.getConstructor().newInstance());


		return true;
	}

}
