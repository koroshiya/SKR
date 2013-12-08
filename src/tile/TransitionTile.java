package tile;

import map.ParentMap;

import screen.GameScreen;

public class TransitionTile extends Tile {

	private ParentMap map;
	private ParentMap currentMap;
	
	public TransitionTile(String sprite, ParentMap map, ParentMap currentMap) {
		
		super(true, true, sprite);
		this.map = map;
		this.currentMap = currentMap;
		
	}

	public void interact(GameScreen parent) {
		parent.setMap(this.map);
	}

	@Override
	public boolean stepOn() {
		interact(this.currentMap.getFrame());
		return true;
	}
	
}
