package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Onion extends Item{

	public Texture texture;

	public Onion() {
		super("Onion");
		texture = new Texture("items/onion.png");
	}
	// this initialiser is used for testing, when making use of a fake texture
	public Onion(Texture texture)	{
		super("Onion");
	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
