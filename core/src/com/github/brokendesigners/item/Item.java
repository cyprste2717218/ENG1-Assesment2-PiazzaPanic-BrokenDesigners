package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {

	public boolean active = false;
	public Texture texture;
	public String name;

	public Item(){

	}
	public Texture getTexture(){
		return this.texture;
	}



}
