package tile;

import interfaces.Photogenic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile implements Photogenic{
	
	private boolean open; //can be stepped on
	
	protected int x;
	protected int y;
	
	protected String sprite;
	protected Image cache;
	
	/**
	 * TODO: make dynamic tile
	 * */

	/**
	 * @param open Whether or not the file can be stepped on, reached, etc.
	 * @param sprite String pointing to image resource to display when Tile is drawn
	 * @param x X coordinate of Tile on map
	 * @param y Y coordinate of Tile on map
	 * */
	public Tile(boolean open, String sprite, int x, int y){
		
		this.open = open;
		this.sprite = sprite;
		
		this.x = x;
		this.y = y;
		
	}

	public void openTile(){this.open = true;}
	
	public void closeTile(){this.open = false;}
	
	public boolean isOpen(){return this.open;}
	
	public void setSprite(String icon){
		this.sprite = icon;
		try {
			this.setCache(new Image(this.sprite));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setSprite(Image icon){this.setCache(icon);}

	@Override
	public String getAvatar() {return this.sprite;}
	
	public void instantiate() throws SlickException{
		this.cache = this.sprite.length() == 0 ? new Image(0,0) : new Image(this.sprite);
	}
	
	public Image getCache(){return this.cache;}
	
	public void setCache(Image cache){this.cache = cache;}

	public int getXCoordinate(){return this.x;}
	
	public int getYCoordinate(){return this.y;}
	
	public void setXCoordinate(int x){this.x = x;}
	
	public void setYCoordinate(int y){this.y = y;}
	
	public void draw(Graphics g, float x, float y){
		g.drawImage(this.cache, x, y);
	}
	
	@Override
	public void drawScaled(Graphics g, int x, int y, float width, float height){
		g.drawImage(cache, x, y, x + width, y + height, 0, 0, cache.getWidth(), cache.getHeight());
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		if (sprite != defaultImage){this.draw(g, x, y);}
	}
	
	public boolean stepOn(){return false;}
	
}
