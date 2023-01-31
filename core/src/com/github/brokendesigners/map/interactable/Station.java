package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;

import java.lang.reflect.InvocationTargetException;

public abstract class Station {

	protected Rectangle interactionArea;
	protected String station_name;
	protected boolean interacting = false;
	public Item hand;
	public Boolean storing;
	public Vector2 handPosition = new Vector2(0,0);
	public boolean inuse;

	Sound pick_up = Gdx.audio.newSound(Gdx.files.internal("audio/pick_up.wav"));
	Sound put_down = Gdx.audio.newSound(Gdx.files.internal("audio/put_down.wav"));


	protected Station(Rectangle rectangle, String n) {
		this.station_name = n;
		this.hand = null;
		this.storing = false;
		this.interactionArea = rectangle;
		this.handPosition.x = this.interactionArea.x;
		this.handPosition.y = this.interactionArea.y;
		this.inuse = false;
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

	public boolean pickUp(Player player){
		if (this.hasEmptyHand() || player.hand.isFull() || this.interacting){
			return false;
		} else {
			pick_up.play();
			player.hand.give(hand);
			this.dumpHand();
			return true;
		}
	}

	public boolean dropOff(Player player){
		if (this.hasEmptyHand()){
			put_down.play();
			this.hand = player.hand.drop();
			return true;
		} else {
			return false;
		}
	}
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
	}


}
