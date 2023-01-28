package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.renderer.BubbleRenderer;

public class ProgressBubble extends Bubble {
	public static Texture texture = new Texture("bubbles/progress_bubble_1.png");
	public static Vector2 relativeItemCoords = new Vector2(29 * Constants.UNIT_SCALE, 18 * Constants.UNIT_SCALE);
	public Item item;

	public ProgressBubble(ShapeRenderer shapeRenderer, BubbleRenderer renderer, Vector2 worldPosition, Item item) {
		super(renderer, worldPosition);
		this.item = item;
	}


}
