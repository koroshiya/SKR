package com.japanzai.skr;

import map.MovementListener;
import map.ParentMap;
import map.Tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapScreen extends BasicGameState implements KeyListener{
	
	 //character whose sprite we'll show
	
	private Image sprite;
	
	public static final int LEFT = 37;
	public static final int RIGHT = 39;
	public static final int UP = 38;
	public static final int DOWN = 40;
	
	private final int state;
	private ParentMap map;
	
	public static int ICON_SIZE = 48;
	 //TODO: turn into constructor
	//TODO: enemy or battle arraylist
	//TODO: dynamically create battle depending on enemy arraylist? mix and match
	
	
	public MapScreen(int state, ParentMap map){
		
		this.state = state;
		this.map = map;

		//Dimension size = new Dimension((int)mapSize.getX(), (int)mapSize.getY());
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		sprite = new Image(map.getDefaultTile());
		map.instantiateImages();
		
		MovementListener ml = new MovementListener(this);
		ml.setInput(gc.getInput());
		
		gc.getInput().addKeyListener(ml);
		gc.getInput().addMouseListener(ml);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {

		try{
			
			double posX = this.map.getCurrentPositionX() / ICON_SIZE;
			double posY = this.map.getCurrentPositionY() / ICON_SIZE;
			Tile tile = null;
						
			for (int i = -1; i < this.map.getCharacterPositionX() * 2 + 2; i++){
								
				for (int j = -1; j < this.map.getCharacterPositionY() * 2 + 2; j++){
					
					try{
						
						tile = map.getTileByIndex(i + posX, j + posY);
						
						g.drawImage(sprite, (float)(i * 48 - map.getXDiff()), (float)(j * 48 - map.getYDiff()), null);	
						if (tile.getAvatar() != map.getDefaultTile()){
							g.drawImage(tile.getCache(), (float)(i * 48 - map.getXDiff()), (float)(j * 48 - map.getYDiff()), null);
						}
						//System.out.println("success");
					}catch (Exception ex){
						//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
					}
				}
			}	
			
			g.drawImage(this.map.getCache(), this.map.getCharacterPositionX() * ICON_SIZE, this.map.getCharacterPositionY() * ICON_SIZE, null);
			
		}catch (Exception ex){
			//ex.printStackTrace();
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.state;
	}

	
	public ParentMap getParentMap() {
		return map;
	}

	
	public void setMap(ParentMap map2) throws SlickException {
		this.map = map2;
		sprite = new Image(map.getDefaultTile());
		map.instantiateImages();
	}

		

}
