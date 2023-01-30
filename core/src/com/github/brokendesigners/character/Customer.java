package com.github.brokendesigners.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.bubble.SimpleItemBubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CustomerStation;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;

public class Customer {

	boolean visible = false;
	Texture texture;
	Vector3 worldPosition;
	CustomerStation station;
	Item desiredMeal; // desired meal for the customer
	public final float WIDTH;
	public final float HEIGHT;
	SimpleItemBubble bubble;
	private int phase = 3;

	float movement_speed = 0; //Intentionally lowercase - NOT A CONSTANT

	public Customer(CustomerRenderer customerRenderer, BubbleRenderer bubbleRenderer, Texture texture, CustomerStation station, Item desiredMeal){
		worldPosition = new Vector3(1/8f,0,0);
		this.station = station;
		this.texture = texture;
		this.desiredMeal = desiredMeal;

		this.WIDTH = this.texture.getWidth() * Constants.UNIT_SCALE;
		this.HEIGHT = this.texture.getHeight() * Constants.UNIT_SCALE;
		this.phase = -1;


		bubble = new SimpleItemBubble(bubbleRenderer, this.desiredMeal, new Vector2(this.station.getCustomerPosition().x + 1f, this.station.getCustomerPosition().y + 2f));

		customerRenderer.addCustomer(this);
	}

	public boolean spawn(){
		this.visible = true;
		this.movement_speed = 1 * Constants.UNIT_SCALE;
		this.phase = 0;
		// PLAY SOUND - DOOR OPENING / BELL RING / ETC
		return true;

	}

	public void update(){
		switch (this.phase) {
			case (0): // Phase 0 -- Customer is moving to Ordering Station

				if (worldPosition.y != this.station.getCustomerPosition().y) {
					worldPosition.y += this.movement_speed;
					if (Math.abs((worldPosition.y - this.station.getCustomerPosition().y)) <= this.movement_speed) {
						this.worldPosition.y = this.station.getCustomerPosition().y;
					}
				} else if (worldPosition.x != this.station.getCustomerPosition().x) {
					worldPosition.x += this.movement_speed;
					if (Math.abs((worldPosition.x - this.station.getCustomerPosition().x)) <= this.movement_speed){
						this.worldPosition.x = this.station.getCustomerPosition().x;
					}
				} else if (worldPosition.equals(this.station.getCustomerPosition())) {
					this.phase += 1;
					this.bubble.setVisible(true);
				}
				break;
			case (1): // Phase 1 -- Customer is waiting for meal
				if (!this.station.hasEmptyHand()) {
					if (this.station.getItemInHand().equals(this.desiredMeal)) {
						this.phase += 1;
						this.station.dumpHand();
						this.bubble.setVisible(false);
					}
				}
				break;
			case (2): // Phase 2 -- Customer is walking to the exit
				if (worldPosition.x != 0) {
					worldPosition.x -= this.movement_speed;
					if (Math.abs(worldPosition.x) <= this.movement_speed){
						this.worldPosition.x = 0;
					}
				} else if (worldPosition.y != 0) {
					worldPosition.y -= this.movement_speed;
					if (Math.abs(worldPosition.y) <= this.movement_speed) {
						this.worldPosition.y = 0;
					}
				} else {

					this.phase = 3;
				}
				break;
			case (3): // Phase 3 -- Customer has completed its tasks, despawns.

				this.visible = false;
				break;
			default:
				break;


		}
	}

	public Texture getTexture() {
		return texture;
	}

	public Vector3 getWorldPosition() {
		return worldPosition;
	}

	public boolean isVisible(){
		return visible;
	}

	public void dispose(){
		this.texture.dispose();
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}
}
