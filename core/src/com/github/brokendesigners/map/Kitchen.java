package com.github.brokendesigners.map;

import static java.lang.Math.abs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.NuclearWeapon;
import com.github.brokendesigners.map.interactable.Dispenser;
import com.github.brokendesigners.map.interactable.Interactable;
import java.util.ArrayList;

public class Kitchen {
	public static TiledMap tileMap = Constants.TILE_MAP;
	private ArrayList<KitchenCollisionObject> kitchenObstacles;
	private ArrayList<Interactable> kitchenStations;

	public Kitchen(Camera camera, SpriteBatch spriteBatch){

		Matrix4 inverseProjection = spriteBatch.getProjectionMatrix().cpy();
		inverseProjection.inv();

		MapObjects mapObjects = tileMap.getLayers().get("Collision Layer").getObjects();
		kitchenObstacles = new ArrayList<>();
		for (RectangleMapObject rectangleMapObject : mapObjects.getByType(RectangleMapObject.class)){
			Rectangle rectangle = rectangleMapObject.getRectangle();
			Vector3 objectPosition = new Vector3(rectangle.x * Constants.UNIT_SCALE, rectangle.y * Constants.UNIT_SCALE, 0);

			kitchenObstacles.add(
				new KitchenCollisionObject(
					objectPosition,
					rectangle.width * Constants.UNIT_SCALE,
					rectangle.height * Constants.UNIT_SCALE));

		}
		MapObjects mapStations = tileMap.getLayers().get("Interact").getObjects();

		kitchenStations = new ArrayList<>();
		for (RectangleMapObject rectangleMapObject : mapStations.getByType(RectangleMapObject.class)){
			Rectangle rectangle = rectangleMapObject.getRectangle();
			Vector3 objectPosition = new Vector3(rectangle.x * Constants.UNIT_SCALE, rectangle.y * Constants.UNIT_SCALE, 0);

			if (rectangleMapObject.getProperties().get("objectType").equals("Dispenser")){

				System.out.println("adding thingma");
				kitchenStations.add(
					new Dispenser(
						objectPosition,
						rectangle.width * Constants.UNIT_SCALE,
						rectangle.height * Constants.UNIT_SCALE,
						NuclearWeapon.class));

			}

		}
	}

	public ArrayList getKitchenObstacles() {
		return kitchenObstacles;
	}

	public ArrayList<? extends Interactable> getKitchenStations(){
		return kitchenStations;
	}

	public static TiledMap getTileMap() {
		return tileMap;
	}

	public static void dispose(){
		tileMap.dispose();
	}

	public void addKitchenObject(KitchenCollisionObject kitchenCollisionObject){
		kitchenObstacles.add(kitchenCollisionObject);
	}
}
