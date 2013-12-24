package com.japanzai.skr;

import java.util.ArrayList;

import map.ParentMap;

public class MapManager {
	
	private static ArrayList<ParentMap> maps = new ArrayList<ParentMap>();
	
	public static final int INDEX_MAP_MAIN = 0;

	private static int currentMap = 0;
	private static int previousMap;
	
	public void addMap(ParentMap map){
		maps.add(map);
	}
	
	public void swapToMap(int targetMapID){
		//TODO: implement
		previousMap = currentMap;
		currentMap = targetMapID;
	}
	
	public static ParentMap getMap(int index){return maps.get(index);}
	
	public static ParentMap getMap(){return maps.get(currentMap);}

}
