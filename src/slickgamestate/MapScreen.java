package slickgamestate;

import interfaces.SlickDrawableFrame;
import map.ParentMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import console.MapConsole;
import controls.SlickCache;
import screen.GameScreen;
import tile.CharacterTile;
import tile.InteractiveTile;
import tile.Tile;
import tile.event.CharacterEventTile;
import tile.event.EventTile;

public class MapScreen extends SlickGameState{
	
	private Image sprite;
	
	private final int INTERACT = Input.KEY_A;
	private final int MENU = Input.KEY_W;
	private final int QUIT = Input.KEY_ESCAPE;
	private final int FULLSCREEN = Input.KEY_F;
	
	private ParentMap map;
	public static int ICON_SIZE = 48;
	private SlickDrawableFrame activeDialog = null;
	public static SlickCache mapCache;
	private SlickCache bgCache;
	
	public MapScreen(ParentMap map){
		
		super(SlickSKR.MAP, map.getFrame());
		this.map = map;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1){

		try { //TODO: check for leak over time
			mapCache = new SlickCache((float)map.getPositionX(), (float)map.getPositionY(), map.getWidth(), map.getHeight());
			bgCache = new SlickCache(ICON_SIZE, ICON_SIZE, gc.getWidth() + ICON_SIZE * 2, gc.getHeight() + ICON_SIZE * 2);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1) throws SlickException {
		
		sprite = new Image(map.getDefaultTile());
		map.instantiateImages();
		SlickSKR.PlayMusic(map.getThemeMusic());
		mapCache.setFlush(true);
		
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		Input i = super.parent.getInput();
		if (!getParentMap().isLocked() && this.activeDialog == null){
			if (i.isKeyDown(Input.KEY_UP)){
				getParentMap().moveUp();
			}else if (i.isKeyDown(Input.KEY_RIGHT)){
				getParentMap().moveRight();
			}else if (i.isKeyDown(Input.KEY_LEFT)){
				getParentMap().moveLeft();
			}else if (i.isKeyDown(Input.KEY_DOWN)){
				getParentMap().moveDown();
			}
		}
		if (!mapCache.needFlush()){
			float yDiff = map.getYDiff();
			float xDiff = map.getXDiff();
			if (xDiff != 0 || yDiff != 0){
				mapCache.move(xDiff, yDiff);
				bgCache.move(xDiff, yDiff);
			}
		}
		
	}
	
	public void resetPosition(){
		mapCache.resetPosition();
		bgCache.resetPosition();
	}
	
	public void resetPosition(float xDiff, float yDiff){
		mapCache.move(xDiff, yDiff);
		mapCache.resetPosition();
		bgCache.move(xDiff, yDiff);
		bgCache.resetPosition(ICON_SIZE, ICON_SIZE);
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		//g.setFont(SlickSKR.DEFAULT_FONT);
		if (mapCache.needFlush()){
			try { //TODO: check for leak over time
				Graphics gc = mapCache.getGraphics();
				gc.clear();
				Graphics gb = bgCache.getGraphics();
				gb.clear();
				gb.fillRect(0, 0, bgCache.getWidth(), bgCache.getHeight(), sprite, 0, 0);
				
				String defaultTile = map.getDefaultTile();
				
				int i = -1;
				int j;
				while (++i < mapCache.getWidth()){
					j = -1;
					while (++j < mapCache.getHeight()){
						try{
							map.getTileByIndex(i, j).drawIfNotDefault(gc, defaultTile, i * ICON_SIZE, j * ICON_SIZE);
						}catch (Exception ex){
							//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
						}
					}
				}
				
				if (this.activeDialog != null){
					
					this.activeDialog.paint(gc, (int)mapCache.getInitX(), (int)mapCache.getInitY());
					this.activeDialog.paint(gb, ICON_SIZE, ICON_SIZE);
				}
				
				mapCache.setFlush(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bgCache.draw(g);
		mapCache.draw(g);
		g.flush();

		this.map.getAnimatedSprite().draw(this.map.getCharacterPositionX() * ICON_SIZE, this.map.getCharacterPositionY() * ICON_SIZE);
		
	}
	
	public ParentMap getParentMap() {return map;}
	
	public void setMap(ParentMap map2, float startX, float startY) {
		this.map = map2;
		super.parent.swapView(SlickSKR.MAP);
		mapCache.setFlush(true);
	}

	public void removeMapConsole() {activeDialog = null;}
	
	public void setMapConsole(SlickDrawableFrame activeDialog){this.activeDialog = activeDialog;}

	@Override
	public void processMouseClick(int clickCount, int x, int y) {
		int px = (int) Math.floor(x / MapScreen.ICON_SIZE);
		int py = (int) Math.floor(y / MapScreen.ICON_SIZE);
		
		getParentMap().tryMoveToTile(px, py);
		mapCache.setFlush(true);
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
	
	private void interact() {
		
		int dir = getParentMap().getDirection();
		int x = (int)getParentMap().getCurrentPositionX() + getParentMap().getCharacterPositionX() * MapScreen.ICON_SIZE;
		int y = (int)getParentMap().getCurrentPositionY() + getParentMap().getCharacterPositionY() * MapScreen.ICON_SIZE;
		
		if (dir == ParentMap.UP){
			y -= MapScreen.ICON_SIZE;
		}else if (dir == ParentMap.RIGHT){
			x += MapScreen.ICON_SIZE;
		}else if (dir == ParentMap.LEFT){
			x -= MapScreen.ICON_SIZE;
		}else if (dir == ParentMap.DOWN){
			y += MapScreen.ICON_SIZE;
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
		}
		mapCache.setFlush(true);
		
	}

	@Override
	public void keyPressed(int code, char arg1) {
		if (activeDialog != null){
			MapConsole console = (MapConsole)activeDialog;
			console.keyPressed(code, arg1);
		}
		
	}

	@Override
	public void keyReleased(int code, char arg1) {
		if (activeDialog != null){
			MapConsole console = (MapConsole)activeDialog;
			console.keyReleased(code, arg1);
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
				System.out.println("MapScreen KeyReleased: " + code);
			}
		}
		
	}

}
