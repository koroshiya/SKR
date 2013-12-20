package slickgamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import controls.SlickBlankRectangle;
import controls.SlickImageRectangle;
import controls.SlickRectangle;
import screen.GameScreen;

public class StartScreen extends SlickGameState{
	
	private SlickRectangle[] rects;
	private final String commands[];
	private final int buttonWidth = 200;
	private final int buttonHeight = 50;
	private final int[] keys = {Input.KEY_C, Input.KEY_N, Input.KEY_L, Input.KEY_M, Input.KEY_S};
	
	public StartScreen(GameScreen parent) {
		super(SlickSKR.MAINMENU, parent);
		commands = new String[]{
			SlickSKR.getValueFromKey("screen.start.controls.continue"),
			SlickSKR.getValueFromKey("screen.start.controls.newgame"),
			SlickSKR.getValueFromKey("screen.start.controls.load"),
			SlickSKR.getValueFromKey("screen.start.controls.controls"),
			SlickSKR.getValueFromKey("screen.start.controls.settings")
		};
	}
	
	private void processMenuItem(String s){
		if (s.equals(commands[0])){
			Log.debug("Continue");
		}else if (s.equals(commands[1])){
			this.parent.swapView(SlickSKR.MAP);
		}else if (s.equals(commands[2])){
			this.parent.swapView(SlickSKR.LOAD);
		}else if (s.equals(commands[3])){
			this.parent.swapView(SlickSKR.CONTROLSCREEN);
		}else if (s.equals(commands[4])){
			//this.parent.swapView(SlickSKR.CONTROLSCREEN);
			//TODO: implement Settings screen
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
	public void init(GameContainer arg0, StateBasedGame arg1){
		int i = -1;
		int total = commands.length;
		rects = new SlickRectangle[total];
		int incY = buttonHeight + 4;
		int startY = 616 - buttonHeight - ((total - 1) * incY);
		while (++i < total){
			rects[i] = new SlickImageRectangle(10, startY + (i * incY), buttonWidth, buttonHeight, commands[i], "/res/buttons/4x1/onyx.png", true);
			rects[i].initialize();
		}
		SlickGameState.initCache(arg0);
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2){
		checkCursor(arg0, rects);
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
		
		if (SlickGameState.needFlush()){
			GradientFill fill = new GradientFill(0, 0, Color.gray, arg0.getWidth(), arg0.getHeight(), Color.white);
			g.fill(new SlickBlankRectangle(0, 0, arg0.getWidth(), arg0.getHeight(), "", true),  fill);
			g.setFont(SlickSKR.getFont(18, false));
			for (int i = 0; i < commands.length; i++){
				rects[i].paintCache(g);
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
