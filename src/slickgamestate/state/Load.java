package slickgamestate.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.japanzai.skr.SaveState;

import controls.SlickRectangle;
import screen.GameScreen;
import slickgamestate.SlickSKR;

public class Load extends StateTemplate {
	
	public Load(GameScreen parent) {
		super(SlickSKR.LOAD, parent);
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		//SlickSKR.PlayMusic("other/public/intro.ogg");
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) {
		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				if (SaveState.load(rect.getTag())){
					parent.swapView(SlickSKR.MAP);
				}else{
					Log.error("Load class - processMouseClick - Load failed");
				}
				return;
			}
		}
		super.processMouseClick(clickCount, x, y);
	}

}
