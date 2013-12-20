package tile;

import map.ParentMap;

import screen.GameScreen;

public class TransitionTile extends Tile {

	private ParentMap map;
	private ParentMap currentMap;
	private final int toX;
	private final int toY;
	
	public TransitionTile(String sprite, ParentMap map, ParentMap currentMap, int startX, int startY, int fromX, int fromY) {
		this(sprite, map, currentMap, startX, startY, true, fromX, fromY);
	}
	
	public TransitionTile(String sprite, ParentMap map, ParentMap currentMap, int startX, int startY, boolean open, int fromX, int fromY) {
		
		super(open, sprite, fromX, fromY);
		this.map = map;
		this.currentMap = currentMap;
		this.toX = startX;
		this.toY = startY;
		
	}

	public void interact(GameScreen parent) {
		parent.setMap(this.map, toX, toY);
	}

	@Override
	public boolean stepOn() {
		interact(this.currentMap.getFrame());
		return true;
	}
	
}
