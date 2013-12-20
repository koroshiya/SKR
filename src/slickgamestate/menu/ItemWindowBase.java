package slickgamestate.menu;

import item.ConsumableItem;
import item.Item;
import item.Weapon;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.Inventory;

import controls.SlickBlankRectangle;
import controls.SlickImageRectangle;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;

public abstract class ItemWindowBase extends SlickGameState{
	
	private String lblMoney;
	protected final String[] commands;
	
	protected SlickImageRectangle[] filterItems;

	protected ArrayList<Item> results = new ArrayList<Item>();
	private SlickBlankRectangle[] itemParams;
	
	private Item item;
	protected SlickImageRectangle[] items;
	private Image background;

	public ItemWindowBase(int state, GameScreen parent, String[] commands) {
		super(state, parent);
		this.commands = commands;
		
		final float x = 410;
		float y = 0;
		float optX = 0;
		float optY = 0;
		
		final int baseX = 200;
		final int baseY = 12;
		
		itemParams = new SlickBlankRectangle[7];
		int i = -1;
		int total = itemParams.length;
		while (++i < total){
			itemParams[i] = new SlickBlankRectangle(x + optX, y, 20, 15, "", false);

			optX = optX == 0 ? baseX : 0;
			y += optY;
			optY = optY == 0 ? baseY : 0;
		}
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		background = new Image("/res/terrain/refinery.png");
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) throws SlickException {

		setFilter(commands[0]);
		for (SlickImageRectangle filterItem : filterItems){
			filterItem.initialize();
		}
		setItem(0);
		lblMoney = SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.enter.funds") + 
					": " + Inventory.getMoney() + " " + SlickSKR.getValueFromKey("common.currency");
		Inventory.initialize();
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) {
		
		if (SlickGameState.needFlush()){
			//g.drawImage(background, 0, 0);
			g.fillRect(0, 0, gc.getWidth(), gc.getHeight(), background, 0, 0);
			
			getInventoryFilterPane(g);
			getInventorySelectedItem(g);
			getMoneyPane(g);
			displayResults(g);
			extraPane(g);
			SlickGameState.capture(g);
		}else{
			SlickGameState.drawCache(g);
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
		
		for (SlickImageRectangle s : items){
			if (s.isWithinBounds(x, y)){
				setItem(results.get(Integer.parseInt(s.getTag())));
				return;
			}
		}
		
	}
	
	protected abstract void setFilter(String filter);
	
	protected abstract int getFilterY(int baseY, int i);
	
	protected void resetFilter(int length){
		final int filterx = 0;
		float filtery;
		final int filterBaseY = 50;
		final int paneWidth = 300;
		
		int i = -1;
		while (++i < length){
			filtery = getFilterY(filterBaseY, i);
			filterItems[i] = new SlickImageRectangle(filterx, filtery, paneWidth, filterBaseY, commands[i], true, "/res/buttons/6x1/gray+border.png", true);
		}
	}
	
	protected void setInventoryFilter(){
		
		final int incX = 150;
		final int baseX = 335;
		final int incY = 50;
		final int baseY = 160;
		
		items = new SlickImageRectangle[results.size()];
		int i = -1;
		int total = results.size();
		if (total > 9){
			total = 9;
		}
		while (++i < total){
			items[i] = new SlickImageRectangle(baseX, baseY + incY * i, incX * 3, incY, Integer.toString(i), true, "/res/buttons/9x1/onyx.png", true);
			items[i].initialize();
		}
		
		if (total > 0){
			item = results.get(0);
		}
		
		SlickGameState.setFlush(true, false);
		
	}
	
	protected void getInventoryFilterPane(Graphics g){
		
		int i = -1;
		int total = filterItems.length;
		if (total > 9){
			total = 9;
		}
		while (++i < total){
			filterItems[i].paintCache(g);
			filterItems[i].paintCenter(g, true);
		}
			
	}

	protected void displayResults(Graphics g){

		g.drawString(SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.displayresults.name"), 350, 140); //TODO: SlickRectangle for headings
		g.drawString(SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.displayresults.quantity"), 500, 140);
		g.drawString(SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.displayresults.value"), 650, 140);
		
		final int incX = 150;
		final int baseX = 350;
		final int incY = 50;
		int baseY = 174;
		
		int i = -1;
		int total = items.length;
		SlickImageRectangle rect;
		Item item;
		if (total > 8){
			total = 8;
		}
		while (++i < total){
			rect = items[i];
			item = results.get(i);
			rect.paintCache(g);
			g.drawString(item.getName(), baseX, baseY);
			g.drawString(Integer.toString(item.getQuantity()), baseX + incX, baseY);
			g.drawString(Integer.toString(item.getValue()) + " " + 
						SlickSKR.getValueFromKey("common.currency"), baseX + incX * 2, baseY);
			baseY += incY;
		}
		
		if (results.size() > 0 && this.item == null){
			setItem(results.get(0));
		}
		
	}
	
	public abstract void extraPane(Graphics g);
	
	public void getMoneyPane(Graphics g){

		SlickImageRectangle stats = new SlickImageRectangle(0, 550, 300, 72, lblMoney, true, "/res/buttons/4x1/onyx.png", false);
		stats.initialize();
		stats.paintCache(g);//TODO: replace 150 with gc size scale
		stats.paintCenter(g, true);
		//g.drawString(lblMoney, x, y);
		
	}
	
	public void getInventorySelectedItem(Graphics g){
		
		float x = 435;
		float y = 40;
		float optX = 0;
		float optY = 0;
		
		int baseX = 200;
		int baseY = 16;
		
		//Rectangle avatar = new Rectangle(300, 0, 100f, 100f);
		//g.draw(avatar);
		SlickImageRectangle stats = new SlickImageRectangle(335, 30, 450, 100, "", false, "/res/buttons/9x2/onyx.png", false);
		stats.initialize();
		stats.paintCache(g);//TODO: replace 150 with gc size scale
		item.drawScaled(g, 352, 47, 64, 64);
		
		int i = -1;
		int total = itemParams.length;
		while (++i < total){
			g.drawString(itemParams[i].getDisplayText(), x + optX, y);

			optX = optX == 0 ? baseX : 0;
			y += optY;
			optY = optY == 0 ? baseY : 0;
		}
		
	}

	public abstract void setItem(String itemName);
	
	public abstract void setItem(int itemIndex);
	
	protected void setItem(Item i){
		
		this.item = i;
		itemParams[0].setText(SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.displayresults.name") + ": " + i.getName());		
		determineItemType(i);
		itemParams[2].setText(SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.displayresults.value") + ": " + Integer.toString(i.getValue()));
		itemParams[6].setText(SlickSKR.getValueFromKey("screen.mainmenu.itemwindowbase.setitem.nodescription"));
		//TODO: implement description parameter for items
		SlickGameState.setFlush(true, false);
	}
	
	private void determineItemType(Item i){
		
		String type;
		String strAttack;
		String strAccuracy;
		String strPotency;
		
		String na = SlickSKR.getValueFromKey("common.notavailable");
		if (i instanceof ConsumableItem){
			ConsumableItem c = (ConsumableItem) i;
			type = SlickSKR.getValueFromKey("item.type.consumable");
			strAttack = na;
			strAccuracy = na;
			strPotency = Integer.toString(c.getPotency());
		}else if (i instanceof Weapon){
			Weapon w = (Weapon) i;
			type = w.getTypeOfWeapon();
			strAttack = Integer.toString(w.getStrength());
			strAccuracy = Double.toString(Math.floor(w.getAccuracy() * 100)) + "%";
			strPotency = na;
		}else {
			type = SlickSKR.getValueFromKey("item.type.misc");
			strAttack = na;
			strAccuracy = na;
			strPotency = na;
		}
		
		itemParams[1].setText(SlickSKR.getValueFromKey("common.type") + ": " + type);
		itemParams[3].setText(SlickSKR.getValueFromKey("stat.strength") + ": " + strAttack);
		itemParams[4].setText(SlickSKR.getValueFromKey("stat.accuracy") + ": " + strAccuracy);
		itemParams[5].setText(SlickSKR.getValueFromKey("item.param.potency") + ": " + strPotency);
		
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		processMouseClick(1, x, y);
	}
	
	protected boolean keyReleased(int code){
		if (code == Input.KEY_A){
			this.setFilter(commands[0]);
		}else if (code == Input.KEY_C){
			this.setFilter(commands[1]);
		}else if (code == Input.KEY_W){
			this.setFilter(commands[2]);
		}else if (code == Input.KEY_M){
			this.setFilter(commands[3]);
		}else if (code == Input.KEY_B){
			this.setFilter(commands[4]);
		}else{
			return false;
		}
		return true;
	}
	
}
