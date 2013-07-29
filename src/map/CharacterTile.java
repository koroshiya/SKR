package map;

import org.newdawn.slick.SlickException;

import interfaces.InteractableObject;

import com.japanzai.skr.Dialogue;

import screen.GameScreen;


import character.NonPlayableCharacter;

public class CharacterTile extends Tile implements InteractableObject {
	
	private NonPlayableCharacter npc;
	
	private String forward;
	private String backward;
	private String left;
	private String right;

	public CharacterTile(String spritePath, NonPlayableCharacter npc) throws SlickException {

		super(false, false, spritePath + "backward2.png");
		this.npc = npc;

		this.forward = (spritePath + "forward2.png");
		this.backward = (spritePath + "backward2.png");
		this.left = (spritePath + "left2.png");
		this.right = (spritePath + "left2.png");
		
	}
	
	public NonPlayableCharacter getCharacter(){
		return this.npc;
	}
	
	public Dialogue getDialogue(){
		return this.npc.getDialogue();
	}

	@Override
	public void interact(GameScreen parent) {
		boolean canContinue = true;
		do{
			canContinue = npc.getDialogueNextLine();
		}while(canContinue);
	}

	
	public void face(int dir) {
		if (dir == ParentMap.UP){
			this.setSprite(this.backward);
		}else if (dir == ParentMap.RIGHT){
			super.setSprite(this.right);
		}else if (dir == ParentMap.LEFT){
			super.setSprite(this.left);
		}else if (dir == ParentMap.DOWN){
			super.setSprite(this.forward);
		}
	}
	
}
