package slickgamestate.menu;

import item.Item;
import item.StoreInstance;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.japanzai.skr.Inventory;

import controls.SlickBlankRectangle;
import controls.SlickImageRectangle;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;

public class Store extends ItemWindowBase{
	
	private StoreInstance store;
	private boolean boolInventory = true;
	private int transQuantity = 0;
	private boolean buyMode = true;
	private SlickImageRectangle[] tempPrompts = null;
	private int timer = 0;
	private SlickBlankRectangle message = new SlickBlankRectangle(150, 150, 500, 300, "", false);
	
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
		
		filterItems = new SlickImageRectangle[commands.length];
		resetFilter(commands.length - 2);
		
		final float filterBaseY = 50;
		final float paneWidth = 149;
		
		int i = commands.length - 3;
		int total = commands.length;
		while (++i < total){
			filterItems[i] = new SlickImageRectangle(paneWidth * (i % 2), 0, paneWidth, filterBaseY, commands[i], true, "/res/buttons/btn_gray+border_500x58.png", true);
		}
		
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) throws SlickException {

		super.enter(arg0, arg1);
		store.initialize();
		this.setFilter(commands[6]);
		
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		if (timer > 0){
			timer--;
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g){
		
		super.render(gc, arg1, g);
		
		if (timer >= 0){
			if (timer == 0){
				SlickGameState.setFlush(true, false);
			}else{
				//TODO: display text and box around it
				message.paintCenter(g, false);
			}
		}
		
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
			buyMode = true;
		}else if (tag.startsWith("SELL")){
			buyMode = false;
		}else if (tag.startsWith("SELECT")){
			
		}
	}
	
	private void processSelection(String tag){
		if (tag == "Accept"){
			//TODO: purchase
		}else if (tag == "Cancel"){
			cancel();
		}else if (tag == "+"){
			//TODO: increase qty
		}else if (tag == "-"){
			//TODO: decrease qty
		}
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) {
		
		for (SlickImageRectangle s : filterItems){
			if (s.isWithinBounds(x, y)){
				this.setFilter(s.getTag());
				return;
			}
		}
		
		//TODO: check for Inventory/Store
		//TODO: check for item click
		
		for (SlickImageRectangle s : items){
			if (s.isWithinBounds(x, y)){
				setItem(results.get(Integer.parseInt(s.getTag())));
				return;
			}
		}
		
		if (tempPrompts != null){
			for (SlickImageRectangle rect : tempPrompts){
				if (rect.isWithinBounds(x, y)){
					processSelection(rect.getTag());
					return;
				}
			}
		}
		
	}
	
	public void extraPane(Graphics g){
		Font VICTORY_FONT = SlickSKR.loadFont("Ubuntu-B.ttf", 28);
		Font tFont = g.getFont();
		g.setFont(VICTORY_FONT);
		g.drawString(boolInventory ? "INVENTORY" : "STORE", 500, 5); //TODO: paint center //TODO: bg image
		g.setFont(tFont);
	} //TODO
	
	private void cancel(){
		tempPrompts = null;
		SlickGameState.setFlush(true, false);
	}
	
	private void selectItem(Item i, Graphics g){
		transQuantity = 0;
		//TODO: bring up dialogue
		SlickGameState.darken();
		tempPrompts = new SlickImageRectangle[]{ //TODO: set once and toggle visibility?
			new SlickImageRectangle(0, 0, 500f, 300f, "", "", false),
			new SlickImageRectangle(0, 0, 150f, 100f, "Accept", "", true),
			new SlickImageRectangle(0, 0, 150f, 100f, "Cancel", "", true),
			new SlickImageRectangle(0, 0, 50f, 50f, "+", "", true),
			new SlickImageRectangle(0, 0, 50f, 50f, "-", "", true),
			new SlickImageRectangle(0, 0, 50f, 50f, "0", "", false)
		};
		
	}
	
	private void sellItem(Item i, int quantity){
		if (Inventory.sellItem(store, i, transQuantity)){
			//TODO: message, success
			//TODO: sfx, coin exchange
			transQuantity = 0;
		}else{
			//TODO: message, cannot afford or not enough in stock
		}
	}
	
	private void buyItem(Item i){
		if (Inventory.buyItem(i, transQuantity)){
			transQuantity = 0;
		}else{
			//TODO: message, cannot afford or not enough in stock
		}
		
	}
	
	private void increaseQuantity(){
		transQuantity += 1;
	}
	
	protected int getFilterY(int filterBaseY, int i){
		return filterBaseY + filterBaseY * i;
	}

	public void setFilter(String filter){
		
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
