package com.japanzai.skr;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;

import character.Status;
import technique.Technique;

public class FightingStyle implements Serializable{
	
	private static final long serialVersionUID = 4483083411175148855L;
	
	private final String style;
	private final Status baseStats;
	private ArrayList<Technique> techniques;
	
	public FightingStyle(int HP, int strength, int defence, double evasion,
						int mind, double accuracy, int speed, String name){
		
		this(new Status(HP, strength, defence, mind, evasion, accuracy, speed), name);
		
	}
	
	public FightingStyle(Status baseStats, String name){
		
		this.baseStats = baseStats;
		this.style = name;
		this.techniques = new ArrayList<Technique>();
		
	}
	
	/**
	 * @return The name of this style.
	 * */
	public String getStyle(){return this.style;}
	
	public int getBaseHP(){return this.baseStats.getHP();}
	public int getBaseStrength(){return this.baseStats.getStrength();}
	public int getBaseDefence(){return this.baseStats.getDefence();}
	public double getBaseEvasion(){return this.baseStats.getEvasion();}
	public int getBaseMind(){return this.baseStats.getMind();}
	public double getBaseAccuracy(){return this.baseStats.getAccuracy();}
	public int getBaseSpeed(){return this.baseStats.getSpeed();}
	
	/**
	 * @return Status object reflecting the base stats associated with this fighting style.
	 * 		ie. The base bonuses this style offers.
	 * */
	public Status getBaseStats(){return this.baseStats;}
	
	public int getHPBonus(){return getBonus(this.baseStats.getHP());}
	public int getStrengthBonus(){return getBonus(this.baseStats.getStrength());}
	public int getDefenceBonus(){return getBonus(this.baseStats.getDefence());}
	public int getMindBonus(){return getBonus(this.baseStats.getMind());}	
	public int getSpeedBonus(){return getBonus(this.baseStats.getSpeed());}
	
	private int getBonus(int stat){
		return (int) Math.ceil((double)stat / 3f);
	}

	/**
	 * @param t Technique to associate with this fighting style.
	 * */
	public void addTechnique(Technique t){this.techniques.add(t);}
	
	/**
	 * Takes a list of techniques and associates them with this fighting style.
	 * 
	 * @param techs List of techniques to associate with this fighting style.
	 * */
	public void addTechniques(ArrayList<Technique> techs){
		
		for (Technique t : techs){
			addTechnique(t);
		}
		
	}
	
	/**
	 * Returns a list of techniques learned at the level specified.
	 * 
	 * @param level Level at which the technique is learned.
	 * 
	 * @return List of techniques learned at the level specified.
	 * */
	public ArrayList<Technique> getTechnique(int level){
		
		ArrayList<Technique> techs = new ArrayList<Technique>();
		
		for (Technique t : this.techniques){
			if (t.getLevelRequired() == level){techs.add(t);}
		}
		
		return techs;
		
	}
	
}
