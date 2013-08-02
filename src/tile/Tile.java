package tile;

import interfaces.Photogenic;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile implements Photogenic{
	
	private boolean open; //can be stepped on
	private boolean reachable;
	
	private int x;
	private int y;
	
	private String sprite;
	private Image cache;
	
	/**
	 * TODO: make dynamic tile
	 * */

	public Tile(boolean open, boolean reachable, String sprite,
			int x, int y){
		
		this(open, reachable, sprite);
		
		this.x = x;
		this.y = y;
		
	}
	
	public Tile(boolean open, boolean reachable, String sprite){
				
		this.open = reachable && open;
		this.reachable = reachable;
		this.sprite = sprite;
		
	}
	
	public void openTile(){
		
		if (this.reachable){
			this.open = true;
		}
		
	}
	
	public void closeTile(){
		
		this.open = false;
		
	}
	
	public boolean isOpen(){
		
		return this.open;
		
	}
	
	public void makeReachable(){
		
		this.reachable = true;
		
	}
	
	public void makeUnreachable(){
		
		this.reachable = false;
		this.open = false;
		
	} 
	
	public boolean isReachable(){
		
		return this.reachable;
		
	}
	
	public void setSprite(String icon){
		this.sprite = icon;
		try {
			this.setCache(new Image(this.sprite));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getAvatar() throws SlickException {
		return (this.sprite);
	}
	
	public void instantiate() throws SlickException{
		this.cache = new Image(this.sprite);
	}
	
	public void setCache(Image cache){
		this.cache = cache;
	}
	
	public Image getCache(){
		return this.cache;
	}

	public int getXCoordinate(){
		return this.x;
	}
	
	public int getYCoordinate(){
		return this.y;
	}
	
	public void setXCoordinate(int x){
		this.x = x;
	}
	
	public void setYCoordinate(int y){
		this.y = y;
	}
	
}
