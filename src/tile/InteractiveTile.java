package tile;

import org.newdawn.slick.Graphics;

import screen.GameScreen;
import slickgamestate.SlickGameState;
import interfaces.InteractableObject;

public abstract class InteractiveTile extends Tile implements InteractableObject {
	
	public InteractiveTile(String sprite) {
		super(false, false, sprite);
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		g.drawImage(super.getCache(), x, y, null);
	}
	
	@Override
	public void finishInteraction(GameScreen parent){
		SlickGameState.flush();
	}
	
}
