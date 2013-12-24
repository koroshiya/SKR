package tile;

import interfaces.Photogenic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import slickgamestate.SlickSKR;

public class Tile implements Photogenic{
	
	private boolean open; //can be stepped on
	private final boolean fore;
	
	protected int x;
	protected int y;
	protected int targetWidth;
	protected int targetHeight;
	
	protected String sprite;
	
	/**
	 * TODO: make dynamic tile
	 * */

	/**
	 * @param open Whether or not the file can be stepped on, reached, etc.
	 * @param sprite String pointing to image resource to display when Tile is drawn
	 * @param x X coordinate of Tile on map
	 * @param y Y coordinate of Tile on map
	 * @param fore True if this tile should be displayed in front of the character
	 * */
	public Tile(boolean open, String sprite, int x, int y, boolean fore){
		this(open, sprite, x, y, fore, SlickSKR.scaled_icon_size, SlickSKR.scaled_icon_size);
	}
	
	/**
	 * @param open Whether or not the file can be stepped on, reached, etc.
	 * @param sprite String pointing to image resource to display when Tile is drawn
	 * @param x X coordinate of Tile on map
	 * @param y Y coordinate of Tile on map
	 * @param fore True if this tile should be displayed in front of the character
	 * */
	public Tile(boolean open, String sprite, int x, int y, boolean fore, float width, float height){
		
		this.open = open;
		this.sprite = sprite;
		this.fore = fore;
		
		this.x = x;
		this.y = y;
		this.targetWidth = (int)width;
		this.targetHeight = (int)height;
		
	}
	
	public void setTileOpen(boolean open){this.open = open;}
	
	public boolean isOpen(){return this.open;}
	
	public void setSprite(String icon){
		this.sprite = icon;
	}

	@Override
	public String getAvatar() {return this.sprite;}
	
	public int getXCoordinate(){return this.x;}
	
	public int getYCoordinate(){return this.y;}
	
	public void setXCoordinate(int x){this.x = x;}
	
	public void setYCoordinate(int y){this.y = y;}
	
	@Override
	public void draw(Graphics g, int x, int y, int targetHeight){
		if (this.sprite != ""){
			SlickSKR.drawImageScaled(g, x, y, targetHeight, this.sprite);
		}
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y, int targetHeight){
		if (sprite != defaultImage){this.draw(g, (int)x, (int)y, targetHeight);}
	}
	
	/**
	 * Used to determine whether this tile is in the foreground (drawn in front of the character on the map)
	 * or the background (drawn behind the character on the map).
	 * 
	 * @return True if tile is drawn in front of character, other tiles, etc.
	 * */
	public boolean isFore(){return fore;}
	
	public boolean stepOn(Graphics g){return false;}
	
}
