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
import com.github.brokendesigners.character.CustomerManager;
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

public class MainGame {
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
		this.map = map;
		this.mapRenderer = mapRenderer;
		this.inputProcessor = inputProcessor;
		this.spriteBatch = spriteBatch;
		this.camera = gameCamera;
		this.hud_cam = hudCamera;
		this.hud_batch = hud_batch;
		this.match = match;
	}


	public void create(){
		// MAP & MAP OBJECT BUILDING
		this.kitchen = new Kitchen(camera, spriteBatch, bubbleRenderer, match);

		ArrayList<KitchenCollisionObject> kitchenCollisionObjects = kitchen.getKitchenObstacles();

		customerManager = new CustomerManager( // Manages when customers should spawn in and holds the Timer
			customerRenderer,
			this.bubbleRenderer,
			5,
			kitchen.getCustomerSpawnPoint(),
			kitchen.getCustomerStations(),
				match);

		// BUILD PLAYERS
		initialisePlayers(); //initialisePlayers is at the end of this java class.

		spriteBatch.enableBlending();
		customerManager.begin();
		powerUpManager = new PowerUpManager(playerList.get(selectedPlayer), this, customerManager);
	}

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
			}    	}
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

		playerRenderer.renderPlayers();

		camera.position.set(new Vector3(playerList.get(selectedPlayer).worldPosition, 1));
		// ^^ camera follows selected player

		spriteBatch.begin();
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Front"));
		// ^^ renders this layer after player which allows the player to go behind walls.

		for(PowerUp powerUp: powerUpManager.getActivePowerUps()){
			powerUp.getSprite().draw(spriteBatch);
		}
		spriteBatch.end();


		for (Station station : kitchen.getKitchenStations()) {
			station.setMatch(match);
			station.renderCounter(spriteBatch);
			//Handles station preparation steps
			if(station instanceof IFailable){
				((IFailable) station).handleStationInteraction();
			}
		}
		for (Station station : kitchen.getLockedKitchenStations())	{
			station.activateLock(spriteBatch);
		}
		for (Player player : playerList)	{
			player.activateLockSprite(spriteBatch, playerList.indexOf(player));

		}
		bubbleRenderer.renderBubbles();
		customerManager.update(spriteBatch, hud_batch);
		//customerManager.handleHUD(spriteBatch);
	}

	public void initialisePlayers(){

		//A list that holds all the animations for the players
		ArrayList<ArrayList<Animation<TextureRegion>>> playerAnimations = new ArrayList<>();

		// Default three chefs
		playerAnimations.add(addGlibbert(Animations.glibbert_idleAnimation,Animations.glibbert_moveAnimation,Animations.glibbert_actionAnimation));
		playerAnimations.add(addGlibbert(Animations.glibbert_idleAnimation2,Animations.glibbert_moveAnimation2,Animations.glibbert_actionAnimation2));
		playerAnimations.add(addGlibbert(Animations.glibbert_idleAnimation3,Animations.glibbert_moveAnimation3,Animations.glibbert_actionAnimation3));

		playerRenderer = new PlayerRenderer(spriteBatch);

		//BUILDING PLAYERS
		playerList = new ArrayList<>(); // List of Players - used to determine which is active
		lockedPlayerList = new ArrayList<>(); // List of locked Players

		for(int i  = 0; i < 3; i++){

			Player player = new Player(playerRenderer, playerAnimations.get(i), new Vector2(kitchen.getPlayerSpawnPoints().get(i).x + (8 * Constants.UNIT_SCALE), kitchen.getPlayerSpawnPoints().get(i).y), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE, this, kitchen, match);
			player.setRenderOffsetX(-1 * Constants.UNIT_SCALE);
			if (i==1 || i==2)	{
				player.lockPlayer();
				lockedPlayerList.add(player);
			}
			playerList.add(player);






		}
		setSelectedPlayer(0);
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

	private void setSelectedPlayer(int selected){
		selectedPlayer = selected;
		for(int i = 0; i< playerList.size(); i++){
			playerList.get(i).setSelected(selected == i);
			if(powerUpManager != null)
				powerUpManager.setPlayer(playerList.get(selectedPlayer));
		}
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
	public void addUnlockedPlayer(Player player)	{
		if (player != null)	{
			this.playerList.add(player);
			this.lockedPlayerList.remove(player);
			System.out.println("LISTS="+this.playerList);
			System.out.println(this.lockedPlayerList);

		}

	}


}
