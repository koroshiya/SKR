package controls;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SlickRectangle extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	
	private final String tag;
	
	private final float x;
	private final float y;
	private final float width;
	private final float height;
	
	public SlickRectangle(float x, float y, float width, float height, String tag) throws SlickException{
		super(x, y, width, height);
		this.tag = tag;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String getTag(){
		return this.tag;
	}
	
	public boolean isWithinBounds(int x, int y){
		if (x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height){
			return true;
		}
		return false;
	}

}