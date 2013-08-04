package character;

import item.ConsumableItem;
import item.Weapon;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Math;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;

import console.BattleConsole;

import technique.FuryBreak;
import technique.HealingTechnique;

public class PlayableCharacter extends CombatCapableCharacter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Weapon> supportedWeapons;
	//PlayableCharacter profile picture?
	
	private String height;
	private String occupation;
	
	private int experience;
	private int experienceToNextLevel;
	private String unique;
	private String nationality;
	private int temper; //0 min, 10 max. If 10, show "Fury break" instead of "attack"
	
	private boolean inParty;
	private FuryBreak fury;
		
	public PlayableCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender,
					String unique, String occupation, String height,
					String nationality, int level, FuryBreak fury, 
					String nickName, String sprite){
						
	this(firstName, lastName, style, weapon, gender, 
			unique, occupation, height, nationality, nickName, sprite);
	setLevel(level, null);
		
	}
	
	public PlayableCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender,
					String unique, String occupation, String height,
					String nationality, String nickName, String sprite){
						
		super(firstName, lastName, style, weapon, gender, nickName, sprite);
	
		this.height = height;
		this.nationality = nationality;
		this.occupation = occupation;
		this.experience = 0;
		this.experienceToNextLevel = 20;
		this.unique = unique;
		this.temper = 0;
		this.inParty = false;
		
	}
	
	/**
	 * Called when seeing if a character can use a certain Weapon.
	 * Invole the weapon's getType method to check its parent's name
	 * */
	public boolean isSupportedWeapon(Weapon newWeapon){
	
		for (Weapon w : supportedWeapons){
			if (w.getTypeOfWeapon() == newWeapon.getTypeOfWeapon()){
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public int takeDamage(int damage, int attackPower){
		
		int damageTaken = super.takeDamage(damage, attackPower);
		
		if (!super.isAlive()){
			this.temper = 0;
		}else if (this.temper < 10){
			temper++;
		}
		
		return damageTaken;
		
	}
	
	//Status ailments?
	//Revive? Attitude? (eg. angry raises critical and/or damage)
	
	public void gainExperience(int xp){
		
		this.experience += xp;
		BattleConsole.writeConsole("Experience gained: " + xp);
		
		while (this.experience >= this.experienceToNextLevel && this.getLevel() < 100){
			int overflow = this.experience - this.experienceToNextLevel;
			levelUp();
			this.experience = overflow;
		}
		
	}
	
	@Override
	public void setLevel(int level, BattleConsole bc){
		
		int difference = level - super.getLevel();
		if (difference <= 0){return;}
		for (int i = 1; i <= difference; i++){levelUp();}
		
	}
	
	@Override
	public void levelUp(){
	
		this.experience = 0;
		
		super.levelUp();
		
		if (this.getLevel() < 100){
			this.experienceToNextLevel = (int) Math.ceil((this.getLevel() + this.getLevel() - 1) * 21);
		}else{
			this.experienceToNextLevel = 0;
		}
		
	}
	
	public void healAlly(PlayableCharacter ally, 
							HealingTechnique tech, 
							ConsumableItem item,
							PlayableCharacter user){
	
			if (tech != null){
				BattleConsole.writeConsole(user.getName() + " used " + tech.getName());
				tech.use(ally);
			}else{
				BattleConsole.writeConsole(user.getName() + " used " + item.getName());
				item.consume(ally);
			}
			
			resetGauge();
			
	}
		
	public ArrayList<Weapon> getSupportedWeapons(){return this.supportedWeapons;}
	
	public String getHeight(){return this.height;}
	
	public String getNationality(){return this.nationality;}
	
	public String getOccupation(){return this.occupation;}
	
	public int getExperience(){return this.experience;}
	
	public int getExperienceToNextLevel(){return this.experienceToNextLevel;}
	
	public String getUniqueInfo(){return this.unique;}
	
	public boolean isInParty(){return this.inParty;}
	
	public void setInParty(boolean inParty){this.inParty = inParty;}
	
	public void toggleInParty(){this.inParty = !this.inParty;}
	
	public int getTemper(){return this.temper;}
	
	public void setTemper(boolean boolTemper){
		this.temper = boolTemper ? 10 : 0; //Used to set temper to max or min. eg. use an item to max it, opponent technique can reduce it.
	}
	
	public FuryBreak getFuryBreak(){
		
		return this.fury;
		
	}
	
	public String getProfilePicture() throws SlickException {
		return this.getSpriteDirectory() + "profile.png";		
	}
	
	@Override
	public void instantiateForBattle(){
		super.resetGauge();
		try {
			this.setAliveIcon(new Image(getSpriteDirectory() + "left2.png"));
			this.setDeadIcon(new Image("/res/dead.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
