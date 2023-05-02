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

import java.util.ArrayList;
import java.util.Random;


/**

 The CustomerManager class is responsible for managing the spawning and timing of the customers.
 It creates an ArrayList of customers and an ArrayList of customer stations,
 and assigns the customers to random stations. The class handles the CustomerPhases,
 spawning the customers, and displaying the HUD.
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
	boolean testing = false;

	/**
	 * Instantiates a CustomerManager object.
	 * Makes customers equal to customerNumber
	 * then assigns them to random CustomerStations.
	 * @param customerRenderer A CustomerRenderer object.
	 * @param bubbleRenderer A BubbleRenderer object.
	 * @param customerNumber An int that represents the number of customers.
	 * @param spawnPoint A Vector2 object that represents the spawn point.
	 * @param stations An ArrayList of CustomerStation objects.
	 * @param match A Match object.
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

	public CustomerManager(int customerNumber, Vector2 spawnPoint, Match match){
		this.match = match;
		this.customerNumber = customerNumber;
		this.spawnPoint = spawnPoint;
		customers = new ArrayList<>();
		customerStations = new ArrayList<>();
		testing = true;
	}
	/**

	 Starts the match and begins the timer.
	 @return true if the match has successfully begun.
	 */
	public boolean begin(){
		running = true;
		timer.scheduleTask(new Task() {
			@Override
			public void run() {
				elapsedTime += 1;
			}
		}, 0, 1);
		return true;
	}
	/**

	 Returns a random meal from a list of available meals.
	 @return A String representing the name of the meal.
	 */
	public String getMeal(){
		Random rnd = new Random();
		int mealInt = rnd.nextInt(4);
		switch (mealInt){ // Which meal do they want? Add more cases as more are added.
			case(0):
				return "Salad";
			case(1):
				return "Burger";
			case(2):
				return "Baked_Pizza";
			case(3):
				return "Baked_JacketPotato";
			default:
				return "VoidItem";
		}
	}
	/**

	 Handles the phases of each customer in the match.
	 */
	private void handleCustomerPhases(){
		for (Customer customer : customers){
			customer.update();
			if (customer.getPhase() == CustomerPhase.DESPAWNING){
				if(customer.hasBeenServed())	{
					match.incrementCustomersServed();
				} else {
					match.decrementReputationPoints();
				}

				customer.getStation().setServingCustomer(false);
				customer.setPhase(CustomerPhase.UNLOADING);
				customer.setIsVisible(false);

			} else if (!customer.isVisible() && customer.getPhase() == CustomerPhase.SPAWNING) {
				if(testing){
					customer.spawn();
				}
				else if(!customer.getStation().isServingCustomer()){
					customer.getStation().setServingCustomer(true);
					customer.spawn();
				}
			}
		}
	}
	/**

	 Handles the Heads-Up Display (HUD) for the match.
	 @param hud_batch The SpriteBatch used to render the HUD.
	 */
	public void handleHUD(SpriteBatch hud_batch){
		hud_batch.begin();
		CharSequence str = isComplete() ? timeToString(finalTime) : timeToString(elapsedTime);
		font.draw(hud_batch, str, 100, 100);
		font.draw(hud_batch, "Rep Points:" + match.getReputationPoints() , 100, 200);
		font.draw(hud_batch, "Money: £" + match.getMoney(), 950, 800);
		hud_batch.end();
	}

	/**

	 Spawns customers based on the elapsed time since the last spawn.
	 */
	private void spawnCustomer(){
		if(TimeUtils.timeSinceMillis(spawningTime) > 10000L){
			spawningTime = TimeUtils.millis();
			Random random = new Random();
			for(int i = 0; i < random.nextInt(3) + 1; i++){
				if(getCustomerStations() == null || getCustomerStations().isEmpty() || i >= customerStations.size()) continue;
				CustomerStation station = getCustomerStations().get(random.nextInt(getCustomerStations().size()));
				if(testing){
					customers.add(new Customer(station, ItemRegister.itemRegister.get(getMeal()), spawnPoint, match));
				}
				else{
					customers.add(new Customer(customerRenderer, bubbleRenderer, animations,
							station, ItemRegister.itemRegister.get(getMeal()), spawnPoint, match));
				}

			}
		}
	}
	/**

	 Updates the match by spawning customers, handling their phases, and updating the HUD.

	 @param hud_batch The SpriteBatch used to render the HUD.
	 */
	public void update(SpriteBatch hud_batch){ // Runs through the array of customers and updates them.
		if (!isComplete()){
			spawnCustomer();
			handleCustomerPhases();

			if(isComplete()){
				finalTime = elapsedTime;
				font.setColor(Color.RED);
			}
		}
		if(!testing) handleHUD(hud_batch);
	}
	/**

	 Converts an integer time value in seconds to a string format MM:SS.

	 @param time the time value in seconds

	 @return the formatted string in MM:SS format
	 */
	public String timeToString(int time){ // converts integer time to string MM:SS
		if(time < 0) return "";
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
	public int getCustomerNumber() {
		return customerNumber;
	}
	public int getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(int elapsedTime){
		this.elapsedTime = elapsedTime;
	}
	public Match getMatch() {
		return match;
	}
	public ArrayList<Customer> getCustomers(){
		return customers;
	}
	public ArrayList<CustomerStation> getCustomerStations() {
		return customerStations;
	}
	public long getSpawningTime() {
		return spawningTime;
	}
	public void setSpawningTime(long spawningTime) {
		this.spawningTime = spawningTime;
	}
}
