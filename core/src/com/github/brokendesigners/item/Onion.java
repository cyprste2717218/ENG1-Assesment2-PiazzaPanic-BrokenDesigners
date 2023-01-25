package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Onion extends Item{

	public Texture texture = new Texture("items/onion.png");

	public Onion() {
		super("Onion");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
