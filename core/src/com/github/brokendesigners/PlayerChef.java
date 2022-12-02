package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.brokendesigners.textures.ChefTextures;

public class PlayerChef {

	private int x_coord;				// vv
	private int y_coord; 				// Current Co-ords of Chef

	private final Animation<TextureRegion> idleAnimation;
	private final Animation<TextureRegion> moveAnimation;
	private float stateTime;
	private boolean isFacingRight = true;

	public final int WIDTH = 80;		// Width of chef when drawn
	public final int HEIGHT = 144;		// Height of Chef when drawn

	private ChefTextures atlas;
	private TextureRegion activeRegion;

	private boolean activeChef; 		// Used for whether or not the current "Chef" is the selected one
	private final int MOVE_SPEED = 5;  // CONSTANT -- MOVEMENT SPEED OF CHEF



	public PlayerChef(int x_coord, int y_coord){
		this.y_coord = y_coord;
		this.x_coord = x_coord;
		this.stateTime = 0;

		moveAnimation =
			new Animation<TextureRegion>(0.3f, ChefTextures.atlasImages.findRegions("running"), PlayMode.LOOP);

		idleAnimation =
			new Animation<TextureRegion>(1.3f, ChefTextures.atlasImages.findRegions("idle"), PlayMode.LOOP);

	}

	public void renderChef(SpriteBatch spriteBatch){
		stateTime += Gdx.graphics.getDeltaTime();
		spriteBatch.begin();

		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){
			spriteBatch.draw(moveAnimation.getKeyFrame(stateTime), x_coord, y_coord, this.WIDTH, this.HEIGHT);
		}
		else {
			spriteBatch.draw(idleAnimation.getKeyFrame(stateTime), x_coord, y_coord, this.WIDTH, this.HEIGHT);
		}

		spriteBatch.end();
	}

	public void processMovement(){
		if (Gdx.input.isKeyPressed(Keys.W)){
			this.moveUp();
		}
		else if (Gdx.input.isKeyPressed(Keys.S)){
			this.moveDown();
		}
		if (Gdx.input.isKeyPressed(Keys.A)){
			this.moveLeft();
		}
		else if(Gdx.input.isKeyPressed(Keys.D)){
			this.moveRight();
		}
	}

	private void moveLeft(){
		this.x_coord -= (MOVE_SPEED);
	}
	private void moveRight(){
		this.x_coord += (MOVE_SPEED);
	}
	private void moveUp(){
		this.y_coord += (MOVE_SPEED);
	}
	private void moveDown(){
		this.y_coord -= (MOVE_SPEED);
	}
	public int getX_coord(){
		return x_coord;
	}

	public int getY_coord() {
		return y_coord;
	}
}
