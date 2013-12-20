package controls;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slickgamestate.SlickSKR;

public class SlickImageRectangle extends SlickRectangle {
	
	private static final long serialVersionUID = 8892796440483186655L;
	
	private Image cache = null;
	
	public SlickImageRectangle(float x, float y, float width, float height, String tag, String url) {
		super(x, y, width, height, tag, url);
	}

	public SlickImageRectangle(float x, float y, float width, float height, String tag, boolean enabled, String url) {
		super(x, y, width, height, tag, enabled, url);
	}

	public SlickImageRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText, String imgSrc) {
		super(x, y, width, height, tag, enabled, displayText, imgSrc);
	}
	
	public void initialize(){
		//Log.debug(imgSrc);
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
	public void paintCache(Graphics g, int xOff, int yOff){
		paintCache(g, xOff, yOff, (float)this.getHeight());
	}
	
	public void paintCache(Graphics g, int xOff, int yOff, float targetHeight){
		targetHeight *=  SlickSKR.scaleSize;
		float newX = x + xOff;
		float newY = y + yOff;
		float targetWidth = (float)Math.floor((float)cache.getWidth() * (float)targetHeight / (float)cache.getHeight());
		g.drawImage(cache, newX, newY, newX + targetWidth, newY + targetHeight, 0, 0, cache.getWidth(), cache.getHeight());
		//Log.debug("SlickRectangle - paintCache(Graphics, int, int, float) - Drawing at size of " + (targetWidth) + ", " + (targetHeight));
	}

}
