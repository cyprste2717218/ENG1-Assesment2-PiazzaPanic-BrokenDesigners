package com.github.brokendesigners.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.bubble.SimpleItemBubble;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.map.interactable.CustomerStation;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import java.util.ArrayList;

/**
 * EXTENDED CLASS
 *
 * A class representing a customer in the game. Each customer has a station assigned to them where they can
 * place an order and be served. Customers also have a desired meal, and will leave if they wait too long to be served.
 */
public class Customer {

	private boolean visible = false; // is visible?
	private boolean beenServed;
	private Texture texture; // texture
	private ArrayList<Animation<TextureRegion>> animations;
	// ^^ animation array - Customers dont interact though, so they only need 2 animations, Idle and move (in that order).
	private Vector2 worldPosition; // position of customer
	private CustomerStation station; // station assigned to customer
	protected Item desiredMeal; // desired meal for the customer
	private final float WIDTH; // width (sprite)
	private final float HEIGHT; // height (sprite)
	private SimpleItemBubble bubble; // the bubble associated with the customer.
	private CustomerPhase phase; // Phase of the customer - determines what they are doing (walking to station/spawn or being served)
	Sound success = Gdx.audio.newSound(Gdx.files.internal("audio/success.wav"));
	Sound failure = Gdx.audio.newSound(Gdx.files.internal("audio/failure.wav"));
	private Vector2 spawnPoint;
	private float stateTime; // used for the renderer to grab frame.
	private Match match;
	private long waitingStartTime = -1L;
	private long customerWaitTime = 90000L; //How long the customer waits to be served before leaving
	private float movement_speed = 0; //The speed that the customer moves to and from their station at

	/**
	 * Instantiates a new customer without animations.
	 *
	 * @param customerRenderer the customer renderer
	 * @param bubbleRenderer   the bubble renderer
	 * @param texture          the texture
	 * @param station          the customer station assigned to this customer
	 * @param desiredMeal      the desired meal for the customer
	 * @param spawnPoint       the spawn point of the customer
	 * @param match            the match this customer belongs to
	 */
	public Customer(CustomerRenderer customerRenderer, BubbleRenderer bubbleRenderer, Texture texture, CustomerStation station, Item desiredMeal, Vector2 spawnPoint, Match match){
		worldPosition = new Vector2(spawnPoint);
		this.setStation(station);
		this.texture = texture;
		this.desiredMeal = desiredMeal;

		this.WIDTH = this.texture.getWidth() * Constants.UNIT_SCALE;
		this.HEIGHT = this.texture.getHeight() * Constants.UNIT_SCALE;
		this.phase = CustomerPhase.SPAWNING;
		this.spawnPoint = spawnPoint;

		bubble = new SimpleItemBubble(bubbleRenderer, this.desiredMeal, new Vector2(this.getStation().getCustomerPosition().x + 1f, this.getStation().getCustomerPosition().y + 2f));

		customerRenderer.addCustomer(this);
		this.stateTime = 0;
		this.match = match;
		beenServed = false;
	}
	/*
	 * Instantiates Customer with animations
	 */

	public Customer(CustomerRenderer customerRenderer, BubbleRenderer bubbleRenderer, ArrayList<Animation<TextureRegion>> animations, CustomerStation station, Item desiredMeal, Vector2 spawnPoint, Match match){
		worldPosition = new Vector2(spawnPoint);
		this.setStation(station);
		this.animations = animations;
		this.desiredMeal = desiredMeal;

		this.phase = CustomerPhase.SPAWNING;
		this.spawnPoint = spawnPoint;

		bubble = new SimpleItemBubble(bubbleRenderer, this.desiredMeal, new Vector2(this.getStation().getCustomerPosition().x + 1f, this.getStation().getCustomerPosition().y + 2f));

		customerRenderer.addCustomer(this);

		this.stateTime = 0;

		this.WIDTH = this.animations.get(0).getKeyFrame(0).getRegionWidth() * Constants.UNIT_SCALE;
		this.HEIGHT = this.animations.get(0).getKeyFrame(0).getRegionHeight() * Constants.UNIT_SCALE;

		this.texture = null;
		this.match = match;
		beenServed = false;
	}

	//This is a headless implementation of the customer constructor
	public Customer(CustomerStation station, Item desiredMeal, Vector2 spawnPoint, Match match){
		worldPosition = new Vector2(spawnPoint);
		this.setStation(station);
		this.animations = new ArrayList<>();
		this.desiredMeal = desiredMeal;
		this.phase = CustomerPhase.SPAWNING;
		this.spawnPoint = spawnPoint;

		this.stateTime = 0;

		this.WIDTH = 32;
		this.HEIGHT = 32;

		this.texture = null;
		this.match = match;
		beenServed = false;
	}
	/**

	 This method spawns the customer and makes them visible.
	 @return true if the customer is spawned successfully, false otherwise
	 */
	public boolean spawn(){
		visible = true;
		movement_speed = 1 * Constants.UNIT_SCALE;
		phase = CustomerPhase.MOVING_TO_STATION;
		// PLAY SOUND - DOOR OPENING / BELL RING / ETC
		return true;
	}
	/**

	 This method updates the customer every frame similar to how the player is updated.
	 Extended method
	 */
	public void update(){
		switch (getPhase()) {
			case MOVING_TO_STATION: // Phase 0 -- Customer is moving to Ordering Station
				movingToStationPhase();
				break;
			case WAITING: // Phase 1 -- Customer is waiting for meal
				waitingPhase();
				break;
			case LEAVING: // Phase 2 -- Customer is walking to the exit
				leavingPhase();
				break;
			case DESPAWNING: // Phase 3 -- Customer has completed its tasks, despawns.
				visible = false;
				break;
			default:
				break;
		}
	}
	/**
	 * Created Method

	 This method handles the customer's behavior during the moving to station phase.
	 */
	private void movingToStationPhase(){
		if (worldPosition.y != getStation().getCustomerPosition().y) {
			worldPosition.y += movement_speed;
			if (Math.abs((worldPosition.y - getStation().getCustomerPosition().y)) <= movement_speed) {
				worldPosition.y = getStation().getCustomerPosition().y;
			}
		} else if (worldPosition.x != getStation().getCustomerPosition().x) {
			worldPosition.x += movement_speed;
			if (Math.abs((worldPosition.x - getStation().getCustomerPosition().x)) <= movement_speed){
				worldPosition.x = getStation().getCustomerPosition().x;
			}
		} else if (worldPosition.equals(getStation().getCustomerPosition())) {
			setPhase(CustomerPhase.WAITING);
			if(bubble != null) bubble.setVisible(true);
		}
	}
	/**
	 * Created Method

	 This method handles the customer's behavior during the waiting phase.
	 */
	private void waitingPhase(){
		if(waitingStartTime == -1L) waitingStartTime = TimeUtils.millis();
		if(TimeUtils.timeSinceMillis(waitingStartTime) > customerWaitTime){
			if(bubble != null)	bubble.setVisible(false);
			if(failure != null) failure.play();
			setPhase(CustomerPhase.LEAVING);
		}
		if (!getStation().hasEmptyHand()) {
			if (getStation().getItemInHand().equals(desiredMeal)) {
				beenServed = true;
				match.incrementReputationPoints();
				match.addMoney(desiredMeal.name, waitingStartTime, customerWaitTime/1000);
				success.play();
				setPhase(CustomerPhase.LEAVING);
				getStation().dumpHand();
				if(bubble != null)  bubble.setVisible(false);
			}
			else{
				if(failure != null)	failure.play();
				getStation().dumpHand();
				match.failedOrder();
			}
		}
	}
	/**
	 * Created Method

	 Updates the customer's position during the leaving phase. If the customer's world position is not at its spawn point,
	 the customer moves towards the spawn point on the x-axis.

	 */
	private void leavingPhase(){
		if (worldPosition.x != spawnPoint.x) {
			worldPosition.x -= movement_speed;
			if (Math.abs(worldPosition.x - spawnPoint.x) <= movement_speed){
				worldPosition.x = spawnPoint.x;
			}
		} else if (worldPosition.y != 0) {
			worldPosition.y -= movement_speed;
			if (Math.abs(worldPosition.y - spawnPoint.y) <= movement_speed) {
				worldPosition.y = 0;
			}
		} else {
			match.incrementCustomersSoFar();
			setPhase(CustomerPhase.DESPAWNING);
		}
	}

	public Texture getTexture() {
		return texture;
	}

	public Vector2 getWorldPosition() {
		return worldPosition;
	}

	public boolean isVisible(){
		return visible;
	}
	public void setIsVisible(boolean visible){
		this.visible = visible;
	}

	public void dispose(){
		texture.dispose();
		success.dispose();
		failure.dispose();
	}

	public CustomerPhase getPhase() {
		return phase;
	}

	public void setPhase(CustomerPhase phase) {
		this.phase = phase;
	}

	public Item getDesiredMeal() {
		return desiredMeal;
	}

	public Match getMatch(){ return match;}

	public CustomerStation getStation() {
		return station;
	}

	public void setStation(CustomerStation station) {
		this.station = station;
	}

	public boolean hasBeenServed() {
		return beenServed;
	}

	public void setBeenServed(boolean beenServed) {
		this.beenServed = beenServed;
	}

	public void setWorldPosition(Vector2 worldPosition) {
		this.worldPosition = worldPosition;
	}

	public float getWIDTH() {
		return WIDTH;
	}

	public float getHEIGHT() {
		return HEIGHT;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public long getWaitingStartTime() {
		return waitingStartTime;
	}

	public void setWaitingStartTime(long waitingStartTime) {
		this.waitingStartTime = waitingStartTime;
	}

	public long getCustomerWaitTime() {
		return customerWaitTime;
	}

	public void setCustomerWaitTime(long customerWaitTime) {
		this.customerWaitTime = customerWaitTime;
	}

	public ArrayList<Animation<TextureRegion>> getAnimations() {
		return animations;
	}

	public SimpleItemBubble getBubble() {
		return bubble;
	}
}
