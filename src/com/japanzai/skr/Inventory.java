package com.japanzai.skr;
import item.ConsumableItem;
import item.Item;
import item.StoreInstance;
import item.Weapon;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Inventory { 
	//Change Weapon objects into Item objects 
	//Add money, sell/buy commands, consume, etc. 
	//If getType consumable
	
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static int money = 0; //Coins? Yen? I'll have to make it a long if we're using Yen
	
	public static void receiveMoney(int payment){
		money += payment;
	}
	
	public static void payMoney(int debt){
		money -= debt;
	}
	
	public static boolean canAfford(int cost){
		return cost <= money;
	}
	
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
	
	public static boolean buyItem(StoreInstance store, int index, int quantity){
		Item src = store.getItem(index);
		if (!isItemInInventory(src)){
			addItem(src);
		}
		Item dest = getItem(src.getName());
		if (canAfford(src.getValue() * quantity) && dest.canBuy(quantity) && src.canSell(quantity)){
			src.decreaseQuantity(quantity);
			dest.buy(quantity);
			payMoney(src.getValue() * quantity);
			return true;
		}
		return false;
	}
	
	public static boolean isItemInInventory(Item item){
		
		return isItemInInventory(item.getName());
		
	}
	
	public static boolean isItemInInventory(String name){
		
		for (Item item : items){
			if (item.getName().equals(name)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public static boolean isItemInStock(String name){
		
		for (Item item : items){
			if (item.getName().equals(name) && item.getQuantity() != 0){
				return true;
			}
		}
		
		return false;
		
	}
	
	public static Item getItem(String name){

		for (Item item : items){
			if (item.getName().equals(name)){
				return item;
			}
		}
		
		return null;
		
	}
	
	public static void addItem(Item item){
		
		if (item != null){
			if (!isItemInInventory(item)){items.add(item.create(0));}
			getItemByName(item.getName()).increaseQuantity(1); //TODO: may create bug with items in store
		}
		
	}
	
	public static void addWeapons(ArrayList<Weapon> itemList){
		
		for (Item item : itemList){
			addItem(item);
		}
		
	}
	
	public static Item getItemByIndex(int index){
		
		if (index < 0 || index >= items.size()){
			return null;
		}
		
		return items.get(index);
			
	}
	
	public static Item getItemByName(String name){
		
		for (Item item : items){
			if (item.getName().equals(name)){
				return item;
			}
		}
		System.out.println("No item found in getItemByName(" + name + ")");
		return null; //assertion?
			
	}
	
	public static ArrayList<Weapon> getWeapons(){
		
		ArrayList<Weapon> itemList = new ArrayList<Weapon>();
		
		for (Item item : items){
			if (item instanceof Weapon){
				Weapon weapon = (Weapon) item;
				itemList.add(weapon);
			}
		}
		
		return itemList;
		
	}
	
	public static ArrayList<ConsumableItem> getConsumables(){
		
		ArrayList<ConsumableItem> itemList = new ArrayList<ConsumableItem>();
		
		for (Item item : items){
			if (item instanceof ConsumableItem && item.getQuantity() != 0){
				ConsumableItem consumable = (ConsumableItem) item;
				itemList.add(consumable);
			}
		}
		
		return itemList;
		
	}
	
	public static ArrayList<Weapon> getWeaponByType(int intType){
		
		if (intType < 0 || intType >= Weapon.TYPE.length){
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
	 * @return ArrayList of items, determined by the number of hands
	 * 					used to wield each.
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
	
	public static int getMoney(){
		
		return money;
		
	}
	
	public static void setMoney(int newMoney){
		money = newMoney;
	}
	
	
	public static ArrayList<Item> getItems(){
		return items;
	}
	
	public static void setItems(ArrayList<Item> newItems){
		items = newItems;
	}
	
	public static ArrayList<Item> getItemsInInventory(){
		
		return getItemsInInventory(items);
		
	}
	
	public static ArrayList<Item> getItemsInInventory(ArrayList<Item> items){
		
		ArrayList<Item> itemsInStock = new ArrayList<Item>();
		
		for (Item i : items){
			if (isItemInStock(i.getName())){
				itemsInStock.add(i);
			}
		}
		return itemsInStock;
		
	}
	
	public static ArrayList<Item> getWeaponsAsItems(){
		
		return getWeaponsAsItems(items);
		
	}
	
	public static ArrayList<Item> getWeaponsAsItems(ArrayList<Item> items){
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Item item : items){
			if (item instanceof Weapon && isItemInStock(item.getName())){
				itemList.add(item);
			}
		}
		
		return itemList;
		
	}
	
	public static ArrayList<Item> getConsumablesAsItems(){
		
		return getConsumablesAsItems(items);
		
	}

	public static ArrayList<Item> getConsumablesAsItems(ArrayList<Item> items){
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Item item : items){
			if (item instanceof ConsumableItem && item.getQuantity() != 0){
				itemList.add(item);
			}
		}
		
		return itemList;
		
	}
	
	public static ArrayList<Item> getMiscAsItems(){
		
		return getMiscAsItems(items);
		
	}
	
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
	
	public static void initialize() throws SlickException {
		
		for (Item i : items){
			i.instantiate();
		}
		
	}
	
}

