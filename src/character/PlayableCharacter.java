package character;

import interfaces.Serial;
import item.ConsumableItem;
import item.Weapon;

import java.util.ArrayList;
import java.lang.Math;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;

import console.BattleConsole;
import slickgamestate.SlickSKR;
import technique.FuryBreak;
import technique.HealingTechnique;

public class PlayableCharacter extends CombatCapableCharacter implements Serial {

	private final ArrayList<Weapon> supportedWeapons;
	
	private final String height;
	private final String occupation;
	
	private int experience;
	private int experienceToNextLevel;
	private final ArrayList<String> unique;
	private final String nationality;
	private int temper; //0 min, 10 max. If 10, show "Fury break" instead of "attack"
	
	private boolean inParty;
	private FuryBreak fury;
	
	public PlayableCharacter (String nameEntry, FightingStyle style, 
					Weapon weapon, Gender gender,
					int level, FuryBreak fury, 
					ArrayList<Weapon> supportedWeapons){
						
		this(nameEntry, style, weapon, gender, supportedWeapons);
		setLevel(level, null);
		
	}
	
	public PlayableCharacter (String nameEntry, FightingStyle style, 
							Weapon weapon, Gender gender,
							ArrayList<Weapon> supportedWeapons){
						
		super(nameEntry, style, weapon, gender);
	
		this.height = SlickSKR.getValueFromKey(nameEntry + "height");
		this.nationality = SlickSKR.getValueFromKey(nameEntry + "nationality");
		this.occupation = SlickSKR.getValueFromKey(nameEntry + "occupation");
		this.experience = 0;
		this.experienceToNextLevel = 20;
		this.unique = new ArrayList<String>();
		unique.add(SlickSKR.getValueFromKey(nameEntry + "unique.one"));
		unique.add(SlickSKR.getValueFromKey(nameEntry + "unique.two"));
		unique.add(SlickSKR.getValueFromKey(nameEntry + "unique.three"));
		unique.add(SlickSKR.getValueFromKey(nameEntry + "unique.four"));
		this.temper = 0;
		this.inParty = false;
		this.supportedWeapons = supportedWeapons;
		
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
	
	public void healAlly(PlayableCharacter ally, HealingTechnique tech, ConsumableItem item, PlayableCharacter user){
	
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
	
	public ArrayList<String> getUniqueInfo(){return this.unique;}
	
	public boolean isInParty(){return this.inParty;}
	
	public void setInParty(boolean inParty){this.inParty = inParty;}
	
	public void toggleInParty(){this.inParty = !this.inParty;}
	
	public int getTemper(){return this.temper;}
	
	public void setTemper(boolean boolTemper){
		this.temper = boolTemper ? 10 : 0; //Used to set temper to max or min. eg. use an item to max it, opponent technique can reduce it.
	}
	
	public FuryBreak getFuryBreak(){return this.fury;}
	
	public String getProfilePicture() {
		return this.getSpriteDirectory() + "profile.png";		
	}
	
	@Override
	public void instantiateForBattle(){
		super.resetGauge();
		try {
			this.instantiate();
			this.setAliveIcon(getBattleIcon());
			this.setDeadIcon(new Image("/res/dead.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void instantiate(){
		//super.instantiate(sizeX);
		super.instantiateSuper();
		int sizeY = 48;
		try {
			sizeY = new Image(spriteDirectory + "avatar.png").getHeight();
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int sizeX = sizeY * 4 / 3;
		attack = new Animation();
		try {
			SpriteSheet tileSheet = new SpriteSheet(this.getSpriteDirectory() + "attack.png", sizeX, sizeY, new Color(0,0,0));
			int y = 0; //this.getWeapon().getTypeOfWeaponIndex(); //TODO:
			for (int x = 0; x < tileSheet.getHorizontalCount(); x++) {
				try{
					attack.addFrame(tileSheet.getSprite(x,y), 120 / tileSheet.getHorizontalCount());
				}catch (Exception e){
					break;
				}
			}
			attack.setCurrentFrame(1);
			attack.setAutoUpdate(true);
			attack.setLooping(false);
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (Exception e){}
	}

	public void setExperience(int experience2) {
		this.experience = experience2;
	}

	public void setExperienceToNextLevel(int experienceToNextLevel2) {
		this.experienceToNextLevel = experienceToNextLevel2;
	}

	public void setTemper(int temper2) {
		this.temper = temper2;
	}
	
}
