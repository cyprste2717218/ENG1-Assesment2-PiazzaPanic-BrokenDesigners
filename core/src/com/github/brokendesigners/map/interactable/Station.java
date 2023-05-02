package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;

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
	public boolean locked;
	public static Sprite lockSprite;
	public Match match;
	public Sound pick_up;
	public Sound put_down;
	public Sound failure;
	public Sound unlockFX;

	public float stationUseTime = 0f;


	protected Station(Rectangle rectangle, String n) {
		this.pick_up = Gdx.audio.newSound(Gdx.files.internal("audio/pick_up.wav"));
		this.put_down = Gdx.audio.newSound(Gdx.files.internal("audio/put_down.wav"));
		this.failure = Gdx.audio.newSound(Gdx.files.internal("audio/failure.wav"));
		this.unlockFX = Gdx.audio.newSound(Gdx.files.internal("audio/unlock.wav"));
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
	/**
	 * Returns the Rectangle representing the interaction area of the station.
	 *
	 * @return the interaction area Rectangle
	 */
	public Rectangle getInteractionArea() {
		return interactionArea;
	}
	/**
	 * Returns the name of the station.
	 *
	 * @return the name of the station
	 */
	public String getStation_name(){
		return station_name;
	}

//	public boolean dropOff(Player player){
//		return false;
//	}

	public boolean action(Player player){return false;}

	/**
	 * Determines if the specified item can be used with this station.
	 *
	 * @param conditions an array of Strings representing the conditions that must be met for the operation to be applicable
	 * @param state a String representing the station state that must be met for the operation to be applicable
	 * @param itemName the name of the item to check for applicability
	 * @return true if the item can be used with this station, false otherwise
	 */
	public Boolean Applicable(String[] Conditions,String state, String itemName)
	{
		if(this.station_name == state) //Checks if its in the correct station before preforming
		{
			for(int i = 0;i<Conditions.length;i++)
			{
				if(Conditions[i] == itemName) return true;
			}
		}
		return false;
	}
	/**
	 * Determines if the station's hand is empty.
	 *
	 * @return true if the station's hand is empty, false otherwise
	 */
	public boolean hasEmptyHand(){
		return hand == null;
	}
	/**
	 * Sets the station's hand to null, effectively dumping any item that was being held.
	 */
	public void dumpHand(){
		this.hand = null;
	}
	/**
	 * Allows a player to pick up an item from the station if it is not currently being interacted with, and the player's hand is not full.
	 *
	 * @param player the player object that is picking up the item
	 * @return true if the item was successfully picked up, false otherwise
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
	/**
	 * Allows a player to drop off an item to the station if it is not currently locked, and the station's hand is empty.
	 *
	 * @param player the player object that is dropping off the item
	 * @return true if the item was successfully dropped off, false otherwise
	 */
	public boolean dropOff(Player player){
		if (this.locked)	{
			failure.play();
			System.out.println("Station Locked");
		} else if (this.hasEmptyHand()) {
			if (!player.hand.isEmpty()) {
				put_down.play();
			}
			this.hand = player.hand.drop();
			return true;
		} else {
			return false;
		}
		return false;
	}
	/**
	 * Renders the items on the station's counter, if any.
	 *
	 * @param spriteBatch the SpriteBatch to use for rendering
	 */
	public void renderCounter(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		if (!this.hasEmptyHand()) {
			spriteBatch.draw(this.hand.getTexture(), this.handPosition.x, this.handPosition.y, 16 * Constants.UNIT_SCALE, 16 * Constants.UNIT_SCALE);
		}
		spriteBatch.end();

	}
	/**
	 * Activates the station's lock sprite, if it is currently locked.
	 *
	 * @param spriteBatch the SpriteBatch to use for rendering
	 */
	public void activateLock(SpriteBatch spriteBatch)  {
		lockSprite = new Sprite(new Texture(Gdx.files.internal("items/lock.png")));
		spriteBatch.begin();
		if (this.locked)	{
			spriteBatch.draw(lockSprite, (float) (this.handPosition.x-0.5), this.handPosition.y, 24 * Constants.UNIT_SCALE, 24 * Constants.UNIT_SCALE);
		}
		spriteBatch.end();
	}
	/**
	 * Unlocks the station if it is currently locked and the match has enough money to pay the unlocking fee.
	 */
	public void unlockStation()	{
		if (this.locked && this.match.getIntMoney() >= 10) {
			this.locked = false;
			unlockFX.play();
			System.out.println("Station Unlocked");
			this.match.subtractMoney(10);
		} else{
			System.out.println("Insufficient Funds");
			failure.play();
		}
	}
	/**
	 * Sets the match that this station belongs to.
	 *
	 * @param match the Match object to set as the station's match
	 */
	public void setMatch(Match match)	{
		this.match = match;
	}
	/**
	 * Returns whether or not the station is currently locked.
	 *
	 * @return true if the station is locked, false otherwise
	 */
	public boolean isLocked(){return locked;}
	/**
	 * Sets whether or not the station is currently locked.
	 *
	 * @param isLocked true to lock the station, false to unlock it
	 */
	public void setIsLocked(boolean isLocked){locked = isLocked;}
	public float getStationUseTime(){return stationUseTime;}
	public Item getItem(){return hand;}

	public float getAdjustedStationUseTime(){return stationUseTime * match.getDifficultyLevel().getSpeedMultiplier();}

	public void dispose(){
		put_down.dispose();
		pick_up.dispose();
		failure.dispose();
		unlockFX.dispose();
	}
	public void setInteractionArea(Rectangle rectangle)	{
		interactionArea = rectangle;
	}

}
