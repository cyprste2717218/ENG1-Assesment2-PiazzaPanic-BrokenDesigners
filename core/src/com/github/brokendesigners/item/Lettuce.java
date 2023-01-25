package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Lettuce extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Lettuce() {
		super("Lettuce");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
