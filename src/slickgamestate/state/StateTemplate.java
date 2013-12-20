package slickgamestate.state;

import java.io.File;
import java.io.FilenameFilter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.SaveState;

import controls.SlickBlankRectangle;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;

public abstract class StateTemplate extends SlickGameState {

	private Image label;
	protected SlickBlankRectangle[] rects;
	private final String[] commands;
	private Image[] imageCache;
	private int back = SlickSKR.CONTROLSCREEN;
	private SlickBlankRectangle backRect = new SlickBlankRectangle(100, 100, 100, 100, "Back [W]");
	
	public StateTemplate(int id, GameScreen parent) {
		
		super(id, parent);
		File f = new File(SaveState.SAVE_DIRECTORY);
		String[] cList = f.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               if (name.lastIndexOf('.')>0){
                  return name.substring(name.lastIndexOf('.')).equals(".sks");
               }
               return false;
            }
        });
		if (cList != null){
			commands = cList;
		}else{
			commands = new String[0];
		}
		
	}
	
	/**
	 * Sets the ID of the SlickGameState to move back to when this Save/Load command is completed or canceled.
	 * 
	 * @param i Integer corresponding to an existing SlickGameState
	 * */
	public void setBack(int i){
		this.back = i;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		label = new Image("/res/terrain/lab-dark.png");
		int i = -1;
		int total = commands.length < 9 ? commands.length : 9;
		rects = new SlickBlankRectangle[total];
		while (++i < total){
			rects[i] = new SlickBlankRectangle(300, i * 50, 200, 50, commands[i]);
		}
		String[] strCache = SaveState.viewAvatars(arg0);
		total = strCache.length < 9 ? strCache.length : 9;
		Image[] cCache = new Image[total];
		i = -1;
		while (++i < total){
			try{
				String cache = strCache[i];
				cCache[i] = new Image(cache);
			}catch (SlickException ex){
				ex.printStackTrace();
			}catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		imageCache = cCache;
		
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		//SlickSKR.PlayMusic("other/public/intro.ogg");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
		if (SlickGameState.needFlush()){
			//g.drawImage(label, 0, 0);
			g.fillRect(0, 0, arg0.getWidth(), arg0.getHeight(), label, 0, 0);
			int i = -1;
			int total = rects.length;
			while (++i < total){
				rects[i].paintCenter(g);
				try{
					imageCache[i].draw(rects[i].getMinX() - 52, rects[i].getMinY(), 48, 48);
				}catch (NullPointerException ex){
					ex.printStackTrace();
				}
			}
			backRect.paintCenter(g);
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
	public void processMouseClick(int clickCount, int x, int y){
		if (backRect.isWithinBounds(x,y)){
			parent.swapView(this.back);
		}
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		if (code == Input.KEY_W){
			parent.swapView(this.back);
		}
	}

}
