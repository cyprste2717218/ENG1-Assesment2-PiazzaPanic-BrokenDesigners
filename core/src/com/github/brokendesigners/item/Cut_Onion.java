package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cut_Onion extends Item{

	public Texture texture = new Texture("items/onion_cut.png");

	public Cut_Onion() {
		super("Cut_Onion");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
