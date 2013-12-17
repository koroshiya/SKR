package slickgamestate;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import map.ParentMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import character.EnemyCharacter;
import screen.GameScreen;
import slickgamestate.menu.CharacterProfileWindow;
import slickgamestate.menu.InventoryWindow;
import slickgamestate.menu.MenuMainWindow;
import slickgamestate.menu.Store;
import slickgamestate.state.Load;
import slickgamestate.state.Save;

public class SlickSKR extends StateBasedGame {

	private static final String GAME_NAME = "SKR";
	public static final int MAP = 0;
	public static final int BATTLE = 1;
	public static final int MENU = 2;
	public static final int INVENTORY = 3;
	public static final int CHARACTER = 4;
	public static final int STORE = 5;
	public static final int SAVE = 662;
	public static final int LOAD = 663;
	public static final int MAINMENU = 664;
	public static final int CONTROLSCREEN = 665;
	public static final int GAMEOVER = 666;

	public static final boolean NO_ENCOUNTERS = true;
	public static final boolean NO_TRANSITIONS = true;
	
	private static final Properties prop = new Properties();
	
	private static String musicPlaying = "";
	
	public SlickSKR(ParentMap current) throws SlickException, FileNotFoundException, IOException{
		
		super(GAME_NAME);
		GameScreen gs = current.getFrame();
		prop.load(ResourceLoader.getResourceAsStream("/res/script/en_US.properties"));
		this.addState(new StartScreen(gs));
		this.addState(new Save(gs));
		this.addState(new Load(gs));
		this.addState(new Battle(gs, new ArrayList<EnemyCharacter>()));
		this.addState(new MapScreen(current));
		this.addState(new MenuMainWindow(gs));
		this.addState(new InventoryWindow(gs));
		this.addState(new CharacterProfileWindow(gs));
		this.addState(new Store(gs));
		this.addState(new ControlScreen(gs));
		this.addState(new GameOver(gs));
		
	}
	
	public SlickSKR() throws IOException{
		super(GAME_NAME);
		prop.load(ResourceLoader.getResourceAsStream("/res/script/en_US.properties"));
	}
		
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setDefaultFont(SlickSKR.loadFont("Ubuntu-R.ttf", 16));
		/*try {
			gc.setMouseCursor("/res/rsword.png", 0, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}*/
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
	
	public static void PlaySFX(String location){
		try {
			Audio sfx = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("/res/sfx/" + location));
			sfx.playAsSoundEffect(1.0f, 1.0f, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void PlayMusic(String location){
		if (location != musicPlaying){
			try {
				musicPlaying = location;
				Audio vict = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("/res/sfx/" + location));
				vict.playAsMusic(1.0f, 1.0f, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void PlayMusic(Audio m){
		if (!m.isPlaying()){m.playAsMusic(1.0f, 1.0f, true);}
	}
	
	public static String getValueFromKey(String key){
		//String result = prop.getProperty(key);
		//System.out.println(key + ": " + result);
		//return result == null ? "" : result;
		return prop.getProperty(key);
	}
	
}
