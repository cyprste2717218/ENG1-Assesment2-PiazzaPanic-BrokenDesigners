package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ChefTextures {
	public static TextureAtlas atlasImages = new TextureAtlas("pack textures/chef.atlas");
	public static TextureRegion chef_idle_one;
	public static TextureRegion chef_idle_two;
	public static TextureRegion chef_move_one;
	public static TextureRegion chef_move_two;

	private void loadTextures() {
		chef_idle_one = atlasImages.findRegion("idle_0");
		chef_idle_two = atlasImages.findRegion("idle_1");
		chef_move_one = atlasImages.findRegion("running_0");
		chef_move_two = atlasImages.findRegion("running_1");
	}

	public void dispose(){
		atlasImages.dispose();

	}

}
