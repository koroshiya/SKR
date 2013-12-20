package tile;

import map.ParentMap;

public class SpanTransitionTile extends SpanTile{
	
	private final int width;
	private final int height;
	private final int transitionX;
	private final int transitionY;
	private final ParentMap map;
	private final ParentMap currentMap;

	/**
	 * Transition tile that spans many other tiles.
	 * eg. A house, where you can't walk through the walls, but can enter through the door.
	 * 
	 * @param entranceXIndex Zero-based X index of Tile of Span cluster that is open
	 * @param entranceYIndex Zero-based Y index of Tile of Span cluster that is open
	 * */
	public SpanTransitionTile(
			ParentMap map, ParentMap currentMap, String sprite, int width, int height, 
			int entranceXIndex, int entranceYIndex, int x, int y) {
		super(false, sprite, width, height, x, y);
		this.width = width;
		this.height = height;
		this.transitionX = this.x + entranceXIndex;
		this.transitionY = this.y + entranceYIndex;
		this.map = map;
		this.currentMap = currentMap;
		
	}
	
	public int getWidth(){return this.width;}
	
	public int getHeight(){return this.height;}
	
	/**
	 * Checks the tile at position x,y to see if it is this SpanTransitionTile's marked TransitionTile.
	 * ie. Of the tiles spanned by this class, only one tile is a TransitionTile.
	 * This method checks if the tile at x,y if that TransitionTile.
	 * 
	 * @param x X coordinate of the tile to check
	 * @param y Y coordinate of the tile to check
	 * 
	 * @return True if tile at position x,y is the TransitionTile of this SpanTransitionTile 
	 * */
	public boolean isTransition(int x, int y){return transitionX == x && transitionY == y;}
	
	public TransitionTile getTransition(){
		return new TransitionTile("", map, currentMap, transitionX, transitionY, transitionX, transitionY);
	}
	
	public int getTransitionX(){return this.transitionX;}
	
	public int getTransitionY(){return this.transitionY;}

}
