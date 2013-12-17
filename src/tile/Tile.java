package tile;

import interfaces.Photogenic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile implements Photogenic{
	
	private boolean open; //can be stepped on
	private boolean reachable;
	
	private int x;
	private int y;
	
	protected String sprite;
	protected Image cache;
	
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
	
	public Tile(boolean open, boolean reachable, Image cache) {
		
		this.open = reachable && open;
		this.reachable = reachable;
		this.cache = cache;
		this.sprite = "";
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
	
	public void setSprite(Image icon){
		//this.sprite = icon;
		this.setCache(icon);
	}

	@Override
	public String getAvatar() {
		return this.sprite;
	}
	
	public void instantiate() throws SlickException{
		if (this.sprite.length() == 0){
			this.cache = new Image(0,0);
		}else{
			this.cache = new Image(this.sprite);
		}
	}
	
	public Image getCache(){
		return this.cache;
	}
	
	public void setCache(Image cache){
		this.cache = cache;
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
	
	public void draw(Graphics g, float x, float y){
		g.drawImage(this.cache, x, y);
	}
	
	public void drawScaled(Graphics g, int x, int y, int width, int height){
		g.drawImage(cache, x, y, x + width, y + height, 0, 0, cache.getWidth(), cache.getHeight());
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		if (sprite != defaultImage){this.draw(g, x, y);}
		//g.drawImage(cache, x - (cache.getWidth() - MapScreen.ICON_SIZE), y - (cache.getHeight() - MapScreen.ICON_SIZE), null);
	}
	
	public boolean stepOn(){return false;}
	
}
