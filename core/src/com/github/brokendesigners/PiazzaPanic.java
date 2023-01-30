package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.Station;

import com.github.brokendesigners.textures.Animations;
import com.github.brokendesigners.textures.Atlases;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import com.github.brokendesigners.renderer.PlayerRenderer;
import com.github.brokendesigners.textures.Textures;


public class PiazzaPanic extends ApplicationAdapter {
	Viewport viewport;
	OrthographicCamera camera;
	OrthographicCamera hud_cam;
	private float VIRTUAL_WIDTH  = 32; // Width of the world
	private float VIRTUAL_HEIGHT = 18; // Height of the world

	SpriteBatch spriteBatch;
	SpriteBatch hud_batch;

	PlayerRenderer playerRenderer;
	CustomerRenderer customerRenderer;
	BubbleRenderer bubbleRenderer;

	TiledMap map_test;
	OrthogonalTiledMapRenderer mapRenderer;

	InputProcessor inputProcessor;

	Player player1;
	Player player2;
	//Player player3;
	ArrayList<Player> playerList;
	int selectedPlayer;


	Kitchen kitchen;
	CustomerManager customerManager;
	ItemInitialiser initialiser;



	@Override
	public void create () {
		// ITEM BUILDING
		initialiser = new ItemInitialiser();
		initialiser.initialise();

		// CAMERA & VIEWPORT BUILDING
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // Camera in charge of rendering the world
		hud_cam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // Camera in charge of rendering the HUD

		camera.setToOrtho(false, VIRTUAL_WIDTH/16, VIRTUAL_HEIGHT/16);
		hud_cam.setToOrtho(false);

		camera.update();
		hud_cam.update();

		camera.position.set(0, 0, 1);
		camera.zoom = 2f; // Zooms the camera out (Smalle number = Zoomed in more)

		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

		// SpriteBatch BUILDING
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined); // Renderer for everything except HUD
		hud_batch = new SpriteBatch();
		hud_batch.setProjectionMatrix(hud_cam.combined); // Renderer for HUD

		// RENDERER BUILDING
		mapRenderer = new OrthogonalTiledMapRenderer(Constants.TILE_MAP, Constants.UNIT_SCALE, spriteBatch);
		mapRenderer.setView(camera.combined, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		customerRenderer = new CustomerRenderer(spriteBatch); // In charge
		bubbleRenderer = new BubbleRenderer(spriteBatch);

		// MAP & MAP OBJECT BUILDING
		kitchen = new Kitchen(camera, spriteBatch, bubbleRenderer);

		ArrayList<KitchenCollisionObject> kitchenCollisionObjects = kitchen.getKitchenObstacles();
		Random c = new Random();
		customerManager = new CustomerManager( // Manages when customers should spawn in and holds the Timer
				customerRenderer,
				bubbleRenderer,
				c.nextInt(12),
				new Vector2(
						(float)6.5 * 4,
						(float)3.5 * 4),
				kitchen.getCustomerStations());

		// BUILD PLAYERS
		initialisePlayers(); //initialisePlayers is at the end of this java class.


		inputProcessor = new InputAdapter(){ // Processes the input

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.UP){
					try {
						player1.pickUp(kitchen.getKitchenStations());
						player2.pickUp(kitchen.getKitchenStations());
						//player3.pickUp(kitchen.getKitchenStations());
						return true;
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (keycode == Keys.DOWN){
					player1.dropOff(kitchen.getKitchenStations());
					player2.dropOff(kitchen.getKitchenStations());
					//player3.dropOff(kitchen.getKitchenStations());

					return true;
				} else if (keycode == Keys.SPACE){
					player1.interact(kitchen.getKitchenStations());
					player2.interact(kitchen.getKitchenStations());
					//player3.interact(kitchen.getKitchenStations());
				} else if (keycode == Keys.TAB) {

					playerList.get(selectedPlayer).setSelected(false);
					selectedPlayer += 1;
					selectedPlayer = selectedPlayer % 3;
					playerList.get(selectedPlayer).setSelected(true);
				}
				return false;
			}
		};
		Gdx.input.setInputProcessor(inputProcessor);

		spriteBatch.enableBlending();
		customerManager.begin();

	}

	@Override
	public void resize(int width, int height){
		camera.update();
		hud_cam.update();

		viewport.update(width, height);

		spriteBatch.setProjectionMatrix(camera.combined);
		hud_batch.setProjectionMatrix(hud_cam.combined);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1f, 1f, 0.8f, 1f);

		camera.update();




		spriteBatch.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);

		player1.processMovement(kitchen.getKitchenObstacles());
		player2.processMovement(kitchen.getKitchenObstacles());
		//player3.processMovement(kitchen.getKitchenObstacles());

		spriteBatch.begin();

		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Tile Layer 2"));
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Tile Layer 1"));
		spriteBatch.end();




		customerRenderer.renderCustomers();

		playerRenderer.renderPlayers();

		bubbleRenderer.renderBubbles();




		camera.position.set(playerList.get(selectedPlayer).worldPosition);

		if (Gdx.input.isKeyPressed(Keys.NUM_1)){
			player1.setSelected(true);
			player2.setSelected(false);
			//player3.setSelected(false);
			selectedPlayer = 0;

		} else if (Gdx.input.isKeyPressed(Keys.NUM_2)){
			player1.setSelected(false);
			player2.setSelected(true);
			//player3.setSelected(false);
			selectedPlayer = 1;

		} /*else if (Gdx.input.isKeyPressed(Keys.NUM_3)){
			player1.setSelected(false);
			player2.setSelected(false);
			player3.setSelected(true);
			selectedPlayer = 2;
		}*/

		spriteBatch.begin();
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Tile Layer 3"));
		spriteBatch.end();
		customerManager.update(spriteBatch, hud_batch);

		for (Station station : kitchen.getKitchenStations()){

			station.renderCounter(spriteBatch);
		}
	}


	@Override
	public void dispose () {
		spriteBatch.dispose();
		map_test.dispose();
		Kitchen.dispose();
		Textures.dispose();
		Atlases.dispose();
		ItemRegister.dispose();
	}

	public void initialisePlayers(){

		//ANIMATION ARRAYS:
		ArrayList<Animation<TextureRegion>> glibbert_animations = new ArrayList<>();
		// Array of player animations
		// Index 0 is idle animation
		// Index 1 is move animation
		// Index 2 is action animation
		glibbert_animations.add(Animations.glibbert_idleAnimation);
		glibbert_animations.add(Animations.glibbert_moveAnimation);
		glibbert_animations.add(Animations.glibbert_actionAnimation);
		playerRenderer = new PlayerRenderer(spriteBatch);

		ArrayList<Animation<TextureRegion>> bluggus_animations = new ArrayList<>();
		bluggus_animations.add(Animations.bluggus_idleAnimation);
		bluggus_animations.add(Animations.bluggus_moveAnimation);
		bluggus_animations.add(Animations.bluggus_idleAnimation); // bluggus has no action animation but still needs to have an animation referenced


		//BUILDING PLAYERS
		playerList = new ArrayList<>(); // List of Players - used to determine which is active

		player1 = new Player(playerRenderer, glibbert_animations, new Vector3((float)6.5 * 4, (float)3.5 * 4, 0), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE);
		player1.setRenderOffsetX(-1 * Constants.UNIT_SCALE);
		// ^^ Offset where the sprite will render relative to the invisible rectangle
		// which represents the players position/collision boundaries

		playerList.add(player1);

		// repeat for Player 2 & 3
		player2 = new Player(playerRenderer, bluggus_animations, new Vector3(0, 0, 0), 54 * Constants.UNIT_SCALE, 51 * Constants.UNIT_SCALE);
		player2.setWidth(26 * Constants.UNIT_SCALE);
		playerList.add(player2);
		player2.setRenderOffsetX(-13 * Constants.UNIT_SCALE);
		//player3 = new Player(playerRenderer, glibbert_animations, new Vector3(1, 0, 0), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE);
		//playerList.add(player3);
		player1.setSelected(true);
		selectedPlayer = 0;

	}
}
