package com.japanzai.skr;

import map.ParentMap;
import menu.MenuMainWindow;
import menu.inventory.InventoryWindow;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;

public class SlickSKR extends StateBasedGame {

	private static final String GAME_NAME = "SKR";
	private static final int MAP = 0;
	private static final int BATTLE = 1;
	private static final int MENU = 2;
	private static final int INVENTORY = 3;
	
	public SlickSKR(ParentMap current){

		super(GAME_NAME);
		this.addState(new BattleScreen(BATTLE));
		this.addState(new MapScreen(MAP, current));
		this.addState(new MenuMainWindow(MENU, current.getFrame()));
		this.addState(new InventoryWindow(INVENTORY, current.getFrame()));
		
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
