package map;

import java.awt.Point;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import org.newdawn.slick.SlickException;

import com.japanzai.skr.MapScreen;

import screen.GameScreen;

import character.NonPlayableCharacter;

public class MovementListener implements KeyListener, MouseListener {

	private MapScreen map;
	
	private final int INTERACT = Input.KEY_A; //keycode to interact //65 = A
	private final int MENU = Input.KEY_W; //keycode to open menu //87 = W
	private final int QUIT = Input.KEY_ESCAPE;
	private final int FULLSCREEN = Input.KEY_F;
	
	public MovementListener(MapScreen map){
		
		this.map = map;
		
	}
	
	private void moveToClick(Point p) throws SlickException{
		
		int x = (int) Math.floor(p.x / ParentMap.ICON_SIZE);
		int y = (int) Math.floor(p.y / ParentMap.ICON_SIZE);
		
		map.getParentMap().tryMoveToTile(x, y);
		
	}
	
	
	private void interact(){
		
		int dir = map.getParentMap().getDirection();
		int x = (int)map.getParentMap().getCurrentPositionX() + map.getParentMap().getCharacterPositionX() * ParentMap.ICON_SIZE;
		int y = (int)map.getParentMap().getCurrentPositionY() + map.getParentMap().getCharacterPositionY() * ParentMap.ICON_SIZE;
		
		if (dir == ParentMap.UP){
			y -= ParentMap.ICON_SIZE;
		}else if (dir == ParentMap.RIGHT){
			x += ParentMap.ICON_SIZE;
		}else if (dir == ParentMap.LEFT){
			x -= ParentMap.ICON_SIZE;
		}else if (dir == ParentMap.DOWN){
			y += ParentMap.ICON_SIZE;
		}
		
		if (map.getParentMap().tileExists(x, y)){
			Tile t = map.getParentMap().getTileByPosition(x, y);
			if (t instanceof InteractiveTile){
				InteractiveTile tile = (InteractiveTile)t;
				tile.interact(map.getParentMap().getFrame());
			}else if (t instanceof CharacterTile){
				CharacterTile tile = (CharacterTile)t;
				tile.face(dir);
				NonPlayableCharacter c = tile.getCharacter();				
				map.getParentMap().getFrame().WriteOnMap(c.getDialogue());
				//tile.interact();
			}
		}
		
	}
	
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int arg0, int x, int y) {
		
		try{
			moveToClick(new Point(x, y));
		}catch (Exception ex){
			System.out.println("Can't move there");
			ex.printStackTrace();
		}
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		int code = arg0;
		
		try{
			if (code == Input.KEY_UP){
				map.getParentMap().moveUp();
			}else if (code == Input.KEY_RIGHT){
				map.getParentMap().moveRight();
			}else if (code == Input.KEY_LEFT){
				map.getParentMap().moveLeft();
			}else if (code == Input.KEY_DOWN){
				map.getParentMap().moveDown();
			}else {
				System.out.println(code);
			}
		}catch (Exception ex){
			
		}
	}

	@Override
	public void keyReleased(int code, char arg1) {
		
		if (code == MENU){
			map.getParentMap().getFrame().swapToMenu();
		}else if (code == QUIT){
			//exit
			//TODO: make exit prompt
			System.exit(0);
		}else if (code == INTERACT){
			interact();
		}else if (code == FULLSCREEN){
			GameScreen parent = map.getParentMap().getFrame();
			parent.setFullScreen();
		}else {
			//System.out.println(code);
		}
		
	}

}
