package com.github.brokendesigners.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class KitchenObject {

	private final Vector3 worldPosition;

	private final float WIDTH;
	private final float HEIGHT;

	private final Rectangle rectangle;

	public KitchenObject(Vector3 worldPosition, float width, float height){

		this.worldPosition = worldPosition;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.rectangle = new Rectangle(this.worldPosition.x, this.worldPosition.y, this.WIDTH, this.HEIGHT);

	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public Vector3 getWorldPosition() {
		return worldPosition;
	}

}
