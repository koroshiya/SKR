package slickgamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;

import interfaces.SlickEventHandler;

public abstract class SlickGameState extends BasicGameState implements SlickEventHandler {
	
	protected final int state;
	protected final GameScreen parent;
	
	public SlickGameState(int state, GameScreen parent){
		this.state = state;
		this.parent = parent;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {}
	
	@Override
	public int getID() {return state;}
		
}
