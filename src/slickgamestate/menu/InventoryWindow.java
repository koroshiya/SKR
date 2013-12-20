package slickgamestate.menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import screen.GameScreen;
import slickgamestate.SlickSKR;

import com.japanzai.skr.Inventory;

import controls.SlickImageRectangle;

public class InventoryWindow extends ItemWindowBase {
	
	public InventoryWindow(GameScreen parent){
		
		super(SlickSKR.INVENTORY, parent, new String[]{
			SlickSKR.getValueFromKey("screen.mainmenu.store.commands.allitems"), 
			SlickSKR.getValueFromKey("screen.mainmenu.store.commands.consumables"),
			SlickSKR.getValueFromKey("screen.mainmenu.store.commands.weapons"),
			SlickSKR.getValueFromKey("screen.mainmenu.store.commands.misc"),
			SlickSKR.getValueFromKey("screen.mainmenu.store.commands.backtomap"),
		});
		
		filterItems = new SlickImageRectangle[commands.length];
		resetFilter(commands.length);
		
	}
	
	public void extraPane(Graphics g){} //TODO
	
	protected int getFilterY(int filterBaseY, int i){
		return filterBaseY * i;
	}
	
	public void setItem(String itemName){
		setItem(Inventory.getItemByName(itemName));
	}
	
	public void setItem(int itemIndex){
		setItem(Inventory.getItemByIndex(itemIndex));
	}
	
	public void setFilter(String filter){
		
		if (filter.equals(commands[0])){
			results = Inventory.getItemsInInventory();
		}else if (filter.equals(commands[1])){
			results = Inventory.getConsumablesAsItems();
		}else if (filter.equals(commands[2])){
			results = Inventory.getWeaponsAsItems();
		}else if (filter.equals(commands[3])){
			results = Inventory.getMiscAsItems();
		}else if (filter.equals(commands[4])){
			this.parent.swapView(SlickSKR.MENU);
			return;
		}else {
			Log.error("Error: This code should never be reached.");
			Log.error("Unhandled filter type: " + filter);
			return;
		}
		
		setInventoryFilter();
		
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		if (!keyReleased(code)){
			//TODO: commands unique to Store
		}
		
	}
	
}
