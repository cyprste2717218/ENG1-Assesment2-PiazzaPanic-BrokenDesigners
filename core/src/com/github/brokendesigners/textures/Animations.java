package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;

public class Animations {

	public static Animation<TextureRegion> cuttingAnimation =
		new Animation<TextureRegion>(0.15f, Atlases.cuttingTextures.findRegions("knife"), PlayMode.LOOP);

	public static Animation<TextureRegion> pattyFormingAnimation =
		new Animation<TextureRegion>(0.15f, Atlases.pattyTextures.findRegions("meat"), PlayMode.LOOP);

	public static Animation<TextureRegion> gearAnimation =
		new Animation<TextureRegion>(0.15f, Atlases.gearImages.findRegions("gear"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_moveAnimation =
			new Animation<TextureRegion>(0.15f, Atlases.glibbertTextures.findRegions("running"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_idleAnimation =
			new Animation<TextureRegion>(1.3f, Atlases.glibbertTextures.findRegions("idle"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_actionAnimation=
			new Animation<TextureRegion>(0.3f, Atlases.glibbertTextures.findRegions("action"), PlayMode.LOOP);

	public static Animation<TextureRegion> glibbert_moveAnimation2 =
		new Animation<TextureRegion>(0.15f, Atlases.glibbertTextures2.findRegions("running"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_idleAnimation2 =
		new Animation<TextureRegion>(1.3f, Atlases.glibbertTextures2.findRegions("idle"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_actionAnimation2=
		new Animation<TextureRegion>(0.3f, Atlases.glibbertTextures2.findRegions("action"), PlayMode.LOOP);

	public static Animation<TextureRegion> glibbert_moveAnimation3 =
			new Animation<TextureRegion>(0.15f, Atlases.glibbertTextures3.findRegions("running"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_idleAnimation3 =
			new Animation<TextureRegion>(1.3f, Atlases.glibbertTextures3.findRegions("idle"), PlayMode.LOOP);
	public static Animation<TextureRegion> glibbert_actionAnimation3=
			new Animation<TextureRegion>(0.3f, Atlases.glibbertTextures3.findRegions("action"), PlayMode.LOOP);

	public static Animation<TextureRegion> bluggus_idleAnimation =
			new Animation<TextureRegion>(1.3f, Atlases.bluggusTextures.findRegions("bluggus_idle"), PlayMode.LOOP);
	public static Animation<TextureRegion> bluggus_moveAnimation =
			new Animation<TextureRegion>(0.23f, Atlases.bluggusTextures.findRegions("bluggus_move"), PlayMode.LOOP);
}
