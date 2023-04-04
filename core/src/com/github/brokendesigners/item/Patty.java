package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Patty extends Item{

	public Texture texture;

	public Patty() {
		super("Patty");
		texture = new Texture("items/patty.png");
	}
	public Patty(Texture texture)	{
		super("Patty");
	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
