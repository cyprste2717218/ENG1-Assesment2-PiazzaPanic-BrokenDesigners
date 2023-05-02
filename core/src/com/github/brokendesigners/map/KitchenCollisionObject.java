package com.github.brokendesigners.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
/**

 Defines the collision boundaries of a kitchen object.
 */
public class KitchenCollisionObject {

	private final Vector3 worldPosition;

	private final float WIDTH;
	private final float HEIGHT;

	private final Rectangle rectangle;

	/**
	 * Constructs a KitchenCollisionObject with a specified world position and dimensions.
	 *
	 * @param worldPosition the position of the kitchen object in the game world.
	 * @param width the width of the kitchen object.
	 * @param height the height of the kitchen object.
	 */
	public KitchenCollisionObject(Vector3 worldPosition, float width, float height){

		this.worldPosition = worldPosition;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.rectangle = new Rectangle(this.worldPosition.x, this.worldPosition.y, this.WIDTH, this.HEIGHT);


	}
	public KitchenCollisionObject(Vector3 worldPosition, Rectangle rectangle){

		this.worldPosition = worldPosition;
		this.WIDTH = rectangle.width;
		this.HEIGHT = rectangle.height;
		this.rectangle = rectangle;
	}


	public Rectangle getRectangle() {
		return this.rectangle;
	}
	public Vector3 getWorldPosition() {
		return this.worldPosition;
	}
	public float getWIDTH(){
		return this.WIDTH;
	}
	public float getHEIGHT(){
		return this.HEIGHT;
	}

}
