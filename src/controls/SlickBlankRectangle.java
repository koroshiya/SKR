package controls;

import org.newdawn.slick.Graphics;

public class SlickBlankRectangle extends SlickRectangle {

	public SlickBlankRectangle(float x, float y, float width, float height, String tag, boolean enabled, String displayText) {
		super(x, y, width, height, tag, enabled, displayText);
		// TODO Auto-generated constructor stub
	}

	public SlickBlankRectangle(float x, float y, float width, float height, String tag, boolean b) {
		super(x, y, width, height, tag, b);
	}

	public SlickBlankRectangle(float x, float y, float width, float height, String tag) {
		super(x, y, width, height, tag);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paintCache(Graphics g, int xOff, int yOff) {
		// TODO Auto-generated method stub

	}

}
