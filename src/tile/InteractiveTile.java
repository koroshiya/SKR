package tile;

import screen.GameScreen;
import slickgamestate.SlickGameState;
import interfaces.InteractableObject;

public abstract class InteractiveTile extends Tile implements InteractableObject {
	
	public InteractiveTile(String sprite, int x, int y, boolean fore) {
		super(false, sprite, x, y, fore);
	}
	
	@Override
	public void finishInteraction(GameScreen parent){
		SlickGameState.setFlush(true, true);
	}
	
}
