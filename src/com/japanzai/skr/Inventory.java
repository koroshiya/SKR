package com.japanzai.skr;

import item.ConsumableItem;
import item.Item;
import item.StoreInstance;
import item.Weapon;

import java.util.ArrayList;

import org.newdawn.slick.util.Log;

public class Inventory {
	
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static int money = 0; //Coins? Change language file to suit "currency"
	
	/**
	 * Increases current funds by the amount passed in.
	 * 
	 * @param payment Amount by which to increase funds.
	 * */
	public static void receiveMoney(int payment){money += payment;}
	
	/**
	 * Reduces current funds by the amount passed in.
	 * This method does NOT check to see if the funds are available.
	 * It is assumed that canAfford(int) or a similar check has been conducted.
	 * 
	 * @param debt Amount to pay.
	 * */
	public static void payMoney(int debt){money -= debt;}
	
	/**
	 * Checks if the item is affordable.
	 * 
	 * @param cost Cost of the item.
	 * 
	 * @return True if can afford, otherwise false.
	 * */
	public static boolean canAfford(int cost){return cost <= money;}
	
	/**
	 * Emulates the process of selling an item.
	 * This includes checking if the store can purchase the item from you,
	 * if you have enough to sell, etc.
	 * 
	 * @param store The store to sell the item to.
	 * @param i Item to sell.
	 * @param quantity How many of the item to sell.
	 * 
	 * @return True if transaction succeeded, otherwise false.
	 */
	public static boolean sellItem(StoreInstance store, Item i, int quantity){
		store.addItem(i);
		Item dest = store.getItem(i);
		if (dest.canBuy(quantity) && i.canSell(quantity)){
			i.decreaseQuantity(quantity);
			dest.buy(quantity);
			receiveMoney(i.getValue() * quantity);
			return true;
		}
		return false;
	}
	
	/**
	 * Emulates the process of purchasing an item.
	 * ie. Creates an item if it doesn't exist, checks if it can be purchased, 
	 * if so deducts the funds and increases the quantity.
	 * 
	 * @param src Item to purchase.
	 * @param quantity How many of the specified item to purchase.
	 * 
	 * @return True if transaction succeeded, otherwise false.
	 * */
	public static boolean buyItem(Item src, int quantity){
		if (!isItemInInventory(src)){addItem(src);}
		Item dest = getItem(src.getName());
		if (canAfford(src.getValue() * quantity) && dest.canBuy(quantity) && src.canSell(quantity)){
			src.decreaseQuantity(quantity);
			dest.buy(quantity);
			payMoney(src.getValue() * quantity);
			return true;
		}
		return false;
	}
	
	/**
	 * Searches for an item in the inventory.
	 * 
	 * @param item Item to search for.
	 * 
	 * @return True if found, otherwise false.
	 * */
	public static boolean isItemInInventory(Item item){return isItemInInventory(item.getName());}
	
	/**
	 * Searches for an item in the inventory.
	 * 
	 * @param name Name of the item to search for.
	 * 
	 * @return True if the item was found, otherwise false.
	 * */
	public static boolean isItemInInventory(String name){
		
		for (Item item : items){
			if (item.getName().equals(name)){
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Searches for an item and checks if it's in stock.
	 * 
	 * @param name Name of the item to search for.
	 * 
	 * @return True if item exists and has a quantity of at least one, otherwise false.
	 * */
	public static boolean isItemInStock(String name){
		
		for (Item item : items){
			if (item.getName().equals(name)){
				return item.getQuantity() > 0;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Retrieves an item from the inventory by name,
	 * 
	 * @param name Name of the item to retrieve.
	 * 
	 * @return Returns the item if found, otherwise null.
	 * */
	public static Item getItem(String name){

		for (Item item : items){
			if (item.getName().equals(name)){
				return item;
			}
		}
		
		Log.error("Item " + name + " does not exist or is currently not in inventory.");
		return null;
		
	}
	
	/**
	 * Creates an item if it doesn't already exist in the Inventory.
	 * Whether it initially does or not, its quantity is then incremented.
	 * 
	 * @param item Item to add to the inventory.
	 * */
	public static void addItem(Item item){
		
		if (item != null){
			if (!isItemInInventory(item)){items.add(item.create(0));}
			getItemByName(item.getName()).increaseQuantity(1); //TODO: may create bug with items in store
		}
		
	}
	
	/**
	 * Adds a list of weapons to the inventory, as specified by addItem(Item)
	 * 
	 * @param itemList List of weapons to add to the inventory.
	 * */
	public static void addWeapons(ArrayList<Weapon> itemList){
		
		for (Item item : itemList){
			addItem(item);
		}
		
	}
	
	/**
	 * Retrieves the item at the specified index.
	 * 
	 * @param index Integer representing the index at which the item is to be found.
	 * 
	 * @return If found, the item is returned. Otherwise, null is returned.
	 * */
	public static Item getItemByIndex(int index){
		
		if (index < 0 || index >= items.size()){
			Log.error("Item at index " + index + " is not available or does not exist.");
			return null;
		}
		
		return items.get(index);
			
	}
	
	/**
	 * Attempts to find and retrieve an item by its name.
	 * 
	 * @param name Name of the item to retrieve.
	 * 
	 * @return If found, the item specified is returned. If not, null is returned.
	 * */
	public static Item getItemByName(String name){
		
		for (Item item : items){
			if (item.getName().equals(name)){
				return item;
			}
		}
		Log.error("No item found in getItemByName(" + name + ")");
		return null; //assertion?
			
	}
	
	/**
	 * Retrieves a list of available weapons from the party's inventory.
	 * ie. Weapons of which the party possesses at least one.
	 * 
	 * @return List of weapons currently in stock in the party's inventory.
	 * */
	public static ArrayList<Weapon> getWeapons(){
		
		ArrayList<Weapon> itemList = new ArrayList<Weapon>();
		
		for (Item item : items){
			if (item instanceof Weapon && item.getQuantity() != 0){
				itemList.add((Weapon) item);
			}
		}
		
		return itemList;
		
	}
	
	/**
	 * Retrieves a list of usable consumables from the party's inventory.
	 * ie. Consumable items of which the party possesses at least one.
	 * 
	 * @return List of consumable items currently in stock in the party's inventory.
	 * */
	public static ArrayList<ConsumableItem> getConsumables(){
		
		ArrayList<ConsumableItem> itemList = new ArrayList<ConsumableItem>();
		
		for (Item item : items){
			if (item instanceof ConsumableItem && item.getQuantity() != 0){
				itemList.add((ConsumableItem) item);
			}
		}
		
		return itemList;
		
	}
	
	/**
	 * Retrieves all weapons of a certain type, as defined by the constants in the Weapon class.
	 * 
	 * @param intType Integer corresponding to the index of the type of Weapon to look for.
	 * 
	 * @return List of weapons that belong to the type passed in.
	 * */
	public static ArrayList<Weapon> getWeaponByType(int intType){
		
		if (intType < 0 || intType >= Weapon.TYPE.length){
			Log.error("Index " + intType + " does not correspond to any existing Weapon types");
			return null;
		}
		
		ArrayList<Weapon> initialWeaponList = getWeapons();
		ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
		
		for (Weapon weapon : initialWeaponList){
			if (weapon.getTypeOfWeaponIndex() == intType){
				weaponList.add(weapon);
			}
		}
		
		return weaponList;
		
	}
	
	/**
	 * @param onehanded If true, return all one-handed items.
	 * 					If false, return all two-handed items.
	 * @return ArrayList of weapons, determined by the number of hands used to wield each.
	 * */
	public static ArrayList<Weapon> getWeaponByHand(boolean onehanded){
		
		ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
		
		for (Item item : items){
			if (item instanceof Weapon){
				Weapon weapon = (Weapon) item;
				if (weapon.isOneHanded() == onehanded){
					weaponList.add(weapon);
				}
			}
		}
		
		return weaponList;
	
	}
	
	/**
	 * Retrieves all items that can be sold.
	 * ie. Items currently in stock, and that aren't key items or otherwise unsellable.
	 * 
	 * @return List of items that can potentially be sold.
	 * */
	public static ArrayList<Item> getSellableItems(){
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Item item : items){
			if (item.isSellable() && item.getQuantity() > 0){
				if (item instanceof Weapon){
					Weapon weapon = (Weapon) item;
					if (weapon.isEquipped()){
						break;
					}
				}
				itemList.add(item);
			}
		}
		
		return itemList;
		
	}
	
	/**
	 * Retrieves the current amount of funds available.
	 * 
	 * @return The party's total funds.
	 * */
	public static int getMoney(){return money;}
	
	/**
	 * Sets the amount of money the party currently has.
	 * Used for setting funds on load, if the party loses all money, etc.
	 * 
	 * @param newMoney The amount of money to set for the party.
	 * */
	public static void setMoney(int newMoney){money = newMoney;}
	
	/**
	 * Retrieves all items, whether in stock, ever owned, etc. or not.
	 * 
	 * @param List of registered items, whether in stock or not.
	 * */
	public static ArrayList<Item> getItems(){return items;}
	
	/**
	 * Sets a list of items as the currently known/possessed and otherwise tracked items.
	 * 
	 * @param newItems List of items to register
	 */
	public static void setItems(ArrayList<Item> newItems){items = newItems;}
	
	/**
	 * Convenience method for getItemsInInventory(ArrayList<Item>) 
	 * using the current inventory item list as the parameter.
	 * 
	 * @return Returns a list of items currently possessed by the player.
	 * */
	public static ArrayList<Item> getItemsInInventory(){return getItemsInInventory(items);}
	
	/**
	 * Iterates through the supplied list of items and returns a subset consisting of those currently in possession.
	 * ie. Looks through the list and sees which items the player has in stock.
	 * 
	 * @param items List of items through which to search for possessed items.
	 * 
	 * @return List of items currently in stock
	 * */
	public static ArrayList<Item> getItemsInInventory(ArrayList<Item> items){
		
		ArrayList<Item> itemsInStock = new ArrayList<Item>();
		
		for (Item i : items){
			if (isItemInStock(i.getName())){
				itemsInStock.add(i);
			}
		}
		return itemsInStock;
		
	}
	
	/**
	 * Convenience method for getWeaponsAsItems(ArrayList<Item>) 
	 * using the current inventory item list as the parameter.
	 * 
	 * @return Returns a list of weapons currently possessed by the player.
	 * */
	public static ArrayList<Item> getWeaponsAsItems(){return getWeaponsAsItems(items);}
	
	/**
	 * Iterates through the supplied list of items and returns a subset consisting of weapons currently in possession.
	 * ie. Looks through the list for Weapon objects and sees which the player has in stock.
	 * 
	 * @param items List of items through which to search for possessed weapons.
	 * 
	 * @return List of weapons currently in stock.
	 * */
	public static ArrayList<Item> getWeaponsAsItems(ArrayList<Item> items){
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Item item : items){
			if (item instanceof Weapon && isItemInStock(item.getName())){
				itemList.add(item);
			}
		}
		
		return itemList;
		
	}
	
	/**
	 * Convenience method for getConsumablesAsItems(ArrayList<Item>) 
	 * using the current inventory item list as the parameter.
	 * 
	 * @return Returns a list of consumable items currently possessed by the player.
	 * */
	public static ArrayList<Item> getConsumablesAsItems(){return getConsumablesAsItems(items);}

	/**
	 * Iterates through the supplied list of items and returns a subset consisting of consumables currently in possession.
	 * ie. Looks through the list for ConsumableItem objects and sees which the player has in stock.
	 * 
	 * @param items List of items through which to search for possessed consumables.
	 * 
	 * @return List of consumables currently in stock.
	 * */
	public static ArrayList<Item> getConsumablesAsItems(ArrayList<Item> items){
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Item item : items){
			if (item instanceof ConsumableItem && item.getQuantity() != 0){
				itemList.add(item);
			}
		}
		
		return itemList;
		
	}
	
	/**
	 * Convenience method for getMiscAsItems(ArrayList<Item>) 
	 * using the current inventory item list as the parameter.
	 * 
	 * @return Returns a list of miscellaneous items currently possessed by the player.
	 * */
	public static ArrayList<Item> getMiscAsItems(){return getMiscAsItems(items);}
	
	/**
	 * Iterates through the supplied list of items and returns a subset consisting of miscellaneous items currently in possession.
	 * ie. Looks through the list for uncategorized items and sees which the player has in stock.
	 * 
	 * @param items List of items through which to search for possessed miscellaneous items.
	 * 
	 * @return List of uncategorized items currently in stock.
	 * */
	public static ArrayList<Item> getMiscAsItems(ArrayList<Item> items){
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Item item : items){
			if (item instanceof Weapon || item instanceof ConsumableItem){
				continue;
			}else if (item.getQuantity() != 0) {
				itemList.add(item);
			}
		}
		
		return itemList;
		
	}
	
}

