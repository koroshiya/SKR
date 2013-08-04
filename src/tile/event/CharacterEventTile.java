package tile.event;

import org.newdawn.slick.SlickException;

import screen.GameScreen;
import tile.CharacterTile;

import character.NonPlayableCharacter;

public class CharacterEventTile extends CharacterTile{
	
	private final int state;

	public CharacterEventTile(String spritePath, NonPlayableCharacter npc, int state) throws SlickException {
		super(spritePath, npc);
		this.state = state;
	}
	
	@Override
	public void interact(GameScreen parent) {
		super.interact(parent);
	}
	
	@Override
	public void finishInteraction(GameScreen parent){
		parent.swapView(state);
	}

}
