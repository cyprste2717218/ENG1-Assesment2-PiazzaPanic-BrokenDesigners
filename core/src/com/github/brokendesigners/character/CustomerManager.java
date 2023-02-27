package com.github.brokendesigners.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.enums.GameMode;
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
	private ArrayList<CustomerStation> customerStations; //list of customer stations
	private boolean running;
	private Timer timer;
	private int elapsedTime; // Time measured in seconds
	private int finalTime;
	private int customerNumber;
	private BitmapFont font;
	private int savedTime;

	private long spawningTime;
	Match match;
	CustomerRenderer customerRenderer;
	BubbleRenderer bubbleRenderer;
	Vector2 spawnPoint;
	ArrayList<Animation<TextureRegion>> animations;


	/*
	 * Instantiates CustomerManager
	 * Makes customers equal to customerNumber
	 * then assigns them to random CustomerStations.
	 */
	public CustomerManager(CustomerRenderer customerRenderer, BubbleRenderer bubbleRenderer, int customerNumber,
			Vector2 spawnPoint, ArrayList<CustomerStation> stations, Match match){

		Random random = new Random();
		this.customers = new ArrayList<>();
		this.timer = new Timer();
		this.font = new BitmapFont();
		this.font.getData().setScale(6, 6);
		this.savedTime = 0;
		this.match = match;
		this.customerNumber = customerNumber;

		ArrayList<Animation<TextureRegion>> animations = new ArrayList<>();
		animations.add(Animations.bluggus_idleAnimation);
		animations.add(Animations.bluggus_moveAnimation);
		spawningTime = TimeUtils.millis();
		customerStations = stations;
		this.customerRenderer = customerRenderer;
		this.bubbleRenderer = bubbleRenderer;
		this.animations = animations;
		this.spawnPoint = spawnPoint;
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

	String getMeal(){
		Random rnd = new Random();
		int mealInt = rnd.nextInt(2);
		switch (mealInt){ // Which meal do they want? Add more cases as more are added.
			case(0):
				return "Salad";
			case(1):
				return "Burger";
			default:
				return "VoidItem";
		}
	}

	//Checks for endless or scenario mode
	//In scenario mode, we take a number, in endless we keep going
	//We periodically spawn a customer



	void handleCustomerPhases(){
		for (Customer customer : customers){
			customer.update();
			if (customer.getPhase() == CustomerPhase.DESPAWNING){
				if(customer.beenServed)	match.incrementCustomersServed();
				else match.decrementReputationPoints();

				customer.station.setServingCustomer(false);
				customer.setPhase(CustomerPhase.UNLOADING);
				customer.visible = false;

			} else if ((!customer.isVisible()) && !customer.station.isServingCustomer() && customer.getPhase() == CustomerPhase.SPAWNING) {
				customer.station.setServingCustomer(true);
				customer.spawn();
			}
		}
	}

	void handleHUD(SpriteBatch hud_batch){
		hud_batch.begin();
		CharSequence str;
		str = isComplete() ? timeToString(finalTime) : timeToString(elapsedTime);
		font.draw(hud_batch, str, 100, 100);
		font.draw(hud_batch, "Rep Points:" + match.getReputationPoints() , 100, 200);
		hud_batch.end();
	}

	void spawnCustomer(){
		if(TimeUtils.timeSinceMillis(spawningTime) > 10000L){
			spawningTime = TimeUtils.millis();
			Random random = new Random();
			CustomerStation station = customerStations.get(random.nextInt(customerStations.size()-1));

			customers.add(new Customer(customerRenderer, bubbleRenderer, animations,
					station, ItemRegister.itemRegister.get(getMeal()), spawnPoint, match));
		}
	}

	public void update(SpriteBatch batch, SpriteBatch hud_batch){ // Runs through the array of customers and updates them.
		if (!isComplete()){
			spawnCustomer();
			handleCustomerPhases();

			if(isComplete()){
				finalTime = elapsedTime;
				font.setColor(Color.RED);
			}
		}
		handleHUD(hud_batch);
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
		return match.getCustomersSoFar() == customerNumber && match.getGameMode() == GameMode.SCENARIO || match.getGameMode() == GameMode.ENDLESS && match.getReputationPoints() <= 0;
	}

	public int getFinalTime() {
		return finalTime;
	}

	public void end(){
		customers.clear();
	}
}
