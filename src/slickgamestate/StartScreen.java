package slickgamestate;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import controls.SlickRectangle;

import screen.GameScreen;

public class StartScreen extends SlickGameState{
	
	private Image backgroundImage;
	private SlickRectangle[] rects;
	private String commands[] = {"Continue", "New Game", "Load", "Controls"};
	private final int buttonWidth = 300;
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
			//Driver.load();
			//processMenuItem(commands[1]);
		}else if (s.equals(commands[3])){
			//this.parent.swapView(SlickSKR.CONTROLSCREEN); //TODO: implement
			System.out.println("Controls");
		}
	}

	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				this.processMenuItem(rect.getTag());
				break;
			}
		}
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		//TODO: init background image
		backgroundImage = new Image("/res/gameover.png");
		rects = new SlickRectangle[commands.length];
		for (int i = 0; i < commands.length; i++){
			rects[i] = new SlickRectangle(250, 200 + (i * 100), buttonWidth, buttonHeight, commands[i]);
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		g.drawImage(backgroundImage, 0, 0);
		
		int textx;
		int texty;
		String s;
		
		for (int i = 0; i < commands.length; i++){
			s = commands[i];
			textx = buttonWidth - g.getFont().getWidth(s);
			texty = 200 + g.getFont().getHeight(s);
			g.drawString(s, 250 + (textx / 2), texty + (i * 100));
			g.draw(rects[i]);
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
