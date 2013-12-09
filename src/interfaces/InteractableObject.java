package interfaces;

import screen.GameScreen;

public interface InteractableObject {

	public abstract void interact(GameScreen parent);
	
	public abstract void finishInteraction(GameScreen parent);
	
}
