package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BluggusTextures {
	public static TextureAtlas atlasImages = new TextureAtlas("pack textures/bluggus.pack");
	public static TextureRegion bluggus_idle_one;
	public static TextureRegion bluggus_idle_two;
	public static TextureRegion bluggus_inch_one;
	public static TextureRegion bluggus_inch_two;

	private void loadTextures(){
		bluggus_idle_one = atlasImages.findRegion("bluggus_idle_one");
		bluggus_idle_two = atlasImages.findRegion("bluggus_idle_two");
		bluggus_inch_one = atlasImages.findRegion("bluggus_inch_one");
		bluggus_inch_two = atlasImages.findRegion("bluggus_inch_two");
	}

}
