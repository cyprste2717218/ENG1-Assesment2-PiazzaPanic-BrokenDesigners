package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cut_Lettuce extends Item{

	public Texture texture = new Texture("items/lettuce_cut.png");

	public Cut_Lettuce() {
		super("Cut_Lettuce");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
