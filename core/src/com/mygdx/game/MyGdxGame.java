package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	ShapeRenderer shape;
	Ball ball_one = new Ball(50, 50, 12, 1, 2);
	Ball ball_two = new Ball(65, 75, 20, -1, 2);
	Ball ball_thr = new Ball(78, 30, 14, 3, -6);
	Ball ball_fou = new Ball(67, 90, 39, 7, -2);
	Ball ball_fiv = new Ball(103, 129, 100, 1, -4);
	Ball ball_six = new Ball(408, 280, 40, 8, 7);
	Ball ball_sev = new Ball(108, 100, 63, -2, -2);
	Ball ball_eig = new Ball(98, 73, 19, -6, 3);


	@Override
	public void create () {
		shape = new ShapeRenderer();
		ball_one.setRenderer(shape);
		ball_two.setRenderer(shape);
		ball_thr.setRenderer(shape);
		ball_fou.setRenderer(shape);
		ball_fiv.setRenderer(shape);
		ball_six.setRenderer(shape);
		ball_sev.setRenderer(shape);
		ball_eig.setRenderer(shape);



	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ball_one.update();
		ball_two.update();
		ball_thr.update();
		ball_fou.update();
		ball_fiv.update();
		ball_six.update();
		ball_sev.update();
		ball_eig.update();
	}
}
