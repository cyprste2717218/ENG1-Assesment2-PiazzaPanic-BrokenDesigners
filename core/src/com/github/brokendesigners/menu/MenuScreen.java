package com.github.brokendesigners.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Font;

public class MenuScreen {

	public boolean active;
	public String finalTime;
	public boolean howToScreen;
	public boolean complete;

	public int selectedButton;
	BitmapFont font;


	public MenuScreen(){
		active = true;
		selectedButton = 0;
		complete = false;
		font = new BitmapFont();
		this.font.getData().setScale(10, 10);
		font.setColor(Color.RED);
	}

	public void render(SpriteBatch batch){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(119/255f, 179/255f, 210/255f, 1f);
		batch.begin();
		if (this.active && this.howToScreen == false) {
			renderMain(batch);


		} else if (this.active && this.howToScreen == true){
			batch.draw(MenuTextures.title, 405, 400, 800, 400);
			batch.draw(MenuTextures.exitButtonSelected, 700, 285, 200, 100);
		}
		if (this.active && this.complete && this.howToScreen == false){
			renderMain(batch);
			batch.draw(MenuTextures.you_win, 405, 400, 800, 400);
			font.draw(batch, finalTime, 650, 200);

		}
		batch.end();
	}
	public int input(boolean gameRunning){

		switch (selectedButton){
			case (0):
				if (gameRunning == true){
					return 0;
				}
				return 1;
			case (1):
				this.howToScreen = true;
				this.selectedButton = 3;
				return 2;
			case (2):
				Gdx.app.exit();
				break;
			case (3):
				this.howToScreen = false;
				this.selectedButton = 0;
				return 2;
		}
		return 0;

	}

	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}

	public void renderMain(SpriteBatch batch){
		switch (selectedButton){
			case (0):
				batch.draw(MenuTextures.playButtonSelected, 700, 515, 200, 100);
				batch.draw(MenuTextures.howtoplayButtonUnselected, 700, 400, 200, 100);
				batch.draw(MenuTextures.exitButtonUnselected, 700, 285, 200, 100);
				break;
			case (1):
				batch.draw(MenuTextures.playButtonUnselected, 700, 515, 200, 100);
				batch.draw(MenuTextures.howtoplayButtonSelected, 700, 400, 200, 100);
				batch.draw(MenuTextures.exitButtonUnselected, 700, 285, 200, 100);
				break;
			case (2):
				batch.draw(MenuTextures.playButtonUnselected, 700, 515, 200, 100);
				batch.draw(MenuTextures.howtoplayButtonUnselected, 700, 400, 200, 100);
				batch.draw(MenuTextures.exitButtonSelected, 700, 285, 200, 100);
		}
		batch.draw(MenuTextures.updown, 1000, 400, 400, 200);
		batch.draw(MenuTextures.wsad, 100, 500, 400, 200);
		batch.draw(MenuTextures.tabSpace, 100, 160, 400, 200);
	}
}
