package character;
import java.io.Serializable;

import interfaces.NPC;

import screen.GameScreen;


import com.japanzai.skr.Dialogue;
import com.japanzai.skr.Gender;

public class NonPlayableCharacter extends Character implements NPC, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dialogue dialogue;

	public NonPlayableCharacter(String firstName, String lastName, 
							Gender gender, Dialogue dialogue, 
							String nickName, String avatar){
	
		super(firstName, lastName, gender, nickName, avatar);
		
		if (dialogue != null){
			this.dialogue = dialogue;
		}else {
			this.dialogue = new Dialogue();
		}
		
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
