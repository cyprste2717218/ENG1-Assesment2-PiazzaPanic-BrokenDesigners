package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.obstacles.Box;

public class PiazzaPanic extends ApplicationAdapter {
	Viewport viewport;
	OrthographicCamera camera;
	private int VIRTUAL_WIDTH  = 800;
	private int VIRTUAL_HEIGHT = 450;
	SpriteBatch spriteBatch;
	PlayerChef playerChef;
	//Box box;
	Texture box_texture;
	TiledMap map_test;
	OrthogonalTiledMapRenderer renderer;
	Vector3 vector3;
	MapObject box2;
	MapObjects objects;
	TiledMapTileLayer tileLayer;
	Player player;



	@Override
	public void create () {



		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.setToOrtho(false);
		camera.update();

		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);



		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(camera.combined);


		box_texture = new Texture("textures/Box_Alt.png");

		//box = new Box(800, 400, 128);
		//box.setTexture(box_texture);

		map_test = new TmxMapLoader().load("map/level1.tmx");

		renderer = new OrthogonalTiledMapRenderer(map_test, 2f);

		objects = map_test.getLayers().get("Collision Layer").getObjects();





		vector3 = new Vector3(0, 0, 0);
		camera.position.set(vector3);

		playerChef = new PlayerChef(440, 440);

		player = new Player();






	}
	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
	}

	@Override
	public void render () {

		spriteBatch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1f, 1f, 0.8f, 1f);

		camera.update();

		renderer.setView(camera);
		renderer.render();

		player.processMovement();
		playerChef.renderChef(spriteBatch, player);

		if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			camera.position.add(10, 0, 0);
		}
		else if (Gdx.input.isKeyPressed((Keys.LEFT))){
			camera.position.add(-10,0,0);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)){
			camera.position.add(0,10,0);
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN)){
			camera.position.add(0,-10,0);
		}
		if (Gdx.input.isKeyPressed((Keys.SPACE))){
			System.out.println(player.worldPosition);
			System.out.println(player.getScreenPosition(spriteBatch.getProjectionMatrix()));
		}

	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		map_test.dispose();
	}
}
