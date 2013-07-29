package character;

import java.io.Serializable;

import interfaces.NPC;
import item.Item;
import item.Weapon;

import com.japanzai.skr.Dialogue;
import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;

import screen.GameScreen;

public class BossCharacter extends EnemyCharacter implements NPC, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dialogue dialogue;

	public BossCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender,
					int experienceGivenWhenDefeated, Dialogue dialogue, 
					String nickName, String sprite, Item item, int rate, int money, 
					int encounterRate){
						
		super(firstName, lastName, style, weapon, gender, 
				experienceGivenWhenDefeated, nickName, sprite, item, rate, money,
				encounterRate);
	
		this.dialogue = dialogue;
		
	}
	
	public BossCharacter create(){
		BossCharacter ex = new BossCharacter(getFirstName(), getLastName(), 
				getFightingStyle(), getWeapon(), getGender(), 
				getExperienceGivenWhenDefeated(), dialogue, getNickName(), getSpriteDirectory(), 
				getDrop(), getDropRate(), getMoney(), getEncounterRate());
		ex.setLevel(getLevel(), null);
		return ex;
	}

	public void getDialogueLine(int lineNumber){
		if (canGetNextLine()){printText();}
	}
	
	public boolean getDialogueNextLine(){
		
		if (canGetNextLine()){
			printText();
			return true;
		}
		return false;
		
	}
	
	public boolean canGetNextLine(){
		return dialogue.moreDialogue();
	}
	
	public void resetDialogue(){
		this.dialogue.reset();
	}
	
	private void printText(){
		GameScreen.WriteOnScreen(dialogue.speak(), this.getFirstName());
		dialogue.increment();
	}
	
	public Dialogue getDialogue() {
		return this.dialogue;
	}
	
	public void setDialogue(Dialogue dialogue){
		this.dialogue = dialogue;
	}

	
}
