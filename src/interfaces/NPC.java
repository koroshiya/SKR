package interfaces;

import com.japanzai.skr.Dialogue;

public interface NPC {
	
	public boolean canGetNextLine();

	public Dialogue getDialogue();
	
}
