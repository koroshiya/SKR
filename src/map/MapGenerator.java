package map;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import tile.BorderTile;
import tile.PresetTile;
import tile.Tile;

public class MapGenerator {
	
	private ArrayList<PresetTile> presets;
	private TileGenerator tg;
	private int length;
	private int height;
	private String borderDirectory;
	
	public MapGenerator(ArrayList<PresetTile> presets, TileGenerator tg,
						int length, int height, String borderDirectory){
		
		this.presets = presets;
		this.tg = tg;
		this.length = length;
		this.height = height;
		this.borderDirectory = borderDirectory;
		
	}
	
	public Tile[][] generateMap() throws SlickException{
		
		Tile[][] tile = new Tile[this.length][this.height];
		BorderTile bTile = new BorderTile(borderDirectory);
		
		for (int i = 0; i < this.length; i++){
			for (int j = 0; j < this.height; j++){
				
				PresetTile t = isPreset(i, j);
				if (t != null){
					tile[i][j] = t.getTile();
					continue;
				}
				
				Tile border = bTile.getTile(i, j, this.length, this.height);
				if (border != null){
					tile[i][j] = border;
					continue;
				}
				
				tile[i][j] = tg.generate();
				tile[i][j].setXCoordinate(i);
				tile[i][j].setYCoordinate(j);
			}
		}
		
		return tile;
		
	}
	
	private PresetTile isPreset(int x, int y){
		
		for (PresetTile t : presets){
			if (t.matches(x, y)){
				return t;
			}
		}
		
		return null;
		
	}
	
}
