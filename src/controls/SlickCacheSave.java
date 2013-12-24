package controls;

import java.io.Serializable;

import org.newdawn.slick.SlickException;

public class SlickCacheSave implements Serializable {
	
	private static final long serialVersionUID = -3215063184098727431L;
	
	private final float xPos;
	private final float yPos;
	private final int width;
	private final int height;
	
	public SlickCacheSave(SlickCache cache){
		this.xPos = cache.getXPos();
		this.yPos = cache.getYPos();
		this.width = cache.getWidth();
		this.height = cache.getHeight();
	}
	
	public SlickCache load() throws SlickException{
		return new SlickCache(xPos, yPos, width, height);
	}

}
