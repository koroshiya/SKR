package slickgamestate;

import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import map.ParentMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import controls.SlickImageRectangle;
import character.EnemyCharacter;
import screen.GameScreen;
import slickgamestate.menu.CharacterProfileWindow;
import slickgamestate.menu.InventoryWindow;
import slickgamestate.menu.MenuMainWindow;
import slickgamestate.menu.Store;
import slickgamestate.state.Load;
import slickgamestate.state.Save;

public class SlickSKR extends StateBasedGame {

	private final ScalableGame game;
	
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

	public static final int MOUSE_STATE_NORMAL = 0;
	public static final int MOUSE_STATE_HOVER = 1;
	public static final int MOUSE_STATE_PRESSED = 2;
	public static int Mouse_state = 0;
	public static Animation mouseStateList;
	
	private static final Properties prop = new Properties();
	public static final boolean FULLSCREEN = false;
	
	private static String musicPlaying = "";
	public static final Point size = new Point(1366,768);
	public static final Point targetSize = new Point(816, 624);
	public static final float scaleSize = (float)size.y / (float)targetSize.y;
	public static final int icon_size = 48;
	public static final int scaled_icon_size = (int)Math.floor(scaleSize * 48);
	public static int refreshRate;
	
	public SlickSKR(ParentMap current) throws SlickException, IOException{
		
		super(GAME_NAME);
		game = new ScalableGame(this, size.x, size.y);
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
		availableResolutions();
	}
	
	public SlickSKR() throws IOException{
		super(GAME_NAME);
		game = new ScalableGame(this, size.x, size.y);
		prop.load(ResourceLoader.getResourceAsStream("/res/script/en_US.properties"));
	}
	
	private void availableResolutions(){
		ArrayList<DisplayMode> best = new ArrayList<DisplayMode>();
		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
	        for (DisplayMode mode : modes) {
	        	if (mode.getFrequency() == 60){
	        		best.add(mode);
	        		//Display.sync(fps) TODO instead of/as well as vsync? 
	        		//Necessary if already set by Slick? 
	        		//Handled in fullscreen already?
	        		System.out.println(mode.getWidth() + "x" + mode.getHeight() + "x" + mode.getBitsPerPixel() + " " + mode.getFrequency() + "Hz");
	        	}
	        }
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setDefaultFont(SlickSKR.loadFont("Ubuntu-R.ttf", 16));
		
		SpriteSheet sprites = new SpriteSheet("/res/cursor.png", 48, 48);
		mouseStateList = new Animation();
		mouseStateList.addFrame(sprites.getSprite(MOUSE_STATE_NORMAL, 0), 1);
		mouseStateList.addFrame(sprites.getSprite(MOUSE_STATE_HOVER, 0), 1);
		mouseStateList.addFrame(sprites.getSprite(MOUSE_STATE_PRESSED, 0), 1);
		mouseStateList.setAutoUpdate(false);
		mouseStateList.stop();
		
		setMousePointer(MOUSE_STATE_NORMAL, gc);
		this.getState(MAINMENU).init(gc, this);
		this.enterState(MAINMENU);
		
	}
	
	private static void setMousePointer(int pointer, GameContainer gc){
		Mouse_state = pointer;
		try {
			gc.setMouseCursor(mouseStateList.getImage(Mouse_state), 0, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void setMouseStateIfDifferent(int newMouseState, GameContainer gc, SlickImageRectangle rect){
		if (newMouseState != Mouse_state){
			setMousePointer(newMouseState, gc);
		}
	}
	
	public static TrueTypeFont loadFont(String fontName, float size){
		try {
			size *= SlickSKR.scaleSize;
			InputStream inputStream	= ResourceLoader.getResourceAsStream("/res/font/" + fontName);
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			return new TrueTypeFont(awtFont2.deriveFont(size), true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Font rendering failed");
			return null;
		}
	}
	
	public static TrueTypeFont getFont(float size, boolean bold){
		return SlickSKR.loadFont(bold ? "Ubuntu-B.ttf" : "Ubuntu-M.ttf", size);
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
		return prop.getProperty(key);
	}
	
	public ScalableGame getGame(){return this.game;}
	
	public static void setRefreshRate(int rate){
		refreshRate = rate;
	}

	public static void drawImageScaled(Graphics g, float x, float y, int targetHeight, String image){
		try {
			Image tmp = new Image(image);
			drawImageScaled(g, x, y, targetHeight, tmp);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void drawImageScaled(Graphics g, float x, float y, int targetHeight, Image tmp){
		targetHeight *= SlickSKR.scaleSize;
		float scale = (float)tmp.getWidth() / (float)tmp.getHeight();
		tmp.getScaledCopy((int)Math.floor(targetHeight*scale), targetHeight).draw(x, y);
	}
	
}
