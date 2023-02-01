package com.github.brokendesigners.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Font;
/*
 * Handles rendering and controls for menu.
 * I did not have as much time for this one :3
 */
public class MenuScreen {

	public boolean active; // Is the menu active? (Should it render)
	public String finalTime; // Final time to be displayed at end of game.
	public boolean howToScreen; // is howToScreen being displayed?
	public boolean complete; // has game been completed?

	public int selectedButton; // Which button has been selected?
	BitmapFont font;

	/*
	 * Instantiates MenuScreen
	 */
	public MenuScreen(){
		active = true;
		selectedButton = 0;
		complete = false;
		font = new BitmapFont();
		this.font.getData().setScale(10, 10);
		font.setColor(Color.RED);
	}
	/*
	 * Renders menu screen.
	 *
	 * Also decides which menu "frame" to render.
	 *
	 */
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
	/*
	 * Handles input for menu screen - whether to start a new game, display the how to screen etc.
	 */
	public int input(boolean gameRunning){

		switch (selectedButton){
			case (0): // Selected button is "play"
				if (gameRunning == true){
					return 0;
				}
				return 1;
			case (1): // Selected button is "how to play"
				this.howToScreen = true;
				this.selectedButton = 3;
				return 2;
			case (2): // selected button is "exit"
				Gdx.app.exit();
				break;
			case (3): // selected button is "Exit" on the how to screen.
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
