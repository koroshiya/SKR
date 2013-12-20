package controls;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class SlickProgressBar extends SlickBlankRectangle{
	
	private static final long serialVersionUID = 1L;
	private int progress;

	public SlickProgressBar(float x, float y, float width, float height, String tag, boolean clickable) {
		this(x, y, width, height, tag, true, clickable);
	}

	public SlickProgressBar(float x, float y, float width, float height, String tag, boolean enabled, boolean clickable) {
		this(x, y, width, height, tag, enabled, tag, clickable);
	}

	public SlickProgressBar(float x, float y, float width, float height, String tag, boolean enabled, String displayText, boolean clickable) {
		super(x, y, width, height, tag, enabled, displayText, clickable);
		this.progress = 0;
	}
	
	public void setProgress(int progress){this.progress = progress;}
	
	public void resetProgress(){this.progress = 0;}
	
	public int getProgress(){return this.progress;}
	
	public void increaseProgress(int progress){
		if (this.progress + progress <= 100){
			this.progress += progress;
		}else{
			this.progress = 100;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.white);
		float ratio = 100 / progress;
		Rectangle rect = new Rectangle(getX(), getY(), getWidth() * ratio, getHeight() * ratio);
		g.fill(rect);
		g.setColor(temp);
		g.draw(this);
	}
	
}
