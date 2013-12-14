package slickgamestate.menu;

import item.Item;
import item.StoreInstance;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.Inventory;

import controls.SlickRectangle;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;

public class Store extends ItemWindowBase{
	
	private StoreInstance store;
	private boolean boolInventory = true;
	
	public Store(GameScreen parent) {
		super(SlickSKR.STORE, parent, new String[]{
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.allitems"), 
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.consumables"),
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.weapons"),
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.misc"),
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.backtomap"),
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.inventory"),
				SlickSKR.getValueFromKey("screen.mainmenu.store.commands.store")}
		);
		
		filterItems = new SlickRectangle[commands.length];
		resetFilter(commands.length - 2);
		
		final float filterBaseY = 50;
		final float paneWidth = 150;
		
		for (int i = commands.length - 2; i < commands.length; i++){
			filterItems[i] = new SlickRectangle(paneWidth * (i % 2), 0, paneWidth, filterBaseY, commands[i]);
		}
		
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) throws SlickException {

		super.enter(arg0, arg1);
		store.initialize();
		
	}
	
	public void setItem(String itemName){
		setItem(store.getItem(itemName));
	}
	
	public void setItem(int itemIndex){
		setItem(store.getItem(itemIndex));
	}
	
	public void setStore(StoreInstance store){this.store = store;}
	
	private void process(String tag){
		if (tag.startsWith("BUY")){
			
		}else if (tag.startsWith("SELL")){
			
		}else if (tag.startsWith("SELECT")){
			
		}
	}
	
	public void extraPane(Graphics g){} //TODO
	
	private void selectItem(Item i){}
	
	private void sellItem(Item i, int quantity){}
	
	private void buyItem(Item i, int quantity){}
	
	protected int getFilterY(int filterBaseY, int i){
		return filterBaseY + filterBaseY * i;
	}

	public void setFilter(String filter){
		
		System.out.println("Filter: " + filter);
		
		if (filter.equals(commands[0])){
			results = boolInventory ? Inventory.getItemsInInventory() : store.getItemsInInventory();
		}else if (filter.equals(commands[1])){
			results = boolInventory ? Inventory.getConsumablesAsItems() : store.getConsumablesAsItems();
		}else if (filter.equals(commands[2])){
			results = boolInventory ? Inventory.getWeaponsAsItems() : store.getWeaponsAsItems();
		}else if (filter.equals(commands[3])){
			results = boolInventory ? Inventory.getMiscAsItems() : store.getMiscAsItems();
		}else if (filter.equals(commands[4])){
			this.parent.swapView(SlickSKR.MAP);
			return;
		}else if (filter.equals(commands[5])){
			boolInventory = true;
			setFilter(commands[0]);
			return;
		}else if (filter.equals(commands[6])){
			boolInventory = false;
			setFilter(commands[0]);
			return;
		}else {
			System.out.println("Error: This code should never be reached.");
			System.out.println("Unhandled filter type: " + filter);
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
