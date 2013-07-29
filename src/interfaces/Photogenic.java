package interfaces;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public interface Photogenic {

	public String getAvatar() throws SlickException;
	
	public Image getCache();
	
	public void instantiate() throws SlickException;
	
}
