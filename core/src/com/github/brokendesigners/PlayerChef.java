package com.github.brokendesigners;

public class PlayerChef {

	private int x_coord;
	private int y_coord; //Current Coords of Chef

	private boolean activeChef; //Used for whether or not the current "Chef" is the selected one
	private final int MOVE_SPEED = 10; //CONSTANT -- MOVEMENT SPEED OF CHEF

	public PlayerChef(){

	}

	public void destroy(){

	}

	private void moveLeft(){
		this.x_coord += MOVE_SPEED;
	}
	private void moveRight(){
		this.x_coord -= MOVE_SPEED;
	}
	private void moveUp(){
		this.y_coord += MOVE_SPEED;
	}
	private void moveDown(){
		this.y_coord -= MOVE_SPEED;
	}




}
