package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class Atlases {
	public static TextureAtlas gearImages = new TextureAtlas("indicators/gear/gear.atlas");
	public static TextureAtlas bluggusTextures = new TextureAtlas("pack textures/bluggus.pack");
	public static TextureAtlas glibbertTextures = new TextureAtlas("pack textures/chef.atlas");
	public static TextureAtlas pattyTextures = new TextureAtlas("indicators/patty forming/meat.atlas");
	public static TextureAtlas cuttingTextures = new TextureAtlas("indicators/cutting/cutting.atlas");

	public static void dispose(){
		gearImages.dispose();
		bluggusTextures.dispose();
		glibbertTextures.dispose();
		pattyTextures.dispose();
		cuttingTextures.dispose();
	}

}
