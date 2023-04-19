package com.github.brokendesigners.menu;

import com.badlogic.gdx.graphics.Texture;
/*
 * Holds all textures for main menu.
 */
public class MenuTextures {
	public static Texture playButtonUnselected = new Texture("menu_assets/play_unselected.png");
	public static Texture playButtonSelected = new Texture("menu_assets/play_selected.png");

	public static Texture howtoplayButtonUnselected = new Texture("menu_assets/howtoplay_unselected.png");
	public static Texture howtoplayButtonSelected = new Texture("menu_assets/howtoplay_selected.png");

	public static Texture exitButtonUnselected = new Texture("menu_assets/exit_unselected.png");
	public static Texture exitButtonSelected = new Texture("menu_assets/exit_selected.png");

	public static Texture difficultySettingButton = new Texture("menu_assets/settingbutton.png");

	public static Texture scenarioButtonUnselected = new Texture("menu_assets/scenario_unselected.png");
	public static Texture scenarioButtonSelected = new Texture("menu_assets/scenario_selected.png");

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

	public void dispose(){
		playButtonSelected.dispose();
		playButtonUnselected.dispose();

		howtoplayButtonSelected.dispose();
		howtoplayButtonUnselected.dispose();

		exitButtonSelected.dispose();
		exitButtonUnselected.dispose();

		difficultySettingButton.dispose();

		easyButtonUnselected.dispose();
		easyButtonSelected.dispose();

		mediumButtonUnselected.dispose();
		mediumButtonSelected.dispose();

		hardButtonUnselected.dispose();
		hardButtonSelected.dispose();




	}
}
