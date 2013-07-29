package interfaces;

import org.newdawn.slick.SlickException;

import screen.GameScreen;

public interface InteractableObject {

	public void interact(GameScreen parent) throws SlickException;
	
}
