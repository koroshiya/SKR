package tile;

import org.newdawn.slick.Graphics;

public class ScaleTile extends Tile {

	public ScaleTile(boolean open, String cache, int x, int y) {
		super(open, cache, x, y);
	}
	
	public void draw(Graphics g, float x, float y){
		g.drawImage(cache, x, y, x + cache.getWidth(), y + cache.getHeight(), 0, 0, cache.getWidth(), cache.getHeight());
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		if (sprite != defaultImage){
			this.draw(g, x, y);
		}
	}

}
