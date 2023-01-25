package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {

	public boolean active = false;
	public Texture texture;
	public String name;
	public Boolean Cut;
	public Boolean Cooking;
	public Boolean Cooked;
	public Boolean Waste;

	public Item(String n){
		this.name = n;
		this.Cut = false;
		this.Cooking = false;
		this.Cooked = false;
		this.Waste = false;

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
