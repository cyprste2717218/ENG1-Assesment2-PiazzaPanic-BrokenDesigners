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

	public String difficultyToMake;

	public Item(String n){
		this.name = n;
		this.Cooking = false;
		this.Baking = false;
		this.difficultyToMake = "Invalid";
		reward = 0;
	}


	public Item(String n, int reward) {
		this.name = n;
		this.reward = reward;
		this.difficultyToMake = "Invalid";
	}

	public Item(String n, int reward, String difficultyToMake){
		this.name = n;
		this.Cooking = false;
		this.reward = reward;
		this.difficultyToMake = difficultyToMake;
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



