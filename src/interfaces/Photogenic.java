package interfaces;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface Photogenic {

	public String getAvatar() throws SlickException;
	
	public void drawScaled(Graphics g, int x, int y, float width, float height);
	
	public void instantiate() throws SlickException;
	
}
