package slickgamestate;

import java.io.IOException;

import interfaces.SlickDrawableFrame;
import map.ParentMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import console.MapConsole;
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
	public static int ICON_SIZE = 47;
	private SlickDrawableFrame activeDialog = null;
	private Audio mCache = null; //TODO: remove and put short transition instead?
	
	
	public MapScreen(ParentMap map){
		
		super(SlickSKR.MAP, map.getFrame());
		this.map = map;
		
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1) throws SlickException {
		
		//super.parent.setTargetFrameRate(60);
		sprite = new Image(map.getDefaultTile());
		map.instantiateImages();
		if (mCache == null){
			try {
				mCache = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("/res/sfx/other/public/summeropenfielddusk.ogg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (mCache != null){SlickSKR.PlayMusic(mCache);}
		
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input i = super.parent.getInput();
		if (!getParentMap().isLocked()){
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
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		g.setFont(SlickSKR.DEFAULT_FONT);
		
		try{
			
			double posX = this.map.getCurrentPositionX() / ICON_SIZE;
			double posY = this.map.getCurrentPositionY() / ICON_SIZE;
			float y;
			Tile tile = null;
			
			float yDiff = map.getYDiff();
			float xDiff = map.getXDiff();
			
			for (int i = -1; i < this.map.getCharacterPositionX() * 2 + 2; i++){
				float iDiff = i * ICON_SIZE - xDiff;
				for (int j = -1; j < this.map.getCharacterPositionY() * 2 + 2; j++){
					
					try{
						
						tile = map.getTileByIndex(i + posX, j + posY);
						y = j * ICON_SIZE - yDiff;
						g.drawImage(sprite, iDiff, y, null);	
						if (tile.getAvatar() != map.getDefaultTile()){
							g.drawImage(tile.getCache(), iDiff, y, null);
						}
						//System.out.println("success");
					}catch (Exception ex){
						//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
					}
				}
			}	
			
			g.drawImage(this.map.getCache(), this.map.getCharacterPositionX() * ICON_SIZE, this.map.getCharacterPositionY() * ICON_SIZE, null);
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		if (this.activeDialog != null){
			this.activeDialog.paint(g);
		}
		
	}
	
	public ParentMap getParentMap() {return map;}
	
	public void setMap(ParentMap map2) throws SlickException {
		this.map = map2;
		//sprite = new Image(map.getDefaultTile());
		//map.instantiateImages();
	}

	public void removeMapConsole() {activeDialog = null;}
	
	public void setMapConsole(SlickDrawableFrame activeDialog){this.activeDialog = activeDialog;}

	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		int px = (int) Math.floor(x / MapScreen.ICON_SIZE);
		int py = (int) Math.floor(y / MapScreen.ICON_SIZE);
		
		try {
			getParentMap().tryMoveToTile(px, py);
		} catch (SlickException e) {
			System.out.println("Can't move there");
			e.printStackTrace();
		}
	}	
	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		
		if (activeDialog != null){
			MapConsole console = (MapConsole)activeDialog;
			console.mouseReleased(arg0, x, y);
		}else{
			try{
				processMouseClick(1, x, y);
			}catch (Exception ex){
				System.out.println("Can't move there");
				ex.printStackTrace();
			}
		}
		
	}
	
	private void interact() throws SlickException{
		
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
				try {
					interact();
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}else if (code == FULLSCREEN){
				GameScreen parent = getParentMap().getFrame();
				parent.setFullScreen();
			}else {
				System.out.println("MapScreen KeyReleased: " + code);
			}
		}
		
	}

}
