package controls;

import org.newdawn.slick.Graphics;

public class SlickRectCache extends SlickCache{
	
	private SlickImageRectangle[] rects = new SlickImageRectangle[0];

	public SlickRectCache(float xPos, float yPos, int width, int height) {
		super(xPos, yPos, width, height);
	}
	
	public void setRects(SlickImageRectangle[] rects){
		this.rects = rects;
	}

	public void draw(Graphics g) {
		for (SlickImageRectangle rect : rects){
			rect.paintCache(g);
			rect.paintCenter(g, true);
		}
	}

}
