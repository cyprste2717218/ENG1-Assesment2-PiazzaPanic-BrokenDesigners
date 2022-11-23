package com.github.brokendesigners;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PiazzaPanic extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Viewport viewport;
	TestBall testBall;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	final int VIRTUAL_WIDTH  = 800;
	final int VIRTUAL_HEIGHT = 450;
	SpriteBatch spriteBatch;
	TestSheth testSheth;

	@Override
	public void create () {


		shapeRenderer = new ShapeRenderer();
		testBall = new TestBall(30,8,3,2,6, shapeRenderer);

		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		spriteBatch = new SpriteBatch();
		Texture chef = new Texture(Gdx.files.internal("sheth.png"));
		Sprite chef_sprite = new Sprite(chef);
		chef_sprite.setPosition(500,600);

		TestSheth testSheth = new TestSheth(60, 60, 3, 4, 20, 36, chef, spriteBatch);

	}

	@Override
	public void resize(int width, int height){


		viewport.setWorldWidth(width);
		viewport.setWorldHeight(height);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		testBall.update(viewport);
		testSheth.update(viewport);
		camera.setToOrtho(false);
		shapeRenderer.setProjectionMatrix(camera.combined);


		//resize(width, height);
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
