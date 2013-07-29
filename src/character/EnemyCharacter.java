package character;
import item.Item;
import item.Weapon;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Math;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;
import com.japanzai.skr.Party;

import technique.CombatTechnique;

public class EnemyCharacter extends CombatCapableCharacter implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int experienceGivenWhenDefeated;
	private Item drop;
	private int dropRate;
	private int money;
	private int encounterRate;
	
	public EnemyCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender,
					int experienceGivenWhenDefeated, String nickName, String sprite,
					Item drop, int dropRate, int money, int encounterRate){
						
		super(firstName, lastName, style, weapon, gender, nickName, sprite);
	
		this.experienceGivenWhenDefeated = experienceGivenWhenDefeated;
		this.drop = drop;
		this.dropRate = dropRate;
		this.money = money;
		this.encounterRate = encounterRate;
		
	}
	
	public EnemyCharacter create() {
		EnemyCharacter ex = new EnemyCharacter(getFirstName(), getLastName(), 
				getFightingStyle(), getWeapon(), getGender(), 
				getExperienceGivenWhenDefeated(), getNickName(), getSpriteDirectory(), 
				getDrop(), getDropRate(), getMoney(), getEncounterRate());
		ex.setLevel(getLevel(), null);
		return ex;
	}

	public void attack(PlayableCharacter opponent){
			
		super.attack(opponent);
		
	}
	
	public void attack(PlayableCharacter opponent, CombatTechnique tech){
		
		super.attack(opponent, tech);
		
	}
	
	public void invokeAI(){
		
		super.resetGauge();
		ArrayList<PlayableCharacter> livingOpponents = new ArrayList<PlayableCharacter>();
		
		for (PlayableCharacter c : Party.getCharactersInParty()){
			if (c.isAlive()){livingOpponents.add(c);}
		}
		
		for (PlayableCharacter c : livingOpponents){
			double chance = 100 * Math.random();
			if (chance >= 100 / livingOpponents.size()){
				attack(c);
				return;
			}
		}
		
		attack(livingOpponents.get(0));
		
	}
	
	public int getExperienceGivenWhenDefeated(){return this.experienceGivenWhenDefeated;}
	
	public Item getDrop(){
		return Math.random() * 100 > dropRate ? drop : null;
	}
	
	public int getDropRate(){
		return this.dropRate;
	}
	
	public int getMoney(){
		return this.money;
	}
	
	public int getEncounterRate(){
		return this.encounterRate;
	}
	
}
