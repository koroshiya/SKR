package tile;

import map.ParentMap;

import org.newdawn.slick.SlickException;

import interfaces.InteractableObject;

import console.dialogue.Dialogue;

import screen.GameScreen;


import character.NonPlayableCharacter;

public class CharacterTile extends Tile implements InteractableObject {
	
	private final NonPlayableCharacter npc;
	
	private final String forward;
	private final String backward;
	private final String left;
	private final String right;

	public CharacterTile(String spritePath, NonPlayableCharacter npc) throws SlickException {

		super(false, false, spritePath + "backward2.png");
		this.npc = npc;

		this.forward = (spritePath + "forward2.png");
		this.backward = (spritePath + "backward2.png");
		this.left = (spritePath + "right2.png");
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
		try {
			parent.WriteOnMap(npc.getDialogue(), this);
		} catch (SlickException e) {
			e.printStackTrace();
		}
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

	
	@Override
	public void finishInteraction(GameScreen parent) {}
	
}
