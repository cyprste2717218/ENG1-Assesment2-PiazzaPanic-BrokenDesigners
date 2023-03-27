package com.github.brokendesigners.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import java.util.ArrayList;
/*
 * Handles rendering for the bubbles.
 */
public class BubbleRenderer {
	public ArrayList<Bubble> bubbles;
	public SpriteBatch batch;

	/*
	 * Instantiates bubble renderer
	 */
	public BubbleRenderer(SpriteBatch batch){
		this.batch = batch;
		this.bubbles = new ArrayList<>();
	}

	/*
	 * renders bubbles
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

	/*
	 * adds bubble to render queue.
	 */
	public void addBubble(Bubble bubble){
		bubbles.add(bubble);
	}

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
