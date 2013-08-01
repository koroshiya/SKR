package screen;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends SlickGameState {

	private Image label;
	
	public GameOver(int state, GameScreen parent) throws SlickException{
		
		super(state, parent);
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		label = new Image("/res/gameover.png");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(label, 0, 0);
	}

	
	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
	}

}
