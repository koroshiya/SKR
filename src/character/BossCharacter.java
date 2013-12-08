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
	


	public BossCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender, int experienceGivenWhenDefeated, 
					Dialogue dialogue, String nickName, String sprite, Item item, 
					int rate, int money, int encounterRate){
		this(firstName, lastName, style, weapon, gender, experienceGivenWhenDefeated, 
				dialogue, nickName, sprite, item, rate, money, encounterRate, "");
	}

	public BossCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender, int experienceGivenWhenDefeated, 
					Dialogue dialogue, String nickName, String sprite, Item item, 
					int rate, int money, int encounterRate, String bgm){
						
		super(firstName, lastName, style, weapon, gender, experienceGivenWhenDefeated, 
				nickName, sprite, item, rate, money, encounterRate);
	
		this.dialogue = dialogue;
		this.battleMusic = bgm;
		
	}
	
	public BossCharacter create(){
		BossCharacter ex = new BossCharacter(getFirstName(), getLastName(), 
				getFightingStyle(), getWeapon(), getGender(), 
				getExperienceGivenWhenDefeated(), dialogue, getNickName(), getSpriteDirectory(), 
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
