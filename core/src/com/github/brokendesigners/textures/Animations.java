package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animations {

	public static Animation<TextureRegion> cuttingAnimation =
		new Animation<TextureRegion>(0.15f, CuttingTextures.cuttingTextures.findRegions("knife"), PlayMode.LOOP);

	public static Animation<TextureRegion> pattyFormingAnimation =
		new Animation<TextureRegion>(0.15f, PattyFormingTextures.pattyTextures.findRegions("meat"), PlayMode.LOOP);

	public static Animation<TextureRegion> gearAnimation =
		new Animation<TextureRegion>(0.15f, Atlases.gearImages.findRegions("gear"), PlayMode.LOOP);



	public void dispose(){

	}
}
