package controls;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import slickgamestate.SlickSKR;

public abstract class SlickRectangle extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private String displayText;
	
	protected final float x;
	protected final float y;
	protected final float width;
	protected final float height;
	
	protected final String imgSrc;
	
	/*
	 * If disabled, text isn't drawn, click events aren't responded to, etc.
	 * */
	protected boolean enabled; //TODO: change color if disabled?
	/*
	 * If disabled, click events aren't responded to.
	 * */
	protected final boolean clickable;
	
	/**
	 * @param x X coordinate at which the upper left corner of this rectangle begins
	 * @param y Y coordinate at which the upper left corner of this rectangle begins
	 * @param width Width of this rectangle
	 * @param height Height of this rectangle
	 * @param tag Text to uniquely identify this rectangle, and also to display when this rectangle is drawn
	 * */
	public SlickRectangle(float x, float y, float width, float height, String tag, boolean clickable) {
		this(x, y, width, height, tag, true, clickable);
	}

	/**
	 * @param x X coordinate at which the upper left corner of this rectangle begins
	 * @param y Y coordinate at which the upper left corner of this rectangle begins
	 * @param width Width of this rectangle
	 * @param height Height of this rectangle
	 * @param tag Text to uniquely identify this rectangle, and also to display when this rectangle is drawn
	 * @param enabled True if this rectangle should be drawn, accept events, etc.
	 * */
	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled, boolean clickable) {
		this(x, y, width, height, tag, enabled, "", clickable);
	}

	/**
	 * @param x X coordinate at which the upper left corner of this rectangle begins
	 * @param y Y coordinate at which the upper left corner of this rectangle begins
	 * @param width Width of this rectangle
	 * @param height Height of this rectangle
	 * @param tag Text to uniquely identify this rectangle, and also to display when this rectangle is drawn
	 * @param imgSrc URL of image to draw as the background for this rectangle
	 * */
	public SlickRectangle(float x, float y, float width, float height, String tag, String url, boolean clickable) {
		this(x, y, width, height, tag, true, tag, url, clickable);
	}

	/**
	 * @param x X coordinate at which the upper left corner of this rectangle begins
	 * @param y Y coordinate at which the upper left corner of this rectangle begins
	 * @param width Width of this rectangle
	 * @param height Height of this rectangle
	 * @param tag Text to uniquely identify this rectangle, and also to display when this rectangle is drawn
	 * @param enabled True if this rectangle should be drawn, accept events, etc.
	 * @param imgSrc URL of image to draw as the background for this rectangle
	 * */
	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled, String url, boolean clickable) {
		this(x, y, width, height, tag, enabled, tag, url, clickable);
	}

	/**
	 * @param x X coordinate at which the upper left corner of this rectangle begins
	 * @param y Y coordinate at which the upper left corner of this rectangle begins
	 * @param width Width of this rectangle
	 * @param height Height of this rectangle
	 * @param tag Text to uniquely identify this rectangle
	 * @param enabled True if this rectangle should be drawn, accept events, etc.
	 * @param displayText Text to display when this rectangle is drawn
	 * @param imgSrc URL of image to draw as the background for this rectangle
	 * */
	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText, String imgSrc, boolean clickable) {
		super(x, y, width, height);
		this.tag = tag;
		this.x = x * SlickSKR.scaleSize;
		this.y = y * SlickSKR.scaleSize;
		this.width = width * SlickSKR.scaleSize;
		this.height = height * SlickSKR.scaleSize;
		this.enabled = enabled;
		this.displayText = displayText;
		this.imgSrc = imgSrc;
		this.clickable = clickable;
	}

	/**
	 * @return Unique identifier for this SlickRectangle
	 * */
	public String getTag(){return this.tag;}
	
	/**
	 * @return Text to display when this SlickRectangle is drawn
	 * */
	public String getDisplayText(){return this.displayText;}
	
	/**
	 * @return X coordinate of the top left corner of this rectangle
	 * */
	public float getX(){return this.x;}
	
	/**
	 * @return Y coordinate of the top left corner of this rectangle
	 * */
	public float getY(){return this.y;}
	
	/**
	 * @return Width of this rectangle
	 * */
	public float getWidth(){return this.width;}
	
	/**
	 * @return Height of this rectangle
	 * */
	public float getHeight(){return this.height;}
	
	/**
	 * Checks if the point x,y is within the bounds of this rectangle.
	 * eg. Point 100,100 is indeed within a rectangle at point 0,0 of size 200,200.
	 * 
	 * @param x X coordinate of the point to check against this rectangle
	 * @param y Y coordinate of the point to check against this rectangle
	 * 
	 * @return True if point lies within this rectangle, otherwise false.
	 * */
	public boolean isWithinBounds(int x, int y){
		if (enabled && clickable && x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height){
			//SlickSKR.PlaySFX("other/public/intro_button.ogg");
			return true;
		}
		return false;
	}
	
	/**
	 * @return True if enabled, otherwise false.
	 * */
	public boolean isEnabled(){return enabled;}
	
	/**
	 * @param enabled True to enable this SlickRectangle, false to disable
	 * */
	public void setEnabled(boolean enabled){this.enabled = enabled;}
	
	/**
	 * @param text Text to use as this rectangle's unique identifier and to display when this rectangle is drawn
	 * */
	public void setText(String text){setText(text, text);}
	
	/**
	 * @param text Text to uniquely identify this SlickRectangle
	 * @param displayText Text to display when this rectangle is drawn
	 * */
	public void setText(String text, String displayText){
		this.tag = text;
		this.displayText = displayText;
	}
	
	public void paint(Graphics g) {paint(g, 0, 0);}
	
	/**
	 * @param g Graphics context within which to paint this rectangle's border
	 * @param xOff X axis offset at which to paint this rectangle's text
	 * @param yOff Y axis offset at which to paint this rectangle's text
	 * */
	public void paint(Graphics g, int offX, int offY) {
		if (enabled){
			Color temp = g.getColor();
			g.setColor(Color.black);
			g.fill(this);
			g.setColor(temp);
			g.draw(this);
			g.drawString(displayText, x + offX, y + offY);
		}
	}
	
	/**
	 * @param g Graphics context within which to paint this rectangle's cache
	 * */
	public void paintCache(Graphics g){paintCache(g, 0, 0);}
	
	/**
	 * @param g Graphics context within which to paint this rectangle's cache
	 * @param xOff X axis offset at which to paint this rectangle's cache
	 * @param yOff Y axis offset at which to paint this rectangle's cache
	 * */
	public abstract void paintCache(Graphics g, int xOff, int yOff);

	public void paintCenter(Graphics g, int offX, int offY){
		paintCenter(g, false, offX, offY);
	}

	public void paintCenter(Graphics g, boolean hollow, int offX, int offY){
		paintCenter(g, g.getFont(), hollow, offX, offY);
	}

	public void paintCenter(Graphics g){
		paintCenter(g, false);
	}

	public void paintCenter(Graphics g, boolean hollow){
		paintCenter(g, g.getFont(), hollow);
	}

	public void paintCenter(Graphics g, Font f, boolean hollow){
		paintCenter(g, f, hollow, 0, 0);
	}

	public void paintCenter(Graphics g, TrueTypeFont f){
		paintCenter(g, f, false);
	}

	public void paintCenter(Graphics g, Font f, boolean hollow, int offX, int offY){
		if (enabled){
			if (!hollow){
				Color temp = g.getColor();
				g.setColor(Color.black);
				g.fill(this);
				g.setColor(temp);
				g.draw(this);
			}
			final int textx = f.getWidth(displayText);
			final int texty = f.getHeight(displayText);
			g.drawString(displayText, offX + x + (int)((width - textx) / 2), offY + y + (int)((height - texty) / 2));
		}
	}
	
}