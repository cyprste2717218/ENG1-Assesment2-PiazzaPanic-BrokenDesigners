package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;

import java.lang.reflect.InvocationTargetException;
/*
 * Abstract class station. All stations inherit from this.
 * should only really be handled by Kitchen class
 */
public abstract class Station {

	protected Rectangle interactionArea; // The area that the player has to stand in to interact with an object.
	protected String station_name; // name of the station.
	protected boolean interacting = false; // is the station being interacted with?
	public Item hand; // the item in the hand.
	public Boolean storing; // is the station storing
	public Vector2 handPosition = new Vector2(0,0);
	// where should the item be rendered - In the TiledMap, the co-ords for HandX and HandY are relative to the bottom left of the interact area.
	public boolean inuse;

	public Sound pick_up;
	public Sound put_down;
	public Sound failure;


	protected Station(Rectangle rectangle, String n) {
		this.pick_up = Gdx.audio.newSound(Gdx.files.internal("audio/pick_up.wav"));
		this.put_down = Gdx.audio.newSound(Gdx.files.internal("audio/put_down.wav"));
		this.failure = Gdx.audio.newSound(Gdx.files.internal("audio/failure.wav"));
		this.station_name = n;
		this.hand = null;
		this.storing = false;
		this.interactionArea = rectangle;
		this.handPosition.x = this.interactionArea.x;
		this.handPosition.y = this.interactionArea.y;
		this.inuse = false;
	}
	// empty constructor used for tests
	public Station()	{
		this.hand = null;
	}


//	public boolean pickUp(Player player)
//		throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//		return false;
//	}

	public Rectangle getInteractionArea() {
		return interactionArea;
	}

//	public boolean dropOff(Player player){
//		return false;
//	}

	public boolean action(Player player){
		return false;
	}


	//Check if operation can be completed
	public Boolean Applicable(String[] Conditions,String state, String itemName)
	{
		if(this.station_name == state) //Checks if its in the correct station before preforming
		{
			for(int i = 0;i<Conditions.length;i++)
			{
				if(Conditions[i] == itemName)
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasEmptyHand(){
		if (this.hand == null){
			return true;
		} else {
			return false;
		}
	}

	public void dumpHand(){
		this.hand = null;
	}

	/*
	 * Handles PickUp from player.
	 */

	public boolean pickUp(Player player){
		if (this.hasEmptyHand() || this.interacting || this.inuse){
			return false;
		} else if (player.hand.isFull()) {
			failure.play();
			return false;
		} else {
			pick_up.play();
			player.hand.give(hand);
			this.dumpHand();
			return true;
		}
	}
	/*
	 * Handles dropOff from player
	 */
	public boolean dropOff(Player player){
		if (this.hasEmptyHand()){
			if (!player.hand.isEmpty()) {
				put_down.play();
			}
			this.hand = player.hand.drop();
			return true;
		} else {
			return false;
		}
	}
	/*
	 * Renders the items on the counter - not the counter itself, that is done by the TiledMapRenderer in MainGame.java
	 */
	public void renderCounter(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		if (!this.hasEmptyHand()) {
			spriteBatch.draw(this.hand.getTexture(), this.handPosition.x, this.handPosition.y, 16 * Constants.UNIT_SCALE, 16 * Constants.UNIT_SCALE);
		}
		spriteBatch.end();

	}

	public void dispose(){
		put_down.dispose();
		pick_up.dispose();
		failure.dispose();
	}


}
