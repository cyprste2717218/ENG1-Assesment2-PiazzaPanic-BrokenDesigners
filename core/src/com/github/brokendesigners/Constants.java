package com.github.brokendesigners;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Constants {

	public static final float UNIT_SCALE = 1/8f;
	public static TiledMap TILE_MAP = new TmxMapLoader().load("map/level1.tmx");


}
