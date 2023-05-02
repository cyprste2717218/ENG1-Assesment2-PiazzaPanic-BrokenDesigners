package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.renderer.BubbleRenderer;
/**

 Bubble class represents a bubble object in the game.
 */
public class Bubble {
	protected boolean visible;
	protected Vector2 worldPosition;
	/**
	 * Constructor that creates a new bubble object.
	 * @param renderer the BubbleRenderer to add the bubble to
	 * @param worldPosition the position of the bubble in the game world
	 */
	public Bubble(BubbleRenderer renderer, Vector2 worldPosition){
		this.worldPosition = worldPosition;
		renderer.addBubble(this);
	}
	/**
	 * Returns whether the bubble is visible or not.
	 * @return true if the bubble is visible, false otherwise
	 */
	public boolean isVisible(){
		return visible;
	}
	/**
	 * Sets whether the bubble is visible or not.
	 * @param visible true if the bubble should be visible, false otherwise
	 */
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	/**
	 * Renders the bubble using the given SpriteBatch.
	 * @param batch the SpriteBatch used to render the bubble
	 */
	public void render(SpriteBatch batch){}

	/**
	 * Disposes of any resources held by the bubble.
	 */
	public void dispose(){}

	/**
	 * Resets the state time of the bubble.
	 */
	public void resetStateTime() {
	}
}
