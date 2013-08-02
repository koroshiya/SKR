package interfaces;

import org.newdawn.slick.SlickException;

import screen.GameScreen;

public interface InteractableObject {

	public abstract void interact(GameScreen parent) throws SlickException;
	
	public abstract void finishInteraction(GameScreen parent);
	
}
