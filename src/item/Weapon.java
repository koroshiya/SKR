package item;

import java.lang.Math;

import character.CombatCapableCharacter;
import character.Status;

public class Weapon extends Item {
	
	private static final long serialVersionUID = -6669888337641654571L;
	
	private Status stats;
	private int critical;
	//Critical hit ratio. Value between 0 and 100 //Multiple attacks in quick succession? Lower critical for rapid attacks?
	
	public final static String[] TYPE = {"Fist", "Gun", "Bat", "Pickaxe", "Katana"};
	private int intType;
	private int range;
	private boolean onehanded;
	private boolean boolEquipped;
	
	public Weapon(String name, int type, int strength, int defence, 
				double evasion, int mind, int critical, double accuracy,
				int range, boolean onehanded, int rarity, int baseValue,
				String avatar){
		super(name, baseValue, rarity, avatar); //baseValue = 1 if sellable, 0 if not sellable
		this.intType = type;
		this.stats = new Status(0, strength, defence, mind, evasion, accuracy, 0);
		this.critical = critical;
				
		this.range = range;
		this.onehanded = onehanded;
		
		if (baseValue != 0){
			int value = (strength + defence + mind + 
				(critical * 5) + ((int) Math.round(accuracy + evasion * 10)) + 
				(rarity * 200)) * baseValue;
			super.setValue(value);
		}
		
	}
	
	public Weapon(String name, int type, Status status, int critical,
				int range, boolean onehanded, int rarity, int baseValue,
				String avatar){
		super(name, baseValue, rarity, avatar); //baseValue = 1 if sellable, 0 if not sellable
		this.intType = type;
		this.stats = new Status(status);
		this.critical = critical;
				
		this.range = range;
		this.onehanded = onehanded;
		
		if (baseValue != 0){
			int value = (status.getStrength() + status.getDefence() + status.getMind() + 
				(critical * 5) + ((int) Math.round(status.getAccuracy() + status.getEvasion() * 10)) + 
				(rarity * 200)) * baseValue;
			super.setValue(value);
		}
		
	}
	
	public int attack(){
		
		int multiplier = calcCritical();
		return multiplier * stats.getStrength();
		
	}
	
	/**
	 * @return Returns either 1 (normal attack) or 2 (critical hit).
	 * */
	private int calcCritical(){
	
		if (this.critical == 0){return 1;}
		else if (this.critical == 100){return 2;}
		
		return calculate(this.critical) ? 2 : 1;
		
	}
	
	private boolean calculate(double chance){
				
		double result = chance + Math.random() * 100;
		return result >= 100 ? true : false;
		
	}
	
	public String getTypeOfWeapon(){
		return TYPE[intType];
	}
	
	public int getTypeOfWeaponIndex(){
		return intType;
	}
	
	public Status getStats(){return this.stats;}
	
	public int getStrength(){return this.stats.getStrength();}
	
	public int getDefence(){return this.stats.getDefence();}
	
	public double getEvasion(){return this.stats.getEvasion();}
	
	public int getMind(){return this.stats.getMind();}
	
	public int getCritical(){return this.critical;}
	
	public double getAccuracy(){return this.stats.getAccuracy();}
	
	public boolean isOneHanded(){return this.onehanded;}
	
	public int getRange(){return this.range;}
	
	public boolean isEquipped(){return this.boolEquipped;}
	
	public void setEquipped(boolean equipped){this.boolEquipped = equipped;}
	
	public int getSpeed(){return this.stats.getSpeed();}

	public String getSFXHit(){
		switch(intType){
			case 2:
				return "other/public/hit_bat.ogg";
			case 3:
				return "other/public/hit_bullet.ogg";
			case 4:
				return "other/public/hit_katana.ogg";
			case 1:
				//return "pickaxe.ogg";
			case 5:
				return "other/public/hit_knife.ogg";
			case 6:
				return "other/public/hit_wrench.ogg";
			case 7:
				return "other/public/hit_log.ogg";
			default:
				return "other/public/hit_punch.ogg";
		}
	}

	public String getSFXHitCritical(){
		switch(intType){
			case 1:
				return "pickaxe.ogg";
			case 2:
				return "other/public/hit_bat_critical.ogg";
			case 3:
				return "other/public/hit_bullet_critical.ogg";
			case 4:
				return "other/public/hit_katana_critical.ogg";
			case 7:
				return "other/public/hit_log_critical.ogg";
			case 5:
				//return "knife.ogg";
			case 6:
				//return "wrench.ogg";
			default:
				return "other/public/hit_punch_critical.ogg";
		}
	}

	public String getSFXMiss(){
		switch(intType){
			case 3:
				return "other/public/miss_bullet.ogg";
			case 5:
				return "other/public/miss_knife.ogg";
			default:
				return "other/public/miss_melee.ogg";
		}
	}

	@Override
	public Item create(int quantity) {
		
		Weapon w = new Weapon(
				getName(), intType, stats, critical, range, 
				onehanded, getRarity(), 0, getAvatar());
		w.increaseQuantity(quantity);
		w.setValue(super.getValue());
		return w;
		
	}

	@Override
	public void use(CombatCapableCharacter ccc) {
		//TODO: create subclass for mystic weapons? eg. Weapons that double back with magic spells? eg. Staff that heals allies.
	}

}
