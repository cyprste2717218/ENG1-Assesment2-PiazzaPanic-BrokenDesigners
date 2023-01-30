package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public abstract class Item implements Disposable {

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
	@Override
	public void dispose(){
		this.texture.dispose();
	}


}
