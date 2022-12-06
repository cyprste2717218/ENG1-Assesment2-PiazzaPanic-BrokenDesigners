package com.github.brokendesigners.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;

public class Kitchen {
	public static TiledMap tileMap = new TmxMapLoader().load("map/level1.tmx");
	private ArrayList mapObstacles;

	public Kitchen(){

		MapObjects mapObjects = tileMap.getLayers().get("Collision Layer").getObjects();

		for (RectangleMapObject rectangleMapObject : tileMap.getLayers().get("Collision Layer").getObjects().getByType(RectangleMapObject.class)){
			Vector3 kitObjVector = new Vector3(
				rectangleMapObject.getRectangle().x,
				rectangleMapObject.getRectangle().y,
				0);
			mapObstacles.add(

				new KitchenObject(
					kitObjVector,
					rectangleMapObject.getRectangle().width,
					rectangleMapObject.getRectangle().height));

		}
	}

	public ArrayList getMapObstacles() {
		return mapObstacles;
	}
}
