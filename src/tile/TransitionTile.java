package tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import map.ParentMap;
import screen.GameScreen;

public class TransitionTile extends Tile {

	private ParentMap map;
	private ParentMap currentMap;
	private final int toX;
	private final int toY;
	
	public TransitionTile(String sprite, ParentMap map, ParentMap currentMap, int startX, int startY, int fromX, int fromY, boolean fore) {
		this(sprite, map, currentMap, startX, startY, true, fromX, fromY, fore);
	}
	
	public TransitionTile(String sprite, ParentMap map, ParentMap currentMap, int startX, int startY, boolean open, int fromX, int fromY, boolean fore) {
		
		super(open, sprite, fromX, fromY, fore);
		this.map = map;
		this.currentMap = currentMap;
		this.toX = startX;
		this.toY = startY;
		
	}

	public void interact(GameScreen parent, Graphics g) throws SlickException {
		this.map.instantiateImages();
		parent.setMap(this.map, toX, toY);
	}

	@Override
	public boolean stepOn(Graphics g) {
		try {
			interact(this.currentMap.getFrame(), g);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
