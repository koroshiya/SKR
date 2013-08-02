package map;

import java.awt.Point;

import tile.TransitionTile;

public class Transition {

	private String sprite;
	private Point entry;
	private Point exit;
	private ParentMap currentMap;
	private ParentMap targetMap;
	
	public Transition(String sprite, Point entry, Point exit, ParentMap currentMap, ParentMap targetMap){
		
		this.sprite = sprite;
		this.entry = entry;
		this.exit = exit;
		this.currentMap = currentMap;
		this.targetMap = targetMap;
		
	}
	
	
	
	public TransitionTile getTile(){
		return new TransitionTile(this.sprite, this.targetMap, this.currentMap);
	}
	
	public Point getEntry(){
		return this.entry;
	}
	
	public Point getExit(){
		return this.exit;
	}
	
	public void invertTransition(){
		Point tempPoint = entry;
		entry = exit;
		exit = tempPoint;
		
		ParentMap map = currentMap;
		currentMap = targetMap;
		targetMap = map;
	}
	
}
