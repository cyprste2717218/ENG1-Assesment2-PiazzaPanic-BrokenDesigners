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

	public PlayerRenderer(SpriteBatch batch){
		this.batch = batch;
		players = new ArrayList<>();
	}

	public void addPlayer(Player player){
		this.players.add(player);
	}

	public void renderPlayers(){
		this.batch.begin();

		this.players.sort(new Comparator<Player>() {
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

		for (Player player : players){
			if (player.animations != null){
				player.keyFrame += Gdx.graphics.getDeltaTime();

				if (player.isSelected()){
					if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){

						this.batch.draw(player.animations.get(1).getKeyFrame(player.keyFrame),
							player.getWorldPosition().x - 1 * Constants.UNIT_SCALE,
							player.getWorldPosition().y,
							player.SPRITE_WIDTH,
							player.SPRITE_HEIGHT);
					}
					else{

						this.batch.draw(player.animations.get(0).getKeyFrame(player.keyFrame),
							player.getWorldPosition().x - 1 * Constants.UNIT_SCALE,
							player.getWorldPosition().y,
							player.SPRITE_WIDTH,
							player.SPRITE_HEIGHT);

					}
				} else {

					this.batch.draw(player.animations.get(0).getKeyFrame(player.keyFrame),
						player.getWorldPosition().x - 1 * Constants.UNIT_SCALE,
						player.getWorldPosition().y,
						player.SPRITE_WIDTH,
						player.SPRITE_HEIGHT);
				}
			}
			else {

				this.batch.draw(
					player.texture,
					player.getWorldPosition().x - 1 * Constants.UNIT_SCALE,
					player.getWorldPosition().y,
					player.SPRITE_WIDTH,
					player.SPRITE_HEIGHT);
			}
			if (!player.hand.isEmpty()){
				float stackOffset = 7 * Constants.UNIT_SCALE;
				for (Item item : player.hand.getHeldItems()){
					this.batch.draw(
						item.getTexture(),
						player.getWorldPosition().x + 3 * Constants.UNIT_SCALE,
						player.getWorldPosition().y + stackOffset,
						16 * Constants.UNIT_SCALE,
						16 * Constants.UNIT_SCALE);
					stackOffset += 0.5;
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
