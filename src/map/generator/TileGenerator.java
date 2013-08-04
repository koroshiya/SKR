package map.generator;

import java.util.ArrayList;

import tile.RandomTile;
import tile.Tile;

public class TileGenerator {
	
	private ArrayList<RandomTile> tiles;
	
	public TileGenerator(ArrayList<RandomTile> tiles){
		
		this.tiles = tiles;
		
	}
	
	public Tile generate(){
		
		for (RandomTile t : this.tiles){
			if (t.canGenerate()){return t.getTile();}
		}
		
		return this.tiles.get(tiles.size()).getTile();
		
	}
	

	
}
