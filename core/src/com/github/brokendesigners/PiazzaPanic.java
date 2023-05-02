package com.github.brokendesigners;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.character.CustomerManager;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.Kitchen;

import com.github.brokendesigners.map.powerups.SpeedPowerUp;
import com.github.brokendesigners.menu.MenuScreen;
import com.github.brokendesigners.textures.Atlases;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import com.github.brokendesigners.renderer.PlayerRenderer;
import com.github.brokendesigners.textures.Textures;


public class PiazzaPanic extends ApplicationAdapter {

	Preferences pref;
	Viewport viewport; // Used for window resizing purposes.
	OrthographicCamera camera; // camera responsible for rendering the game world in the right place.
	OrthographicCamera hud_cam; // used for rendering the HUD and Main Menu in a constant place.
	private float VIRTUAL_WIDTH  = 32; // Width of the world
	private float VIRTUAL_HEIGHT = 18; // Height of the world

	SpriteBatch spriteBatch; // Spritebatch for game camera.
	SpriteBatch hud_batch;   // Spritebatch for HUD camera.

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

	MenuScreen menu;
	MainGame game;
	Match match;

	LoadGame loader;

	/**

	 This method is called when the game is started.

	 It is responsible for building the game world.
	 */
	@Override
	public void create () {
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

		// MENU BUILDING
		menu = new MenuScreen(hud_cam,this);
		menu.active = true;

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

		//region inputProcessor

		inputProcessor = new InputAdapter(){
			// Handles all non-polling inputs  --
			// Inputs that shouldn't be hold-downable are handled here. like picking up to the stack.


			@Override
			public boolean keyDown(int keycode) {
				if (!menu.active) { // If menu is not active
					if (keycode == Keys.UP) { // Handles player pickup
						try {
							for(Player player : game.playerList){
								player.pickUp(game.kitchen.getKitchenStations());
							}
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
					} else if (keycode == Keys.DOWN) { // handles player drop off
						for(Player player : game.playerList){
							player.dropOff(game.kitchen.getKitchenStations());
						}

						return true;
					} else if (keycode == Keys.SPACE) { // handles player interact.
						for(Player player : game.playerList){
							player.interact(game.kitchen.getKitchenStations());
							player.playerInteract(game.getPlayerList());
						}
					} else if (keycode == Keys.TAB) { // handles player switching - *shouldn't* need to be updated
						int selected = game.selectedPlayer + 1;
						while (game.playerList.get(selected % game.playerList.size()).isLocked()){
							selected++;
						}
						game.setSelectedPlayer((selected) % game.playerList.size());
					} else if (keycode == Keys.ESCAPE) { // activates menu.
						game.customerManager.pause();
						menu.cont = true;
						menu.active = true;
						menu.playOptions = false;
						menu.howToScreen = false;
						menu.isDifficultyScreen = false;
					}
				} else { // if menu is active
					if (keycode == Keys.ESCAPE && game != null) {
						game.customerManager.unpause();
						menu.active = false;
					}
				}
				return false;
			}
		};

		Gdx.input.setInputProcessor(inputProcessor);
		//endregion
	}

	/**

	 This method resizes the game screen to the given width and height, and updates the camera
	 and hud_cam accordingly.
	 @param width the new width of the game screen

	 @param height the new height of the game screen
	 */
	@Override
	public void resize(int width, int height){
		camera.update();
		hud_cam.update();

		viewport.update(width, height);

		spriteBatch.setProjectionMatrix(camera.combined);
		hud_batch.setProjectionMatrix(hud_cam.combined);
	}
	/**

	 This method renders the game screen, and handles the game loop by calling the appropriate
	 methods depending on whether the game is in menu or game mode. If the game is complete, the
	 method ends the game and sets menu to active.
	 */

	@Override
	public void render () {
		startGame();
		if (menu.active) {
			menu.render(hud_batch); // renders menu
		} else if(game != null){
			game.renderGame(); // renders game
			if (game.customerManager.isComplete()){ // if game is complete, ends the game.
				menu.active = true;
				menu.setFinalTime(game.customerManager.timeToString(game.customerManager.getFinalTime()));
				menu.complete = true;
				bubbleRenderer.end();
				game.end();
				game = null;
			}
		}
	}
	/**

	 This method returns the current game object.
	 @return the current game object
	 */
	public MainGame getGame(){
		return game;
	}
	/**

	 This method sets the current game object to null and initializes the customerRenderer, bubbleRenderer
	 and game objects.
	 */
	public void setGameNull(){
		customerRenderer = new CustomerRenderer(spriteBatch);
		bubbleRenderer = new BubbleRenderer(spriteBatch);
		game = null;
	}
	/**

	 This method starts the game by initializing the game objects, such as the match and the game
	 itself, based on the selected game options and menu selections. If a new game is selected,
	 the method resumes the game or instantiates a new one.
	 */
	public void startGame(){
		// If a new game is selected, it resumes the game or instantiates a new one
		if(!menu.tryActivateGame) return;
		menu.tryActivateGame = false;
		if(game != null) return;
		if(menu.isLoading){
			loader = new LoadGame(menu);
			if(loader.loadFailed){
				menu.loadingFailed = true;
				menu.isLoading = false;
				return;
			}
			else{
				match = loader.getMatch();
			}
		}
		else{
			match = new Match(menu.isEndless ? GameMode.ENDLESS : GameMode.SCENARIO, menu.getDifficulty(), menu.customerCount);
		}
		game = new MainGame(spriteBatch, hud_batch, camera, hud_cam, playerRenderer,
				customerRenderer, bubbleRenderer, mapRenderer, inputProcessor, match);
		game.create(menu.isLoading, loader);
		menu.isLoading = false;
		menu.active = false;
	}

	public void setMenu(MenuScreen menuScreen){
		this.menu = menuScreen;
	}

	public void setGame(MainGame game){this.game = game;}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		Kitchen.dispose();
		Textures.dispose();
		Atlases.dispose();
		ItemRegister.dispose();
	}
}
