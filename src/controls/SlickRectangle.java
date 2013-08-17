package controls;

import interfaces.SlickDrawableFrame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SlickRectangle extends Rectangle implements SlickDrawableFrame {
	
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private String displayText;
	
	private final float x;
	private final float y;
	private final float width;
	private final float height;
	
	
	private boolean enabled; //TODO: change color if disabled?
	
	public SlickRectangle(float x, float y, float width, float height, String tag) throws SlickException{
		this(x, y, width, height, tag, true);
	}

	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled) throws SlickException{
		this(x, y, width, height, tag, enabled, tag);
	}

	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText) throws SlickException{
		super(x, y, width, height);
		this.tag = tag;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = enabled;
		this.displayText = displayText;
	}
	
	public String getTag(){
		return this.tag;
	}
	
	public String getDisplayText(){
		return this.displayText;
	}
	
	public float getX(){return this.x;}
	
	public float getY(){return this.y;}
	
	public float getWidth(){return this.width;}
	
	public float getHeight(){return this.height;}
	
	public boolean isWithinBounds(int x, int y){
		if (enabled && x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height){
			return true;
		}
		return false;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public void setText(String text){
		this.tag = text;
		this.displayText = text;
	}
	
	public void setText(String text, String displayText){
		this.tag = text;
		this.displayText = displayText;
	}
	
	@Override
	public void paint(Graphics g) {
		if (enabled){
			Color temp = g.getColor();
			g.setColor(Color.black);
			g.fill(this);
			g.setColor(temp);
			g.draw(this);
			g.drawString(displayText, x, y);
		}
	}
	
	public void paintCenter(Graphics g){
		if (enabled){
			Color temp = g.getColor();
			g.setColor(Color.black);
			g.fill(this);
			g.setColor(temp);
			g.draw(this);
			final int textx = g.getFont().getWidth(displayText);
			final int texty = g.getFont().getHeight(displayText);
			g.drawString(displayText, x + (width - textx) / 2, y + (height - texty) / 2);
		}
	}
	
}