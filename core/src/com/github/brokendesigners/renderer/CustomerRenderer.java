package com.github.brokendesigners.renderer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.enums.CustomerPhase;

import java.util.ArrayList;

public class CustomerRenderer {

	public ArrayList<Customer> customers;
	public SpriteBatch batch;

	public CustomerRenderer(SpriteBatch batch){
		this.batch = batch;
		this.customers = new ArrayList<>();

	}
 	/*
 	 * adds customer to the render queue
 	 */
	public void addCustomer(Customer customer){
		customers.add(customer);
	}
	/*
	 removes customer from the render queue.
	 */
	public void removeCustomer(Customer customer){
		customers.remove(customer);
	}
	/*
	 * renders all customers.
	 */
	public void renderCustomers() {
		this.batch.begin();
		for (Customer customer : customers) {


			if (customer.isVisible()) {
				if (customer.getTexture() == null) {
					customer.setStateTime(customer.getStateTime() + Gdx.graphics.getDeltaTime());

					TextureRegion frame;
					if (customer.getPhase() == CustomerPhase.WAITING) {

						frame = customer.getAnimations().get(0).getKeyFrame(customer.getStateTime());
						// ^^ renders idle animation if customer is at station
					} else {

						frame = customer.getAnimations().get(1).getKeyFrame(customer.getStateTime());
						// ^^ renders move animation if customer is moving.
					}
					this.batch.draw(frame, customer.getWorldPosition().x,
							customer.getWorldPosition().y, customer.getWIDTH(), customer.getHEIGHT());

				} else {
					this.batch.draw(customer.getTexture(), customer.getWorldPosition().x,
							customer.getWorldPosition().y, customer.getWIDTH(), customer.getHEIGHT());
					// ^^ renders customer without animation if they only have a texture.
				}
				if (customer.getPhase() == CustomerPhase.LEAVING && customer.hasBeenServed()) { // renders the customer with their item if they are leaving the restaurant.
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
	/*
	 * Clears customer render queue for when the game ends.
	 */
	public void end(){
		customers.clear();
	}

}
