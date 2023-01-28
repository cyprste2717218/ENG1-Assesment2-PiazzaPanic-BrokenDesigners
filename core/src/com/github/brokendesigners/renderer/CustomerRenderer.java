package com.github.brokendesigners.renderer;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
				this.batch.draw(customer.getTexture(), customer.getWorldPosition().x,
					customer.getWorldPosition().y, customer.WIDTH, customer.HEIGHT);
				customer.update();
			}
		}
		this.batch.end();
	}

}
