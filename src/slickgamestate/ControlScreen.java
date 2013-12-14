package slickgamestate;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;

public class ControlScreen extends SlickGameState{

	private Image backgroundImage;
	
	public ControlScreen(GameScreen battleScreen) {
		
		super(SlickSKR.CONTROLSCREEN, battleScreen);
		
	}	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		backgroundImage = new Image("/res/start.png");
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		//SlickSKR.PlayMusic("other/public/intro.ogg");
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {

		if (SlickGameState.needFlush()){
			g.drawImage(backgroundImage, 0, 0);
			
			g.drawString(SlickSKR.getValueFromKey("screen.control.commands.heading"), 20, 100);
			g.drawString(SlickSKR.getValueFromKey("screen.control.commands.interact"), 20, 120);
			g.drawString(SlickSKR.getValueFromKey("screen.control.commands.menu"), 20, 140);
			g.drawString(SlickSKR.getValueFromKey("screen.control.commands.fullscreen"), 20, 160);
			g.drawString(SlickSKR.getValueFromKey("screen.control.commands.quit"), 20, 180);
	
			g.drawString("Programming: Koro", 20, 220);
			g.drawString("Sprites: Peter Hull", 20, 240);

			SlickGameState.capture(g);
		}else{
			SlickGameState.drawCache(g);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {}

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
