package tile.event;

import item.StoreInstance;

import org.newdawn.slick.SlickException;

import screen.GameScreen;
import slickgamestate.SlickSKR;
import slickgamestate.Store;
import character.NonPlayableCharacter;

public class CharacterEventStoreTile extends CharacterEventTile{
	
	private final StoreInstance si;
	
	public CharacterEventStoreTile(String spritePath, NonPlayableCharacter npc, StoreInstance store) throws SlickException {

		super(spritePath, npc, SlickSKR.STORE);
		this.si = store;
		
	}
	
	@Override
	public void interact(GameScreen parent) {
		Store store = ((Store)parent.getState(SlickSKR.STORE));
		store.setStore(this.si);
		super.interact(parent);
	}

}
