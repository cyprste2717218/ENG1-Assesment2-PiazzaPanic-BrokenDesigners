package com.github.brokendesigners.map;

import static java.lang.Math.abs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.LoadGame;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.*;
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
	public static TiledMap tileMap;
	private ArrayList<KitchenCollisionObject> kitchenObstacles;
	private ArrayList<Station> kitchenStations;
	private ArrayList<Station> lockedKitchenStations;
	private ArrayList<AssemblyStation> assembly;
	private ArrayList<CookingStation> cookings;
	private ArrayList<BakingStation> bakings;
	private ArrayList<CuttingStation> cuttings;
	private ArrayList<CounterStation> counters;
 	private ArrayList<CustomerStation> customerStations;
	private BubbleRenderer bubbleRenderer;
	private ArrayList<Vector2> playerSpawnPoints;
	private Vector2 customerSpawnPoint;
	private Match match;


	/*
	 * Instantiates Kitchen.
	 */
	public Kitchen(Camera camera, SpriteBatch spriteBatch, BubbleRenderer bubbleRenderer, Match match, boolean isLoading, LoadGame loader){
		this.bubbleRenderer = bubbleRenderer;
		this.match = match;
		Matrix4 inverseProjection = spriteBatch.getProjectionMatrix().cpy();
		inverseProjection.inv();
		tileMap = Constants.TILE_MAP;
		kitchenObstacles = new ArrayList<>();
		customerStations = new ArrayList<>();

		setUpCollisionLayer();
		setUpInteractLayer();
		setUpSpawnPoints();

		if(isLoading) loadStations(loader);
	}

	public Kitchen(Match match, boolean isLoading, LoadGame loader, boolean testing){
		this.match = match;
		setUpCollisionLayer();
		setUpInteractLayer();
		setUpSpawnPoints();
		if(isLoading) loadStations(loader);
	}

	private void loadStations(LoadGame loader){
		int assemblerCount = 0;
		int customerStationCount = 0;
		for(int i = 0; i < kitchenStations.size(); i++){
			Station station = kitchenStations.get(i);
			station.setIsLocked(loader.getStationLocked().get(i));
			station.hand = loader.getStationHand().get(i);
			if(station instanceof AssemblyStation){
				((AssemblyStation) station).setItems(loader.getAssemblerItems().get(assemblerCount));
				assemblerCount++;
			}
			else if(station instanceof CustomerStation){
				((CustomerStation) station).setServingCustomer(loader.getCustomerIsServed().get(customerStationCount));
				customerStationCount++;
			}
		}
	}

	private void setUpCollisionLayer(){
		MapObjects mapObjects = tileMap.getLayers().get("Collision Layer").getObjects();
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
	}

	private void setUpInteractLayer(){
		MapObjects mapStations = tileMap.getLayers().get("Interact").getObjects();

		kitchenStations = new ArrayList<>();
		lockedKitchenStations = new ArrayList<>();
		assembly = new ArrayList<>();
		cookings = new ArrayList<>();
		bakings = new ArrayList<>();
		cuttings = new ArrayList<>();
		counters = new ArrayList<>();
		for (RectangleMapObject rectangleMapObject : mapStations.getByType(RectangleMapObject.class)){
			Rectangle rectangle = rectangleMapObject.getRectangle();
			Vector2 objectPosition = new Vector2(rectangle.x * Constants.UNIT_SCALE, rectangle.y * Constants.UNIT_SCALE);

			switch (rectangleMapObject.getProperties().get("objectType").toString()){
				case "Dispenser":
					setUpDispenserStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Counter":
					setUpCounterStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "CustomerCounter":
					setUpCustomerCounter(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Bin":
					setUpBinStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Assembly":
					setUpAssemblyStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Baking":
					setUpBakingStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Baking_Locked":
					setUpLockedBakingStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Cooking":
					setUpCookingStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Cooking_Locked":
					setUpLockedCookingStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Cutting":
					setUpCuttingStation(rectangleMapObject, rectangle, objectPosition);
					break;
				case "Cutting_Locked":
					setUpLockedCuttingStation(rectangleMapObject, rectangle, objectPosition);
					break;
			}
		}
	}

	private void setUpDispenserStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		kitchenStations.add(
				new DispenserStation(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
						ItemRegister.itemRegister.get(rectangleMapObject.getProperties().get("itemType"))));
	}

	private void setUpCounterStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		CounterStation counterStation = new CounterStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, match);

		kitchenStations.add(counterStation);
		counters.add(counterStation);
	}

	private void setUpCustomerCounter(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		CustomerStation station = new CustomerStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY,
				bubbleRenderer, match);

		kitchenStations.add(station);
		customerStations.add(station);
	}

	private void setUpBinStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		kitchenStations.add(
				new BinStation(
						new Rectangle(
								objectPosition.x,
								objectPosition.y,
								rectangle.width * Constants.UNIT_SCALE,
								rectangle.height * Constants.UNIT_SCALE)));
	}

	private void setUpAssemblyStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		MapProperties assemblerProperties = rectangleMapObject.getProperties();
		ArrayList<Vector2> handPositions = new ArrayList<>(4);
		handPositions.add(new Vector2((float)assemblerProperties.get("hand1X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand1Y") * Constants.UNIT_SCALE + objectPosition.y));
		handPositions.add(new Vector2((float)assemblerProperties.get("hand2X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand2Y") * Constants.UNIT_SCALE + objectPosition.y));
		handPositions.add(new Vector2((float)assemblerProperties.get("hand3X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand3Y") * Constants.UNIT_SCALE + objectPosition.y));
		handPositions.add(new Vector2((float)assemblerProperties.get("hand4X") * Constants.UNIT_SCALE + objectPosition.x, (float)assemblerProperties.get("hand4Y") * Constants.UNIT_SCALE + objectPosition.y));

		AssemblyStation assemblyStation = new AssemblyStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handPositions,
				bubbleRenderer, match);

		kitchenStations.add(assemblyStation);
		assembly.add(assemblyStation);
	}

	private void setUpCookingStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		CookingStation cookingStation = new CookingStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, false, match);

		kitchenStations.add(cookingStation);
		cookings.add(cookingStation);
	}

	private void setUpLockedCookingStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		CookingStation LockedCookingStation = new CookingStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, true, match);
		kitchenStations.add(LockedCookingStation);
		lockedKitchenStations.add(LockedCookingStation);
		cookings.add(LockedCookingStation);
	}

	private void setUpBakingStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		BakingStation bakingStation = new BakingStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, false, match);

		kitchenStations.add(bakingStation);
		bakings.add(bakingStation);
	}

	private void setUpLockedBakingStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		BakingStation LockedBakingStation = new BakingStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, true, match);
		kitchenStations.add(LockedBakingStation);
		lockedKitchenStations.add(LockedBakingStation);
		bakings.add(LockedBakingStation);

	}

	private void setUpCuttingStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		CuttingStation cuttingStation = new CuttingStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, false, match);

		kitchenStations.add(cuttingStation);
		cuttings.add(cuttingStation);
	}

	private void setUpLockedCuttingStation(RectangleMapObject rectangleMapObject, Rectangle rectangle, Vector2 objectPosition){
		float handX = (float)rectangleMapObject.getProperties().get("handX") * Constants.UNIT_SCALE + objectPosition.x;
		float handY = (float)rectangleMapObject.getProperties().get("handY") * Constants.UNIT_SCALE + objectPosition.y;

		CuttingStation LockedCuttingStation = new CuttingStation(
				objectPosition,
				rectangle.width * Constants.UNIT_SCALE,
				rectangle.height * Constants.UNIT_SCALE,
				handX,
				handY, bubbleRenderer, true, match);
		kitchenStations.add(LockedCuttingStation);
		lockedKitchenStations.add(LockedCuttingStation);
		cuttings.add(LockedCuttingStation);
	}

	private void setUpSpawnPoints(){
		MapObjects mapPoints = tileMap.getLayers().get("Map").getObjects();

		ArrayList<Vector2> playerSpawnPoints = new ArrayList<>();
		RectangleMapObject playerSpawn = (RectangleMapObject)mapPoints.get("PlayerSpawn");
		RectangleMapObject playerSpawn2 = (RectangleMapObject)mapPoints.get("PlayerSpawn2");
		RectangleMapObject playerSpawn3 = (RectangleMapObject)mapPoints.get("PlayerSpawn3");

		RectangleMapObject customerSpawn = (RectangleMapObject)mapPoints.get("CustomerSpawn");

		Vector2 playerSpawnPoint = new Vector2(playerSpawn.getRectangle().x * Constants.UNIT_SCALE, playerSpawn.getRectangle().y * Constants.UNIT_SCALE);
		playerSpawnPoints.add(playerSpawnPoint);
		Vector2 playerSpawn2Point = new Vector2(playerSpawn2.getRectangle().x * Constants.UNIT_SCALE, playerSpawn2.getRectangle().y * Constants.UNIT_SCALE);
		playerSpawnPoints.add(playerSpawn2Point);
		Vector2 playerSpawn3Point = new Vector2(playerSpawn3.getRectangle().x * Constants.UNIT_SCALE, playerSpawn3.getRectangle().y * Constants.UNIT_SCALE);
		playerSpawnPoints.add(playerSpawn3Point);

		this.customerSpawnPoint = new Vector2(customerSpawn.getRectangle().x * Constants.UNIT_SCALE, customerSpawn.getRectangle().y * Constants.UNIT_SCALE);
		this.playerSpawnPoints = playerSpawnPoints;
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
	public ArrayList<Station> getKitchenStations(){
		return kitchenStations;
	}
	public ArrayList<? extends Station> getLockedKitchenStations(){
		return lockedKitchenStations;
	}
	public ArrayList<AssemblyStation> getAssembly(){
		return assembly;
	}

	public ArrayList<CookingStation> getCookings(){
		return cookings;
	}

	public ArrayList<BakingStation> getBakings(){
		return bakings;
	}

	public ArrayList<CuttingStation> getCuttings(){
		return cuttings;
	}

	public ArrayList<CounterStation> getCounters(){
		return counters;
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

	public ArrayList<Vector2> getPlayerSpawnPoints() {
		return playerSpawnPoints;
	}

}
