package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
/*
 * Contains atlases for textures for lots of things.
 *
 * Atlases are used to address regions of a png containing multiple assets.
 *
 * Atlases are used for animation.
 */
public class Atlases {
	public static TextureAtlas gearImages = new TextureAtlas("indicators/gear/gear.atlas");
	// Frames for gear animation

	public static TextureAtlas countdownImages = new TextureAtlas("indicators/countdown/countdown.atlas");
	// Frames for countdown animation

	public static TextureAtlas attentionImages = new TextureAtlas("indicators/attention/attention.atlas");
	//Frames for attention animation
	public static TextureAtlas bluggusTextures = new TextureAtlas("characters/bluggus.pack");
	//frames for customer bluggus
	public static TextureAtlas glibbertTextures = new TextureAtlas("pack textures/chef.atlas");
	// frames for glibbertTextures
	public static TextureAtlas glibbertTextures2 = new TextureAtlas("pack textures/chef2.atlas");
	public static TextureAtlas glibbertTextures3 = new TextureAtlas("pack textures/chef3.atlas");

	public static TextureAtlas pattyTextures = new TextureAtlas("indicators/patty forming/meat.atlas");
	// frames for patty forming animation
	public static TextureAtlas cuttingTextures = new TextureAtlas("indicators/cutting/cutting.atlas");
	// frames for cutting animation (the red is not meant to be blood)



	public static void dispose(){
		gearImages.dispose();
		bluggusTextures.dispose();
		glibbertTextures.dispose();
		glibbertTextures2.dispose();
		glibbertTextures3.dispose();
		pattyTextures.dispose();
		cuttingTextures.dispose();
	}

}
