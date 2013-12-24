package item;

import java.io.Serializable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slickgamestate.SlickSKR;
import interfaces.Photogenic;

public abstract class Item implements Photogenic, Serializable {
	
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
	
	public String getName(){return this.name;}
	
	public int getValue(){return this.value;}
	
	public void setValue(int value){this.value = value;}
	
	public int getRarity(){return this.rarity;}
	
	public boolean isSellable(){return this.sellable;}
	
	public int getQuantity(){return this.quantity;}
	
	public boolean buy(){
		return buy(1);
	}
	
	public boolean buy(int quantity){
		if (canBuy(quantity)){
			increaseQuantity(quantity);
			return true;
		}
		return false;
	}
	
	public boolean sell(){
		return sell(1);
	}
	
	public boolean sell(int quantity){
		if (canSell(quantity)){
			decreaseQuantity(quantity);
			return true;
		}
		return false;
	}
	
	public void increaseQuantity(int quantity){this.quantity += quantity;}
	
	public void decreaseQuantity(int quantity){this.quantity -= quantity;}
	
	public boolean canBuy(int quantity){
		return this.quantity + quantity <= 99 ? true : false;
	}
	
	public boolean canSell(int quantity){
		return this.quantity >= quantity ? true : false;
	}
	
	public boolean canUse(){
		return this.quantity != 0;
	}
	
	@Override
	public String getAvatar() {
		return (this.avatar);
	}
	
	@Override
	public void draw(Graphics g, int x, int y){
		try {
			new Image(this.avatar).getScaledCopy(SlickSKR.scaleSize).draw(x, y);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
