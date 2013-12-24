package com.japanzai.skr;

import java.util.ArrayList;

import map.ParentMap;

public class MapManager {
	
	private ArrayList<ParentMap> maps = new ArrayList<ParentMap>();
	
	public static final int INDEX_MAP_MAIN = 0;

	private int currentMap;
	private int previousMap;
	
	public void addMap(ParentMap map){
		maps.add(map);
	}
	
	public void swapToMap(int targetMapID){
		//TODO: implement
	}

}
