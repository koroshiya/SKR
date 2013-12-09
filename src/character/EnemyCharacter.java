package character;

import item.Item;
import item.Weapon;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Math;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import animation.AnimatedSprite;

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
	protected final AnimatedSprite sprite;
	
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
		this.sprite = new AnimatedSprite(this);
		
	}
	
	public EnemyCharacter create() {
		EnemyCharacter ex = new EnemyCharacter(this.getPropertyValue(),
				getFightingStyle(), getWeapon(), getGender(), 
				getExperienceGivenWhenDefeated(), 
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
	
	@Override
	public void instantiateForBattle(){
		try {
			this.sprite.instantiate();
			this.setAliveIcon(this.sprite.getBattleIconEnemy());
			this.setDeadIcon(new Image("/res/dead.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
