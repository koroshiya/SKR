package interfaces;

import org.newdawn.slick.Graphics;

public interface SlickDrawableFrame {
	
	public abstract void paint(Graphics g);
	
	public abstract void paint(Graphics g, int offX, int offY);
	
}
