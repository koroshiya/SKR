package tile;

import org.newdawn.slick.SlickException;

import screen.GameScreen;

public class EventTile extends InteractiveTile {
	
	private final int state;
	
	public EventTile(String sprite, int state) {
		super(sprite);
		this.state = state;
	}

	@Override
	public void interact(GameScreen parent) throws SlickException {
		parent.swapView(state);
	}
	
}
