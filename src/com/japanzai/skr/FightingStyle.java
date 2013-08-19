package com.japanzai.skr;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;

import character.Status;
import technique.Technique;

public class FightingStyle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String style; //Name of fighting style
	private Status baseStats;
	private ArrayList<Technique> techniques;
	
	public FightingStyle(int HP, int strength, int defence, double evasion,
						int mind, double accuracy, int speed, String name){
		
		this.baseStats = new Status(HP, strength, defence, mind, evasion, accuracy, speed);
		this.style = name;
		this.techniques = new ArrayList<Technique>();
		
	}
	
	public String getStyle(){return this.style;}
	
	public int getBaseHP(){return this.baseStats.getHP();}
	public int getBaseStrength(){return this.baseStats.getStrength();}
	public int getBaseDefence(){return this.baseStats.getDefence();}
	public double getBaseEvasion(){return this.baseStats.getEvasion();}
	public int getBaseMind(){return this.baseStats.getMind();}
	public double getBaseAccuracy(){return this.baseStats.getAccuracy();}
	public int getBaseSpeed(){return this.baseStats.getSpeed();}
	
	public Status getBaseStats(){
		return this.baseStats;
	}
	
	public int getHPBonus(){return getBonus(this.baseStats.getHP());}
	
	public int getStrengthBonus(){return getBonus(this.baseStats.getStrength());}
	
	public int getDefenceBonus(){return getBonus(this.baseStats.getDefence());}
		
	public int getMindBonus(){return getBonus(this.baseStats.getMind());}	
	
	public int getSpeedBonus(){return getBonus(this.baseStats.getSpeed());}
	
	private int getBonus(int stat){
		double bonus = Math.ceil((double)stat / 3);
		return (int) bonus;
	}

	public void addTechnique(Technique t){
		
		this.techniques.add(t);
		
	}
	
	public void addTechniques(ArrayList<Technique> techs){
		
		for (Technique t : techs){
			addTechnique(t);
		}
		
	}
	
	public ArrayList<Technique> getTechnique(int level){
		
		ArrayList<Technique> techs = new ArrayList<Technique>();
		
		for (Technique t : this.techniques){
			if (t.getLevelRequired() == level){techs.add(t);}
		}
		
		return techs;
		
	}
	
}
