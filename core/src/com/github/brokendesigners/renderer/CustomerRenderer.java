package com.github.brokendesigners.renderer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.character.Customer;
import java.util.ArrayList;

public class CustomerRenderer {

	public ArrayList<Customer> customers;
	public SpriteBatch batch;

	public CustomerRenderer(SpriteBatch batch){
		this.batch = batch;
		this.customers = new ArrayList<>();

	}

	public void addCustomer(Customer customer){
		customers.add(customer);
	}

	public void removeCustomer(Customer customer){
		customers.remove(customer);
	}

	public void renderCustomers(){
		this.batch.begin();
		for (Customer customer : customers){


			if (customer.isVisible()) {
				if (customer.texture == null) {
					customer.stateTime += Gdx.graphics.getDeltaTime();

					TextureRegion frame;
					if (customer.getPhase() == 1){

						frame = customer.animations.get(0).getKeyFrame(customer.stateTime);
					} else {

						frame = customer.animations.get(1).getKeyFrame(customer.stateTime);
					}
					this.batch.draw(frame, customer.getWorldPosition().x,
						customer.getWorldPosition().y, customer.WIDTH, customer.HEIGHT);
				} else {
					//this.batch.draw(customer.getTexture(), customer.getWorldPosition().x,
						//customer.getWorldPosition().y, customer.WIDTH, customer.HEIGHT);
				}
				if (customer.getPhase() == 2){
					this.batch.draw(customer.getDesiredMeal().getTexture(),
						customer.getWorldPosition().x + 19 * Constants.UNIT_SCALE,
						customer.getWorldPosition().y + 5 * Constants.UNIT_SCALE,
						12 * Constants.UNIT_SCALE,
						12 * Constants.UNIT_SCALE);
				}
			}
		}
		this.batch.end();
	}

}
