package map;

import java.util.ArrayList;

import screen.GameScreen;

import com.japanzai.skr.Dialogue;
import com.japanzai.skr.Inventory;
import com.japanzai.skr.Line;

import interfaces.InteractableObject;
import item.Item;

public class ChestTile extends InteractiveTile implements InteractableObject {

	//TODO: make subclass dynamicTile; class that takes two or more string destination params to swap between
	
	private boolean open;
	private ArrayList<Item> treasure;
	private String sprite;
	private String closed;
	private String background;

	public ChestTile(String chestClosed, String chestOpen, String grass, ArrayList<Item> treasure) {
		
		super(chestClosed);
		this.open = false;
		this.sprite = chestOpen;
		this.closed = chestClosed;
		this.background = grass;
		this.treasure = treasure;
		
	}

	@Override
	public void interact(GameScreen parent) {
		
		Dialogue d;
		
		if (!this.open){
			
			StringBuffer itemList = new StringBuffer("<html>Obtained:");
			for (Item item : treasure){
				Inventory.addItem(item);
				itemList.append("<br />-" + item.getName());
			}
			itemList.append("</html>");
			this.setSprite(sprite);
			this.open = true;
			//this.updateUI(); //TODO: Update necessary?

			Line line = new Line(this, itemList.toString(), 0, false);
			d = new Dialogue(line);
			
		}else {
			Line line = new Line(this, "The chest is empty...", 0, false);
			d = new Dialogue(line);			
		}
		
		parent.WriteOnMap(d);
		
	}
	
	public ChestTile create(){
		return new ChestTile(this.closed, this.sprite, this.background, this.treasure);
	}

	
}
