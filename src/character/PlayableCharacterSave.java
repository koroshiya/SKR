package character;

import interfaces.Serial;
import interfaces.SerialChild;
import item.ItemSave;
import item.Weapon;

import java.io.File;
import java.util.ArrayList;

import technique.FuryBreak;
import technique.Technique;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;

public class PlayableCharacterSave implements SerialChild{
	
	private static final long serialVersionUID = 1L;
	
	private final String firstName;
	private final String lastName;
	private final String nickName;
	
	private final Gender gender;
	private final String avatar;
	private final FightingStyle style;
	private final SerialChild weapon; //Weapon type. eg. Fists, pickaxe, etc.   //Weapon leftHand and rightHand?
	private final ArrayList<Technique> techniques;
	
	private final int level;
	private final Status status;
	private final Status currentStatus;
	private final double gauge;
	private ArrayList<SerialChild> supportedWeapons;
	private final String height;
	private final String occupation;
	
	private final int experience;
	private final int experienceToNextLevel;
	private final ArrayList<String> unique;
	private final String nationality;
	private final int temper;
	private final boolean inParty;
	private final FuryBreak fury;
	
	public PlayableCharacterSave(PlayableCharacter pc){
		
		this.firstName = pc.getFirstName();
		this.lastName = pc.getLastName();
		this.nickName = pc.getNickName();
		
		this.gender = pc.getGender();
		this.avatar = new File(pc.getAvatar()).getParent() + "/";
		
		this.style = pc.getFightingStyle();
		this.weapon = new ItemSave(pc.getWeapon());
		this.techniques = pc.getTechniques();
		
		this.level = pc.getLevel();
		this.status = pc.getStats();
		this.currentStatus = pc.getCurrentStats();
		this.gauge = pc.getGauge();
		
		this.supportedWeapons = new ArrayList<SerialChild>();
		for (Weapon w : pc.getSupportedWeapons()){
			this.supportedWeapons.add(new ItemSave(w));
		};
		
		this.height = pc.getHeight();
		this.occupation = pc.getOccupation();
		
		this.experience = pc.getExperience();
		this.experienceToNextLevel = pc.getExperienceToNextLevel();
		this.unique = pc.getUniqueInfo();
		this.nationality = pc.getNationality();
		this.temper = pc.getTemper();
		
		this.inParty = pc.isInParty();
		this.fury = pc.getFuryBreak();
		
	}
	
	@Override
	public Serial serialLoad(){
		
		PlayableCharacter pc;
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		for (SerialChild is : supportedWeapons){
			weapons.add((Weapon)is.serialLoad());
		}
		pc = new PlayableCharacter(firstName, lastName, style, 
				(Weapon)weapon.serialLoad(), gender,
				unique, occupation, height,
				nationality, level, fury, 
				nickName, avatar, weapons);
		pc.getStats().setStats(status);
		pc.getCurrentStats().setStats(currentStatus);
		pc.setInParty(inParty);
		pc.setTechniques(techniques);
		pc.setExperience(experience);
		pc.setExperienceToNextLevel(experienceToNextLevel);
		pc.setTemper(temper);
		pc.setGauge(gauge);
		
		return pc;
		
	}
	
}
