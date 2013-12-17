package slickgamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controls.SlickCache;

import screen.GameScreen;
import interfaces.SlickEventHandler;

public abstract class SlickGameState extends BasicGameState implements SlickEventHandler {
	
	protected final int state;
	protected final GameScreen parent;
	private static SlickCache screenCache;
	
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
	
	public static void initCache(GameContainer arg0){
		try {
			screenCache = new SlickCache(0, 0, arg0.getWidth(),arg0.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean needFlush(){
		return screenCache.needFlush();
	}
	
	public static void setFlush(boolean needed, boolean map){
		if (map){
			MapScreen.mapCache.setFlush(needed);
		}else{
			screenCache.setFlush(needed);
		}
	}
	
	public static void capture(Graphics g){
		g.copyArea(screenCache, 0, 0);
		SlickGameState.setFlush(false, false);
	}
	
	public static void drawCache(Graphics g){
		g.drawImage(screenCache, 0, 0);
	}
	
	public static void darken(){
		screenCache.setColor(0, 155, 155, 155);
	}
		
}
