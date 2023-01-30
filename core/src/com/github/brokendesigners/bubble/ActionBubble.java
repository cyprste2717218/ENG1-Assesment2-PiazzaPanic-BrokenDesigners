package com.github.brokendesigners.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.renderer.BubbleRenderer;

public class ActionBubble extends Bubble{
	public static Texture texture = new Texture("bubbles/simple_bubble.png");
	public static Vector2 relativeItemCoords = new Vector2(8 * Constants.UNIT_SCALE, 24 * Constants.UNIT_SCALE);

	Vector2 itemPosition;
	Animation<TextureRegion> animation;

	float stateTime;
	public ActionBubble(BubbleRenderer renderer, Vector2 worldPosition, Animation<TextureRegion> animation) {
		super(renderer, worldPosition);

		this.animation = animation;

		this.itemPosition = new Vector2(this.worldPosition.x + relativeItemCoords.x, this.worldPosition.y + relativeItemCoords.y);
		stateTime = 0;

	}

	@Override
	public void render(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		batch.draw(
			texture,
			this.worldPosition.x,
			this.worldPosition.y,
			texture.getWidth() * Constants.UNIT_SCALE,
			texture.getHeight() * Constants.UNIT_SCALE);
		batch.setColor(1f, 1f, 1f, 0.8f);
		batch.draw(this.animation.getKeyFrame(stateTime),
			this.itemPosition.x,
			this.itemPosition.y,
			16 * Constants.UNIT_SCALE,
			16 * Constants.UNIT_SCALE);
		batch.setColor(1f, 1f, 1f, 0.5f);
	}
}
