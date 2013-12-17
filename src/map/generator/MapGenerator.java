package map.generator;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import slickgamestate.MapScreen;
import tile.BorderTile;
import tile.PresetTile;
import tile.SpanTile;
import tile.SpanTransitionTile;
import tile.Tile;

public class MapGenerator {
	
	private PresetTile[] presets;
	private TileGenerator tg;
	private int length;
	private int height;
	private String borderDirectory;
	
	public MapGenerator(PresetTile[] presets, TileGenerator tg,
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
		System.out.println("Generating map");
		ArrayList<Point> fPointList = new ArrayList<Point>();
		
		for (int i = 0; i < this.length; i++){
			for (int j = 0; j < this.height; j++){
				
				PresetTile t = isPreset(i, j);
				if (t != null){
					tile[i][j] = t.getTile();
					if (t.getTile() instanceof SpanTile){
						System.out.println("SpanTile alert");
						SpanTile stile = (SpanTile) t.getTile();
						int curX = 0; //NOTE: meant to be 0; don't wanna override original
						int curY;
						int maxX = (int)Math.floor(((float)stile.getWidth())/((float)MapScreen.ICON_SIZE));
						int maxY = (int)Math.floor(((float)stile.getHeight())/((float)MapScreen.ICON_SIZE));
						while (++curX < maxX){
							curY = -1;
							while (++curY < maxY){
								fPointList.add(new Point(i+curX,j+curY));
							}
						}
					}else if(t.getTile() instanceof SpanTransitionTile){
						SpanTransitionTile stile = (SpanTransitionTile) t.getTile();
						int curX = 0; //NOTE: meant to be 0; don't wanna override original
						int curY;
						int maxX = (int)Math.floor(((float)stile.getWidth())/((float)MapScreen.ICON_SIZE));
						int maxY = (int)Math.floor(((float)stile.getHeight())/((float)MapScreen.ICON_SIZE));
						while (++curX < maxX){
							curY = -1;
							while (++curY < maxY){
								if (stile.getXCoordinate() == curX && stile.getYCoordinate() == curY){
									tile[i+curX][j+curY] = stile.getTransition();
								}else{
									fPointList.add(new Point(i+curX,j+curY));
								}
							}
						}
					}
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
		
		for (Point p : fPointList){
			tile[p.x][p.y] = new Tile(false, false, "");
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
