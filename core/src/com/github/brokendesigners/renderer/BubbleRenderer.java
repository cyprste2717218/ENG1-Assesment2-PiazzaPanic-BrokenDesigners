package com.github.brokendesigners.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import java.util.ArrayList;
/**

 The BubbleRenderer class is responsible for rendering the bubbles.
 It uses a SpriteBatch to draw the bubbles onto the screen.
 This class contains an ArrayList of bubbles to be rendered and
 provides methods to add bubbles to the queue and clear the queue.
 */
public class BubbleRenderer {
	public ArrayList<Bubble> bubbles;
	public SpriteBatch batch;

	/**
	 * Instantiates a BubbleRenderer object with the specified SpriteBatch.
	 *
	 * @param batch the SpriteBatch used to draw the bubbles
	 */
	public BubbleRenderer(SpriteBatch batch){
		this.batch = batch;
		this.bubbles = new ArrayList<>();
	}

	/**
	 * Renders the bubbles onto the screen.
	 * Only visible bubbles are drawn.
	 */

	public void renderBubbles(){
		this.batch.begin();
		this.batch.setColor(1f, 1f, 1f, 0.5f);
		for (Bubble bubble : bubbles){
			if (bubble.isVisible()){
				bubble.render(this.batch);
			}
		}
		this.batch.setColor(1f, 1f, 1f, 1f);
		this.batch.end();
	}

	/**
	 * Adds a bubble to the render queue.
	 *
	 * @param bubble the bubble to be added to the queue
	 */
	public void addBubble(Bubble bubble){
		bubbles.add(bubble);
	}
	/**
	 * Disposes of all bubbles in the render queue.
	 */
	public void dispose(){
		for (Bubble bubble : bubbles){
			bubble.dispose();
		}
	}
	/*
	 * Clears the bubble render queue for new game to start.
	 */
	public void end(){
		bubbles.clear();
	}

}
