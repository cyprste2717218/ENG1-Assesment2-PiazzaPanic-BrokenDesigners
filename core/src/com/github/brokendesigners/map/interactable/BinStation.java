package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.Player;

public class BinStation extends Station{


	public BinStation(Rectangle interactionArea) {
		super(interactionArea,"bin_station");
	}
	@Override
	public boolean dropOff(Player player){
		if (player.hand.isEmpty()){
			return false;
		}
		player.hand.drop();
		put_down.play();
		return true;
	}

	@Override
	public boolean pickUp(Player player) {
		return false;
	}

}
