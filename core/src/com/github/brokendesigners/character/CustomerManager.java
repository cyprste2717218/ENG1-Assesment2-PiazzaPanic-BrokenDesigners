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

	public long spawningTime;
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

	public CustomerManager(int customerNumber, Vector2 spawnPoint, Match match){
		this.match = match;
		this.customerNumber = customerNumber;
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

	//Checks for endless or scenario mode
	//In scenario mode, we take a number, in endless we keep going
	//We periodically spawn a customer



	void handleCustomerPhases(){

//		Random random = new Random();
//		int temp;
//
//		for (int i = 0; i < customers.size(); i++){
//			Customer elem = customers.get(i);
//			elem.update();
//			if (elem.getPhase() == CustomerPhase.DESPAWNING){
//				if(elem.beenServed)	match.incrementCustomersServed();
//				else match.decrementReputationPoints();
//
//				elem.station.setServingCustomer(false);
//				elem.setPhase(CustomerPhase.UNLOADING);
//				elem.visible = false;
//
//			} else if ((!elem.isVisible()) && !elem.station.isServingCustomer() && elem.getPhase() == CustomerPhase.SPAWNING) {
//				if(i == customers.size()-3){
//					temp = random.nextInt(1,2);
//				} else if(i >= customers.size()-2){
//					temp = 1;
//				} else{
//					temp = random.nextInt(1,3);
//				}
//				for(int j = 0; j<temp; j++,i++){
//					customers.get(i).station.setServingCustomer(true);
//					customers.get(i).spawn();
//					customers.get(i).update();
//				}
//
//			}
//		}

		for (Customer customer : customers){
			customer.update();
			if (customer.getPhase() == CustomerPhase.DESPAWNING){

				if(customer.beenServed)	{
					match.incrementCustomersServed();
				} else {
					match.decrementReputationPoints();
				}

				customer.getStation().setServingCustomer(false);
				customer.setPhase(CustomerPhase.UNLOADING);
				customer.visible = false;

			} else if ((!customer.isVisible()) && !customer.getStation().isServingCustomer() && customer.getPhase() == CustomerPhase.SPAWNING) {
				customer.getStation().setServingCustomer(true);
				customer.spawn();
			}
		}
	}

	public void handleHUD(SpriteBatch hud_batch){
		hud_batch.begin();
		CharSequence str = isComplete() ? timeToString(finalTime) : timeToString(elapsedTime);
		font.draw(hud_batch, str, 100, 100);
		font.draw(hud_batch, "Rep Points:" + match.getReputationPoints() , 100, 200);
		font.draw(hud_batch, "Money: £" + match.getMoney(), 1000, 800);
		hud_batch.end();
	}

	void spawnCustomer(){
		if(TimeUtils.timeSinceMillis(spawningTime) > 10000L){
			spawningTime = TimeUtils.millis();
			Random random = new Random();
			for(int i = 0; i < random.nextInt(4); i++){
				if(getCustomerStations() == null || getCustomerStations().isEmpty()) continue;
				CustomerStation station = getCustomerStations().get(random.nextInt(getCustomerStations().size()-1));
				customers.add(new Customer(customerRenderer, bubbleRenderer, animations,
						station, ItemRegister.itemRegister.get(getMeal()), spawnPoint, match));
			}
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
}
