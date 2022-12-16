package com.github.brokendesigners;

import static java.lang.Math.abs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.Station;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Player {
	Vector3 worldPosition;

	public float MOVEMENT_SPEED = 2 * Constants.UNIT_SCALE;  // Movement Speed of Chef differs between vertical and horizontal due to following 2 lines

	private final float WIDTH = 18 * Constants.UNIT_SCALE; //NOTE:  NOT THE WIDTH OF CHEF SPRITE
	private final float HEIGHT = 4 * Constants.UNIT_SCALE;	//NOTE: NOT HEIGHT OF CHEF SPRITE
	public Hand hand;

	Rectangle playerRectangle;

	private boolean selected;



	public Player(Vector3 worldPosition){

		this.worldPosition = worldPosition;

		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.WIDTH, this.HEIGHT);

		hand = new Hand();

	}

	public Rectangle getPlayerRectangle() {
		return playerRectangle;
	}

	private void updateRectangle(){
		playerRectangle.x = worldPosition.x;
		playerRectangle.y = worldPosition.y;
	}

	public Vector3 getWorldPosition(){
		return worldPosition;
	}

	public void processMovement(ArrayList<KitchenCollisionObject> objects){

		if (this.selected == true) {

			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.moveUp(objects);
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				this.moveDown(objects);
			}
			this.updateRectangle();
			if (Gdx.input.isKeyPressed(Keys.A)) {
				this.moveLeft(objects);
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				this.moveRight(objects);

			}
			this.updateRectangle();
		}
	}

	public void processPickUp(ArrayList<? extends Station> stations)
		throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			this.pickUp(stations);
		}
	}

	public void processDropOff(ArrayList<? extends  Station> stations){
		if (Gdx.input.isKeyPressed(Keys.DOWN)){
			this.dropOff(stations);
		}
	}


	public boolean moveUp(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.y += (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.y = object.getRectangle().y - this.HEIGHT;

				return false;
			}
		}
		this.worldPosition.y += (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}
	public boolean moveDown(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.y -= (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.y = object.getRectangle().y + object.getHEIGHT();
				return false;
			}
		}
		this.worldPosition.y -= (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}
	public boolean moveRight(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.x += (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.x = object.getRectangle().x - this.WIDTH;
				return false;
			}
		}

		this.worldPosition.x += (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}
	public boolean moveLeft(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.x -= (this.MOVEMENT_SPEED);
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.x = object.getRectangle().x + object.getWIDTH();
				return false;
			}
		}
		this.worldPosition.x -= (this.MOVEMENT_SPEED);
		this.updateRectangle();
		return true;

	}

	public boolean pickUp(ArrayList<? extends Station> stations)
		throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		for (Station station : stations){
			if(Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())){
				station.pickUp(this);
				return true;
			}
		}
		return false;
	}
	public boolean dropOff(ArrayList<? extends Station> stations){
		for (Station station : stations){
			if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())){
				station.dropOff(this);
				return true;
			}
		}
		return false;
	}


	public void setSelected(boolean isSelected){
		this.selected = isSelected;
	}

	public boolean isSelected() {
		return selected;
	}

	public class Hand{

		ArrayList<Item> heldItems;

		public Hand(){
			this.heldItems = new ArrayList<Item>(3);
		}

		public void give(Item item){

			if (this.heldItems.size() == 3){
				return;
			}
			this.heldItems.add(item);
		}

		public Item drop(Item item){
			this.heldItems.remove(item);
			return(item);
		}

		public Item drop() {

			if (!this.heldItems.isEmpty()) {
				int amountOfItems = this.heldItems.size();

				Item droppedItem = this.heldItems.get(
					amountOfItems - 1);
				this.heldItems.remove(amountOfItems - 1);

				return droppedItem;

			} else {
				return null;
			}
		}

		public boolean isEmpty(){
			return this.heldItems.isEmpty();
		}

		public boolean isFull(){
			if (this.heldItems.size() == 3){
				return true;
			}
			else{
				return false;
			}
		}


	}

}
