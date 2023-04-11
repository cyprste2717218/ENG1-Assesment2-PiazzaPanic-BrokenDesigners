package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Salad extends Item{

	public Texture texture = new Texture("items/salad.png");

	public Salad() {
		super("Salad", 15);

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
