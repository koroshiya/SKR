package item;

import interfaces.Serial;
import interfaces.SerialChild;

import character.Status;

public class ItemSave implements SerialChild{
	
	private static final long serialVersionUID = -6650719063054671627L;
	
	private final int value;
	private final int rarity;
	
	private final int quantity;
	private boolean boolWeapon;
	
	private final String name;
	private final String avatar;
	
	private int potency; //Consumable

	private int intType;
	private int range;
	private boolean onehanded;
	private boolean boolEquipped;
	private Status stats;
	private int critical;
	
	public ItemSave(Serial serial){
		
		Item i = (Item)serial;
		
		this.name = i.getName();
		this.value = i.getValue();
		this.rarity = i.getRarity();
		this.avatar = i.getAvatar();
		this.quantity = i.getQuantity();
		
		if (i instanceof Weapon){
			Weapon w = (Weapon)i;
			this.intType = w.getTypeOfWeaponIndex();
			this.range = w.getRange();
			this.onehanded = w.isOneHanded();
			this.boolEquipped = w.isEquipped();
			this.stats = new Status(w.getStats());
			this.critical = w.getCritical();
			this.boolWeapon = true;
		}else{
			ConsumableItem ci = (ConsumableItem)i;
			this.potency = ci.getPotency();
			this.boolWeapon = false;
		}
	}
	
	@Override
	public Serial serialLoad(){
		
		Item i;
		
		if (boolWeapon){
			i = new Weapon(name, intType, stats, critical,
					range, onehanded, rarity, 1,
					avatar);
			i.setValue(value);
			((Weapon)i).setEquipped(boolEquipped);
		}else{
			i = new ConsumableItem(name, value, rarity, potency, avatar);
		}
		
		i.increaseQuantity(quantity);
		
		return i;
		
	}
	
}
