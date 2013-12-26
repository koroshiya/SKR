package slickgamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controls.SlickImageCache;
import controls.SlickImageRectangle;
import controls.SlickRectCache;
import screen.GameScreen;
import interfaces.SlickEventHandler;

public abstract class SlickGameState extends BasicGameState implements SlickEventHandler {
	
	protected final int state;
	protected final GameScreen parent;
	private static SlickImageCache screenCache;
	private static SlickRectCache rectCache;
	
	public SlickGameState(int state, GameScreen parent){
		this.state = state;
		this.parent = parent;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {}
	
	@Override
	public int getID() {return state;}
	
	public void checkCursor(GameContainer arg0, SlickImageRectangle[] rects){
		if (!arg0.getInput().isMouseButtonDown(0)){
			boolean changeOccurred = false;
			for (SlickImageRectangle rect : rects){
				if (rect.isWithinBounds(arg0.getInput().getMouseX(), arg0.getInput().getMouseY())){
					if (rect.setHighlighted(true)){
						changeOccurred = true;
					}
				}else{
					if (rect.setHighlighted(false)){
						changeOccurred = true;
					}
				}
			}
			setRectFlush(changeOccurred);
		}
	}
	
	public void checkCursor(GameContainer arg0, SlickImageRectangle[][] rects2){
		if (!arg0.getInput().isMouseButtonDown(0)){
			boolean changeOccurred = false;
			for (SlickImageRectangle[] rects : rects2){
				for (SlickImageRectangle rect : rects){
					if (rect.isWithinBounds(arg0.getInput().getMouseX(), arg0.getInput().getMouseY())){
						if (rect.setHighlighted(true)){
							changeOccurred = true;
						}
					}else{
						if (rect.setHighlighted(false)){
							changeOccurred = true;
						}
					}
				}
			}
			setRectFlush(changeOccurred);
		}
	}
	
	public static void initCache(GameContainer arg0){
		try {
			screenCache = new SlickImageCache(0, 0, arg0.getWidth(),arg0.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		rectCache = new SlickRectCache(0, 0, arg0.getWidth(), arg0.getHeight());
	}
	
	public static boolean needFlush(){
		return screenCache.needFlush();
	}
	
	public static boolean needRectFlush(){
		return rectCache.needFlush();
	}
	
	public static void setFlush(boolean needed, boolean map){
		if (map){
			MapScreen.mapCache.setFlush(needed);
			MapScreen.fgCache.setFlush(needed);
		}else{
			screenCache.setFlush(needed);
		}
	}
	
	public static void setRectFlush(boolean needed){
		rectCache.setFlush(needed);
	}
	
	public static void capture(Graphics g){
		screenCache.copyArea(g);
	}
	
	public static void captureRect(SlickImageRectangle[] rects){
		rectCache.setRects(rects);
		SlickGameState.setRectFlush(false);
	}
	
	public static void drawCache(Graphics g){
		screenCache.draw(g);
	}
	
	public static void drawRectCache(Graphics g){
		rectCache.draw(g);
	}
	
	public static void darken(){
		screenCache.darken();
	}
		
}
