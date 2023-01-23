package com.github.brokendesigners.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.bubble.SimpleItemBubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CustomerStation;

public class Customer {

	Boolean visible = false;
	Texture texture;
	Vector3 worldPosition;
	CustomerStation station;
	Item desiredMeal; // desired meal for the customer
	public final float WIDTH;
	public final float HEIGHT;
	SimpleItemBubble bubble;

	float movement_speed = 0; //Intentionally lowercase - NOT A CONSTANT

	public Customer(Texture texture, CustomerStation station){
		worldPosition = new Vector3(1/8f,0,0);
		this.station = station;
		this.texture = texture;
		this.desiredMeal = ItemRegister.itemRegister.get("wmd");

		this.WIDTH = this.texture.getWidth() * Constants.UNIT_SCALE; // 1-size-fits-all probably doesnt work, change this to be part of the constructor.
		this.HEIGHT = this.texture.getHeight() * Constants.UNIT_SCALE;

		bubble = new SimpleItemBubble(this.desiredMeal, new Vector3(this.station.getCustomerPosition().x + 1f, this.station.getCustomerPosition().y + 2f, 0));
	}

	public boolean Spawn(){
		if (station.isFree()){
			this.visible = true;
			this.movement_speed = 1 * Constants.UNIT_SCALE;
			// PLAY SOUND - DOOR OPENING / BELL RING / ETC
			return true;
		}
		else{
			return false;
		}
	}

	public boolean render(SpriteBatch batch){ // make Customer Renderer - Make 3 render phases.
		if (this.visible){
			//batch begin should happen before the loop in "PiazzaPanic.class" to avoid overuse or whatever.
			batch.draw(this.texture, worldPosition.x, worldPosition.y, this.WIDTH, this.HEIGHT, 0, 0, this.texture.getWidth(), this.texture.getHeight(), true, false);

			// basic code::
			if (worldPosition.y != this.station.getCustomerPosition().y){
				worldPosition.y += this.movement_speed;
			}
			else if (worldPosition.x != this.station.getCustomerPosition().x){
				worldPosition.x += this.movement_speed;
			}
			else if (!this.station.hasEmptyHand()){
				System.out.println("STATION HAS SOMETHING ON IT");
				if (this.station.getItemInHand().equals(this.desiredMeal)){
					this.station.customerPosition = new Vector3(0,0,0);
					this.movement_speed = this.movement_speed * -1;
					this.station.dumpHand();
				}
			}
			if (worldPosition.equals(this.station.getCustomerPosition())){
				if (worldPosition.equals(new Vector3(0,0,0))){
					this.visible = false;

				} else {
					this.bubble.setVisible(true);
				}

			}
			else{
				this.bubble.setVisible(false);
			}

			this.bubble.render(batch);

			return true;


		}
		else {
			return false;
		}
	}


}
