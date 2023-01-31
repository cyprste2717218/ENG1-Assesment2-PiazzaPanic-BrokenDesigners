package com.github.brokendesigners;

import static java.lang.Math.abs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.Station;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import com.github.brokendesigners.renderer.PlayerRenderer;
import com.github.brokendesigners.textures.Animations;

public class Player {
	Vector2 worldPosition; // Position of the player in world-coords

	public float MOVEMENT_SPEED = 2 * Constants.UNIT_SCALE;  // Movement Speed of Chef differs between vertical and horizontal due to following 2 lines

	private float width; //NOTE:  NOT THE WIDTH OF CHEF SPRITE
	private float height;	//NOTE: NOT HEIGHT OF CHEF SPRITE

	public final float SPRITE_WIDTH;		// Width of chef when drawn
	public final float SPRITE_HEIGHT;		// Height of chef when drawn

	public Hand hand; // Items held by the chef

	Rectangle playerRectangle; // Rectangle representation of chef - Used for interactions/collisions

	private boolean selected; // Is Player Selected

	public Texture texture; // Texture of player if there are no animations for player
	public ArrayList<Animation<TextureRegion>> animations; // A list of animations
	public float keyFrame; // float updated every frame - used to determine which frame of animation of the player should render
	public boolean flipped; // is flipped or not -- NOT USED
	public boolean moving_disabled = false; // Is moving disabled? - Moving is disabled when interacting with most stations.

	float renderOffsetX = 0;
	// ^^ Offset where the sprite will render relative to the invisible rectangle
	// which represents the players position/collision boundaries


	public Player(PlayerRenderer renderer, Texture texture, Vector2 worldPosition){

		this.worldPosition = worldPosition;

		SPRITE_HEIGHT = this.texture.getHeight() * Constants.UNIT_SCALE; // Sets height of player to height of player's texture
		SPRITE_WIDTH = this.texture.getWidth() * Constants.UNIT_SCALE; // Sets width of player to width of player's texture




		this.texture = texture;

		hand = new Hand(); // Instantiates Hand so the player can pick items up

		renderer.addPlayer(this); // Adds player to the renderer's "List of Players to Render every frame"

		boolean flipped = false;

		this.width = 18 * Constants.UNIT_SCALE; // default values for the dimensions of the Player Rectangle
		this.height = 4 * Constants.UNIT_SCALE; //
		this.renderOffsetX = -1; // Default renderOffsetX

		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.width, this.height);


	}
	public Player(PlayerRenderer renderer, ArrayList<Animation<TextureRegion>> animations, Vector2 worldPosition, float sprite_width, float sprite_height){

		this.worldPosition = worldPosition;

		SPRITE_HEIGHT = sprite_height;
		SPRITE_WIDTH = sprite_width;

		this.animations = animations;

		hand = new Hand();

		renderer.addPlayer(this);
		this.width = 18 * Constants.UNIT_SCALE;
		this.height = 4 * Constants.UNIT_SCALE;

		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.width, this.height);
	}

	public Rectangle getPlayerRectangle() {
		return playerRectangle;
	}

	private void updateRectangle(){
		playerRectangle.x = worldPosition.x;
		playerRectangle.y = worldPosition.y;

	}

	public Vector2 getWorldPosition(){
		return worldPosition;
	}

	public void processMovement(ArrayList<KitchenCollisionObject> objects){

		if (this.selected == true && !this.moving_disabled) {

			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.moveUp(objects);
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				this.moveDown(objects);
			}
			this.updateRectangle();
			if (Gdx.input.isKeyPressed(Keys.A)) {
				this.moveLeft(objects);
				if (!this.flipped){
					for (Animation animation : animations){
						for (TextureRegion region : (TextureRegion[]) animation.getKeyFrames()){
							region.flip(true, false);
						}
					}
					this.flipped = true;
				}
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				this.moveRight(objects);
				if (this.flipped){
					for (Animation animation : animations){
						for (TextureRegion region : (TextureRegion[]) animation.getKeyFrames()){
							region.flip(true, false);
						}
					}
					this.flipped = false;
				}

			}
			this.updateRectangle();
		}
	}

	public boolean moveUp(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.y += (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.y = object.getRectangle().y - this.height;

				return false;
			}
		}
		this.worldPosition.y += (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}
	public boolean moveDown(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.y -= (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.y = object.getRectangle().y + object.getHEIGHT();
				return false;
			}
		}
		this.worldPosition.y -= (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}
	public boolean moveRight(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.x += (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.x = object.getRectangle().x - this.width;
				return false;
			}
		}

		this.worldPosition.x += (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}
	public boolean moveLeft(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.x -= (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.x = object.getRectangle().x + object.getWIDTH();
				return false;
			}
		}
		this.worldPosition.x -= (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}

	public boolean pickUp(ArrayList<? extends Station> stations)
		throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		if (this.isSelected()) {
			for (Station station : stations) {
				if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())) {
					station.pickUp(this);
					return true;
				}
			}
		}
		return false;
	}
	public boolean dropOff(ArrayList<? extends Station> stations){
		if (this.isSelected()) {
			for (Station station : stations) {
				if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())) {
					station.dropOff(this);
					return true;
				}
			}
		}
		return false;
	}

	public void setSelected(boolean isSelected){
		this.selected = isSelected;
	}

	public boolean isSelected() {
		return selected;
	}


	public boolean interact(ArrayList<? extends Station> stations){
		if (this.isSelected()) {
			for (Station station : stations) {
				if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())) {
					station.action(this);
					return true;
				}
			}
		}
		return false;
	}

	public void dispose(){
		if (this.texture != null){
			this.texture.dispose();
		}
	}
	public void disableMovement(){
		this.moving_disabled = true;
	}
	public void enableMovement(){
		this.moving_disabled = false;
	}

	public void setWidth(float width) {
		this.width = width;
		playerRectangle.width = this.width;
	}

	public void setHeight(float height) {
		this.height = height;
		playerRectangle.height = this.height;
	}

	public void setRenderOffsetX(float renderOffsetX) {
		this.renderOffsetX = renderOffsetX;
	}

	public float getRenderOffsetX() {
		return renderOffsetX;
	}
}
