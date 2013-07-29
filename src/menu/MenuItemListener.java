package menu;

import java.io.IOException;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import com.japanzai.skr.Driver;

import screen.GameScreen;

public class MenuItemListener implements MouseListener, KeyListener {

	private GameScreen parent;
	private MenuMainWindow window;
	
	private final int MENU = Input.KEY_W;
	private final int QUIT = Input.KEY_ESCAPE;
	private final int FULLSCREEN = Input.KEY_F;

	private final int INVENTORY = Input.KEY_I; //i
	private final int EQUIPMENT = Input.KEY_E; //e
	private final int CHARACTERS = Input.KEY_C; //c
	private final int SAVE = Input.KEY_S; //s
	private final int LOAD = Input.KEY_L; //l
	
	public MenuItemListener(GameScreen parent, MenuMainWindow window){
		
		this.parent = parent;
		this.window = window;
		
	}
	
	private void load(){
		try {
			Driver.load();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void save(){
		try {
			Driver.save();
		} catch (IOException e) {
			e.printStackTrace();
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
		return true;
	}

	
	@Override
	public void setInput(Input arg0) {}

	
	@Override
	public void keyPressed(int arg0, char arg1) {}

	
	@Override
	public void keyReleased(int code, char arg1) {
				
		if (code == MENU){
			parent.swapToMap();
		}else if (code == QUIT){
			Driver.quit();
		}else if (code == CHARACTERS){
			parent.swapToCharacterWindow();
		}else if (code == INVENTORY){
			parent.swapToInventory();
		}else if (code == EQUIPMENT){
			//parent.displayInventoryPanel();
		}else if (code == SAVE){
			save();
		}else if (code == LOAD){
			load();
		}else if (code == FULLSCREEN){
			parent.setFullScreen();
		}
		
	}

	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}

	
	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	
	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	
	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		
		try {
			window.processMouseClick(arg0, x, y);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	
	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
