package com.github.brokendesigners.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.menu.Buttons.*;

import java.util.ArrayList;

/*
 * Handles rendering and controls for menu.
 * I did not have as much time for this one :3
 */
public class MenuScreen {

	public boolean active; // Is the menu active? (Should it render)
	public String finalTime; // Final time to be displayed at end of game.
	public String totalMoney; // Total amount of money the player has
	public boolean howToScreen; // is howToScreen being displayed?
	public boolean playOptions;
	public boolean complete; // has game been completed?
	public int selectedButton; // Which button has been selected?
	BitmapFont font;

	public ArrayList<Button> menuButtons = new ArrayList<>(); //A list of all the buttons, which is automatically created in the constructor of Button
	Button playButton, exitGameButton, exitHowToPlayButton, showHowToPlayButton, scenarioModeButton, endlessModeButton,difficultyModeButtonEasy,difficultyModeButtonMedium,difficultyModeButtonHard, exitDifficultyButton,DifficultySettingsButton;
	OrthographicCamera camera;
	public boolean tryActivateGame, isEndless;

	DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

	public boolean DifficultyScreen;


	/*
	 * Instantiates MenuScreen
	 */

	//TODO: Add button to create mode choice for endless or scenario
	public MenuScreen(OrthographicCamera camera){
		active = true;
		tryActivateGame = false;
		selectedButton = 0;
		complete = false;
		font = new BitmapFont();
		this.font.getData().setScale(10, 10);
		font.setColor(Color.RED);
		this.camera = camera;
		initialiseButtons();
		DifficultyScreen = false;
	}

	public void initialiseButtons(){
		playButton = new PlayButton(new Rectangle(700, 515, 200, 100),
				MenuTextures.playButtonSelected, MenuTextures.playButtonUnselected, this);

		showHowToPlayButton = new HowToPlayButton(new Rectangle(700, 400, 200, 100),
				MenuTextures.howtoplayButtonSelected, MenuTextures.howtoplayButtonUnselected, this);

		exitHowToPlayButton = new ExitHowToPlayButton(new Rectangle(700, 285, 200, 100),
				MenuTextures.exitButtonSelected, MenuTextures.exitButtonUnselected, this);

		exitGameButton = new ExitGameButton(new Rectangle(700, 285, 200, 100),
				MenuTextures.exitButtonSelected, MenuTextures.exitButtonUnselected, this);

		scenarioModeButton = new ScenarioModeButton(new Rectangle(700, 515 , 200,100),
				MenuTextures.scenarioButtonSelected, MenuTextures.scenarioButtonUnselected, this);

		endlessModeButton = new EndlessModeButton(new Rectangle(700, 400 , 200,100),
				MenuTextures.endlessButtonSelected, MenuTextures.endlessButtonUnselected, this);

		exitDifficultyButton = new ExitDifficultyButton(new Rectangle(700, 170 , 200,100),
				MenuTextures.endlessButtonSelected, MenuTextures.endlessButtonUnselected, this);

		DifficultySettingsButton = new SettingsButton(new Rectangle(1000, 700, 200, 100),
				MenuTextures.exitButtonSelected, MenuTextures.exitButtonUnselected, this);




		difficultyModeButtonEasy = new DifficultyButton(new Rectangle(700, 515, 200, 100),
				MenuTextures.endlessButtonSelected, MenuTextures.endlessButtonUnselected, this, difficultyLevel.EASY);

		difficultyModeButtonMedium = new DifficultyButton(new Rectangle(700, 400, 200, 100),
				MenuTextures.scenarioButtonSelected, MenuTextures.scenarioButtonUnselected, this, difficultyLevel.MEDIUM);

		difficultyModeButtonHard = new DifficultyButton(new Rectangle(700, 285, 200, 100),
				MenuTextures.exitButtonSelected, MenuTextures.exitButtonUnselected, this, difficultyLevel.HARD);

	}


	/*
	 * Renders menu screen.
	 *
	 * Also decides which menu "frame" to render.
	 *
	 */
	public void render(SpriteBatch batch){
		if(!active) return;
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(119/255f, 179/255f, 210/255f, 1f);
		setSelectedButton();
		batch.begin();
		for(Button button : menuButtons){
			button.render(batch, camera);
			button.onActivate(camera);
		}
		if(complete){
			exitHowToPlayButton.setRendered(false);
			exitGameButton.setRendered(true);
			scenarioModeButton.setRendered(false);
			endlessModeButton.setRendered(false);
			playButton.setRendered(true);
			showHowToPlayButton.setRendered(true);
			batch.draw(MenuTextures.you_win, 405, 400, 800, 400);

		}
		else if(DifficultyScreen){
			difficultyModeButtonEasy.setRendered(true);
			difficultyModeButtonMedium.setRendered(true);
			difficultyModeButtonHard.setRendered(true);
			exitDifficultyButton.setRendered(true);
			DifficultySettingsButton.setRendered(false);
			playButton.setRendered(false);
			exitGameButton.setRendered(false);
			showHowToPlayButton.setRendered(false);


		}
		else if(howToScreen) {
			batch.draw(MenuTextures.how_to_play, 405, 400, 800, 400);
			exitHowToPlayButton.setRendered(true);
			exitGameButton.setRendered(false);
		}
		else if(playOptions){
			exitHowToPlayButton.setRendered(true);
			scenarioModeButton.setRendered(true);
			exitGameButton.setRendered(false);
			endlessModeButton.setRendered(true);
			playButton.setRendered(false);
			showHowToPlayButton.setRendered(false);
		}
		else{
			difficultyModeButtonEasy.setRendered(false);
			difficultyModeButtonMedium.setRendered(false);
			difficultyModeButtonHard.setRendered(false);
			exitDifficultyButton.setRendered(false);
			DifficultySettingsButton.setRendered(true);
			playButton.setRendered(true);
			showHowToPlayButton.setRendered(true);
			exitHowToPlayButton.setRendered(false);
			exitGameButton.setRendered(true);
			endlessModeButton.setRendered(false);
			scenarioModeButton.setRendered(false);
			batch.draw(MenuTextures.updown, 1000, 400, 400, 200);
			batch.draw(MenuTextures.wsad, 100, 500, 400, 200);
			batch.draw(MenuTextures.tabSpace, 100, 160, 400, 200);
		}

		batch.end();
	}


	/***
	 * A function that allows scrolling through the different available buttons using up/w and down/s
	 */
	void setSelectedButton(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			for(int i = 0; i < menuButtons.size(); i++){
				int selection = selectedButton - (i+1) >= 0 ? (selectedButton - (i + 1)) : menuButtons.size() - ((i + 1) - selectedButton);
				if(menuButtons.get(selection).isRendered()){
					selectedButton = selection;
					return;
				}
			}
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			for(int i = 0; i < menuButtons.size(); i++){
				int selection = selectedButton + (i+1) < menuButtons.size() ? (selectedButton + (i + 1)) : (selectedButton + (i + 1)) - (menuButtons.size());
				if(menuButtons.get(selection).isRendered()){
					selectedButton = selection;
					return;
				}
			}
		}

		for(int i = 0; i < menuButtons.size(); i++){
			menuButtons.get(i).setSelected(selectedButton == i);
		}
	}

	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}

	public void setMoney(String totalMoney) { this.totalMoney = totalMoney; }

	public DifficultyLevel getDifficulty(){
		return difficultyLevel;
	}
	public void setDifficultyLevel(DifficultyLevel difficultyLevel){
		this.difficultyLevel = difficultyLevel;
	}
}
