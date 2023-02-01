package com.github.brokendesigners;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
/*
 * Some useful constants.
 */
public class Constants {

	public static final float UNIT_SCALE = 1/8f; // Use this whenever you reference in-world co-ords
	public static TiledMap TILE_MAP = new TmxMapLoader().load("map2/level1.tmx"); // change this to change map


}
