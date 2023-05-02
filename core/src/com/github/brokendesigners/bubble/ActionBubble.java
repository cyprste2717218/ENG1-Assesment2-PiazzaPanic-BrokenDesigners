package com.github.brokendesigners.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Textures;
/**

 ActionBubble class - A type of bubble that displays an animation and serves as an indicator for interactive objects.
 */
public class ActionBubble extends Bubble{
	public Texture texture = Textures.simple_bubble;
	public static Vector2 relativeItemCoords = new Vector2(8 * Constants.UNIT_SCALE, 24 * Constants.UNIT_SCALE);

	Vector2 itemPosition;
	Animation<TextureRegion> animation;

	float stateTime;

	/**
	 * Constructor for the ActionBubble class
	 * @param renderer the renderer of the bubble
	 * @param worldPosition the position of the bubble in the game world
	 * @param animation the animation of the bubble
	 */
	public ActionBubble(BubbleRenderer renderer, Vector2 worldPosition, Animation<TextureRegion> animation) {
		super(renderer, worldPosition);

		this.animation = animation;

		this.itemPosition = new Vector2(this.worldPosition.x + relativeItemCoords.x, this.worldPosition.y + relativeItemCoords.y);
		stateTime = 0;

	}
	/**
	 * Resets the state time of the action bubble's animation.
	 */
	public void resetStateTime(){
		stateTime = 0;
	}
	/**
	 * Renders the action bubble on the screen.
	 * @param batch the sprite batch used for rendering the bubble
	 */
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
