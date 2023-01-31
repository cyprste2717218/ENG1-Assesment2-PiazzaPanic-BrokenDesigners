package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Waste extends Item{

	public Texture texture = new Texture("items/waste.png");

	public Waste() {
		super("Waste");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
