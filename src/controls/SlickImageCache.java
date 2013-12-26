package controls;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SlickImageCache extends SlickCache {
	
	private final Image img;
	
	public SlickImageCache(float xPos, float yPos, int width, int height) throws SlickException{
		super(xPos, yPos, width, height);
		img = new Image(width,height);
		resetPosition(xPos, yPos);
	}
	
	public void draw(Graphics g){
		draw(g, -xPos, -yPos);
	}
	
	public void draw(Graphics g, float x, float y){
		g.drawImage(img, x, y);
	}
	
	public void copyArea(Graphics g){
		g.copyArea(img, 0, 0);
		this.setFlush(false);
	}
	
	public void darken(){
		img.setColor(0, 155, 155, 155);
	}
	
	public Graphics getGraphics() throws SlickException{
		return img.getGraphics();
	}

}
