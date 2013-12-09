package slickgamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;
import interfaces.SlickEventHandler;

public abstract class SlickGameState extends BasicGameState implements SlickEventHandler {
	
	protected final int state;
	protected final GameScreen parent;
	protected static Image screenCache;
	private static boolean toFlush = false;
	
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
	
	public static boolean needFlush(){
		return toFlush;
	}
	
	public static void setFlush(boolean needed){
		toFlush = needed;
	}
	
	public static void capture(Graphics g){
		g.copyArea(screenCache, 0, 0);
		SlickGameState.setFlush(false);
	}
		
}
