package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PattyFormingTextures {
	public static TextureAtlas pattyTextures = new TextureAtlas("indicators/patty forming/meat.atlas");
	public static TextureRegion meat_1;
	public static TextureRegion meat_2;
	public static TextureRegion meat_3;
	public static TextureRegion meat_4;
	public static TextureRegion meat_5;
	public static TextureRegion meat_6;



	private void loadTextures(){
		meat_1 = pattyTextures.findRegion("meat_1");
		meat_2 = pattyTextures.findRegion("knife_2");
		meat_3 = pattyTextures.findRegion("knife_3");
		meat_4 = pattyTextures.findRegion("knife_4");
		meat_5 = pattyTextures.findRegion("knife_5");
		meat_6 = pattyTextures.findRegion("knife_6");
	}
	public void dispose(){
		pattyTextures.dispose();
	}

}

