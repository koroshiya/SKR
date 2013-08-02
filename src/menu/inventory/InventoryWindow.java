package menu.inventory;

import java.io.IOException;
import java.util.ArrayList;

import item.ConsumableItem;
import item.Item;
import item.Weapon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;

import screen.GameScreen;
import screen.SlickGameState;

import com.japanzai.skr.Inventory;

import controls.SlickRectangle;

public class InventoryWindow extends SlickGameState {

	private String lblName;
	private String lblType;
	private String lblValue;
	private String lblAttack;
	private String lblAccuracy;
	private String lblPotency;
	private String lblDescription;
	private String lblMoney;

	private ArrayList<Item> results = new ArrayList<Item>();

	private String[] commands = {"All Items [A]", "Consumables [C]", "Weapons [W]", "Miscellaneous [M]", "Back to Menu [B]"};
	private SlickRectangle[] menuItems;
	
	private Item item;
	
	public InventoryWindow(int state, GameScreen parent){
		
		super(state, parent);
		setFilter(commands[0]);
		lblMoney = "Funds: " + Inventory.getMoney() + " yen";
		
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
			this.parent.swapToMenu();
		}else {
			System.out.println("Error: This code should never be reached.");
			System.out.println("Unhandled filter type: " + filter);
		}
		
	}
	
	//TODO: setFilter
	private void getInventoryFilterPane(Graphics g){
		
		//Dimension size = new Dimension(300, 550);
	
		float x = 0;
		float y = 50;
					
		int textx;		
		int texty;
		
		Rectangle pane = new Rectangle(0, 0, 300f, 550f);
		g.draw(pane);
		
		String s = "";
		for (int i = 0; i < commands.length; i++){
			s = commands[i];
			Rectangle rect = new Rectangle(x, y * i, 300f, 50f);
			g.draw(rect);
			textx = g.getFont().getWidth(s);
			texty = g.getFont().getHeight(s);
			g.drawString(s, x + (300 - textx) / 2, y * i + texty);
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
		
		String[] labels = {lblName, lblType, lblValue, lblAttack, lblAccuracy, lblPotency, lblDescription};
		String s = "";
		for (int i = 0; i < labels.length; i++){
			s = labels[i];
			g.drawString(s, x + optX, y);

			optX = optX == 0 ? baseX : 0;
			y += optY;
			optY = optY == 0 ? baseY : 0;
		}
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		setItem(0);
		Inventory.initialize();
		menuItems = new SlickRectangle[commands.length];
		for (int i = 0; i < commands.length; i++){
			menuItems[i] = new SlickRectangle(0, 50 * i, 300, 50, commands[i]);
		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		
		getInventoryFilterPane(g);
		getInventorySelectedItem(g);
		getMoneyPane(g);
		displayResults(g);
		
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
		
	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		for (SlickRectangle s : menuItems){
			if (s.isWithinBounds(x, y)){
				this.setFilter(s.getTag());
				break;
			}
		}
		
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		
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
		}
		
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
	
}
