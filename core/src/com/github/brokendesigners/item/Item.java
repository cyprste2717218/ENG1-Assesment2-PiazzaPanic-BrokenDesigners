package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {

	public boolean active = false;
	public Texture texture;
	public String name;
	public Boolean Cooking;

	public Item(String n){
		this.name = n;
		this.Cooking = false;
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



}
