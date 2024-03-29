package com.github.brokendesigners.menu;

import com.badlogic.gdx.graphics.Texture;
/**
 * Extended class
 * Holds all textures for main menu.
 */
public class MenuTextures {
	public static Texture playButtonUnselected = new Texture("menu_assets/play_unselected.png");
	public static Texture playButtonSelected = new Texture("menu_assets/play_selected.png");
	public static Texture resumeButtonUnselected = new Texture("menu_assets/resume_unselected.png");
	public static Texture resumeButtonSelected = new Texture("menu_assets/resume_selected.png");
	public static Texture loadButtonUnselected = new Texture("menu_assets/load_unselected.png");
	public static Texture loadButtonSelected = new Texture("menu_assets/load_selected.png");
	public static Texture saveButtonUnselected = new Texture("menu_assets/save_unselected.png");
	public static Texture saveButtonSelected = new Texture("menu_assets/save_selected.png");
	public static Texture howtoplayButtonUnselected = new Texture("menu_assets/howtoplay_unselected.png");
	public static Texture howtoplayButtonSelected = new Texture("menu_assets/howtoplay_selected.png");

	public static Texture exitButtonUnselected = new Texture("menu_assets/exit_unselected.png");
	public static Texture exitButtonSelected = new Texture("menu_assets/exit_selected.png");
	public static Texture backButtonUnselected = new Texture("menu_assets/back_unselected.png");
	public static Texture backButtonSelected = new Texture("menu_assets/back_selected.png");
	public static Texture quitButtonUnselected = new Texture("menu_assets/quit_unselected.png");
	public static Texture quitButtonSelected = new Texture("menu_assets/quit_selected.png");

	public static Texture scenarioButtonUnselected = new Texture("menu_assets/scenario_unselected.png");
	public static Texture scenarioButtonSelected = new Texture("menu_assets/scenario_selected.png");

	public static Texture customerCountAddUnselected = new Texture("menu_assets/CustomerCountAddButtonUnselected.png");
	public static Texture customerCountAddSelected = new Texture("menu_assets/CustomerCountAddButtonSelected.png");
	public static Texture customerCountSubtractUnselected = new Texture("menu_assets/CustomerCountButtonSubtractUnselected.png");
	public static Texture customerCountSubtractSelected = new Texture("menu_assets/CustomerCountSubtractButtonSelected.png");

	public static Texture endlessButtonUnselected = new Texture("menu_assets/endless_unselected.png");
	public static Texture endlessButtonSelected = new Texture("menu_assets/endless_selected.png");

	public static Texture easyButtonUnselected = new Texture("menu_assets/EasyButton.png");
	public static Texture easyButtonSelected = new Texture ("menu_assets/EasyButtonSelected.png");

	public static Texture mediumButtonUnselected = new Texture("menu_assets/MediumButton.png");
	public static Texture mediumButtonSelected = new Texture("menu_assets/MediumButtonSelected.png");

	public static Texture hardButtonUnselected = new Texture("menu_assets/HardButton.png");
	public static Texture hardButtonSelected = new Texture("menu_assets/HardButtonSelected.png");


	public static Texture wsad = new Texture("menu_assets/wsad.png");
	public static Texture updown = new Texture("menu_assets/updown.png");
	public static Texture tabSpace = new Texture("menu_assets/tab space.png");
	public static Texture you_win = new Texture("menu_assets/finaltime.png");
	public static Texture how_to_play = new Texture("menu_assets/how_to_play.png");
	public static Texture powerupsTut = new Texture("menu_assets/powerups_tutorial.png");


	public void dispose(){
		playButtonSelected.dispose();
		playButtonUnselected.dispose();
		resumeButtonSelected.dispose();
		resumeButtonUnselected.dispose();
		loadButtonSelected.dispose();
		loadButtonUnselected.dispose();
		saveButtonSelected.dispose();
		saveButtonUnselected.dispose();
		howtoplayButtonSelected.dispose();
		howtoplayButtonUnselected.dispose();
		backButtonSelected.dispose();
		backButtonUnselected.dispose();
		quitButtonSelected.dispose();
		quitButtonUnselected.dispose();
		exitButtonSelected.dispose();
		exitButtonUnselected.dispose();

		scenarioButtonSelected.dispose();
		scenarioButtonUnselected.dispose();
		endlessButtonSelected.dispose();
		endlessButtonUnselected.dispose();

		easyButtonUnselected.dispose();
		easyButtonSelected.dispose();

		mediumButtonUnselected.dispose();
		mediumButtonSelected.dispose();

		hardButtonUnselected.dispose();
		hardButtonSelected.dispose();

		wsad.dispose();
		updown.dispose();
		tabSpace.dispose();
		you_win.dispose();
		how_to_play.dispose();
		powerupsTut.dispose();
	}
}
