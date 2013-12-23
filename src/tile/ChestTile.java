package tile;

import java.util.ArrayList;

import screen.GameScreen;
import slickgamestate.SlickSKR;

import com.japanzai.skr.Inventory;

import console.dialogue.Dialogue;
import console.dialogue.Line;

import interfaces.InteractableObject;
import item.Item;

public class ChestTile extends InteractiveTile implements InteractableObject {

	//TODO: make subclass dynamicTile; class that takes two or more string destination params to swap between
	
	private boolean open;
	private ArrayList<Item> treasure;
	private String sprite;
	private String closed;

	public ChestTile(String chestClosed, String chestOpen, ArrayList<Item> treasure, int x, int y, boolean fore) {
		
		super(chestClosed, x, y, fore);
		this.open = false;
		this.sprite = chestOpen;
		this.closed = chestClosed;
		this.treasure = treasure;
		
	}

	@Override
	public void interact(GameScreen parent) {
		
		Dialogue d;
		
		if (!this.open){

			SlickSKR.PlaySFX("other/public/chest-opening.ogg");
			ArrayList<String> itemList = new ArrayList<String>();
			itemList.add("Obtained:");
			for (Item item : treasure){
				Inventory.addItem(item);
				itemList.add("-" + item.getName());
			}
			this.setSprite(sprite);
			this.open = true;

			d = new Dialogue(itemList, this);
			
		}else {
			Line line = new Line(this, "The chest is empty...", 0, false);
			d = new Dialogue(line);			
		}
		
		parent.WriteOnMap(d, this);
		
	}
	
	public ChestTile create(int x, int y){
		return new ChestTile(this.closed, this.sprite, this.treasure, x, y, super.isFore());
	}

	
}
