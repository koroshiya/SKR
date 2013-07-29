package item;

import java.io.Serializable;
import java.lang.Math;

public class Weapon extends Item implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int Strength;
	private int Defence;
	private double Evasion;
	private int Mind;
	private int critical; //Critical hit ratio. Value between 0 and 100
	private double accuracy;
	private int speed; //Multiple attacks in quick succession? Lower critical for rapid attacks?
	
	public final static String[] TYPE = {"Fist", "Pickaxe", "Bat", "Gun", "Katana", "Sashimi Knife", "Wrench", "Log"};
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
		this.Strength = strength;
		this.Defence = defence;
		this.Evasion = evasion;
		this.Mind = mind;
		this.critical = critical;
		this.accuracy = accuracy;
				
		this.range = range;
		this.onehanded = onehanded;
		
		if (baseValue != 0){
			int value = (strength + defence + mind + 
				(critical * 5) + ((int) Math.round(accuracy + evasion * 10)) + 
				(rarity * 200)) * baseValue;
			super.setValue(value);
		}
		
	}
	
	public int attack(){
		
		int multiplier = calcCritical();
		return multiplier * Strength;
		
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
	
	public int getStrength(){return this.Strength;}
	
	public int getDefence(){return this.Defence;}
	
	public double getEvasion(){return this.Evasion;}
	
	public int getMind(){return this.Mind;}
	
	public int getCritical(){return this.critical;}
	
	public double getAccuracy(){return this.accuracy;}
	
	public boolean isOneHanded(){return this.onehanded;}
	
	public int getRange(){return this.range;}
	
	public boolean isEquipped(){return this.boolEquipped;}
	
	public void setEquipped(boolean equipped){this.boolEquipped = equipped;}
	
	public int getSpeed(){return this.speed;}



}
