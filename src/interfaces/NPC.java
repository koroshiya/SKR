package interfaces;

import com.japanzai.skr.Dialogue;

public interface NPC {

	public void getDialogueLine(int lineNumber);
	
	public boolean getDialogueNextLine();
	
	public boolean canGetNextLine();

	public Dialogue getDialogue();
	
}
