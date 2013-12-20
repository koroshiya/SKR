package tile.event;

import item.StoreInstance;

import screen.GameScreen;
import slickgamestate.SlickSKR;
import slickgamestate.menu.Store;
import character.NonPlayableCharacter;

public class CharacterEventStoreTile extends CharacterEventTile{
	
	private final StoreInstance si;
	
	public CharacterEventStoreTile(String spritePath, NonPlayableCharacter npc, StoreInstance store, int x, int y) {

		super(spritePath, npc, SlickSKR.STORE, x, y);
		this.si = store;
		
	}
	
	@Override
	public void interact(GameScreen parent) {
		Store store = ((Store)parent.getState(SlickSKR.STORE));
		store.setStore(this.si);
		super.interact(parent);
	}

}
