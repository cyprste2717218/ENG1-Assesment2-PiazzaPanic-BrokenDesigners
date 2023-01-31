package com.github.brokendesigners.menu;

import com.badlogic.gdx.graphics.Texture;

public class MenuTextures {
	public static Texture playButtonUnselected = new Texture("menu_assets/play_unselected.png");
	public static Texture playButtonSelected = new Texture("menu_assets/play_selected.png");

	public static Texture howtoplayButtonUnselected = new Texture("menu_assets/howtoplay_unselected.png");
	public static Texture howtoplayButtonSelected = new Texture("menu_assets/howtoplay_selected.png");

	public static Texture exitButtonUnselected = new Texture("menu_assets/exit_unselected.png");
	public static Texture exitButtonSelected = new Texture("menu_assets/exit_selected.png");

	public static Texture wsad = new Texture("menu_assets/wsad.png");
	public static Texture updown = new Texture("menu_assets/updown.png");
	public static Texture tabSpace = new Texture("menu_assets/tab space.png");

	public static Texture you_win = new Texture("menu_assets/finaltime.png");


	public static Texture title = new Texture("menu_assets/how_to_play.png");

	public void dispose(){
		playButtonSelected.dispose();
		playButtonUnselected.dispose();

		howtoplayButtonSelected.dispose();
		howtoplayButtonUnselected.dispose();

		exitButtonSelected.dispose();
		exitButtonUnselected.dispose();
	}
}
