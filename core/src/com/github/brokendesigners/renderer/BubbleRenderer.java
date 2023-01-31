package com.github.brokendesigners.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.brokendesigners.bubble.Bubble;
import java.util.ArrayList;

public class BubbleRenderer {
	public ArrayList<Bubble> bubbles;
	public SpriteBatch batch;

	public BubbleRenderer(SpriteBatch batch){
		this.batch = batch;
		this.bubbles = new ArrayList<>();
	}

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

	public void addBubble(Bubble bubble){
		bubbles.add(bubble);
	}

	public void dispose(){
		for (Bubble bubble : bubbles){
			bubble.dispose();
		}
	}
	public void end(){
		bubbles.clear();
	}

}
