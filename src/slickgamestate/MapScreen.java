package slickgamestate;

import java.awt.Point;
import java.io.Serializable;

import map.ParentMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import console.MapConsole;
import controls.SlickCacheSave;
import controls.SlickImageCache;
import screen.GameScreen;
import tile.CharacterTile;
import tile.InteractiveTile;
import tile.Tile;
import tile.event.CharacterEventTile;
import tile.event.EventTile;

/**
 * @author Koroshiya
 * MapScreen is the game state responsible for managing the "map".
 * ie. It draws and emulates an area, a field, a jungle, or whatever vicinity the character is in.
 * This class provides methods and listeners for interacting with the map, but is predominantly 
 * responsible for any rendering that must take place. 
 * 
 * Difference between MapScreen and ParentMap:
 * MapScreen is the game state. It renders the images you see, listens for keystrokes, etc.
 * ParentMap is the underlying "grid" of tiles that MapScreen draws and interacts with.
 * Think of ParentMap as a blueprint, and MapScreen as the class responsible for rendering and interacting with the blueprint. 
 */
public class MapScreen extends SlickGameState{
	
	private final int INTERACT = Input.KEY_A;
	private final int MENU = Input.KEY_W;
	private final int QUIT = Input.KEY_ESCAPE;
	private final int FULLSCREEN = Input.KEY_F;
	
	private ParentMap map;
	private ParentMap tempMap;
	private MapConsole activeDialog = null;
	private Point step = null;
	
	public static SlickImageCache mapCache; //Cache for the objects on the map. eg. Trees, rocks, chests, etc.
	public static SlickImageCache fgCache; //Same as mapCache, but in the foreground.
	public static SlickImageCache dCache; //Dialogue cache
	private SlickImageCache bgCache; //Cache for the map itself. This is usually a repeating background pattern.
	
	public MapScreen(ParentMap map){
		super(SlickSKR.MAP, map.getFrame());
		this.map = map;
	}
	
	private class MapScreenSave implements Serializable{
		private static final long serialVersionUID = -3313109186859812280L;
		private final SlickCacheSave mapCacheSave;
		private final SlickCacheSave fgCacheSave;
		private final SlickCacheSave dCacheSave;
		private final SlickCacheSave bgCacheSave;
		public MapScreenSave(
				SlickCacheSave mapCacheSave, 
				SlickCacheSave fgCacheSave, 
				SlickCacheSave dCacheSave, 
				SlickCacheSave bgCacheSave
		){
			this.mapCacheSave = mapCacheSave; 
			this.fgCacheSave = fgCacheSave; 
			this.dCacheSave = dCacheSave; 
			this.bgCacheSave = bgCacheSave;
		}
		public SlickCacheSave getMapCacheSave(){return this.mapCacheSave;}
		public SlickCacheSave getFgCacheSave(){return this.fgCacheSave;}
		public SlickCacheSave getDCacheSave(){return this.dCacheSave;}
		public SlickCacheSave getBgCacheSave(){return this.bgCacheSave;}
	}
	
	public void load(Object obj) throws SlickException{
		MapScreenSave cache = (MapScreenSave) obj;
		mapCache = (SlickImageCache) cache.getMapCacheSave().load();
		fgCache = (SlickImageCache) cache.getFgCacheSave().load();
		dCache = (SlickImageCache) cache.getDCacheSave().load();
		bgCache = (SlickImageCache) cache.getBgCacheSave().load();
	}
	
	public MapScreenSave save(){
		return new MapScreenSave(mapCache.save(),fgCache.save(),dCache.save(),bgCache.save());
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException{
		mapCache = new SlickImageCache(map.getPositionX(), map.getPositionY(), map.getWidth(), map.getHeight());
		fgCache = new SlickImageCache(map.getPositionX(), map.getPositionY(), map.getWidth(), map.getHeight());
		bgCache = new SlickImageCache(SlickSKR.scaled_icon_size, SlickSKR.scaled_icon_size, gc.getWidth() + SlickSKR.scaled_icon_size * 2, gc.getHeight() + SlickSKR.scaled_icon_size * 2);
		dCache = new SlickImageCache(0,0,gc.getWidth(),gc.getHeight());
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1) throws SlickException {
		
		map.instantiateImages();
		SlickSKR.PlayMusic(map.getThemeMusic());
		mapCache.setFlush(true);
		fgCache.setFlush(true);
		bgCache.setFlush(true);
		dCache.setFlush(false);
		
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		Input i = super.parent.getInput();
		if (!getParentMap().isLocked() && this.activeDialog == null){
			if (i.isKeyDown(Input.KEY_UP)){
				getParentMap().move(ParentMap.UP);
			}else if (i.isKeyDown(Input.KEY_RIGHT)){
				getParentMap().move(ParentMap.RIGHT);
			}else if (i.isKeyDown(Input.KEY_LEFT)){
				getParentMap().move(ParentMap.LEFT);
			}else if (i.isKeyDown(Input.KEY_DOWN)){
				getParentMap().move(ParentMap.DOWN);
			}
		}
		if (!mapCache.needFlush()){
			float yDiff = map.getYDiff();
			float xDiff = map.getXDiff();
			if (xDiff != 0 || yDiff != 0){
				mapCache.move(xDiff, yDiff);
				fgCache.move(xDiff, yDiff);
				bgCache.move(xDiff, yDiff);
			}
		}
		if (this.activeDialog != null){
			checkCursor(arg0, this.activeDialog.getRects());
		}
		
	}
	
	/**
	 * Resets the offset of all caches.
	 * Should be used when character movement ceases, or the underlying map changes.
	 * */
	public void resetPosition(){
		mapCache.resetPosition();
		fgCache.resetPosition();
		bgCache.resetPosition();
	}
	
	/**
	 * Moves both caches by the offset indicated, then resets their internal offsets to match the new coordinates.
	 * 
	 * @param xDiff Distance on the x-axis for the caches to move before resetting.
	 * @param yDiff Distance on the y-axis for the caches to move before resetting.
	 * */
	public void resetPosition(float xDiff, float yDiff){
		mapCache.move(xDiff, yDiff);
		fgCache.move(xDiff, yDiff);
		bgCache.move(xDiff, yDiff);
		mapCache.resetPosition();
		fgCache.resetPosition();
		bgCache.resetPosition(SlickSKR.scaled_icon_size, SlickSKR.scaled_icon_size);
		//Log.error("Resetting to " + xDiff);
		//mapCache.setFlush(true);
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		if (bgCache.needFlush()){
			g.clear();
			Graphics gb = bgCache.getGraphics();
			gb.clear();
			gb.fillRect(0, 0, bgCache.getWidth(), bgCache.getHeight(), new Image(map.getDefaultTile()).getScaledCopy(SlickSKR.scaleSize), 0, 0);
			bgCache.setFlush(false);
		}
		
		if (mapCache.needFlush()){
			//Log.error("Flush Cycle");
			Graphics gc = mapCache.getGraphics();
			gc.clear();
			Graphics gf = fgCache.getGraphics();
			gf.clear();
			
			String defaultTile = map.getDefaultTile();
			
			int i = -1;
			int j;
			Tile temp;
			while (++i < mapCache.getWidth()){
				j = -1;
				while (++j < mapCache.getHeight()){
					temp = map.getTileByIndex(i, j);
					if (temp != null){
						temp.drawIfNotDefault(temp.isFore() ? gf : gc, defaultTile, i * SlickSKR.scaled_icon_size, j * SlickSKR.scaled_icon_size, SlickSKR.icon_size);
					}
				}
			}
			
			mapCache.setFlush(false);
			fgCache.setFlush(false);
			
		}
		
		if (dCache.needFlush() && this.activeDialog != null){
			Graphics gd = dCache.getGraphics();
			gd.clear();
			this.activeDialog.paint(gd, 0, 0);
			dCache.setFlush(false);
		}
		
		bgCache.draw(g);
		mapCache.draw(g);
		g.flush();

		this.map.getAnimatedSprite().draw(g, this.map.getCharacterPositionX() * SlickSKR.scaled_icon_size, this.map.getCharacterPositionY() * SlickSKR.scaled_icon_size, SlickSKR.icon_size);
		
		fgCache.draw(g);
		if (this.activeDialog != null){dCache.draw(g);}
		g.flush();

		if (step != null){
			this.setMap(tempMap, step.x, step.y, g);
			step = null;
		}
		g.flush();
		
	}
	
	/**
	 * Returns the underlying map layout that this state emulates.
	 * 
	 * @return Map contained by this class, responsible for the layout, preset tiles, etc.
	 * */
	public ParentMap getParentMap() {return map;}
	
	/**
	 * Sets the map associated with this screen.
	 * 
	 * @param map2 Map to set for this screen.
	 * @param x X coordinate at which to place the character.
	 * @param y Y coordinate at which to place the character.
	 * */
	public void setMap(ParentMap map2, float x, float y, Graphics g) {
		this.map = map2;
		try {
			this.map.instantiateImages();
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
		this.map.moveToPosition(x, y);
		super.parent.swapView(SlickSKR.MAP);
	}
	
	public void setMap(ParentMap map2, float x, float y) {
		this.step = new Point((int)x,(int)y);
		this.tempMap = map2;
	}

	/**
	 * Sets any active on-screen dialogues to null, effectively clearing them from the screen.
	 * eg. Used to end a dialogue with an NPC, or to dismiss a notification about picking up an item.
	 * */
	public void removeMapConsole() {
		activeDialog = null;
		mapCache.setFlush(true);
		bgCache.setFlush(true);
	}
	
	/**
	 * Sets an on-screen dialogue.
	 * eg. A prompt that the player can interact with, an alert, etc.
	 * 
	 * @param activeDialog Console to display on the screen.
	 */
	public void setMapConsole(MapConsole activeDialog){this.activeDialog = activeDialog;}

	@Override
	public void processMouseClick(int clickCount, int x, int y) {
		int px = (int) Math.floor(x / SlickSKR.scaled_icon_size);
		int py = (int) Math.floor(y / SlickSKR.scaled_icon_size);
		
		getParentMap().tryMoveToTile(px, py);
		//mapCache.setFlush(true);
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		if (activeDialog != null){
			MapConsole console = (MapConsole)activeDialog;
			console.mouseReleased(arg0, x, y);
		}else{
			processMouseClick(1, x, y);
		}
		
	}
	
	/**
	 * Attempts to interact with the tile directly in front of the character.
	 * Used to open treasure chests, talk to NPCs, etc.
	 */
	private void interact() {
		
		int dir = getParentMap().getDirection();
		int x = (int)getParentMap().getCurrentPositionX() + getParentMap().getCharacterPositionX() * SlickSKR.scaled_icon_size;
		int y = (int)getParentMap().getCurrentPositionY() + getParentMap().getCharacterPositionY() * SlickSKR.scaled_icon_size;
		
		if (dir == ParentMap.UP){
			y -= SlickSKR.scaled_icon_size;
		}else if (dir == ParentMap.RIGHT){
			x += SlickSKR.scaled_icon_size;
		}else if (dir == ParentMap.LEFT){
			x -= SlickSKR.scaled_icon_size;
		}else if (dir == ParentMap.DOWN){
			y += SlickSKR.scaled_icon_size;
		}
		
		if (getParentMap().tileExists(x, y)){
			Tile t = getParentMap().getTileByPosition(x, y);
			if (t instanceof CharacterTile || t instanceof CharacterEventTile){
				CharacterTile tile = (CharacterTile)t;
				tile.face(dir);
				tile.interact(parent);
			}else if (t instanceof InteractiveTile || t instanceof EventTile){
				InteractiveTile tile = (InteractiveTile)t;
				tile.interact(getParentMap().getFrame());
			}
			dCache.setFlush(true);
		}
		//mapCache.setFlush(true);
		
	}

	@Override
	public void keyPressed(int code, char arg1) {
		//if (activeDialog != null){
			//MapConsole console = (MapConsole)activeDialog;
			//console.keyPressed(code, arg1);
			//dCache.setFlush(true);
		//}
		
	}

	@Override
	public void keyReleased(int code, char arg1) {
		if (activeDialog != null){
			MapConsole console = (MapConsole)activeDialog;
			console.keyReleased(code, arg1);
			dCache.setFlush(true);
		}else {
			if (code == MENU){
				super.parent.swapView(SlickSKR.MENU);
			}else if (code == QUIT){
				//TODO: make exit prompt
				System.exit(0);
			}else if (code == INTERACT){
				interact();
			}else if (code == FULLSCREEN){
				GameScreen parent = getParentMap().getFrame();
				parent.setFullScreen();
			}else {
				//System.out.println("MapScreen KeyReleased: " + code);
			}
		}
		
	}

}
