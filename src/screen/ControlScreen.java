package screen;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.SlickSKR;

public class ControlScreen extends SlickGameState{
	
	public ControlScreen(GameScreen battleScreen) {
		
		super(SlickSKR.CONTROLSCREEN, battleScreen);
		
	}	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		g.drawString("Controls:", 20, 100);
		g.drawString("A - Interact", 20, 120);
		g.drawString("W - Menu", 20, 140);
		g.drawString("F - Fullscreen", 20, 160);
		g.drawString("Esc - Quit", 20, 180);

		g.drawString("Programming: Koro", 20, 220);
		g.drawString("Sprites: Peter Hull", 20, 240);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {}

	@Override
	public int getID() {
		return SlickSKR.CONTROLSCREEN;
	}
	
	public void returnToMainMenu(){
		this.parent.swapView(SlickSKR.MAINMENU); //TODO: implement when start screen has been created
	}

	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		
		
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		if (code == Input.KEY_W){
			returnToMainMenu();
		}
	}

}
