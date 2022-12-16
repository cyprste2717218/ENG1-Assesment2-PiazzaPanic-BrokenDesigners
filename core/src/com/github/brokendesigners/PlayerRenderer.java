package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.textures.ChefTextures;

public class PlayerRenderer {

	private final Animation<TextureRegion> idleAnimation;
	private final Animation<TextureRegion> moveAnimation;
	private float stateTime;									// Will decide which frame of animation Glibbert is on
	private boolean isFacingRight = true;

	public final float WIDTH = 20 * Constants.UNIT_SCALE;		// Width of chef when drawn
	public final float HEIGHT = 36 * Constants.UNIT_SCALE;		// Height of Chef when drawn


	public PlayerRenderer(){

		this.stateTime = 0;

		moveAnimation =
			new Animation<TextureRegion>((float)(0.15 * 3f), ChefTextures.atlasImages.findRegions("running"), PlayMode.LOOP);

		idleAnimation =
			new Animation<TextureRegion>((float)(1.3 * 3f), ChefTextures.atlasImages.findRegions("idle"), PlayMode.LOOP);
			// frameTime multiplied by 3 because of multiple Glibberts



	}

	public void renderChef(SpriteBatch spriteBatch, Player player){
		stateTime += Gdx.graphics.getDeltaTime();
		spriteBatch.begin();

		Vector3 scr_pos = player.getWorldPosition();


		if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)) && player.isSelected()){
			spriteBatch.draw(moveAnimation.getKeyFrame(stateTime), scr_pos.x - 1 * Constants.UNIT_SCALE, scr_pos.y, this.WIDTH, this.HEIGHT);
		}
		else {
			spriteBatch.draw(idleAnimation.getKeyFrame(stateTime), scr_pos.x - 1 * Constants.UNIT_SCALE, scr_pos.y, this.WIDTH, this.HEIGHT);
		}

		if (!player.hand.isEmpty()){
			float stackOffset = 7 * Constants.UNIT_SCALE;
			for (Item item : player.hand.heldItems){
				spriteBatch.draw(item.getTexture(), scr_pos.x + 3 * Constants.UNIT_SCALE, scr_pos.y + stackOffset, 16 * Constants.UNIT_SCALE, 16 * Constants.UNIT_SCALE);
				stackOffset += 0.5;
			}
		}

		spriteBatch.end();
	}
}
