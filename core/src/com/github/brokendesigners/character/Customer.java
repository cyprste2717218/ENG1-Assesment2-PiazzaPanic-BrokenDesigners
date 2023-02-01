package com.github.brokendesigners.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.bubble.SimpleItemBubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CustomerStation;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import java.util.ArrayList;
/*
 * Customer
 */
public class Customer {

	boolean visible = false; // is visible?
	public Texture texture; // texture
	public ArrayList<Animation<TextureRegion>> animations;
	// ^^ animation array - Customers dont interact though, so they only need 2 animations, Idle and move (in that order).
	public Vector2 worldPosition; // position of customer
	protected CustomerStation station; // station assigned to customer
	protected Item desiredMeal; // desired meal for the customer
	public final float WIDTH; // width (sprite)
	public final float HEIGHT; // height (sprite)
	public SimpleItemBubble bubble; // the bubble associated with the customer.
	private int phase = 3; // Phase of the customer - determines what they are doing (walking to station/spawn or being served)
	Sound success = Gdx.audio.newSound(Gdx.files.internal("audio/success.wav"));
	Sound failure = Gdx.audio.newSound(Gdx.files.internal("audio/failure.wav"));
	private Vector2 spawnPoint;
	public float stateTime; // used for the renderer to grab frame.




	float movement_speed = 0; //Intentionally lowercase - NOT A CONSTANT - kind of a constant - you decide :)
	/*
	 * Instantiates customer without animations.
	 */
	public Customer(CustomerRenderer customerRenderer, BubbleRenderer bubbleRenderer, Texture texture, CustomerStation station, Item desiredMeal, Vector2 spawnPoint){
		worldPosition = new Vector2(spawnPoint);
		this.station = station;
		this.texture = texture;
		this.desiredMeal = desiredMeal;

		this.WIDTH = this.texture.getWidth() * Constants.UNIT_SCALE;
		this.HEIGHT = this.texture.getHeight() * Constants.UNIT_SCALE;
		this.phase = -1;
		this.spawnPoint = spawnPoint;

		bubble = new SimpleItemBubble(bubbleRenderer, this.desiredMeal, new Vector2(this.station.getCustomerPosition().x + 1f, this.station.getCustomerPosition().y + 2f));

		customerRenderer.addCustomer(this);
		this.stateTime = 0;
	}
	/*
	 * Instantiates Customer with animations
	 */
	public Customer(CustomerRenderer customerRenderer, BubbleRenderer bubbleRenderer, ArrayList<Animation<TextureRegion>> animations, CustomerStation station, Item desiredMeal, Vector2 spawnPoint){
		worldPosition = new Vector2(spawnPoint);
		this.station = station;
		this.animations = animations;
		this.desiredMeal = desiredMeal;

		this.phase = -1;
		this.spawnPoint = spawnPoint;

		bubble = new SimpleItemBubble(bubbleRenderer, this.desiredMeal, new Vector2(this.station.getCustomerPosition().x + 1f, this.station.getCustomerPosition().y + 2f));

		customerRenderer.addCustomer(this);

		this.stateTime = 0;

		this.WIDTH = this.animations.get(0).getKeyFrame(0).getRegionWidth() * Constants.UNIT_SCALE;
		this.HEIGHT = this.animations.get(0).getKeyFrame(0).getRegionHeight() * Constants.UNIT_SCALE;

		this.texture = null;
	}


	/*
	 * Spawns customer, makes them visible etc.
	 */
	public boolean spawn(){
		this.visible = true;
		this.movement_speed = 1 * Constants.UNIT_SCALE;
		this.phase = 0;
		// PLAY SOUND - DOOR OPENING / BELL RING / ETC
		return true;

	}
	/*
	 * Updates the customer every frame similar to how the player is updated.
	 */
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
						success.play();
						this.phase += 1;
						this.station.dumpHand();
						this.bubble.setVisible(false);
					}
					else{
						failure.play();
						this.station.dumpHand();
					}
				}
				break;
			case (2): // Phase 2 -- Customer is walking to the exit
				if (worldPosition.x != spawnPoint.x) {
					worldPosition.x -= this.movement_speed;
					if (Math.abs(worldPosition.x - this.spawnPoint.x) <= this.movement_speed){
						this.worldPosition.x = spawnPoint.x;
					}
				} else if (worldPosition.y != 0) {
					worldPosition.y -= this.movement_speed;
					if (Math.abs(worldPosition.y - this.spawnPoint.y) <= this.movement_speed) {
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

	public Vector2 getWorldPosition() {
		return worldPosition;
	}

	public boolean isVisible(){
		return visible;
	}

	public void dispose(){
		this.texture.dispose();
		success.dispose();
		failure.dispose();
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public Item getDesiredMeal() {
		return desiredMeal;
	}
}
