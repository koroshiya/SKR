package slickgamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;

import controls.SlickRectangle;
import screen.GameScreen;

public class StartScreen extends SlickGameState{
	
	private Image backgroundImage;
	private SlickRectangle[] rects;
	private final String commands[];
	private final int buttonWidth = 200;
	private final int buttonHeight = 50;
	private final int[] keys = {Input.KEY_C, Input.KEY_N, Input.KEY_L, Input.KEY_M};
	
	public StartScreen(GameScreen parent) {
		super(SlickSKR.MAINMENU, parent);
		commands = new String[]{
			SlickSKR.getValueFromKey("screen.start.controls.continue"),
			SlickSKR.getValueFromKey("screen.start.controls.newgame"),
			SlickSKR.getValueFromKey("screen.start.controls.load"),
			SlickSKR.getValueFromKey("screen.start.controls.controls")
		};
	}
	
	private void processMenuItem(String s){
		if (s.equals(commands[0])){
			System.out.println("Continue");
		}else if (s.equals(commands[1])){
			this.parent.swapView(SlickSKR.MAP);
		}else if (s.equals(commands[2])){
			this.parent.swapView(SlickSKR.LOAD);
		}else if (s.equals(commands[3])){
			this.parent.swapView(SlickSKR.CONTROLSCREEN);
		}
	}

	@Override
	public void processMouseClick(int clickCount, int x, int y) {
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
		//SoundStore.get().setMusicOn(false);
		//SoundStore.get().setSoundsOn(false);
		SoundStore.get().setMusicVolume(0.3f);
		SoundStore.get().setSoundVolume(0.3f);
		//gc.setMusicOn(false);
		SlickSKR.PlayMusic("other/public/intro.ogg");
		SlickGameState.setFlush(true, false);
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		backgroundImage = new Image("/res/start.png");
		rects = new SlickRectangle[commands.length];
		for (int i = 0; i < commands.length; i++){
			rects[i] = new SlickRectangle(300, 150 + (i * 100), buttonWidth, buttonHeight, commands[i], "/res/button_onyx_200x50.png");
			rects[i].initialize();
		}
		SlickGameState.initCache(arg0);
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		if (SlickGameState.needFlush()){
			//g.drawImage(backgroundImage, 0, 0);
			g.drawImage(backgroundImage, 0, 0, 0, 0, 1920, 1200);
			
			for (int i = 0; i < commands.length; i++){
				g.drawImage(rects[i].getCache(), rects[i].getMinX(), rects[i].getMinY());
				rects[i].paintCenter(g, true);
			}
			SlickGameState.capture(g);
		}else{
			SlickGameState.drawCache(g);
		}
		
	}
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		processMouseClick(arg3, arg1, arg2);
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		
		int i = -1;
		int total = commands.length;
		
		while (++i < total){
			if (code == keys[i]){
				SlickSKR.PlaySFX("other/public/intro_button.ogg");
				this.processMenuItem(commands[i]);
			}
		}
	}

}
