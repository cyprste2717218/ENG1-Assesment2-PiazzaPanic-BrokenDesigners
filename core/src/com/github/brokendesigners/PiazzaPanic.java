package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.brokendesigners.obstacles.Box;

public class PiazzaPanic extends ApplicationAdapter {
	Viewport viewport;
	OrthographicCamera camera;
	final int VIRTUAL_WIDTH  = 800;
	final int VIRTUAL_HEIGHT = 450;
	SpriteBatch spriteBatch;
	PlayerChef playerChef;
	Box box;
	Texture box_texture;

	@Override
	public void create () {
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		spriteBatch = new SpriteBatch();

		playerChef = new PlayerChef(400, 400);

		box_texture = new Texture("textures/box.png");

		box = new Box(800, 400, 100);
		box.setTexture(box_texture);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1f, 1f, 0.8f, 1f);

		camera.setToOrtho(false);

		playerChef.processMovement();
		playerChef.renderChef(spriteBatch);
		box.renderBox(spriteBatch);
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
