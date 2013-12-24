package character;

import item.Item;
import item.Weapon;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Math;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;
import com.japanzai.skr.Party;

import technique.CombatTechnique;

public class EnemyCharacter extends CombatCapableCharacter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int experienceGivenWhenDefeated;
	private Item drop;
	private int dropRate;
	private int money;
	private int encounterRate;
	
	public EnemyCharacter (String name, FightingStyle style, 
					Weapon weapon, Gender gender,
					int experienceGivenWhenDefeated,
					Item drop, int dropRate, int money, int encounterRate){
						
		super(name, style, weapon, gender);
	
		this.experienceGivenWhenDefeated = experienceGivenWhenDefeated;
		this.drop = drop;
		this.dropRate = dropRate;
		this.money = money;
		this.encounterRate = encounterRate;
		
	}
	
	public EnemyCharacter create() {
		EnemyCharacter ex = new EnemyCharacter(this.getPropertyValue(),
				getFightingStyle(), getWeapon(), getGender(), 
				getExperienceGivenWhenDefeated(), 
				getDrop(), getDropRate(), getMoney(), getEncounterRate());
		ex.setLevel(level);
		return ex;
	}

	/**
	 * Performs an attack on the player specified.
	 * 
	 * @param opponent Character to attack.
	 * */
	public void attack(PlayableCharacter opponent){super.attack(opponent);}
	
	/**
	 * Performs an attacking technique on the player specified.
	 * 
	 * @param opponent Character on whom to use the technique.
	 * @param tech Technique to use on opponent.
	 * */
	public void attack(PlayableCharacter opponent, CombatTechnique tech){
		super.attack(opponent, tech);
	}
	
	/**
	 * Invokes the character's AI.
	 * The character may then attack an opponent, heal an ally, etc.
	 * **Currently only performs standard attacks.
	 * */
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
	
	/**
	 * Retrieves XP given for defeating this character.
	 * 
	 * @return Returns the experience given to a character when they defeat this character.
	 * */
	public int getExperienceGivenWhenDefeated(){return this.experienceGivenWhenDefeated;}
	
	/**
	 * Attempts to cause this character to drop an item.
	 * Likelihood is determined by character's droprate.
	 * 
	 * @return Held item if successful, otherwise null.
	 * */
	public Item getDrop(){return Math.random() * 100 > dropRate ? drop : null;} //TODO: factor in item's rarity as well/instead?
	
	/**
	 * Returns the droprate of this character.
	 * Higher number means lower likelihood of drop and vice-versa.
	 * Value should be less than 100, and greater than or equal to 0.
	 * 0 = always drop
	 * 100 = never drop
	 * 
	 * @return Value of 1-100 indicating the inverse likelihood out of 100 of a drop occurring.
	 * */
	public int getDropRate(){return this.dropRate;}
	
	/**
	 * Returns the money held by this character such that the one who
	 * defeats this character may collect it.
	 * 
	 * @return Money held by this character.
	 * */
	public int getMoney(){return this.money;}
	
	/**
	 * Returns likelihood of encountering this opponent by random.
	 * Higher number means lower likelihood and vice-versa.
	 * 0 = always encounter
	 * 100 = never encounter
	 * 
	 * @return Value of 1-100 indicating the inverse likelihood out of 100 of randomly encountering this opponent.
	 * */
	public int getEncounterRate(){return this.encounterRate;}

	public Image getBattleIcon(){
		if (this.isAlive()){
			return getBattleIconEnemy();
		}else{
			try {
				return new Image("/res/dead.png");
			} catch (SlickException e) {
				e.printStackTrace();
				try {
					return new Image(0,0);
				} catch (SlickException e1) {
					e1.printStackTrace();
					return null;
				}
			}
		}
	}
	
	@Override
	public void instantiateForBattle(){
		super.instantiate();
	}
	
}
