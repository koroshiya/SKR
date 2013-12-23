package map;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import tile.CharacterTile;
import tile.Tile;

public class TileMap implements TileBasedMap {

	private Tile[][] tiles;
	
	public TileMap(Tile[][] tiles){
		this.tiles = tiles;
	}
	
	@Override
	public boolean blocked(PathFindingContext arg0, int arg1, int arg2) {
		try{
			return !tiles[arg1][arg2].isOpen();
		}catch(ArrayIndexOutOfBoundsException ex){
			return false;
		}
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
		
		try{
			return this.tiles[(int) x][(int) y];
		}catch (NullPointerException ex){
			return null;
		}catch (ArrayIndexOutOfBoundsException ex){
			return null;
		}
		
	}
	
	public boolean isOpenAndReachable(double x, double y){
		try{
			return tiles[(int) x][(int) y].isOpen();
		}catch (ArrayIndexOutOfBoundsException ex){
			return false;
		}
	}
	
	public void instantiate(){
		for (Tile[] tileset : this.tiles){
			for (Tile tile : tileset){
				if (tile instanceof CharacterTile){
					((CharacterTile)tile).getCharacter().instantiate();
				}
			}
		}
	}

}
