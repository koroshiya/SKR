package com.japanzai.skr;

import map.ParentMap;
import menu.MenuMainWindow;
import menu.character.CharacterProfileWindow;
import menu.inventory.InventoryWindow;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameOver;
import screen.GameScreen;

public class SlickSKR extends StateBasedGame {

	private static final String GAME_NAME = "SKR";
	public static final int MAP = 0;
	public static final int BATTLE = 1;
	public static final int MENU = 2;
	public static final int INVENTORY = 3;
	public static final int CHARACTER = 4;
	public static final int GAMEOVER = 666;
	
	public static final boolean DEBUG_MODE = false;
	
	public SlickSKR(ParentMap current) throws SlickException{

		super(GAME_NAME);
		this.addState(new BattleScreen(BATTLE));
		this.addState(new MapScreen(MAP, current));
		this.addState(new MenuMainWindow(MENU, current.getFrame()));
		this.addState(new InventoryWindow(INVENTORY));
		this.addState(new CharacterProfileWindow(CHARACTER, current.getFrame()));
		//this.addState(new GameOver(GAMEOVER));
		
	}
	
	public SlickSKR(){

		super(GAME_NAME);
		
	}
	
	public void setSKR(GameScreen gs, ParentMap current){

		this.addState(new BattleScreen(BATTLE));
		MapScreen ms = new MapScreen(MAP, current);
		this.addState(ms);
		
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.getState(MAP).init(gc, this);
		this.enterState(MAP);
		
	}
	
}
