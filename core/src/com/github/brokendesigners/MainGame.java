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
import com.github.brokendesigners.map.interactable.Station;
import com.github.brokendesigners.map.powerups.CarryCapacityPowerUp;
import com.github.brokendesigners.map.powerups.CustomerWaitTimePowerUp;
import com.github.brokendesigners.map.powerups.DoubleMoneyPowerUp;
import com.github.brokendesigners.map.powerups.SpeedPowerUp;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import com.github.brokendesigners.renderer.PlayerRenderer;
import com.github.brokendesigners.textures.Animations;

import java.security.Key;
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
		this.kitchen = new Kitchen(camera, spriteBatch, bubbleRenderer);

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
			setSelectedPlayer(0);			
    	}
		else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
		  setSelectedPlayer(1);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
		  setSelectedPlayer(2);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.F)){
			//SpeedPowerUp speedPowerUp = new SpeedPowerUp(new Vector3(5,5,5),1,1,playerList.get(selectedPlayer), this);
			CarryCapacityPowerUp powerUp = new CarryCapacityPowerUp(new Vector3(5,5,5),1,1,playerList.get(selectedPlayer), this, 10);
			powerUp.usePowerUp();
		}

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
		spriteBatch.end();
		customerManager.update(spriteBatch, hud_batch);

		for (Station station : kitchen.getKitchenStations()) {
			station.renderCounter(spriteBatch);
		}
		bubbleRenderer.renderBubbles();
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

		for(int i  = 0; i < 3; i++){
			Player player = new Player(playerRenderer, playerAnimations.get(i), new Vector2(kitchen.getPlayerSpawnPoint().x + (i * 32 * Constants.UNIT_SCALE), kitchen.getPlayerSpawnPoint().y), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE);
			player.setRenderOffsetX(-1 * Constants.UNIT_SCALE);
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
		}
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


}
