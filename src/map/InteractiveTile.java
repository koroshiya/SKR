package map;

import interfaces.InteractableObject;

public abstract class InteractiveTile extends Tile implements InteractableObject {

	public InteractiveTile(String chestClosed) {
		super(false, false, chestClosed);
	}

}
