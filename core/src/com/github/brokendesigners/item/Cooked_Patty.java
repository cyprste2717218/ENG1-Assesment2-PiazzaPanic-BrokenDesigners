package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cooked_Patty extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Cooked_Patty() {
		super("Cooked_Patty");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
