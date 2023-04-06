package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cooked_Patty extends Item{

	public Texture texture;

	public Cooked_Patty() {
		super("Cooked_Patty");
		texture = new Texture("items/patty_cooked.png");
	}
	public Cooked_Patty(Texture texture)	{
		super("Cooked_Patty");
	}

	@Override
	public Texture getTexture() {
		return texture;
	}


}
