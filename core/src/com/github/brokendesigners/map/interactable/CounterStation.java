package com.github.brokendesigners.map.interactable;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

/**
 * Extended Class
 */

public class CounterStation extends Station {

	Vector2 handPosition;
	Bubble bubble;
	Match match;


	public CounterStation(Rectangle interactionArea, float handX, float handY, BubbleRenderer bubbleRenderer, Match match) {

		super(interactionArea,"Counter_Station");

		handPosition = new Vector2(handX, handY);

		this.bubble = new ActionBubble(bubbleRenderer, this.handPosition,
			Animations.pattyFormingAnimation);

		this.match = match;
	}
	/**
	 * Constructor that takes in the object position, width, height, hand position, bubble renderer, and match.
	 * @param objectPosition The position of the object.
	 * @param width The width of the object.
	 * @param height The height of the object.
	 * @param handX The x position of the hand.
	 * @param handY The y position of the hand.
	 * @param bubbleRenderer The bubble renderer object.
	 * @param match The match object.
	 */
	public CounterStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, Match match){
		super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Counter_Station");
		handPosition = new Vector2(handX, handY);
		System.out.println(handX);
		System.out.println(handY);
		this.bubble = new ActionBubble(bubbleRenderer, this.handPosition, Animations.pattyFormingAnimation);
		stationUseTime = 5f;
		this.match = match;
	}

    public CounterStation() {
        stationUseTime = 5f;
    }

	/**
	 * Renders the CounterStation object.
	 * @param spriteBatch The sprite batch object.
	 */
    public void renderCounter(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		if (!this.hasEmptyHand()) {
			spriteBatch.draw(this.hand.getTexture(), this.handPosition.x, this.handPosition.y, 16 * Constants.UNIT_SCALE, 16 * Constants.UNIT_SCALE);
		}
		spriteBatch.end();

	}


	public Item formPatty() //Turn meat into patty
	{
		if(hand.getName()=="Meat")
		{
			hand =  ItemRegister.itemRegister.get("Patty");
		}
		return hand;

	}
	/**
	 * Extended Method
	 *
	 * Performs the action of using the counter station
	 *
	 * @param player The player performing the action
	 * @return true if the action was successful, false otherwise
	 */
	@Override
	public boolean action(final Player player){
		if(this.inuse) return false;
		if(hand == null) return false;
		if (hand.equals(ItemRegister.itemRegister.get("Meat")) || hand.equals(ItemRegister.itemRegister.get("Dough"))) {
			this.bubble.setVisible(true);
			this.inuse = true;
			interacting = true;
			Timer timer = new Timer();
			player.disableMovement();
			if (hand.equals(ItemRegister.itemRegister.get("Meat"))) {
				timer.scheduleTask(new Task() {
					@Override
					public void run() {
						player.enableMovement();
						bubble.setVisible(false);
						dumpHand();
						hand = ItemRegister.itemRegister.get("Patty");
						interacting = false;
						inuse = false;
					}
				}, getAdjustedStationUseTime());
				return true;
			}
			timer.scheduleTask(new Task() {
				@Override
				public void run() {
					player.enableMovement();
					bubble.setVisible(false);
					dumpHand();
					hand = ItemRegister.itemRegister.get("Base");
					interacting = false;
					inuse = false;
				}
			}, getAdjustedStationUseTime());
			return true;

		}
		return false;
	}



}
