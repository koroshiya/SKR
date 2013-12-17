package tile;

import slickgamestate.MapScreen;
import map.ParentMap;

public class SpanTransitionTile extends TransitionTile{ //TODO: implement; currently just a transition tile
	
	private final int width;
	private final int height;
	private final TransitionTile tTile;

	/**
	 * Transition tile that spans many other tiles.
	 * eg. A house, where you can't walk through the walls, but can enter through the door.
	 * 
	 * @param entranceXIndex Zero-based X index of Tile of Span cluster that is open
	 * @param entranceYIndex Zero-based Y index of Tile of Span cluster that is open
	 * */
	public SpanTransitionTile(String sprite, ParentMap map,
			ParentMap currentMap, int startX, int startY, 
			int width, int height, int entranceXIndex, int entranceYIndex) {
		super(sprite, map, currentMap, startX, startY);
		this.width = width;
		this.height = height;
		tTile = new TransitionTile(sprite, map, currentMap, startX + entranceXIndex, startY + entranceYIndex);
	}
	
	public int getWidth(){return this.width;}
	
	public int getHeight(){return this.height;}
	
	public TransitionTile getTransition(){
		return tTile;
	}

}
