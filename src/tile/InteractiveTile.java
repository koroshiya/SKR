package tile;

import screen.GameScreen;
import interfaces.InteractableObject;

public abstract class InteractiveTile extends Tile implements InteractableObject {
	
	public InteractiveTile(String sprite) {
		super(false, false, sprite);
	}
	
	@Override
	public void finishInteraction(GameScreen parent){}
	
}
