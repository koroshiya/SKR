package slickgamestate.menu;

import item.ConsumableItem;
import item.Item;
import item.Weapon;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.Inventory;

import controls.SlickRectangle;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;

public abstract class ItemWindowBase extends SlickGameState{
	
	private String lblMoney;
	private final Rectangle filterPane = new Rectangle(0, 0, 300f, 550f);
	protected final String[] commands;
	
	protected SlickRectangle[] filterItems;

	protected ArrayList<Item> results = new ArrayList<Item>();
	private SlickRectangle[] itemParams;
	
	private Item item;
	private SlickRectangle[] items;
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
		
		itemParams = new SlickRectangle[7];
		for (int i = 0; i < itemParams.length; i++){
			try {
				itemParams[i] = new SlickRectangle(x + optX, y, 20, 15, "");
			} catch (SlickException e) {
				e.printStackTrace();
			}

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
		setItem(0);
		lblMoney = "Funds: " + Inventory.getMoney() + " yen";
		Inventory.initialize();
		//SlickSKR.PlayMusic("other/public/");
		
	}
	
	
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {

		if (screenCache == null){
			g.drawImage(background, 0, 0);
			
			getInventoryFilterPane(g);
			getInventorySelectedItem(g);
			getMoneyPane(g);
			displayResults(g);
			SlickGameState.capture(g);
		}else{
			g.drawImage(screenCache, 0, 0);
		}
		
	}

	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		for (SlickRectangle s : filterItems){
			if (s.isWithinBounds(x, y)){
				this.setFilter(s.getTag());
				break;
			}
		}
		
		for (SlickRectangle s : items){
			if (s.isWithinBounds(x, y)){
				setItem(results.get(Integer.parseInt(s.getTag())));
				break;
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
		
		for (int i = 0; i < length; i++){
			filtery = getFilterY(filterBaseY, i);
			try {
				filterItems[i] = new SlickRectangle(filterx, filtery, paneWidth, filterBaseY, commands[i]);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void setInventoryFilter(){
		
		final int incX = 150;
		final int baseX = 300;
		final int incY = 15;
		final int baseY = 130;
		
		items = new SlickRectangle[results.size()];
		for (int i = 0; i < results.size(); i++){
			try {
				items[i] = new SlickRectangle(baseX, baseY + incY * i, incX, incY, Integer.toString(i));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	protected void getInventoryFilterPane(Graphics g){
		
		g.draw(filterPane);
		
		for (int i = 0; i < filterItems.length; i++){
			filterItems[i].paintCenter(g);
		}
			
	}

	protected void displayResults(Graphics g){

		g.drawString("Name", 300, 100);
		g.drawString("Quantity", 450, 100);
		g.drawString("Value", 600, 100);
		
		final int incX = 150;
		final int baseX = 300;
		final int incY = 15;
		int baseY = 130;
		
		for (Item i : results){

			g.drawString(i.getName(), baseX, baseY);
			g.drawString(Integer.toString(i.getQuantity()), baseX + incX, baseY);
			g.drawString(Integer.toString(i.getValue()) + " yen", baseX + incX * 2, baseY);
			baseY += incY;			
			
		}
		
		if (results.size() > 0 && this.item == null){
			setItem(results.get(0));
		}
		
	}
	
	public void getMoneyPane(Graphics g){
		
		final float x = 0f;
		final float y = 550f;
		
		final float maxX = 300f;
		final float maxY = 600f;
		
		Rectangle pane = new Rectangle(x, y, maxX, maxY);
		g.draw(pane);
		
		g.drawString(lblMoney, x, y);
		
	}
	
	public void getInventorySelectedItem(Graphics g){
		
		float x = 410;
		float y = 0;
		float optX = 0;
		float optY = 0;
		
		int baseX = 200;
		int baseY = 12;
		
		Rectangle avatar = new Rectangle(300, 0, 100f, 100f);
		g.draw(avatar);
		g.drawImage(item.getCache(), 300 + 2, 2);
		
		for (int i = 0; i < itemParams.length; i++){
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
		itemParams[0].setText("Name: " + i.getName());		
		determineItemType(i);
		itemParams[2].setText("Value: " + Integer.toString(i.getValue()));
		itemParams[6].setText("No description available"); //TODO: implement description parameter for items
		
	}
	
	private void determineItemType(Item i){
		
		String type;
		String strAttack;
		String strAccuracy;
		String strPotency;
		
		if (i instanceof ConsumableItem){
			ConsumableItem c = (ConsumableItem) i;
			type = "Consumable";
			strAttack = "N/A";
			strAccuracy = "N/A";
			strPotency = Integer.toString(c.getPotency());
		}else if (i instanceof Weapon){
			Weapon w = (Weapon) i;
			type = w.getTypeOfWeapon();
			strAttack = Integer.toString(w.getStrength());
			strAccuracy = Double.toString(Math.floor(w.getAccuracy() * 100)) + "%";
			strPotency = "N/A";
		}else {
			type = "Miscellaneous";
			strAttack = "N/A";
			strAccuracy = "N/A";
			strPotency = "N/A";
		}
		
		itemParams[1].setText("Type: " + type);
		itemParams[3].setText("Strength: " + strAttack);
		itemParams[4].setText("Accuracy: " + strAccuracy);
		itemParams[5].setText("Potency: " + strPotency);
		
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		
		try {
			processMouseClick(1, x, y);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
