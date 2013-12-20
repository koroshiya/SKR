package tile;

import screen.GameScreen;

public class EventTile extends InteractiveTile {
	
	private final int state;
	
	public EventTile(String sprite, int state, int x, int y) {
		super(sprite, x, y);
		this.state = state;
	}

	@Override
	public void interact(GameScreen parent) {
		parent.swapView(state);
	}
	
}
