package map.generator;

import tile.RandomTile;
import tile.Tile;

public class TileGenerator {
	
	private RandomTile[] tiles;
	
	public TileGenerator(RandomTile[] tiles){
		this.tiles = tiles;
	}
	
	public Tile generate(int x, int y){
		
		for (RandomTile t : this.tiles){
			if (t.canGenerate()){return t.getTile(x, y);}
		}
		
		return this.tiles[tiles.length].getTile(x, y);
		
	}
	
}
