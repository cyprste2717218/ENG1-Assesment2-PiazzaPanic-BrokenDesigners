package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Tomato extends Item{

	public Tomato() {
		super("Tomato");
		texture = new Texture("items/tomato.png");
	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
