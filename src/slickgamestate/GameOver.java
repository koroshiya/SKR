package slickgamestate;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import controls.SlickRectangle;
import screen.GameScreen;

public class GameOver extends SlickGameState {

	private Image label;
	private SlickRectangle[] rects;
	private final String[] commands = {"Main menu", "Quit"};
	
	public GameOver(GameScreen parent) throws SlickException{
		
		super(SlickSKR.GAMEOVER, parent);
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		label = new Image("/res/gameover.png");
		rects = new SlickRectangle[2];
		rects[0] = new SlickRectangle(300, 350, 100, 50, commands[0]);
		rects[1] = new SlickRectangle(402, 350, 100, 50, commands[1]);
		
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		//SlickSKR.PlayMusic("other/public/intro.ogg");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		if (screenCache == null || SlickGameState.needFlush()){
			g.drawImage(label, 0, 0);
			for (SlickRectangle rect : rects){
				rect.paint(g);
			}
			SlickGameState.capture(g);
		}else{
			g.drawImage(screenCache, 0, 0);
		}
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				process(rect.getTag());
				break;
			}
		}
	}
	
	private void process(String tag){
		if (tag.equals(commands[0])){
			parent.swapView(SlickSKR.MAINMENU);
		}else if (tag.equals(commands[1])){
			System.exit(0);
		}
	}

}
