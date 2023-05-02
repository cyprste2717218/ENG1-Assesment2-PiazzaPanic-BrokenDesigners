package com.github.brokendesigners.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.github.brokendesigners.PiazzaPanic;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.menu.Buttons.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Handles rendering and controls for menu.
 * I did not have as much time for this one :3
 */
public class MenuScreen {

	public PiazzaPanic panic;
	public boolean active; // Is the menu active? (Should it render)
	public String finalTime; // Final time to be displayed at end of game.
	public String totalMoney; // Total amount of money the player has
	public boolean howToScreen; // is howToScreen being displayed?
	public boolean playOptions;
	public boolean cont;
	public boolean gameSaved, loadingFailed;
	public boolean isLoading;
	public boolean complete; // has game been completed?
	public int selectedButton; // Which button has been selected?
	BitmapFont font;
	public BitmapFont savedGameFont, cannotLoadFont, customerCountFont;
	Music music;

	private ArrayList<Button> menuButtons = new ArrayList<>(); //A list of all the buttons, which is automatically created in the constructor of Button
	Button playButton, resumeButton, loadButton, saveButton, showHowToPlayButton, backButton, exitGameButton, quitButton, scenarioModeButton, endlessModeButton, difficultyModeButtonEasy, difficultyModeButtonMedium, difficultyModeButtonHard, customerCountAdderButton, customerCountSubtractorButton;
	OrthographicCamera camera;
	public boolean tryActivateGame, isEndless;
	DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

	public int customerCount = 5;
	public boolean isDifficultyScreen;

	/*
	 * Instantiates MenuScreen
	 */

	//TODO: Add button to create mode choice for endless or scenario
	public MenuScreen(OrthographicCamera camera, PiazzaPanic panic){
		this.panic = panic;
		active = true;
		tryActivateGame = false;
		isLoading = false;
		complete = false;
		loadingFailed = false;
		font = new BitmapFont();
		this.font.getData().setScale(10, 10);
		font.setColor(Color.RED);
		savedGameFont = new BitmapFont();
		savedGameFont.getData().setScale(5,5);
		savedGameFont.setColor(Color.RED);
		cannotLoadFont = new BitmapFont();
		cannotLoadFont.getData().setScale(5,5);
		cannotLoadFont.setColor(Color.RED);
		customerCountFont = new BitmapFont();
		customerCountFont.getData().setScale(2,2);
		customerCountFont.setColor(Color.RED);
		this.camera = camera;
		initialiseButtons();
		music = Gdx.audio.newMusic(Gdx.files.internal("audio/Cooking-Long-Version.mp3"));
		music.setLooping(true);
		music.play();
	}

	public MenuScreen(OrthographicCamera camera, PiazzaPanic panic, boolean testing){
		this.panic = panic;
		active = true;
		tryActivateGame = false;
		isLoading = false;
		complete = false;
		loadingFailed = false;
		font = new BitmapFont();
		this.font.getData().setScale(10, 10);
		font.setColor(Color.RED);
		savedGameFont = new BitmapFont();
		savedGameFont.getData().setScale(5,5);
		savedGameFont.setColor(Color.RED);
		cannotLoadFont = new BitmapFont();
		cannotLoadFont.getData().setScale(5,5);
		cannotLoadFont.setColor(Color.RED);
		customerCountFont = new BitmapFont();
		customerCountFont.getData().setScale(2,2);
		customerCountFont.setColor(Color.RED);
		this.camera = camera;
	}

	private void initialiseButtons(){
		playButton = new PlayButton(new Rectangle(700, 515, 200, 100),
				MenuTextures.playButtonSelected, MenuTextures.playButtonUnselected, this);

		resumeButton = new ResumeButton(new Rectangle(700, 515, 200, 100),
				MenuTextures.resumeButtonSelected, MenuTextures.resumeButtonUnselected, this);

		loadButton = new LoadButton(new Rectangle(700, 400, 200, 100),
				MenuTextures.loadButtonSelected, MenuTextures.loadButtonUnselected, this);

		saveButton = new SaveButton(new Rectangle(700, 400, 200, 100),
				MenuTextures.saveButtonSelected, MenuTextures.saveButtonUnselected, this);

		showHowToPlayButton = new HowToPlayButton(new Rectangle(700, 285, 200, 100),
				MenuTextures.howtoplayButtonSelected, MenuTextures.howtoplayButtonUnselected, this);

		backButton = new BackButton(new Rectangle(700, 170, 200, 100),
				MenuTextures.backButtonSelected, MenuTextures.backButtonUnselected, this);

		exitGameButton = new ExitGameButton(new Rectangle(700, 170, 200, 100),
				MenuTextures.exitButtonSelected, MenuTextures.exitButtonUnselected, this);

		quitButton = new QuitButton(new Rectangle(700, 170, 200, 100),
				MenuTextures.quitButtonSelected, MenuTextures.quitButtonUnselected, this);

		scenarioModeButton = new ScenarioModeButton(new Rectangle(700, 515 , 200,100),
				MenuTextures.scenarioButtonSelected, MenuTextures.scenarioButtonUnselected, this);

		endlessModeButton = new EndlessModeButton(new Rectangle(700, 400 , 200,100),
				MenuTextures.endlessButtonSelected, MenuTextures.endlessButtonUnselected, this);

		difficultyModeButtonEasy = new DifficultyButton(new Rectangle(700, 515, 200, 100),
				MenuTextures.easyButtonSelected, MenuTextures.easyButtonUnselected, this, difficultyLevel.EASY);

		difficultyModeButtonMedium = new DifficultyButton(new Rectangle(700, 400, 200, 100),
				MenuTextures.mediumButtonSelected, MenuTextures.mediumButtonUnselected, this, difficultyLevel.MEDIUM);

		difficultyModeButtonHard = new DifficultyButton(new Rectangle(700, 285, 200, 100),
				MenuTextures.hardButtonSelected, MenuTextures.hardButtonUnselected, this, difficultyLevel.HARD);

		customerCountAdderButton = new ScenarioCustomerCountButton(new Rectangle(1300, 540 , 100,50),
				MenuTextures.customerCountAddSelected, MenuTextures.customerCountAddUnselected, this, 1);

		customerCountSubtractorButton = new ScenarioCustomerCountButton(new Rectangle(950, 540 , 100,50),
				MenuTextures.customerCountSubtractSelected, MenuTextures.customerCountSubtractUnselected, this, -1);
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
			selectedButton = 7;
			setDisplayedButtons(quitButton);
			batch.draw(MenuTextures.you_win, 405, 400, 800, 400);
			font.draw(batch, finalTime, 650, 560);
		}
		else if(howToScreen) {
			Texture power = MenuTextures.powerupsTut;
			batch.draw(MenuTextures.how_to_play, 0, 195, 700, 720);
			batch.draw(power, 1030, 275, power.getWidth()*2, power.getHeight()*2);
			selectedButton = 5;
			setDisplayedButtons(backButton);
		}
		else if(playOptions){
			setDisplayedButtons(Arrays.asList(scenarioModeButton,endlessModeButton,backButton, customerCountAdderButton, customerCountSubtractorButton));
			customerCountFont.draw(batch, "No. Customers: " + customerCount,1052,575);
		}
		else if(isDifficultyScreen){
			setDisplayedButtons(Arrays.asList(difficultyModeButtonEasy, difficultyModeButtonMedium,
					difficultyModeButtonHard, backButton));
		}
		else{
			if (cont) {
				selectedButton = 1;
				setDisplayedButtons(Arrays.asList(resumeButton,saveButton,showHowToPlayButton,quitButton));
				if(gameSaved){
					savedGameFont.draw(batch, "Game saved", 590, 800);
				}
			}
			else {
				selectedButton = 0;
				setDisplayedButtons(Arrays.asList(playButton, loadButton, showHowToPlayButton, exitGameButton));
				if(loadingFailed){
					cannotLoadFont.draw(batch, "No Save File Found", 500, 800);
				}
			}
			batch.draw(MenuTextures.updown, 1000, 400, 400, 200);
			batch.draw(MenuTextures.wsad, 100, 500, 400, 200);
			batch.draw(MenuTextures.tabSpace, 100, 160, 400, 200);
		}

		batch.end();
	}

	private void setDisplayedButtons(List<Button> setButtonsTrue){
		for (Button menuButton : menuButtons) {
			menuButton.setRendered(false);
		}
		for (Button button : setButtonsTrue) {
			button.setRendered(true);
		}
	}

	private void setDisplayedButtons(Button button){
		setDisplayedButtons(Arrays.asList(button));
	}

	public void setGameNull(){
		panic.setGameNull();
	}

	public void unpauseGame(){
		panic.getGame().getCustomerManager().unpause();
	}

	/***
	 * A function that allows scrolling through the different available buttons using up/w and down/s
	 */
	private void setSelectedButton(){
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

	public DifficultyLevel getDifficulty(){
		return difficultyLevel;
	}
	public void setDifficultyLevel(DifficultyLevel difficultyLevel){
		this.difficultyLevel = difficultyLevel;
	}
	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}
	public void setMoney(String totalMoney) { this.totalMoney = totalMoney; }
	public ArrayList<Button> getMenuButtons(){return menuButtons;}

	public void setMenuButtons(ArrayList<Button> menuButtons) {
		this.menuButtons = menuButtons;
	}
}
