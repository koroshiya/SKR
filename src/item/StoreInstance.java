package item;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import com.japanzai.skr.Inventory;

public class StoreInstance {
	
	private ArrayList<Item> stock;
	
	public StoreInstance(ArrayList<Item> stock){
		this.stock = stock;
	}
	
	public boolean buy(int index, int quantity){
		return Inventory.buyItem(this, index, quantity);
	}
	
	public boolean sell(Item i, int quantity){
		return Inventory.sellItem(this, i, quantity);
	}
	
	public Item getItem(int i){return stock.get(i);}
	
	public Item getItem(Item item){return getItem(item.getName());}
	
	public Item getItem(String name){
		for (Item i : stock){
			if (i.getName().equals(name)){
				return i;
			}
		}
		return null;
	}
	
	public void addItem(Item i){
		String name = i.getName();
		for (Item item : stock){
			if (item.getName().equals(name)){
				return;
			}
		}
		stock.add(i.create(0));
	}

	public ArrayList<Item> getItemsInInventory() {
		return Inventory.getItemsInInventory(stock);
	}

	public ArrayList<Item> getConsumablesAsItems() {
		return Inventory.getConsumablesAsItems(stock);
	}

	public ArrayList<Item> getMiscAsItems() {
		return Inventory.getMiscAsItems(stock);
	}

	public ArrayList<Item> getWeaponsAsItems() {
		return Inventory.getWeaponsAsItems(stock);
	}
	
	public void initialize() throws SlickException {
		
		for (Item i : stock){
			i.instantiate();
		}
		
	}

}
