package slickgamestate.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.SaveState;

import controls.SlickBlankRectangle;
import screen.GameScreen;
import slickgamestate.SlickSKR;

public class Save extends StateTemplate {
	
	private SlickBlankRectangle newSave;
	
	public Save(GameScreen parent) throws SlickException{
		
		super(SlickSKR.SAVE, parent);
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		super.init(arg0, arg1);
		newSave = new SlickBlankRectangle(600,100, 100, 100, "New Save");
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		//SlickSKR.PlayMusic("other/public/intro.ogg");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
		super.render(arg0, arg1, g);
		newSave.paintCenter(g);
		//TODO: display rect for new save file
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y){
		for (SlickBlankRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				SaveState.save(rect.getTag());
				//TODO: render alert on timer
				return;
			}
		}
		if (newSave.isWithinBounds(x,y)){
			//TODO: create new save file
		}else{
			super.processMouseClick(clickCount, x, y);
		}
	}

}
