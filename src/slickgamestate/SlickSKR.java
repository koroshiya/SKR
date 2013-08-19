package slickgamestate;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import map.ParentMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import character.EnemyCharacter;
import screen.GameScreen;
import slickgamestate.menu.CharacterProfileWindow;
import slickgamestate.menu.InventoryWindow;
import slickgamestate.menu.MenuMainWindow;
import slickgamestate.menu.Store;

public class SlickSKR extends StateBasedGame {

	private static final String GAME_NAME = "SKR";
	public static final int MAP = 0;
	public static final int BATTLE = 1;
	public static final int MENU = 2;
	public static final int INVENTORY = 3;
	public static final int CHARACTER = 4;
	public static final int STORE = 5;
	public static final int MAINMENU = 664;
	public static final int CONTROLSCREEN = 665;
	public static final int GAMEOVER = 666;
	
	public static final boolean DEBUG_MODE = false;
	public static TrueTypeFont DEFAULT_FONT;
	
	public SlickSKR(ParentMap current) throws SlickException{
		
		super(GAME_NAME);
		GameScreen gs = current.getFrame();
		this.addState(new Battle(gs, new ArrayList<EnemyCharacter>()));
		this.addState(new MapScreen(current));
		this.addState(new MenuMainWindow(gs));
		this.addState(new InventoryWindow(gs));
		this.addState(new CharacterProfileWindow(gs));
		this.addState(new Store(gs));
		this.addState(new StartScreen(gs));
		this.addState(new ControlScreen(gs));
		this.addState(new GameOver(gs));
		
	}
	
	public SlickSKR(){super(GAME_NAME);}
		
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		DEFAULT_FONT = SlickSKR.loadFont("Ubuntu-B.ttf", 16);
		this.getState(MAINMENU).init(gc, this);
		this.enterState(MAINMENU);
		
	}
	
	public static TrueTypeFont loadFont(String fontName, float size){
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("/res/font/" + fontName);
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			return new TrueTypeFont(awtFont2.deriveFont(size), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Font rendering failed");
			return null;
		}
	}
	
}
