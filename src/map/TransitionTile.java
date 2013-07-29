package map;

import org.newdawn.slick.SlickException;

import screen.GameScreen;

public class TransitionTile extends Tile {

	private ParentMap map;
	private ParentMap currentMap;
	
	public TransitionTile(String sprite, ParentMap map, ParentMap currentMap) {
		
		super(true, true, sprite);
		this.map = map;
		this.currentMap = currentMap;
		
	}

	public void interact(GameScreen parent) throws SlickException {
		this.currentMap.getFrame().setMap(this.map);
	}

	
	
}
