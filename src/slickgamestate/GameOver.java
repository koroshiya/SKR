package slickgamestate;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import controls.SlickImageRectangle;
import screen.GameScreen;

public class GameOver extends SlickGameState {

	private Image label;
	private SlickImageRectangle[] rects;
	private final String[] commands;
	
	public GameOver(GameScreen parent) throws SlickException{
		
		super(SlickSKR.GAMEOVER, parent);
		commands = new String[]{
			SlickSKR.getValueFromKey("screen.gameover.commands.mainmenu"),
			SlickSKR.getValueFromKey("screen.gameover.commands.quit")
		};
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		label = new Image("/res/terrain/lab-dark.png");
		rects = new SlickImageRectangle[2];
		rects[0] = new SlickImageRectangle(300, 350, 100, 50, commands[0], "", true);
		rects[1] = new SlickImageRectangle(402, 350, 100, 50, commands[1], "", true);
		
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		SlickSKR.PlaySFX("other/public/game-over-evil.ogg");
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2){
		checkCursor(gc, rects);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
		if (SlickGameState.needFlush()){
			//g.drawImage(label, 0, 0);
			g.fillRect(0, 0, arg0.getWidth(), arg0.getHeight(), label, 0, 0);
			for (SlickImageRectangle rect : rects){
				rect.paint(g);
			}
			SlickGameState.capture(g);
		}else{
			SlickGameState.drawCache(g);
		}
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		for (SlickImageRectangle rect : rects){
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
