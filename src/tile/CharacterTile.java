package tile;

import map.ParentMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import animation.AnimatedSprite;
import interfaces.InteractableObject;
import console.dialogue.Dialogue;
import screen.GameScreen;
import slickgamestate.SlickGameState;
import character.NonPlayableCharacter;

public class CharacterTile extends Tile implements InteractableObject {
	
	private final NonPlayableCharacter npc;
	
	private final AnimatedSprite sprite;

	public CharacterTile(String spritePath, NonPlayableCharacter npc) throws SlickException {

		super(false, false, spritePath);
		this.npc = npc;
		
		this.sprite = new AnimatedSprite(npc);
		this.sprite.setDirection(AnimatedSprite.BACKWARD);
		
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
	
	public void instantiate() throws SlickException{
		this.sprite.instantiate();
		this.sprite.setDirection(AnimatedSprite.BACKWARD);
	}
	
	public void face(int dir) {
		if (dir == ParentMap.UP){
			this.sprite.setDirection(AnimatedSprite.BACKWARD);
		}else if (dir == ParentMap.RIGHT){
			this.sprite.setDirection(AnimatedSprite.LEFT);
		}else if (dir == ParentMap.LEFT){
			this.sprite.setDirection(AnimatedSprite.RIGHT);
		}else if (dir == ParentMap.DOWN){
			this.sprite.setDirection(AnimatedSprite.FORWARD);
		}
	}
	
	public void draw(int x, int y){
		this.sprite.draw(x, y);
	}
	
	public void drawIfNotDefault(Graphics g, String defaultImage, float x, float y){
		this.sprite.draw(x, y);
	}
	
	@Override
	public void finishInteraction(GameScreen parent) {
		SlickGameState.setFlush(true);
	}
	
}
