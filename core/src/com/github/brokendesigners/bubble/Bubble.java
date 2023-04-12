package com.github.brokendesigners.bubble;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.renderer.BubbleRenderer;

public class Bubble {
	protected boolean visible;
	protected Vector2 worldPosition;


	public Bubble(BubbleRenderer renderer, Vector2 worldPosition){
		this.worldPosition = worldPosition;
		renderer.addBubble(this);
	}
	public boolean isVisible(){
		return visible;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public void render(SpriteBatch batch){}

	public void dispose(){}

	public void resetStateTime() {
	}
}
