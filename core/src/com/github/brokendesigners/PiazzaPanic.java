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

public class PiazzaPanic extends ApplicationAdapter {
	//SpriteBatch batch;
	//Texture img;
	Viewport viewport;
	//TestBall testBall;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	final int VIRTUAL_WIDTH  = 800;
	final int VIRTUAL_HEIGHT = 450;
	SpriteBatch spriteBatch;
	TestSheth testSheth;
	InputProcessor inputProcessor;
	PlayerChef playerChef;

	@Override
	public void create () {


		shapeRenderer = new ShapeRenderer();
		//testBall = new TestBall(30,8,3,2,6, shapeRenderer);

		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		spriteBatch = new SpriteBatch();
		Texture chef = new Texture(Gdx.files.internal("Glibbert.png"));

		Sprite chef_sprite = new Sprite(chef);
		chef_sprite.setPosition(500,600);

		testSheth = new TestSheth(60, 60, 3, 4, 80, 144, chef, spriteBatch);

		playerChef = new PlayerChef(400, 400);

		/*inputProcessor = new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {

				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				if (character == 'w'){
					playerChef.moveUp();
				}
				else if (character == 's'){
					playerChef.moveDown();
				}
				if (character == 'd'){
					playerChef.moveLeft();
				}
				else if (character == 'a'){
					playerChef.moveRight();
				}
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
		Gdx.input.setInputProcessor(inputProcessor);*/
		// Above is Event Listener for User Input - Polling may be better for our Use-Case
	}

	@Override
	public void resize(int width, int height){


		viewport.setWorldWidth(width);
		viewport.setWorldHeight(height);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1f, 1f, 0.9f, 1f);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();


		testSheth.update(viewport);
		//testBall.update(viewport);
		camera.setToOrtho(false);
		shapeRenderer.setProjectionMatrix(camera.combined);

		playerChef.processMovement();
		playerChef.renderChef(spriteBatch);



		//resize(width, height);
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		spriteBatch.dispose();
	}
}
