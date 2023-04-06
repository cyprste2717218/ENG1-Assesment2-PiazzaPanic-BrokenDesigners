package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Meat extends Item{

	public Texture texture;

	public Meat() {
		super("Meat");
		texture = new Texture("items/meat.png");
	}
	public Meat(Texture texture)	{
		super("Meat");
	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
