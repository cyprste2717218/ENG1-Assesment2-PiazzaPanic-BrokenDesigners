package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.renderer.BubbleRenderer;

public class SimpleItemBubble extends Bubble {
	public static Texture texture = new Texture("bubbles/simple_bubble_with_item.png");
	public static Vector2 relativeItemCoords = new Vector2(8 * Constants.UNIT_SCALE, 24 * Constants.UNIT_SCALE);

	Vector2 itemPosition;
	Item item;


	public SimpleItemBubble(BubbleRenderer renderer, Item item, Vector2 worldPosition){

		super(renderer, worldPosition);

		this.item = item;
		this.itemPosition = new Vector2(this.worldPosition.x + relativeItemCoords.x, this.worldPosition.y + relativeItemCoords.y);

	}

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
