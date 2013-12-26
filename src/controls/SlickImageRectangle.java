package controls;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SlickImageRectangle extends SlickRectangle {
	
	private static final long serialVersionUID = 8892796440483186655L;

	private boolean highlighted = false;
	private boolean flush = true;
	private Image cache = null;
	
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
			if (flush){
				if (cache == null){
					try {
						cache = new Image(imgSrc).getScaledCopy((int)(width), (int)(height));
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
				cache.setAlpha(highlighted ? 0.5f : 1.0f);
			}
			cache.draw(x + xOff, y + yOff);
		}
	}

	public boolean setHighlighted(boolean b) {
		if (highlighted == b){
			flush = true;
			return false;
		}
		highlighted = b;
		return true;
	}

}
