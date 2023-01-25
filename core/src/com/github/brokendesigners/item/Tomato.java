package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Tomato extends Item{

	public Texture texture = new Texture("items/tomato.png");

	public Tomato() {
		super("Tomato");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
