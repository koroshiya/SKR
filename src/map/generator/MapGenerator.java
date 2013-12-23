package map.generator;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import slickgamestate.SlickSKR;
import tile.BorderTile;
import tile.SpanTile;
import tile.SpanTransitionTile;
import tile.Tile;

public class MapGenerator {
	
	private Tile[] presets;
	private TileGenerator tg;
	private int length;
	private int height;
	private String borderDirectory;
	
	public MapGenerator(Tile[] presets, TileGenerator tg, int length, int height, String borderDirectory){
		
		this.presets = presets;
		this.tg = tg;
		this.length = length;
		this.height = height;
		this.borderDirectory = borderDirectory;
		
	}
	
	public Tile[][] generateMap() throws SlickException{
		
		Tile[][] tile = new Tile[this.length][this.height];
		BorderTile bTile = new BorderTile(borderDirectory);
		//System.out.println("Generating map");
		ArrayList<Point> fPointList = new ArrayList<Point>();
		ArrayList<Point> sPointList = new ArrayList<Point>();
		
		int i = -1;
		int j;
		while (++i < this.length){
			j = -1;
			while (++j < this.height){
				
				Tile t = getPreset(i, j);
				if (t != null){
					if (t instanceof SpanTile || t instanceof SpanTransitionTile){
						sPointList.add(new Point(i,j));
						int curX = i-1;
						int curY;
						int maxX = i;
						int maxY = j;
						if(t instanceof SpanTransitionTile){
							SpanTransitionTile stile = (SpanTransitionTile) t;
							maxX += (int)Math.floor(((float)stile.getWidth())/((float)SlickSKR.scaled_icon_size));
							maxY += (int)Math.floor(((float)stile.getHeight())/((float)SlickSKR.scaled_icon_size));
							while (++curX < maxX){
								curY = j-1;
								while (++curY < maxY){
									if (stile.isTransition(curX, curY)){
										//System.out.println("Creating transition at " + curX + ", " + curY);
										//tile[curX][curY] = stile.getTransition(curX, curY);
										sPointList.add(new Point(curX, curY));
									}else{
										fPointList.add(new Point(curX,curY));
									}
								}
							}
						}else{
							SpanTile stile = (SpanTile) t;
							maxX += (int)Math.floor(((float)stile.getWidth())/((float)SlickSKR.scaled_icon_size));
							maxY += (int)Math.floor(((float)stile.getHeight())/((float)SlickSKR.scaled_icon_size));
							while (++curX < maxX){
								curY = j-1;
								while (++curY < maxY){
									fPointList.add(new Point(curX,curY));
								}
							}
						}
					}else{
						tile[i][j] = t;
					}
					continue;
				}
				
				Tile border = bTile.getTile(i, j, this.length, this.height);
				if (border != null){
					tile[i][j] = border;
					continue;
				}
				
				tile[i][j] = tg.generate(i,j);
				tile[i][j].setXCoordinate(i);
				tile[i][j].setYCoordinate(j);
			}
		}
		
		for (Point p : fPointList){
			//System.out.println("Creating blocked tile at: " + p.x + ", " + p.y);
			tile[p.x][p.y] = new Tile(false, "", p.x, p.y, false);
		}
		for (Point p : sPointList){
			
			Tile t = getPreset(p.x, p.y);
			if (t instanceof SpanTransitionTile){
				SpanTransitionTile stile = (SpanTransitionTile) t;
				tile[stile.getTransitionX()][stile.getTransitionY()] = stile.getTransition();
				tile[p.x][p.y] = stile;
			}else{
				Log.error("ERROR - Preset tile as null at " + p.x + ", " + p.y);
			}
			
		}
		
		return tile;
		
	}
	
	private Tile getPreset(int x, int y){
		
		for (Tile t : presets){
			if (t.getXCoordinate() == x && t.getYCoordinate() == y){
				return t;
			}
		}
		return null;
		
	}
	
}
