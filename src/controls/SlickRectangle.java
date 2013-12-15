package controls;

import interfaces.SlickDrawableFrame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class SlickRectangle extends Rectangle implements SlickDrawableFrame {
	
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private String displayText;
	
	private final float x;
	private final float y;
	private final float width;
	private final float height;
	
	private final String imgSrc;
	private Image cache = null;
	
	private boolean enabled; //TODO: change color if disabled?
	
	public SlickRectangle(float x, float y, float width, float height, String tag) {
		this(x, y, width, height, tag, true);
	}

	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled) {
		this(x, y, width, height, tag, enabled, "");
	}

	public SlickRectangle(float x, float y, float width, float height, String tag, String url) {
		this(x, y, width, height, tag, true, tag, url);
	}

	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled, String url) {
		this(x, y, width, height, tag, enabled, tag, url);
	}

	public SlickRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText, String imgSrc) {
		super(x, y, width, height);
		this.tag = tag;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = enabled;
		this.displayText = displayText;
		this.imgSrc = imgSrc;
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
			//SlickSKR.PlaySFX("other/public/intro_button.ogg");
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
	
	public void initialize(){
		try {
			if (!imgSrc.equals("")){
				cache = new Image(imgSrc);
			}else{
				cache = new Image(0,0);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		paint(g, 0, 0);
	}
	
	@Override
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
	
	public Image getCache(){
		return this.cache;
	}

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