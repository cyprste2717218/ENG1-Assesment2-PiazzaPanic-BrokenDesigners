package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.item.NuclearWeapon;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.CounterStation;
import com.github.brokendesigners.map.interactable.Station;
import com.github.brokendesigners.obstacles.Box;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class PiazzaPanic extends ApplicationAdapter {
	Viewport viewport;
	OrthographicCamera camera;
	private float VIRTUAL_WIDTH  = 32;
	private float VIRTUAL_HEIGHT = 18;
	SpriteBatch spriteBatch;
	PlayerRenderer playerRenderer;
	Box box;
	Texture box_texture;
	TiledMap map_test;
	OrthogonalTiledMapRenderer mapRenderer;
	Vector3 vector3;
	MapObject box2;
	MapObjects objects;
	TiledMapTileLayer tileLayer;

	Player player1;
	Player player2;
	Player player3;

	Kitchen kitchen;
	KitchenCollisionObject kitchenCollisionObject;

	@Override
	public void create () {

		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		camera.setToOrtho(false, VIRTUAL_WIDTH/16, VIRTUAL_HEIGHT/16);
		camera.update();


		System.out.println("Cam Pos");
		camera.position.set(0, 0, 1);

		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);


		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined);
		mapRenderer = new OrthogonalTiledMapRenderer(Constants.TILE_MAP, Constants.UNIT_SCALE, spriteBatch);
		mapRenderer.setView(camera.combined, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);


		player1 = new Player(new Vector3((float)6.5 * 4, (float)3.5 * 4, 0));
		player2 = new Player(new Vector3(8, -2, 0));
		player3 = new Player(new Vector3(12, -2, 0));
		playerRenderer = new PlayerRenderer();


		kitchen = new Kitchen(camera, spriteBatch);

		ArrayList<KitchenCollisionObject> kitobj = kitchen.getKitchenObstacles();
		KitchenCollisionObject kitobj2 = kitobj.get(0);

		camera.update();

		kitchenCollisionObject = new KitchenCollisionObject(new Vector3(220, 130, 0),32, 64);
		kitchen.addKitchenObject(kitchenCollisionObject);

		player1.setSelected(true);

		camera.zoom = 1.5f;


	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		camera.update();
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
		try {
			player1.processPickUp(kitchen.getKitchenStations());
			player1.processDropOff(kitchen.getKitchenStations());
		} catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		player2.processMovement(kitchen.getKitchenObstacles());
		player3.processMovement(kitchen.getKitchenObstacles());

		mapRenderer.render();
		playerRenderer.renderChef(spriteBatch, player1); // Perhaps fix the relationship of playerRenderer and player
		playerRenderer.renderChef(spriteBatch, player2);
		playerRenderer.renderChef(spriteBatch, player3);
		for (Station station : kitchen.getKitchenStations()){
			if (station instanceof CounterStation){
				((CounterStation) station).renderCounter(spriteBatch);
			}
		}


		camera.position.set(player1.worldPosition);



		if (Gdx.input.isKeyPressed(Keys.NUM_1)){
			player1.setSelected(true);
			player2.setSelected(false);
			player3.setSelected(false);

		} else if (Gdx.input.isKeyPressed(Keys.NUM_2)){
			player1.setSelected(false);
			player2.setSelected(true);
			player3.setSelected(false);

		} else if (Gdx.input.isKeyPressed(Keys.NUM_3)){
			player1.setSelected(false);
			player2.setSelected(false);
			player3.setSelected(true);

		}
		if (Gdx.input.isKeyPressed(Keys.Q)){
			//player1.hand.drop();


		}
	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		map_test.dispose();
		Kitchen.dispose();
	}
}
