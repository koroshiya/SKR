package menu.inventory;

import java.util.ArrayList;

import item.ConsumableItem;
import item.Item;
import item.Weapon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;

import screen.GameScreen;

import com.japanzai.skr.Inventory;

public class InventoryWindow extends BasicGameState {

	private String lblName;
	private String lblType;
	private String lblValue;
	private String lblAttack;
	private String lblAccuracy;
	private String lblPotency;
	private String lblDescription;
	private String lblMoney;

	private ArrayList<Item> results = new ArrayList<Item>();

	public static final String ALL = "All Items";
	public static final String CONSUMABLES = "Consumables";
	public static final String WEAPONS = "Weapons";
	public static final String MISC = "Miscellaneous";
	public static final int MENU_ITEM_HEIGHT = 50;
	public static final Object[][] MENU_ITEMS = {{ALL, 1 * MENU_ITEM_HEIGHT},
													{CONSUMABLES, 2 * MENU_ITEM_HEIGHT}, 
													{WEAPONS, 3 * MENU_ITEM_HEIGHT},
													{MISC, 4 * MENU_ITEM_HEIGHT}};
	
	
	private final int state;
	private Item item;
	private GameScreen parent;
	
	public InventoryWindow(int state, GameScreen parent){
		
		this.state = state;
		this.parent = parent;
		setFilter(ALL);
		lblMoney = "Funds: " + Inventory.getMoney() + " yen";
		
	}
	
	public void setFilter(String filter){
		
		if (filter.equals(ALL)){
			results = Inventory.getItemsInInventory();
		}else if (filter.equals(CONSUMABLES)){
			results = Inventory.getConsumablesAsItems();
		}else if (filter.equals(WEAPONS)){
			results = Inventory.getWeaponsAsItems();
		}else if (filter.equals(MISC)){
			results = Inventory.getMiscAsItems();
		}else {
			System.out.println("Error: This code should never be reached.");
			System.out.println("Unhandled filter type: " + filter);
		}
		
	}
	
	
	//TODO: setFilter
	private void getInventoryFilterPane(Graphics g){
		
		//Dimension size = new Dimension(300, 550);
	
		float x = 0;
		float y = 0;
					
		int textx;		
		int texty;
		
		Rectangle pane = new Rectangle(0, 0, 300f, 550f);
		g.draw(pane);
		
		String s = "";
		for (int i = 0; i < MENU_ITEMS.length; i++){
			s = (String) MENU_ITEMS[i][0];
			Rectangle rect = new Rectangle(x, y, 300f, 50f);
			g.draw(rect);
			textx = g.getFont().getWidth(s);
			texty = g.getFont().getHeight(s);
			g.drawString(s, x + (300 - textx) / 2, y + texty);

			y += MENU_ITEM_HEIGHT;
		}
			
	}
	
	private void displayResults(Graphics g){

		g.drawString("Name", 300, 100);
		g.drawString("Quantity", 450, 100);
		g.drawString("Value", 600, 100);
				
		int incX = 150;
		int baseX = 300;
		int incY = 15;
		int baseY = 130;
		
		for (Item i : results){

			g.drawString(i.getName(), baseX, baseY);
			g.drawString(Integer.toString(i.getQuantity()), baseX + incX, baseY);
			g.drawString(Integer.toString(i.getValue()) + " yen", baseX + incX * 2, baseY);
			baseY += incY;			
			
		}
		
		if (results.size() > 0){
			setItem(results.get(0));
		}
		
	}
	
	public void getMoneyPane(Graphics g){
		
		float x = 0f;
		float y = 550f;
		
		float maxX = 300f;
		float maxY = 600f;
		
		Rectangle pane = new Rectangle(x, y, maxX, maxY);
		g.draw(pane);
		
		g.drawString(lblMoney, x, y);
		
	}
	
	public void getInventorySelectedItem(Graphics g){
		
		//500, 100
		
		float x = 410;
		float y = 0;
		float optX = 0;
		float optY = 0;
		
		int baseX = 200;
		int baseY = 12;

		Rectangle avatar = new Rectangle(300, 0, 100f, 100f);
		g.draw(avatar);
		g.drawImage(item.getCache(), 300 + 2, 2);
		
		String[] labels = {lblName, lblType, lblValue, lblAttack, lblAccuracy, lblPotency, lblDescription};
		String s = "";
		for (int i = 0; i < labels.length; i++){
			s = labels[i];
			//Rectangle rect = new Rectangle(x, y, 250f, baseX);
			//g.draw(rect);
			g.drawString(s, x + optX, y);

			optX = optX == 0 ? baseX : 0;
			y += optY;
			optY = optY == 0 ? baseY : 0;
		}
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		setItem(0);
		Inventory.initialize();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		getInventoryFilterPane(g);
		getInventorySelectedItem(g);
		getMoneyPane(g);
		displayResults(g);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return this.state;
	}
	

	public void setItem(String itemName){
		
		setItem(Inventory.getItemByName(itemName));
		
	}
	
	public void setItem(int itemIndex){
		
		setItem(Inventory.getItemByIndex(itemIndex));
		
	}
	
	private void setItem(Item i){
		
		this.item = i;
		this.lblName = ("Name: " + i.getName());		
		determineItemType(i);
		this.lblValue = ("Value: " + Integer.toString(i.getValue()));
		this.lblDescription = ("No description available"); //TODO: implement description parameter for items
		
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
		
		this.lblType = ("Type: " + type);
		this.lblAttack = ("Strength: " + strAttack);
		this.lblAccuracy = ("Accuracy: " + strAccuracy);
		this.lblPotency = ("Potency: " + strPotency);
		
	}
	
	public void process(int x, int y){
		
		if (x <= 300){
			String command = null;
			if (y <= 50){
				command = InventoryWindow.ALL;
			} else if (y <= 100){
				command = InventoryWindow.CONSUMABLES;
			} else if (y <= 150){
				command = InventoryWindow.WEAPONS;
			} else if (y <= 200){
				command = InventoryWindow.MISC;
			}
			if (command != null){
				this.setFilter(command);
			}
			
		}
		
		/*Object source = arg0.getSource();
		
		//TODO: separate into filters and menu items
		
		if (source instanceof JLabel){
			
			JLabel label = (JLabel) source;
			String command = label.getName();
			
			
			
			if (command.equals(InventoryWindow.ALL) || 
					command.equals(InventoryWindow.CONSUMABLES) || 
					command.equals(InventoryWindow.WEAPONS) || 
					command.equals(InventoryWindow.MISC)){
				parent.setFilter(command);
			}else {
				System.out.println(label.getName());
				parent.setItem(command);
			}
			
		}*/
		
	}
	
}
