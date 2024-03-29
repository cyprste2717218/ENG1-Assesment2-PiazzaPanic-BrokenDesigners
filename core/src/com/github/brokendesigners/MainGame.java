package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.CustomerPhase;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.*;
import com.github.brokendesigners.map.powerups.*;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import com.github.brokendesigners.renderer.PlayerRenderer;
import com.github.brokendesigners.textures.Animations;

import java.util.ArrayList;

/**
 *
 *Extended class
 */

public class MainGame {

	public static SaveGame saveGame;
	ItemInitialiser itemInitialiser;

	PlayerRenderer playerRenderer;
	CustomerRenderer customerRenderer;
	BubbleRenderer bubbleRenderer;

	public Match match;
	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;

	InputProcessor inputProcessor;
	ArrayList<Player> playerList;
	ArrayList<Player> lockedPlayerList;
	int selectedPlayer;
	Kitchen kitchen;
	CustomerManager customerManager;
	ItemInitialiser initialiser;

	SpriteBatch spriteBatch;
	SpriteBatch hud_batch;
	ItemRegister itemRegister;

	float VIRTUAL_WIDTH = 32;  // Width of the world
	float VIRTUAL_HEIGHT = 18; // Height of the world

	OrthographicCamera camera;
	OrthographicCamera hud_cam;
	PowerUpManager powerUpManager;
	boolean testing;

	/**

	 The MainGame class is responsible for managing the game loop, rendering, and updating game state.

	 It initializes and manages the game's components such as the kitchen, customers, players, and power-ups.

	 @param spriteBatch the SpriteBatch used to render the game graphics

	 @param hud_batch the SpriteBatch used to render the HUD

	 @param gameCamera the OrthographicCamera used for the game world

	 @param hudCamera the OrthographicCamera used for the HUD

	 @param playerRenderer the PlayerRenderer used to render players

	 @param customerRenderer the CustomerRenderer used to render customers

	 @param bubbleRenderer the BubbleRenderer used to render speech bubbles

	 @param mapRenderer the OrthogonalTiledMapRenderer used to render the game map

	 @param inputProcessor the InputProcessor used to handle user input

	 @param match the Match object that holds game settings and state
	 */

	public MainGame(
		SpriteBatch spriteBatch,
		SpriteBatch hud_batch,
		OrthographicCamera gameCamera,
		OrthographicCamera hudCamera,
		PlayerRenderer playerRenderer,
		CustomerRenderer customerRenderer,
		BubbleRenderer bubbleRenderer,
		OrthogonalTiledMapRenderer mapRenderer,
		InputProcessor inputProcessor,
		Match match
	){
		this.playerRenderer = playerRenderer;
		this.customerRenderer = customerRenderer;
		this.bubbleRenderer = bubbleRenderer;
		this.mapRenderer = mapRenderer;
		this.inputProcessor = inputProcessor;
		this.spriteBatch = spriteBatch;
		this.camera = gameCamera;
		this.hud_cam = hudCamera;
		this.hud_batch = hud_batch;
		this.match = match;
		testing = false;
	}

	/**
	 * Created Constructor

	 A constructor for creating a MainGame object for testing.

	 @param gameCamera the OrthographicCamera used for the game world

	 @param hudCamera the OrthographicCamera used for the HUD

	 @param match the Match object that holds game settings and state

	 @param kitchen the Kitchen object used to manage game kitchen state
	 */
	public MainGame(OrthographicCamera gameCamera, OrthographicCamera hudCamera, Match match, Kitchen kitchen){
		this.camera = gameCamera;
		this.hud_cam = hudCamera;
		this.match = match;
		this.kitchen = kitchen;
		playerList = new ArrayList<>();
		customerManager = new CustomerManager(match.getCustomerNumber(), new Vector2(0,0), match);
		saveGame = new SaveGame(match,kitchen,playerList,customerManager, this);
		testing = true;
	}

	/**
		Extended Method

	 This method initializes and creates the game world, including the kitchen, customers, players, and power-ups.

	 @param isLoading true if the game is loading from a saved state, false otherwise

	 @param loader the LoadGame object used to load saved game state
	 */
	public void create(boolean isLoading, LoadGame loader){
		// MAP & MAP OBJECT BUILDING
		if(!testing) kitchen = new Kitchen(camera, spriteBatch, bubbleRenderer, match, isLoading, loader);

		ArrayList<KitchenCollisionObject> kitchenCollisionObjects = kitchen.getKitchenObstacles();

		customerManager = new CustomerManager( // Manages when customers should spawn in and holds the Timerc
			customerRenderer,
			this.bubbleRenderer,
			match.getCustomerNumber(),
			kitchen.getCustomerSpawnPoint(),
			kitchen.getCustomerStations(),
				match);

		if(isLoading){
			customerManager.setElapsedTime(loader.getElapsedTime());
			customerManager.setSpawningTime(TimeUtils.millis() - loader.getCustomerManagerTimeSinceSpawn());
			ArrayList<Animation<TextureRegion>> animations = new ArrayList<>();
			animations.add(Animations.bluggus_idleAnimation);
			animations.add(Animations.bluggus_moveAnimation);
			for(int i = 0; i < loader.getCustomerSize(); i++){
				Customer customer = new Customer(customerRenderer, bubbleRenderer, animations, kitchen.getCustomerStations().get(loader.getCustomerStationIndex().get(i)),
						loader.getCustomerOrders().get(i), kitchen.getCustomerSpawnPoint(), match);
				customer.getWorldPosition().x = loader.getCustomerX().get(i);
				customer.getWorldPosition().y = loader.getCustomerY().get(i);
				customer.setBeenServed(loader.getCustomerIsServed().get(i));
				customer.spawn();
				customer.setPhase(loader.getCustomerPhases().get(i));
				if(customer.getPhase() == CustomerPhase.WAITING){
					customer.setWaitingStartTime(TimeUtils.millis() - loader.getCustomerTimeSpentWaiting().get(i));
					if(customer.getBubble() != null && !customer.hasBeenServed()) customer.getBubble().setVisible(true);
				}
				customerManager.getCustomers().add(customer);
			}
		}

		// BUILD PLAYERS
		initialisePlayers(isLoading, loader, testing); //initialisePlayers is at the end of this java class.

		saveGame = new SaveGame(match,kitchen,playerList,customerManager, this);
		if(!testing) spriteBatch.enableBlending();
		customerManager.begin();
		powerUpManager = new PowerUpManager(playerList.get(selectedPlayer), match, customerManager, kitchen);
	}
	/**

	 Extended Method

	 This method is responsible for rendering the game. It clears the screen and sets the background color.

	 It updates the camera, sets the projection matrix, and sets the view for the map renderer.

	 It also processes the movement of all players and handles power-ups.

	 It renders the map layers in a specific order, then the customers, players, stations, and bubbles.

	 It updates the customer manager and HUD batch.
	 */
	public void renderGame(){

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(14 / 255f, 104 / 255f, 44 / 255f, 1f);

		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);

		for(Player player : playerList){
			player.processMovement(kitchen.getKitchenObstacles());
		}

		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			if (!this.playerList.get(0).isLocked()){
				setSelectedPlayer(0);
			}
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			if (!this.playerList.get(1).isLocked()){
				setSelectedPlayer(1);
			}
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			if (!this.playerList.get(2).isLocked()){
				setSelectedPlayer(2);
			}
		}
		powerUpManager.setPlayer(playerList.get(selectedPlayer));
		powerUpManager.handlePowerUps();

		spriteBatch.begin();
		// Renders map in specific order to allow some cool rendering effects.
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Floor"));
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Walls"));

		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Extras"));
		spriteBatch.end();

		customerRenderer.renderCustomers();

		camera.position.set(new Vector3(playerList.get(selectedPlayer).worldPosition, 1));
		// ^^ camera follows selected player

		spriteBatch.begin();
		for(PowerUp powerUp: powerUpManager.getActivePowerUps()){
			powerUp.getSprite().draw(spriteBatch);
		}
		spriteBatch.end();
		playerRenderer.renderPlayers();
		spriteBatch.begin();
		mapRenderer.renderTileLayer(
				(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("StationTops"));
		spriteBatch.end();
		// ^^ renders this layer after player which allows the player to go behind walls.
		for (Station station : kitchen.getKitchenStations()) {
			station.setMatch(match);
			station.renderCounter(spriteBatch);
			//Handles station preparation steps
			if(station instanceof IFailable){
				((IFailable) station).handleStationInteraction();
			}
		}
		spriteBatch.begin();
		mapRenderer.renderTileLayer(
				(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Front"));
		// ^^ renders this layer after player which allows the player to go behind walls.
		spriteBatch.end();

		for (Station station : kitchen.getLockedKitchenStations())	{
			station.activateLock(spriteBatch);
		}
		for (Player player : playerList)	{
			player.activateLockSprite(spriteBatch, playerList.indexOf(player));
		}
		bubbleRenderer.renderBubbles();
		customerManager.update(hud_batch);
	}
	/**
	 * Extended Method

	 Initializes the players in the game.
	 @param isLoading A boolean that indicates whether the game is loading or not.
	 @param loader An instance of LoadGame class used to load a saved game.
	 @param testing A boolean that indicates whether the game is in testing mode or not.
	 */
	public void initialisePlayers(boolean isLoading, LoadGame loader, boolean testing){

		ArrayList<ArrayList<Animation<TextureRegion>>> playerAnimations = new ArrayList<>();
		if(!testing){
			playerAnimations = setPlayerAnimations();
			playerRenderer = new PlayerRenderer(spriteBatch);
		}

		//BUILDING PLAYERS
		playerList = new ArrayList<>(); // List of Players - used to determine which is active
		lockedPlayerList = new ArrayList<>(); // List of locked Players

		if(isLoading){
			for(int i = 0; i <3; i++){
				Player player = testing ? new Player(kitchen.getPlayerSpawnPoints().get(i)) :
						new Player(playerRenderer, playerAnimations.get(i), new Vector2(loader.getChefX()[i], loader.getChefY()[i]), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE, this, kitchen, match);
				player.hand.heldItems = loader.getChefItemStacks()[i];
				player.setRenderOffsetX(-1 * Constants.UNIT_SCALE);
				if (loader.getChefLocked()[i])	{
					player.lockPlayer();
					lockedPlayerList.add(player);
				}
				playerList.add(player);
			}
			setSelectedPlayer(loader.getSelectedChef());
		}
		else{
			for(int i = 0; i < 3; i++){
				Player player = testing ? new Player(kitchen.getPlayerSpawnPoints().get(i)):
				new Player(playerRenderer, playerAnimations.get(i), new Vector2(kitchen.getPlayerSpawnPoints().get(i).x + (8 * Constants.UNIT_SCALE), kitchen.getPlayerSpawnPoints().get(i).y), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE, this, kitchen, match);
				player.setRenderOffsetX(-1 * Constants.UNIT_SCALE);
				if (i==1 || i==2)	{
					player.lockPlayer();
					lockedPlayerList.add(player);
				}
				playerList.add(player);
			}
			setSelectedPlayer(0);
		}
	}

	private ArrayList<ArrayList<Animation<TextureRegion>>> setPlayerAnimations(){
		//A list that holds all the animations for the players
		ArrayList<ArrayList<Animation<TextureRegion>>> playerAnimations = new ArrayList<>();

		// Default three chefs
		playerAnimations.add(addGlibbert(Animations.glibbert_idleAnimation,Animations.glibbert_moveAnimation,Animations.glibbert_actionAnimation));
		playerAnimations.add(addGlibbert(Animations.glibbert_idleAnimation2,Animations.glibbert_moveAnimation2,Animations.glibbert_actionAnimation2));
		playerAnimations.add(addGlibbert(Animations.glibbert_idleAnimation3,Animations.glibbert_moveAnimation3,Animations.glibbert_actionAnimation3));

		return playerAnimations;
	}

	/**
	 * @param idle Index 0 is idle animation
	 * @param move Index 1 is move animation
	 * @param action Index 2 is action animation
	 * @return list of the player's animation
	 */
	private ArrayList<Animation<TextureRegion>> addGlibbert(Animation<TextureRegion> idle, Animation<TextureRegion> move, Animation<TextureRegion> action){
		ArrayList<Animation<TextureRegion>> glibbert_animations = new ArrayList<>();
		glibbert_animations.add(idle);
		glibbert_animations.add(move);
		glibbert_animations.add(action);
		return glibbert_animations;
	}
	/**
	 Created Method

	 Sets the currently selected player based on the provided index.
	 @param selected the index of the player to be selected
	 */
	public void setSelectedPlayer(int selected){
		selectedPlayer = selected;
		for(int i = 0; i< playerList.size(); i++){
			playerList.get(i).setSelected(selected == i);
			if(powerUpManager != null)
				powerUpManager.setPlayer(playerList.get(selectedPlayer));
		}
	}

	public int getSelectedPlayerIndex(){
		return selectedPlayer;
	}

	public CustomerManager getCustomerManager(){
		return customerManager;
	}

	public void end(){
		customerManager.end();
		for (Player player : playerList){
			if (player.flipped){
				player.flipAnimations();
			}
		}
		customerRenderer.end();
	}



	public Kitchen getKitchen(){
		return kitchen;
	}
	public Match getMatch()	{
		return match;
	}
	public ArrayList<Player> getPlayerList()	{
		return playerList;
	}
	public ArrayList<Player> getLockedPlayerList()	{
		return lockedPlayerList;
	}
}
