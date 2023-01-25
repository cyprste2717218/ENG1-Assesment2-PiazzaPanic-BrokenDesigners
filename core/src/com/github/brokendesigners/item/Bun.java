package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Bun extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Bun() {
		super("Bun");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
