package tile.event;

import screen.GameScreen;
import tile.CharacterTile;

import character.NonPlayableCharacter;

public class CharacterEventTile extends CharacterTile{
	
	private final int state;

	public CharacterEventTile(String spritePath, NonPlayableCharacter npc, int state, int x, int y) {
		super(spritePath, npc, x, y);
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
