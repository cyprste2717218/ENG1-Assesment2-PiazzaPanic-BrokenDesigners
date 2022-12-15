package com.github.brokendesigners.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class KitchenCollisionObject {

	private final Vector3 worldPosition;

	private final float WIDTH;
	private final float HEIGHT;

	private final Rectangle rectangle;

	public KitchenCollisionObject(Vector3 worldPosition, float width, float height){

		this.worldPosition = worldPosition;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.rectangle = new Rectangle(this.worldPosition.x, this.worldPosition.y, this.WIDTH, this.HEIGHT);

	}
	public KitchenCollisionObject(Rectangle rectangle){

		this.worldPosition = new Vector3(rectangle.x, rectangle.y, 0);
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
