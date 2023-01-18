package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import java.lang.reflect.InvocationTargetException;

public class DispenserStation extends Station {

	protected final Item dispenserItem;


	public DispenserStation(Rectangle interactionArea, Item dispenserItem) {
		super(interactionArea);
		this.dispenserItem = dispenserItem;
	}

	public DispenserStation(Vector3 worldPosition, float width, float height, Item dispenserItem){
		super(new Rectangle(worldPosition.x, worldPosition.y, width, height));
		this.dispenserItem = dispenserItem;
	}


	@Override
	public boolean pickUp(Player player)
		throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		player.hand.give(dispenserItem);


		return true;
	}

}
