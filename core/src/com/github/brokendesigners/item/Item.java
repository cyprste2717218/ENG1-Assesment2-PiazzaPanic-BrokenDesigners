package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
/*
 * Simple item, not yet in the register
 *
 * All items hold a texture, a name, and whether it is cooking or not.
 */
public abstract class Item implements Disposable {

	public boolean active = false;
	public Texture texture;
	public String name;
	public Boolean Cooking;
	public Boolean Baking;

	public Item(String n){
		this.name = n;
		this.Cooking = false;
		this.Baking = false;
	}

	public Texture getTexture(){
		return this.texture;
	}

	public String getName()
	{
		return this.name;
	}
	public void setName(String x)
	{
		this.name = x;
	}

	public String toString(){
		return name;
	}

	@Override
	public void dispose(){
		this.texture.dispose();
	}
}



