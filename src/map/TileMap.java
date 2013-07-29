package map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class TileMap implements TileBasedMap {

	private Tile[][] tiles;
	
	public TileMap(Tile[][] tiles){
		this.tiles = tiles;
	}
	
	@Override
	public boolean blocked(PathFindingContext arg0, int arg1, int arg2) {
		return !tiles[arg1][arg2].isOpen();
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 1;
	}

	@Override
	public int getHeightInTiles() {
		return 26;
	}

	@Override
	public int getWidthInTiles() {
		return 32;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {}
	
	public Tile getTileByIndex(double x, double y){
		
		Tile tile = null;
		
		try{
			tile = this.tiles[(int) x][(int) y];
			return tile;
		}catch (NullPointerException ex){
			return null;
		}catch (ArrayIndexOutOfBoundsException ex){
			return null;
		}
		
	}
	
	public boolean isOpenAndReachable(double x, double y){
		return (tiles[(int) x][(int) y].isReachable() && tiles[(int) x][(int) y].isOpen());
	}
	
	public void instantiate(String defaultTile) throws SlickException{
		Tile def = new Tile(false, false, defaultTile);
		def.instantiate();
		for (Tile[] t1 : tiles){
			for (Tile t2 : t1){
				if (t2.getAvatar().equals(def.getAvatar())){
					t2.setCache(def.getCache());
				}else{
					t2.instantiate();					
				}
			}
		}
	}

}
