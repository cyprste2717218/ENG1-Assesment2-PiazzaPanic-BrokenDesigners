package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cooked_Bun extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Cooked_Bun() {
		super("Cooked_Bun");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
