package screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.SlickSKR;

public class ControlScreen extends BasicGameState{

	private final GameScreen parent;
	
	public ControlScreen(GameScreen battleScreen) {
		
		this.parent = battleScreen;
		
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
		//this.parent.swapToMap() //TODO: implement when start screen has been created
	}

}
