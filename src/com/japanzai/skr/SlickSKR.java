package com.japanzai.skr;

import java.util.ArrayList;

import map.ParentMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import character.EnemyCharacter;


import slickgamestate.Battle;
import slickgamestate.GameOver;
import slickgamestate.MapScreen;
import slickgamestate.menu.CharacterProfileWindow;
import slickgamestate.menu.InventoryWindow;
import slickgamestate.menu.MenuMainWindow;

public class SlickSKR extends StateBasedGame {

	private static final String GAME_NAME = "SKR";
	public static final int MAP = 0;
	public static final int BATTLE = 1;
	public static final int MENU = 2;
	public static final int INVENTORY = 3;
	public static final int CHARACTER = 4;
	public static final int MAINMENU = 664;
	public static final int CONTROLSCREEN = 665;
	public static final int GAMEOVER = 666;
	
	public static final boolean DEBUG_MODE = false;
	
	public SlickSKR(ParentMap current) throws SlickException{
		
		super(GAME_NAME);
		this.addState(new Battle(current.getFrame(), new ArrayList<EnemyCharacter>()));
		this.addState(new MapScreen(current));
		this.addState(new MenuMainWindow(current.getFrame()));
		this.addState(new InventoryWindow(current.getFrame()));
		this.addState(new CharacterProfileWindow(current.getFrame()));
		this.addState(new GameOver(current.getFrame()));
		
	}
	
	public SlickSKR(){super(GAME_NAME);}
		
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.getState(MAP).init(gc, this);
		this.enterState(MAP);
		
	}
	
}
