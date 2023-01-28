package com.github.brokendesigners.textures;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class CuttingTextures {
	public static TextureAtlas cuttingTextures = new TextureAtlas("indicators/cutting/cutting.atlas");
	public static TextureRegion knife_1;
	public static TextureRegion knife_2;
	public static TextureRegion knife_3;
	public static TextureRegion knife_4;
	public static TextureRegion knife_5;
	public static TextureRegion knife_6;



	private void loadTextures(){
		knife_1 = cuttingTextures.findRegion("knife_1");
		knife_2 = cuttingTextures.findRegion("knife_2");
		knife_3 = cuttingTextures.findRegion("knife_3");
		knife_4 = cuttingTextures.findRegion("knife_4");
		knife_5 = cuttingTextures.findRegion("knife_5");
		knife_6 = cuttingTextures.findRegion("knife_6");
	}
	public void dispose(){
		cuttingTextures.dispose();
	}

}
