package item;

import java.io.Serializable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slickgamestate.SlickSKR;
import interfaces.Photogenic;
import interfaces.QuantitativeCommand;

public abstract class Item implements Photogenic, Serializable, QuantitativeCommand {
	
	private static final long serialVersionUID = -8563039334149363189L;
	
	private int value; //Value an item can be sold for. Set to 0 if not sellable
	private final int rarity; //Rarity: Scale of 1-5 with color coding? Rarity as a measure of change? eg. 1 in 1000 drop
	private final boolean sellable; //False if main weapon, quest item, etc.
	
	private int quantity; //How many of this item you have in the inventory
	
	private final String name;
	private final String avatar;
	
	public Item(String name, int value, int rarity, String avatar2){
		
		this.name = name;
		this.value = value;
		this.rarity = rarity;
		this.sellable = value == 0 ? false : true;
		this.quantity = 0;
		this.avatar = avatar2;
		
	}
	
	public abstract Item create(int quantity);
	
	@Override
	public String getName(){return this.name;}
	
	public int getValue(){return this.value;}
	
	public void setValue(int value){this.value = value;}
	
	/**
	 * Checks how rare the item is.
	 * Defines the drop rate of the item, helps determine the price, etc.
	 * 
	 * @return Rarity of this item.
	 * */
	public int getRarity(){return this.rarity;}
	
	/**
	 * Checks whether the item has been marked as unsellable or not.
	 * 
	 * @return True if the item can be sold, otherwise false.
	 * */
	public boolean isSellable(){return this.sellable;}
	
	/**
	 * Checks how many instances of this item are in the inventory.
	 * 
	 * @return Number of copies of this item in the inventory.
	 * */
	public int getQuantity(){return this.quantity;}
	
	/**
	 * Attempts to purchase a single copy of this item.
	 * 
	 * @return True if transaction was successful, otherwise false.
	 * */
	public boolean buy(){return buy(1);}
	
	/**
	 * Attempts to purchase a specified number of copies of this item.
	 * 
	 * @param quantity How many copies of this item should be purchased.
	 * 
	 * @return True if transaction was successful, otherwise false.
	 * */
	public boolean buy(int quantity){
		if (canBuy(quantity)){
			increaseQuantity(quantity);
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to sell a single copy of this item.
	 * 
	 * @return True if transaction was successful, otherwise false.
	 * */
	public boolean sell(){return sell(1);}
	
	/**
	 * Attempts to sell a specified number of copies of this item.
	 * 
	 * @param quantity How many copies of this item should be sold.
	 * 
	 * @return True if transaction was successful, otherwise false.
	 * */
	public boolean sell(int quantity){
		if (canSell(quantity)){
			decreaseQuantity(quantity);
			return true;
		}
		return false;
	}
	
	/**
	 * Increases the number of copies of this item in the inventory.
	 * This method does NOT check to see how many copies, if any, are available.
	 * It is assumed you have already checked with canBuy(int) or a similar method.
	 * 
	 * @param quantity Number by which to increase the quantity of this item.
	 * */
	public void increaseQuantity(int quantity){this.quantity += quantity;}
	
	/**
	 * Decreases the number of copies of this item in the inventory.
	 * This method does NOT check to see how many copies, if any, are available.
	 * It is assumed you have already checked with canSell(int) or a similar method.
	 * 
	 * @param quantity Number by which to decrease the quantity of this item.
	 * */
	public void decreaseQuantity(int quantity){this.quantity -= quantity;}
	
	/**
	 * Tests if there is enough space to purchase the specified number of copies of this item.
	 * 
	 * @param quantity Number of copies that should be purchased.
	 * 
	 * @return True if there's enough space, otherwise false.
	 * */
	public boolean canBuy(int quantity){
		return this.quantity + quantity <= 99 ? true : false;
	}
	
	/**
	 * Tests if there are enough copies of this item to be sold.
	 * 
	 * @param quantity Number of copies that should be sold.
	 * 
	 * @return True if there are enough copies, otherwise false.
	 * */
	public boolean canSell(int quantity){
		return this.sellable && this.quantity >= quantity ? true : false;
	}
	
	@Override
	public boolean canUse(){return this.quantity != 0;}
	
	@Override
	public String getAvatar() {return this.avatar;}
	
	@Override
	public void draw(Graphics g, int x, int y){
		try {
			new Image(this.avatar).getScaledCopy(SlickSKR.scaleSize).draw(x, y);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
