package map;

import java.util.ArrayList;

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
