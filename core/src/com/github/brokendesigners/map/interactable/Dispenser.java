package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.NuclearWeapon;
import java.lang.reflect.InvocationTargetException;

public class Dispenser extends Interactable {

	protected final Class<? extends Item> dispenserItem;


	public Dispenser(Rectangle interactionArea, Class<? extends Item> dispensorItem) {
		super(interactionArea);
		this.dispenserItem = dispensorItem;
	}

	public Dispenser(Vector3 worldPosition, float width, float height, Class<? extends Item> dispenserItem){
		super(new Rectangle(worldPosition.x, worldPosition.y, width, height));
		this.dispenserItem = dispenserItem;
	}


	@Override
	public boolean pickup(Player player)
		throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		player.hand.give(this.dispenserItem.getConstructor().newInstance());


		return true;
	}

}
