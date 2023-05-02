package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Burger extends Item{

	public Texture texture = new Texture("items/burger.png");

	public Burger() {
		super("Burger");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
