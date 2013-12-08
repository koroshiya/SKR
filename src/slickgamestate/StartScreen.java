package slickgamestate;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

//import com.japanzai.skr.Driver; //TODO: reimplement


import controls.SlickRectangle;
import screen.GameScreen;

public class StartScreen extends SlickGameState{
	
	private Image backgroundImage;
	private SlickRectangle[] rects;
	private String commands[] = {"Continue", "New Game", "Load", "Controls"};
	private final int buttonWidth = 200;
	private final int buttonHeight = 50;
	
	public StartScreen(GameScreen parent) {
		super(SlickSKR.MAINMENU, parent);
	}
	
	private void processMenuItem(String s) throws IOException, ClassNotFoundException{
		if (s.equals(commands[0])){
			System.out.println("Continue");
		}else if (s.equals(commands[1])){
			this.parent.swapView(SlickSKR.MAP);
		}else if (s.equals(commands[2])){
			System.out.println("Load");
			/*if (Driver.load()){
				processMenuItem(commands[1]);
			}*/ //TODO: reimplement
		}else if (s.equals(commands[3])){
			this.parent.swapView(SlickSKR.CONTROLSCREEN);
		}
	}

	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				SlickSKR.PlaySFX("other/public/intro_button.ogg");
				this.processMenuItem(rect.getTag());
				break;
			}
		}
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		SlickSKR.PlayMusic("other/public/intro.ogg");
		screenCache = null;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		backgroundImage = new Image("/res/start.png");
		rects = new SlickRectangle[commands.length];
		for (int i = 0; i < commands.length; i++){
			rects[i] = new SlickRectangle(300, 150 + (i * 100), buttonWidth, buttonHeight, commands[i], "/res/button_onyx_200x50.png");
			rects[i].initialize();
		}
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		if (screenCache == null){
			g.drawImage(backgroundImage, 0, 0);
			
			for (int i = 0; i < commands.length; i++){
				g.drawImage(rects[i].getCache(), rects[i].getMinX(), rects[i].getMinY());
				rects[i].paintCenter(g, true);
			}
			SlickGameState.capture(g);
		}else{
			g.drawImage(screenCache, 0, 0);
		}
		
	}
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		
		try {
			processMouseClick(arg3, arg1, arg2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
