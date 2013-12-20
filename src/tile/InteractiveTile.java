package tile;

import org.newdawn.slick.Graphics;

import screen.GameScreen;
import slickgamestate.SlickGameState;
import interfaces.InteractableObject;

public abstract class InteractiveTile extends Tile implements InteractableObject {
	
	public InteractiveTile(String sprite, int x, int y) {
		super(false, sprite, x, y);
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		g.drawImage(cache, x, y, null);
	}
	
	@Override
	public void finishInteraction(GameScreen parent){
		SlickGameState.setFlush(true, true);
	}
	
}
