package controls;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slickgamestate.SlickSKR;

public class SlickImageRectangle extends SlickRectangle {
	
	private static final long serialVersionUID = 8892796440483186655L;
	
	public SlickImageRectangle(float x, float y, float width, float height, String tag, String url, boolean clickable) {
		super(x, y, width, height, tag, url, clickable);
	}

	public SlickImageRectangle(float x, float y, float width, float height, String tag, boolean enabled, String url, boolean clickable) {
		super(x, y, width, height, tag, enabled, url, clickable);
	}

	public SlickImageRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText, String imgSrc, boolean clickable) {
		super(x, y, width, height, tag, enabled, displayText, imgSrc, clickable);
	}
	
	@Override
	public void paintCache(Graphics g, int xOff, int yOff){
		if (!imgSrc.equals("")){
			try {
				new Image(imgSrc).getScaledCopy((int)(width * SlickSKR.scaleSize), (int)(height * SlickSKR.scaleSize)).draw(x + xOff, y + yOff);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

}
