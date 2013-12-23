package controls;

import org.newdawn.slick.Graphics;

public class SlickBlankRectangle extends SlickRectangle {
	
	private static final long serialVersionUID = -1733861031169150097L;

	public SlickBlankRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText, boolean clickable) {
		super(x, y, width, height, tag, enabled, displayText, clickable);
	}

	public SlickBlankRectangle(float x, float y, float width, float height, String tag, boolean enabled, boolean clickable) {
		super(x, y, width, height, tag, enabled, clickable);
	}

	public SlickBlankRectangle(float x, float y, float width, float height, String tag, boolean clickable) {
		super(x, y, width, height, tag, clickable);
	}

	@Override
	public void paintCache(Graphics g, int xOff, int yOff) {}

}
