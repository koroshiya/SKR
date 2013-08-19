package item;

import character.CombatCapableCharacter;

public class ConsumableItem extends Item {
	
	private int potency;
	
	public ConsumableItem(String name, int value, int rarity, int potency, String avatar){
		
		super(name, value, rarity, avatar);
		this.potency = potency;
		
	}
	
	public void consume(CombatCapableCharacter target){
		
		super.decreaseQuantity(1);
		
		if (this.potency == -1){
			target.revive();
		}else if (this.potency == -2){
			target.revive(100);
		}else {
			target.heal(this.potency);
		}
		
	}
	
	public int getPotency(){return this.potency;}
	
	/**
	 * Returns true if the tech can be used on the dead (ie. it revives)
	 * Returns false if used on the living (ie. it heals)
	 * */
	public boolean usedOnDead(){return this.potency < 0;}

	@Override
	public Item create(int quantity) {
		
		Item i = new ConsumableItem(getName(), getValue(), getRarity(), potency, getAvatar());
		i.increaseQuantity(quantity);
		return i;
		
	}

}
