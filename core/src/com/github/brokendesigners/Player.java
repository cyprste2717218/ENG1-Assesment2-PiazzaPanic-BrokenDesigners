package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
	Vector3 worldPosition;

	public final float MOVEMENT_SPEED = 1000f;  // Movement Speed of Chef differs between vertical and horizontal due to following 2 lines

	private final int WIDTH  = 80; //NOTE:  NOT THE WIDTH OF CHEF SPRITE
	private final int HEIGHT = 40;	//NOTE: NOT HEIGHT OF CHEF SPRITE

	Rectangle playerRectangle;


	public Player(){
		worldPosition = new Vector3(0, 0, 0);
		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.WIDTH, this.HEIGHT);

	}

	public Rectangle getPlayerRectangle() {
		return playerRectangle;
	}

	public Vector3 getScreenPosition(Matrix4 projectionMatrix){
		Vector3 screenPosition = worldPosition.cpy();
		return screenPosition.prj(projectionMatrix);
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
	public void moveUp(){
		this.worldPosition.y += (this.MOVEMENT_SPEED / 2f);
	}
	public void moveDown(){
		this.worldPosition.y -= (this.MOVEMENT_SPEED / 2f);
	}
	public void moveRight(){
		this.worldPosition.x += this.MOVEMENT_SPEED;
	}
	public void moveLeft(){
		this.worldPosition.x -= this.MOVEMENT_SPEED;
	}


}
