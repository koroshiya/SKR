package character;

import java.io.Serializable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import interfaces.NPC;
import item.Item;
import item.Weapon;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;

import console.dialogue.Dialogue;

public class BossCharacter extends EnemyCharacter implements NPC, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Dialogue dialogue;
	private final String battleMusic;

	public BossCharacter (String name, FightingStyle style, 
					Weapon weapon, Gender gender, int experienceGivenWhenDefeated, 
					Dialogue dialogue, Item item, 
					int rate, int money, int encounterRate){
		this(name, style, weapon, gender, experienceGivenWhenDefeated, 
				dialogue, item, rate, money, encounterRate, "");
	}

	public BossCharacter (String name, FightingStyle style, 
					Weapon weapon, Gender gender, int experienceGivenWhenDefeated, 
					Dialogue dialogue, Item item, 
					int rate, int money, int encounterRate, String bgm){
						
		super(name, style, weapon, gender, experienceGivenWhenDefeated, 
				item, rate, money, encounterRate);
	
		this.dialogue = dialogue;
		this.battleMusic = bgm;
		
	}
	
	public BossCharacter create(){
		BossCharacter ex = new BossCharacter(getPropertyValue(), 
				getFightingStyle(), getWeapon(), getGender(), 
				getExperienceGivenWhenDefeated(), dialogue, 
				getDrop(), getDropRate(), getMoney(), getEncounterRate(), this.battleMusic);
		ex.setLevel(getLevel(), null);
		return ex;
	}
	
	public boolean canGetNextLine(){
		return dialogue.moreDialogue();
	}
	
	public void resetDialogue(){
		this.dialogue.reset();
	}
		
	public Dialogue getDialogue() {
		return this.dialogue;
	}
	
	public void setDialogue(Dialogue dialogue){
		this.dialogue = dialogue;
	}
	
	public String getBattleMusic(){
		return this.battleMusic;
	}
	
	@Override
	public void instantiateForBattle(){
		try {
			this.sprite.instantiate(96);
			this.setAliveIcon(this.sprite.getBattleIconEnemy());
			this.setDeadIcon(new Image("/res/dead.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	//TODO: implement boss dialog
	
}
