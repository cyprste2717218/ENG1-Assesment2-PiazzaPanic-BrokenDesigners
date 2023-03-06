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
	private int reward;

	public Item(String n){
		this.name = n;
		this.Cooking = false;
		this.Baking = false;
		reward = 0;
	}
	public Item(String n, int reward){
		this.name = n;
		this.Cooking = false;
		this.reward = reward;
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
	@Override
	public void dispose(){
		this.texture.dispose();

	}

}



