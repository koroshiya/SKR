package character;
import java.io.Serializable;

import interfaces.NPC;

import com.japanzai.skr.Gender;

import console.dialogue.Dialogue;

public class NonPlayableCharacter extends Character implements NPC, Serializable{

	private static final long serialVersionUID = 1L;
	private Dialogue dialogue;

	public NonPlayableCharacter(String firstName, String lastName, 
							Gender gender, Dialogue dialogue, 
							String nickName, String avatar){
	
		super(firstName, lastName, gender, nickName, avatar);
		this.dialogue = dialogue != null ? dialogue : new Dialogue();
		
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

}
