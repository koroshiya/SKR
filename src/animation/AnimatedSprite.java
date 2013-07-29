package animation;

import java.io.Serializable;

import org.newdawn.slick.SlickException;

import character.CombatCapableCharacter;

public class AnimatedSprite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WalkingSprite left;
	private WalkingSprite right;
	private WalkingSprite up;
	private WalkingSprite down;
	
	public AnimatedSprite(CombatCapableCharacter c){

		try{
			this.left = new WalkingSprite(c.getSpriteDirectory() + "left");
			this.right = new WalkingSprite(c.getSpriteDirectory() + "right");
			this.up = new WalkingSprite(c.getSpriteDirectory() + "forward");
			this.down = new WalkingSprite(c.getSpriteDirectory() + "backward");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void instantiate() throws SlickException{
		left.initialize();
		right.initialize();
		up.initialize();
		down.initialize();
	}

	public WalkingSprite getLeft(){
		return this.left;
	}
	
	public WalkingSprite getRight(){
		return this.right;
	}
	
	public WalkingSprite getUp(){
		return this.up;
	}
	
	public WalkingSprite getDown(){
		return this.down;
	}
	
}
