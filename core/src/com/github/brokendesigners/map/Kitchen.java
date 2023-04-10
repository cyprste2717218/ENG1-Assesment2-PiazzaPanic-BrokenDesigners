package com.github.brokendesigners.map;

import static java.lang.Math.abs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.AssemblyStation;
import com.github.brokendesigners.map.interactable.BakingStation;
import com.github.brokendesigners.map.interactable.BinStation;
import com.github.brokendesigners.map.interactable.CookingStation;
import com.github.brokendesigners.map.interactable.CounterStation;
import com.github.brokendesigners.map.interactable.CustomerStation;
import com.github.brokendesigners.map.interactable.CuttingStation;
import com.github.brokendesigners.map.interactable.DispenserStation;
import com.github.brokendesigners.map.interactable.Station;
import com.github.brokendesigners.renderer.BubbleRenderer;
import java.util.ArrayList;
/*
 * Handles building of the game map.
 * It grabs all of the RectangleMapObjects in the TiledMap and decides which java class it belongs to.
 * It also builds a few arrays -
 * 	• An array of collision objects from the collision layer
 * 	• An array of interaction objects from the interact layer.
 *
 * It also grabs the spawn point for customers and for players.
 */
public class Kitchen {
	public static TiledMap tileMap = Constants.TILE_MAP;
	private ArrayList<KitchenCollisionObject> kitchenObstacles;
	private ArrayList<Station> kitchenStations;
	private ArrayList<CustomerStation> customerStations;

	private Vector2 playerSpawnPoint;
	private Vector2 customerSpawnPoint;


	/*
	 * Instantiates Kitchen.
	 */
	public Kitchen(Camera camera, SpriteBatch spriteBatch, BubbleRenderer bubbleRenderer){

		Matrix4 inverseProjection = spriteBatch.getProjectionMatrix().cpy();
		inverseProjection.inv();

		MapObjects mapObjects = tileMap.getLayers().get("Collision Layer").getObjects();
		kitchenObstacles = new ArrayList<>();
		customerStations = new ArrayList<>();
		for (RectangleMapObject rectangleMapObject : mapObjects.getByType(RectangleMapObject.class)){
			Rectangle rectangle = rectangleMapObject.getRectangle();
			Vector3 objectPosition = new Vector3(rectangle.x * Constants.UNIT_SCALE, rectangle.y * Constants.UNIT_SCALE, 0);

			kitchenObstacles.add(
				new KitchenCollisionObject(
					objectPosition,
					rectangle.width * Constants.UNIT_SCALE,
					rectangle.height * Constants.UNIT_SCALE
						));

		}
		MapObjects mapStations = tileMap.getLayers().get("Interact").getObjects();

		kitchenStations = new ArrayList<>();
		for (RectangleMapObject rectangleMapObject : mapStations.getByType(RectangleMapObject.class)){
			Rectangle rectangle = rectangleMapObject.getRectangle();
			Vector2 objectPosition = new Vector2(rectangle.x * Constants.UNIT_SCALE, rectangle.y * Constants.UNIT_SCALE);

			if (rectangleMapObject.getProperties().get("objectType").equals("Dispenser")){

				kitchenStations.add(
					new DispenserStation(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
							ItemRegister.itemRegister.get(rectangleMapObject.getProperties().get("itemType"))));

			} else if (rectangleMapObject.getProperties().get("objectType").equals("Counter")){

				float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
				float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;
				kitchenStations.add(
					new CounterStation(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
						handX,
						handY, bubbleRenderer));
			} else if (rectangleMapObject.getProperties().get("objectType").equals("CustomerCounter")){

				float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
				float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

				CustomerStation station = new CustomerStation(
					objectPosition,
					rectangle.width * Constants.UNIT_SCALE,
					rectangle.height * Constants.UNIT_SCALE,
					handX,
					handY,
					bubbleRenderer);

				kitchenStations.add(station);
				customerStations.add(station);
			} else if (rectangleMapObject.getProperties().get("objectType").equals("Bin")){

				kitchenStations.add(
					new BinStation(
						new Rectangle(
							objectPosition.x,
							objectPosition.y,
							rectangle.width * Constants.UNIT_SCALE,
							rectangle.height * Constants.UNIT_SCALE)));
			} else if (rectangleMapObject.getProperties().get("objectType").equals("Assembly")){

				MapProperties assemblerProperties = rectangleMapObject.getProperties();


				ArrayList<Vector2> handPositions = new ArrayList<>(4);
				handPositions.add(new Vector2((float)assemblerProperties.get("hand1X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand1Y") * Constants.UNIT_SCALE + objectPosition.y));
				handPositions.add(new Vector2((float)assemblerProperties.get("hand2X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand2Y") * Constants.UNIT_SCALE + objectPosition.y));
				handPositions.add(new Vector2((float)assemblerProperties.get("hand3X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand3Y") * Constants.UNIT_SCALE + objectPosition.y));
				handPositions.add(new Vector2((float)assemblerProperties.get("hand4X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand4Y") * Constants.UNIT_SCALE + objectPosition.y));

				kitchenStations.add(
						new AssemblyStation(
								objectPosition,
								rectangle.width * Constants.UNIT_SCALE,
								rectangle.height * Constants.UNIT_SCALE,
								handPositions, bubbleRenderer));
			} else if (rectangleMapObject.getProperties().get("objectType").equals("Baking")){

				float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
				float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

				kitchenStations.add(
					new BakingStation(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
						handX,
						handY, bubbleRenderer));
			} else if (rectangleMapObject.getProperties().get("objectType").equals("Cooking")){

				float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
				float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

				kitchenStations.add(
					new CookingStation(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
						handX,
						handY, bubbleRenderer));
			} else if (rectangleMapObject.getProperties().get("objectType").equals("Cutting")){

				float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
				float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

				kitchenStations.add(
					new CuttingStation(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
						handX,
						handY, bubbleRenderer));
			}

		}
		MapObjects mapPoints = tileMap.getLayers().get("Map").getObjects();

		RectangleMapObject playerSpawn = (RectangleMapObject)mapPoints.get("PlayerSpawn");
		RectangleMapObject customerSpawn = (RectangleMapObject)mapPoints.get("CustomerSpawn");

		this.playerSpawnPoint = new Vector2(playerSpawn.getRectangle().x * Constants.UNIT_SCALE, playerSpawn.getRectangle().y * Constants.UNIT_SCALE);
		this.customerSpawnPoint = new Vector2(customerSpawn.getRectangle().x * Constants.UNIT_SCALE, customerSpawn.getRectangle().y * Constants.UNIT_SCALE);

		// kitchenObstacles.add(new KitchenCollisionObject(new Vector3(5,5,0),10,10,true));
	}

	/*
	 * returns kitchen collision objects
	 */
	public ArrayList getKitchenObstacles() {
		return kitchenObstacles;
	}

	/*
	 * Returns array of stations
	 */
	public ArrayList<? extends Station> getKitchenStations(){
		return kitchenStations;
	}

	/*
	 * returns the tileMap itself.
	 */
	public static TiledMap getTileMap() {
		return tileMap;
	}

	public static void dispose(){
		tileMap.dispose();
	}

	public void addKitchenObject(KitchenCollisionObject kitchenCollisionObject){
		kitchenObstacles.add(kitchenCollisionObject);
	}


	public ArrayList<CustomerStation> getCustomerStations() {
		return customerStations;
	}

	public Vector2 getCustomerSpawnPoint() {
		return customerSpawnPoint;
	}

	public Vector2 getPlayerSpawnPoint() {
		return playerSpawnPoint;
	}
}
