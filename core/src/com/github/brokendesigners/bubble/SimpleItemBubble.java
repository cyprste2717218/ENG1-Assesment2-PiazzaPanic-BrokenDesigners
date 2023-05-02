package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Textures;

public class SimpleItemBubble extends Bubble {
	public static Texture texture = Textures.simple_item_bubble;
	public static Vector2 relativeItemCoords = new Vector2(8 * Constants.UNIT_SCALE, 24 * Constants.UNIT_SCALE);
	Vector2 itemPosition;
	Item item;
	/**
	 * Constructs a new SimpleItemBubble with the given BubbleRenderer, Item, and position in the world.
	 *
	 * @param renderer the BubbleRenderer to use for rendering
	 * @param item the Item contained within the bubble
	 * @param worldPosition the position of the bubble within the world
	 */
	public SimpleItemBubble(BubbleRenderer renderer, Item item, Vector2 worldPosition){

		super(renderer, worldPosition);

		this.item = item;
		this.itemPosition = new Vector2(this.worldPosition.x + relativeItemCoords.x, this.worldPosition.y + relativeItemCoords.y);

	}
	/**
	 * Renders the bubble and the item it contains using the given SpriteBatch.
	 * Only renders the bubble and the item if it is visible.
	 *
	 * @param batch the SpriteBatch to use for rendering
	 */
	@Override
	public void render(SpriteBatch batch){
		if (this.isVisible()) {
			batch.draw(
				texture,
				this.worldPosition.x,
				this.worldPosition.y,
				texture.getWidth() * Constants.UNIT_SCALE,
				texture.getHeight() * Constants.UNIT_SCALE);

			batch.draw(
				this.item.getTexture(),
				itemPosition.x,
				itemPosition.y,
				16 * Constants.UNIT_SCALE,
				16 * Constants.UNIT_SCALE);
		}
	}

	@Override
	public void dispose(){
		texture.dispose();
	}
}
