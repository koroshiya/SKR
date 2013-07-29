package com.japanzai.skr;

import java.io.Serializable;

public class Gender implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean boolMale;
	
	public Gender(boolean isMale){this.boolMale = isMale;}
	
	public String getGender(){return this.boolMale ? "Male" : "Female";}
	
	//methods containing techniques?

	/**
	 * @param favorableGender Gender which benefits from this algorithm.
	 * ie. If a male is receiving a bonus geared towards them, it would be true.
	 * If a female was receiving the same bonus, it would be false.
	 * @return Bonus to stat between 0 and 6 inclusive
	 * */
	private int getStatBonus(boolean favorableGender){
		
		int multiplier = favorableGender ? 3 : 2;
		double base = Math.round(Math.random() * 2) * multiplier;
		return (int) base;
		
	}

	/**
	 * @return stat bonus of 0-6
	 * These classes use boolMale is males gain more from the stat,
	 * and !boolMale if females benefit more.
	 * eg. Males get a 3:2 HP bonus over females, so getHPBonus uses boolMale
	 * Females get a 3:2 Defence bonus over males, so getDefenceBonus uses !boolMale
	 * */
	public int getHPBonus(){return getStatBonus(boolMale);}
	
	public int getStrengthBonus(){return getStatBonus(boolMale);}
	
	public int getDefenceBonus(){return getStatBonus(!boolMale);}
	
	public int getMindBonus(){return getStatBonus(!boolMale);}
	
	public int getSpeedBonus(){return getStatBonus(!boolMale);}
	
	public boolean isMale(){return this.boolMale;}

}
