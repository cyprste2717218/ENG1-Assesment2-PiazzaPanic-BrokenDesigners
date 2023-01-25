package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Patty extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Patty() {
		super("Patty");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
