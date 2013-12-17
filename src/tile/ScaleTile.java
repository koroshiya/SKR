package tile;

import org.newdawn.slick.Graphics;

public class ScaleTile extends Tile {

	public ScaleTile(boolean open, boolean reachable, String cache) {
		super(open, reachable, cache);
	}
	
	public void draw(Graphics g, float x, float y){
		//cache.draw(x, y, x + cache.getWidth(), y + cache.getHeight(), 0, 0, cache.getWidth(), cache.getHeight());
		g.drawImage(cache, x, y, x + cache.getWidth(), y + cache.getHeight(), 0, 0, cache.getWidth(), cache.getHeight());
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		if (sprite != defaultImage){
			this.draw(g, x, y);
		}
		//g.drawImage(cache, x - (cache.getWidth() - MapScreen.ICON_SIZE), y - (cache.getHeight() - MapScreen.ICON_SIZE), null);
	}

}
