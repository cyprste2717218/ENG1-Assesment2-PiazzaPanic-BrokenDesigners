package com.github.brokendesigners.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CustomerStation;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import com.github.brokendesigners.textures.Animations;
import com.github.brokendesigners.textures.Textures;

import java.util.ArrayList;
import java.util.Random;

/*
 * Manages the spawning and timing of the customers.
 */
public class CustomerManager {



	private ArrayList<Customer> customers; // array of customers
	private boolean running;
	private Timer timer;
	private int elapsedTime; // Time measured in seconds
	private int finalTime;
	private int customerNumber;
	private int completeCustomers;
	private BitmapFont font;
	private int savedTime;
	/*
	 * Instantiates CustomerManager
	 * Makes customers equal to customerNumber
	 * then assigns them to random CustomerStations.
	 */
	public CustomerManager(
		CustomerRenderer customerRenderer,
		BubbleRenderer bubbleRenderer,
		int customerNumber,
		Vector2 spawnPoint,
		ArrayList<CustomerStation> stations){

		Random random = new Random();
		this.customerNumber = customerNumber;
		this.completeCustomers = 0;
		this.customers = new ArrayList<>();
		this.timer = new Timer();
		this.font = new BitmapFont();
		this.font.getData().setScale(6, 6);
		this.savedTime = 0;
		ArrayList<Animation<TextureRegion>> animations = new ArrayList<>();
		animations.add(Animations.bluggus_idleAnimation);
		animations.add(Animations.bluggus_moveAnimation);
		for (int i = 0; i < customerNumber; i++){
			CustomerStation station = stations.get(random.nextInt(stations.size()-1));
			int mealInt = random.nextInt(2);
			String meal;
			switch (mealInt){ // Which meal do they want? Add more cases as more are added.
				case(0):
					meal = "Salad";
					break;
				case(1):
					meal = "Burger";
					break;
				default:
					meal = "VoidItem";
					break;
			}



			customers.add(
				new Customer(
					customerRenderer,
					bubbleRenderer,
					animations,
					station,
					ItemRegister.itemRegister.get(meal),
					spawnPoint));

		}
	}

	public boolean begin(){
		this.running = true;
		timer.scheduleTask(new Task() {
			@Override
			public void run() {
				elapsedTime += 1;
			}
		}, 0, 1);
		return true;
	}

	public void update(SpriteBatch batch, SpriteBatch hud_batch){ // Runs through the array of customers and updates them.
		if (completeCustomers != customerNumber){
			for (Customer customer : customers){
				customer.update();
				if (customer.getPhase() == 3){
					completeCustomers += 1;
					customer.station.setServingCustomer(false);
					customer.setPhase(4);
					customer.visible = false;

				} else if ((!customer.isVisible()) && !customer.station.isServingCustomer() && customer.getPhase() == -1) {
					customer.station.setServingCustomer(true);
					customer.spawn();
				}
			}
			if (completeCustomers == customerNumber){
				System.out.println("upda");
				finalTime = elapsedTime;
				font.setColor(Color.RED);
			}
		}
		hud_batch.begin();
		CharSequence str;
		if (!(completeCustomers == customerNumber)){
			str = timeToString(elapsedTime);
		} else {
			str = timeToString(finalTime);
		}

		font.draw(hud_batch, str, 100, 100);

		hud_batch.end();

	}
	public String timeToString(int time){ // converts integer time to string MM:SS
		String currentTime = "";
		Integer minutes = time / 60;
		Integer seconds = time - (minutes * 60);
		currentTime += minutes.toString();
		currentTime += ":";

		if (seconds < 10){
			currentTime += "0";
		}
		currentTime += seconds.toString();
		return currentTime;
	}
	public void pause(){
		this.savedTime = this.elapsedTime;
	}

	public void unpause(){
		this.elapsedTime = this.savedTime;
	}

	public boolean isComplete(){
		if (completeCustomers == customerNumber){
			return true;
		}
		return false;
	}

	public int getFinalTime() {
		return finalTime;
	}

	public void end(){
		customers.clear();
	}
}
