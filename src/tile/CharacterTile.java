package tile;

import map.ParentMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import interfaces.InteractableObject;
import console.dialogue.Dialogue;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import character.Character;
import character.NonPlayableCharacter;

public class CharacterTile extends Tile implements InteractableObject {
	
	private final NonPlayableCharacter npc;

	public CharacterTile(String spritePath, NonPlayableCharacter npc, int x, int y) {

		super(false, spritePath, x, y, true);
		this.npc = npc;
		
	}
	
	public NonPlayableCharacter getCharacter(){
		return this.npc;
	}
	
	public Dialogue getDialogue(){
		return this.npc.getDialogue();
	}

	@Override
	public void interact(GameScreen parent) {
		parent.WriteOnMap(npc.getDialogue(), this);
	}
	
	public void instantiate() throws SlickException{
		this.npc.instantiate();
		this.npc.setDirection(Character.BACKWARD);
	}
	
	public void face(int dir) {
		if (dir == ParentMap.UP){
			this.npc.setDirection(Character.BACKWARD);
		}else if (dir == ParentMap.RIGHT){
			this.npc.setDirection(Character.LEFT);
		}else if (dir == ParentMap.LEFT){
			this.npc.setDirection(Character.RIGHT);
		}else if (dir == ParentMap.DOWN){
			this.npc.setDirection(Character.FORWARD);
		}
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int targetHeight){
		this.npc.draw(g, x, y, targetHeight);
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y, int targetHeight){
		this.npc.draw(g, x, y, targetHeight);
	}
	
	@Override
	public void finishInteraction(GameScreen parent) {
		SlickGameState.setFlush(true, true);
	}
	
}
