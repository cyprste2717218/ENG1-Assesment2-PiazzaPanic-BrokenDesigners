package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Salad extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Salad() {
		super("Salad");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
