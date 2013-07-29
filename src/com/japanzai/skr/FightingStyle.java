package com.japanzai.skr;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;

import technique.Technique;

public class FightingStyle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String style; //Name of fighting style
	private int baseHP;
	private int baseStrength;
	private int baseDefence;
	private double baseEvasion;
	private int baseMind;
	private double baseAccuracy;
	private int baseSpeed;
	private ArrayList<Technique> techniques;
	
	public FightingStyle(int HP, int strength, int defence, double evasion,
						int mind, double accuracy, int speed, String name){
		
		this.baseHP = HP;
		this.baseStrength = strength;
		this.baseDefence = defence;
		this.baseEvasion = evasion;
		this.baseMind = mind;
		this.baseAccuracy = accuracy;
		this.baseSpeed = speed;
		this.style = name;
		this.techniques = new ArrayList<Technique>();
		
	}
	
	public String getStyle(){return this.style;}
	
	public int getBaseHP(){return this.baseHP;}
	public int getBaseStrength(){return this.baseStrength;}
	public int getBaseDefence(){return this.baseDefence;}
	public double getBaseEvasion(){return this.baseEvasion;}
	public int getBaseMind(){return this.baseMind;}
	public double getBaseAccuracy(){return this.baseAccuracy;}
	public int getBaseSpeed(){return this.baseSpeed;}
	
	public int getHPBonus(){return getBonus(this.baseHP);}
	
	public int getStrengthBonus(){return getBonus(this.baseStrength);}
	
	public int getDefenceBonus(){return getBonus(this.baseDefence);}
		
	public int getMindBonus(){return getBonus(this.baseMind);}	
	
	public int getSpeedBonus(){return getBonus(this.baseSpeed);}
	
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
