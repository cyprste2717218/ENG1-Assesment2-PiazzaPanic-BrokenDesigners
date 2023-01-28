package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.Station;

import com.github.brokendesigners.textures.BluggusTextures;
import com.github.brokendesigners.textures.ChefTextures;
import com.github.brokendesigners.textures.CuttingTextures;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.renderer.CustomerRenderer;
import com.github.brokendesigners.renderer.PlayerRenderer;

public class PiazzaPanic extends ApplicationAdapter {
	Viewport viewport;
	OrthographicCamera camera;
	private float VIRTUAL_WIDTH  = 32;
	private float VIRTUAL_HEIGHT = 18;

	SpriteBatch spriteBatch;
	PlayerRenderer playerRenderer;

	Texture box_texture;
	TiledMap map_test;

	InputProcessor inputProcessor;

	OrthogonalTiledMapRenderer mapRenderer;
	CustomerRenderer customerRenderer;
	BubbleRenderer bubbleRenderer;

	Player player1;
	Player player2;
	Player player3;

	Customer bluggus;

	Player selectedPlayer;

	Kitchen kitchen;
	KitchenCollisionObject kitchenCollisionObject;
	ItemInitialiser initialiser;



	@Override
	public void create () {
		initialiser = new ItemInitialiser();
		initialiser.initialise();

		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		camera.setToOrtho(false, VIRTUAL_WIDTH/16, VIRTUAL_HEIGHT/16);
		camera.update();



		System.out.println("Cam Pos");
		camera.position.set(0, 0, 1);

		viewport = new FillViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);


		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined);
		mapRenderer = new OrthogonalTiledMapRenderer(Constants.TILE_MAP, Constants.UNIT_SCALE, spriteBatch);
		mapRenderer.setView(camera.combined, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		customerRenderer = new CustomerRenderer(spriteBatch);
		bubbleRenderer = new BubbleRenderer(spriteBatch);


		Animation<TextureRegion> glibbert_moveAnimation =
			new Animation<TextureRegion>(0.15f, ChefTextures.atlasImages.findRegions("running"), PlayMode.LOOP);

		Animation<TextureRegion> glibbert_idleAnimation =
			new Animation<TextureRegion>(1.3f, ChefTextures.atlasImages.findRegions("idle"), PlayMode.LOOP);

		Animation<TextureRegion> bluggus_idleAnimation =
			new Animation<TextureRegion>(1.3f, BluggusTextures.atlasImages.findRegions("bluggus_idle"));
		Animation<TextureRegion> bluggus_moveAnimation =
			new Animation<TextureRegion>(0.15f, BluggusTextures.atlasImages.findRegions("bluggus_inch"));



		ArrayList<Animation<TextureRegion>> glibbert_animations = new ArrayList<>();
		glibbert_animations.add(glibbert_idleAnimation);
		glibbert_animations.add(glibbert_moveAnimation);
		playerRenderer = new PlayerRenderer(spriteBatch);

		ArrayList<Animation<TextureRegion>> bluggus_animations = new ArrayList<>();
		bluggus_animations.add(bluggus_idleAnimation);
		bluggus_animations.add(bluggus_moveAnimation);


		player1 = new Player(playerRenderer, glibbert_animations, new Vector3((float)6.5 * 4, (float)3.5 * 4, 0), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE);
		player2 = new Player(playerRenderer, bluggus_animations, new Vector3(0, 0, 0), 36 * Constants.UNIT_SCALE, 34 * Constants.UNIT_SCALE);
		player3 = new Player(playerRenderer, glibbert_animations, new Vector3(1, 0, 0), 20 * Constants.UNIT_SCALE, 36 * Constants.UNIT_SCALE);




		kitchen = new Kitchen(camera, spriteBatch, bubbleRenderer);

		ArrayList<KitchenCollisionObject> kitobj = kitchen.getKitchenObstacles();
		KitchenCollisionObject kitobj2 = kitobj.get(0);

		camera.update();

		kitchenCollisionObject = new KitchenCollisionObject(new Vector3(220, 130, 0),32, 64);
		kitchen.addKitchenObject(kitchenCollisionObject);

		player1.setSelected(true);
		selectedPlayer = player1;
		camera.zoom = 2f;
		Texture bluggusTexture = new Texture("characters/bluggus2.png");

		bluggus = new Customer(bluggusTexture, kitchen.getCustomerStations().get(2), customerRenderer, bubbleRenderer); // TEST THING

		inputProcessor = new InputProcessor(){

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.UP){
					try {
						player1.pickUp(kitchen.getKitchenStations());
						player2.pickUp(kitchen.getKitchenStations());
						player3.pickUp(kitchen.getKitchenStations());
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
					player3.dropOff(kitchen.getKitchenStations());

					return true;
				} else if (keycode == Keys.L){
					bluggus.Spawn();
				} else if (keycode == Keys.SPACE){
					player1.interact(kitchen.getKitchenStations());
					player2.interact(kitchen.getKitchenStations());
					player3.interact(kitchen.getKitchenStations());
				}
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		};
		Gdx.input.setInputProcessor(inputProcessor);

		spriteBatch.enableBlending();


	}

	@Override
	public void resize(int width, int height){
		camera.update();
		viewport.update(width, height);

		spriteBatch.setProjectionMatrix(camera.combined);
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
		player3.processMovement(kitchen.getKitchenObstacles());

		spriteBatch.begin();

		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Tile Layer 2"));
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Tile Layer 1"));
		spriteBatch.end();

		for (Station station : kitchen.getKitchenStations()){

				station.renderCounter(spriteBatch);
		}


		customerRenderer.renderCustomers();
		bubbleRenderer.renderBubbles();
		playerRenderer.renderPlayers();






		camera.position.set(selectedPlayer.worldPosition);

		spriteBatch.begin();

		spriteBatch.end();

		if (Gdx.input.isKeyPressed(Keys.NUM_1)){
			player1.setSelected(true);
			player2.setSelected(false);
			player3.setSelected(false);
			selectedPlayer = player1;

		} else if (Gdx.input.isKeyPressed(Keys.NUM_2)){
			player1.setSelected(false);
			player2.setSelected(true);
			player3.setSelected(false);
			selectedPlayer = player2;

		} else if (Gdx.input.isKeyPressed(Keys.NUM_3)){
			player1.setSelected(false);
			player2.setSelected(false);
			player3.setSelected(true);
			selectedPlayer = player3;

		}
		if (Gdx.input.isKeyPressed(Keys.Q)){
			System.out.println(player1.getWorldPosition());
			//player1.hand.drop();


		}
		spriteBatch.begin();
		mapRenderer.renderTileLayer(
			(TiledMapTileLayer) mapRenderer.getMap().getLayers().get("Tile Layer 3"));
		spriteBatch.end();
	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		map_test.dispose();
		Kitchen.dispose();
	}

}
