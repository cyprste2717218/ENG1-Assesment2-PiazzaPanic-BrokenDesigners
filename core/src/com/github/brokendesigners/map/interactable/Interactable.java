package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.Player;
import java.lang.reflect.InvocationTargetException;

public abstract class Interactable {

	protected Rectangle interactionArea;

	protected Interactable(Rectangle rectangle) {
		this.interactionArea = rectangle;
	}


	public boolean pickup(Player player)
		throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		return false;
	}

	public Rectangle getInteractionArea() {
		return interactionArea;
	}

	public boolean dropOff(Player player){
		return false;
	}

	public boolean action(Player player){
		return false;
	}

}
