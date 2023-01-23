package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.Item;

public class SimpleItemBubble {
	public static Texture texture = new Texture("bubbles/simple_bubble.png");
	public static Vector3 relativeItemCoords = new Vector3(8 * Constants.UNIT_SCALE, 40 * Constants.UNIT_SCALE, 0);
	Vector3 worldPosition;
	Vector3 itemPosition;
	Item item;
	boolean visible = false;
	Sprite sprite;

	public SimpleItemBubble(Item item, Vector3 worldPosition){
		sprite = new Sprite(SimpleItemBubble.texture);
		sprite.setColor(1,1,1,0.3f);
		this.item = item;
		this.worldPosition = worldPosition;
		this.itemPosition = new Vector3(worldPosition.x + relativeItemCoords.x, worldPosition.y + relativeItemCoords.y, 0);

	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void render(SpriteBatch batch){

		if (this.visible) {

			batch.setColor(1f, 1f, 1f, 0.5f);

			batch.draw(
				sprite,
				worldPosition.x,
				worldPosition.y,
				32f * Constants.UNIT_SCALE,
				64f * Constants.UNIT_SCALE);

			batch.draw(
				this.item.getTexture(),
				itemPosition.x,
				itemPosition.y,
				16 * Constants.UNIT_SCALE,
				16 * Constants.UNIT_SCALE);

			batch.setColor(1f, 1f, 1f, 1f);

		}
	}
}
