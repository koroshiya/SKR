package tile.event;

import screen.GameScreen;
import tile.InteractiveTile;

public class EventTile extends InteractiveTile {
	
	private final int state;
	
	public EventTile(String sprite, int state, int x, int y, boolean fore) {
		super(sprite, x, y, fore);
		this.state = state;
	}

	@Override
	public void interact(GameScreen parent) {
		parent.swapView(state);
	}
	
}
