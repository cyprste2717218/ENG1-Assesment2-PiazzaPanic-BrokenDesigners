package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Textures;

/**

 The ProgressBubble class represents a visual indicator of progress for a task associated with an Item.
 It extends the Bubble class and inherits its properties and methods.
 */
public class ProgressBubble extends Bubble {
	public static Texture texture = Textures.simple_bubble;
	public static Vector2 relativeItemCoords = new Vector2(29 * Constants.UNIT_SCALE, 18 * Constants.UNIT_SCALE);
	public Item item;
	/**
	 * Constructs a ProgressBubble object with a ShapeRenderer, a BubbleRenderer, a world position and an Item.
	 * @param shapeRenderer a ShapeRenderer object to render the ProgressBubble.
	 * @param renderer a BubbleRenderer object to render the Bubble.
	 * @param worldPosition a Vector2 object representing the world position of the ProgressBubble.
	 * @param item an Item object associated with the ProgressBubble.
	 */
	public ProgressBubble(ShapeRenderer shapeRenderer, BubbleRenderer renderer, Vector2 worldPosition, Item item) {
		super(renderer, worldPosition);
		this.item = item;
	}


}
