package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.Station;

import com.github.brokendesigners.menu.MenuScreen;
import com.github.brokendesigners.menu.MenuTextures;
import com.github.brokendesigners.textures.Animations;
import com.github.brokendesigners.textures.Atlases;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

	Boolean menu_mode;
	MenuScreen menu;
	MainGame game;



	@Override
	public void create () {
		// MENU BUILDING
		menu_mode = true;
		menu = new MenuScreen();

		// CAMERA & VIEWPORT BUILDING
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // Camera in charge of rendering the world
		hud_cam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // Camera in charge of rendering the HUD

		// ITEM BUILDING
		initialiser = new ItemInitialiser();
		initialiser.initialise();

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



		inputProcessor = new InputAdapter(){ // Processes the input


			@Override
			public boolean keyDown(int keycode) {
				if (menu_mode == false) {
					if (keycode == Keys.UP) {
						try {
							game.player1.pickUp(game.kitchen.getKitchenStations());
							game.player2.pickUp(game.kitchen.getKitchenStations());
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
					} else if (keycode == Keys.DOWN) {
						game.player1.dropOff(game.kitchen.getKitchenStations());
						game.player2.dropOff(game.kitchen.getKitchenStations());
						//player3.dropOff(kitchen.getKitchenStations());

						return true;
					} else if (keycode == Keys.SPACE) {
						game.player1.interact(game.kitchen.getKitchenStations());
						game.player2.interact(game.kitchen.getKitchenStations());
						//player3.interact(kitchen.getKitchenStations());
					} else if (keycode == Keys.TAB) {

						game.playerList.get(game.selectedPlayer).setSelected(false);
						game.selectedPlayer += 1;
						game.selectedPlayer = game.selectedPlayer % game.playerList.size();
						game.playerList.get(game.selectedPlayer).setSelected(true);
					} else if (keycode == Keys.ESCAPE) {
						game.customerManager.pause();
						menu_mode = true;
					}
				} else if (menu_mode == true){
					if (keycode == Keys.W || keycode == Keys.UP){
						menu.selectedButton -= 1;
						if (menu.selectedButton == -1){
							menu.selectedButton = 2;
						}
					} else if (keycode == Keys.S || keycode == Keys.DOWN) {
						menu.selectedButton += 1;
						if (menu.selectedButton == 3){
							menu.selectedButton = 0;
						}
					} else if (keycode == Keys.SPACE) {

						int menuOutput = menu.input(game != null);
						if (menuOutput == 1) {
							menu_mode = false;
							if (game != null){
								game.customerRenderer.end();
								game.customerRenderer.end();
								game.bubbleRenderer.end();
								game.end();



							}
							game = new MainGame(
								spriteBatch,
								hud_batch,
								camera,
								hud_cam,
								playerRenderer,
								customerRenderer,
								bubbleRenderer,
								mapRenderer,
								inputProcessor);
							game.create();

						} else if  (menuOutput == 0){
							menu_mode = false;
						}


					} else if (keycode == Keys.ESCAPE && game != null) {
						game.customerManager.unpause();
						menu_mode = false;
					}

				}
				return false;
			}
		};

		Gdx.input.setInputProcessor(inputProcessor);

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

		if (menu_mode) {
			menu.render(hud_batch);
		} else {
			game.renderGame();
			if (game.customerManager.isComplete()){
				menu_mode = true;
				menu.setFinalTime(game.customerManager.timeToString(game.customerManager.getFinalTime()));
				menu.complete = true;
				game.end();
				game = null;
			}
		}
	}


	@Override
	public void dispose () {
		spriteBatch.dispose();
		//Kitchen.dispose();
		//Textures.dispose();
		//Atlases.dispose();
		//ItemRegister.dispose();
	}


}
