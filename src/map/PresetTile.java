package map;

import java.awt.Point;

public class PresetTile {

	private Tile t;
	private int x;
	private int y;
	
	public PresetTile(Tile t, Point p){
		
		this.t = t;
		this.x = (int)p.getX();
		this.y = (int)p.getY();
		
	}
	
	public PresetTile(Tile t, int x, int y){
		
		this.t = t;
		this.x = x;
		this.y = y;
		
	}
	
	public boolean matches(int a, int b){
		
		return (a == x) && (b == y);
		
	}
	
	public Tile getTile(){
		return this.t;
	}
	
}
