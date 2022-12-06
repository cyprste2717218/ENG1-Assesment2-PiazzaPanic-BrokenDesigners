package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.textures.ChefTextures;

public class PlayerChef {

	private int x_coord;				// vv
	private int y_coord; 				// Current Co-ords of Chef
	private Vector3 chefPosition;

	private final Animation<TextureRegion> idleAnimation;
	private final Animation<TextureRegion> moveAnimation;
	private float stateTime;
	private boolean isFacingRight = true;

	public final int WIDTH = 40;		// Width of chef when drawn
	public final int HEIGHT = 72;		// Height of Chef when drawn

	private ChefTextures atlas;
	private TextureRegion activeRegion;

	private boolean activeChef; 		// Used for whether or not the current "Chef" is the selected one
	private final int MOVE_SPEED = 5;  // CONSTANT -- MOVEMENT SPEED OF CHEF

	private RectangleMapObject rectangle;


	public PlayerChef(int x_coord, int y_coord){
		this.y_coord = y_coord;
		this.x_coord = x_coord;

		chefPosition = new Vector3(x_coord, y_coord, 0);

		this.stateTime = 0;

		moveAnimation =
			new Animation<TextureRegion>(0.3f, ChefTextures.atlasImages.findRegions("running"), PlayMode.LOOP);

		idleAnimation =
			new Animation<TextureRegion>(1.3f, ChefTextures.atlasImages.findRegions("idle"), PlayMode.LOOP);

		this.rectangle = new RectangleMapObject(this.x_coord, this.y_coord, this.WIDTH, this.HEIGHT);

	}

	public void renderChef(SpriteBatch spriteBatch, Player player){
		stateTime += Gdx.graphics.getDeltaTime();
		spriteBatch.begin();

		Vector3 scr_pos = player.getScreenPosition(spriteBatch.getProjectionMatrix());

		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){
			spriteBatch.draw(moveAnimation.getKeyFrame(stateTime), scr_pos.x, scr_pos.y, this.WIDTH, this.HEIGHT);


		}
		else {
			spriteBatch.draw(idleAnimation.getKeyFrame(stateTime), scr_pos.x, scr_pos.y, this.WIDTH, this.HEIGHT);
		}

		spriteBatch.end();
	}

	private void updateVector3(float newX, float newY){
		this.chefPosition.x = newX;
		this.chefPosition.y = newY;
	}
}
