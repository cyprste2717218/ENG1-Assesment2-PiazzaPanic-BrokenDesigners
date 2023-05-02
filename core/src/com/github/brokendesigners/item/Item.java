package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
/**

 An abstract class representing a simple item that is not yet registered.
 All items have a texture, a name, and a cooking/baking status.
 */

public abstract class Item implements Disposable {

	public boolean active = false;
	public Texture texture;
	public String name;
	public Boolean Cooking;
	public Boolean Baking;
  
	/**
	 * Constructs an item with the specified name and default cooking and baking statuses.
	 * @param n The name of the item.
	 */
	public Item(String n){
		this.name = n;
		this.Cooking = false;
		this.Baking = false;
	}
	/**
	 * Returns the texture of the item.
	 * @return The texture of the item.
	 */
	public Texture getTexture(){
		return this.texture;
	}

	/**
	 * Returns the name of the item.
	 * @return The name of the item.
	 */

	public String getName()
	{
		return this.name;
	}
	/**
	 * Sets the name of the item to the specified value.
	 * @param x The new name of the item.
	 */
	public void setName(String x)
	{
		this.name = x;
	}

	/**
	 * Returns a string representation of the item.
	 * @return A string representation of the item.
	 */
	public String toString(){
		return name;
	}

	@Override
	public void dispose(){
		this.texture.dispose();
	}
}



