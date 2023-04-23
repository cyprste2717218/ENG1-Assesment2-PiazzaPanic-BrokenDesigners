package com.github.brokendesigners.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import java.util.ArrayList;
import java.util.Comparator;

public class PlayerRenderer {

	ArrayList<Player> players;
	SpriteBatch batch;

	/*
	 * Instantiates PlayerRenderer
	 */
	public PlayerRenderer(SpriteBatch batch){
		this.batch = batch;
		players = new ArrayList<>();
	}
	/*
	 * Adds player to render queue
	 */
	public void addPlayer(Player player){
		this.players.add(player);
	}

	/*
	 * Renders all players in the queue
	 */
	public void renderPlayers(){
		boolean[] current_axis = new boolean[]{false,false,false};
		int c = -1;
		this.batch.begin();

		this.players.sort(new Comparator<Player>() {
			// Sorts players so that players on a lower y value render in front of ones with a higher y value.
			@Override
			public int compare(Player o1, Player o2) {
				if(o1.getWorldPosition().y < o2.getWorldPosition().y){
					return 1;
				} else if(o1.getWorldPosition().y > o2.getWorldPosition().y){
					return -1;
				}
				return 0;
			}
		}); // This changes the "depth" of each player chef based on their relative y axes
		for (Player player : players) {
			c++;
			if (player.animations != null) { // Following code decides which frame of code each player needs to render.
				player.keyFrame += Gdx.graphics.getDeltaTime();
				TextureRegion frame;

				if (player.texture == null) {
					if (player.moving_disabled) {
						frame = player.animations.get(2).getKeyFrame(player.keyFrame);

					} else if (player.isSelected()) {
						if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S)
							|| Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){
							frame = player.animations.get(1).getKeyFrame(player.keyFrame); //selected and moving
						} else {  //Player selected and not moving
							frame = player.animations.get(0).getKeyFrame(player.keyFrame);
						}

					} else { //Idle and not selected
						frame = player.animations.get(0).getKeyFrame((player.keyFrame));
					}
					this.batch.draw(frame, player.getWorldPosition().x + player.getRenderOffsetX(),
						player.getWorldPosition().y, player.SPRITE_WIDTH, player.SPRITE_HEIGHT);
				} else {
					this.batch.draw(player.texture,
						player.getWorldPosition().x + player.getRenderOffsetX(),
						player.getWorldPosition().y, player.SPRITE_WIDTH, player.SPRITE_HEIGHT);
				}

				if (!player.hand.isEmpty()) { // renders all items the player may have.
					float stackOffset = 7 * Constants.UNIT_SCALE;
					//System.out.println(player.hand.heldItems.get(0).name);
					for (Item item : player.hand.heldItems) {
						//System.out.println("item name:"  + item.name);
						this.batch.draw(
							item.getTexture(),
							player.getWorldPosition().x + 4 * Constants.UNIT_SCALE
								+ player.getRenderOffsetX(),
							player.getWorldPosition().y + stackOffset,
							16 * Constants.UNIT_SCALE,
							16 * Constants.UNIT_SCALE);
						stackOffset += 0.5;
					}
				}
			}
		}
		this.batch.end();
	}
	public void dispose(){
		for (Player player: this.players){
			player.dispose();
		}
	}
}
