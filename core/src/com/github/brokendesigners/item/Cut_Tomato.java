package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cut_Tomato extends Item{

	public Texture texture = new Texture("items/WMD.png");

	public Cut_Tomato() {
		super("Cut_Tomato");

	}


	@Override
	public Texture getTexture() {
		return texture;
	}


}
