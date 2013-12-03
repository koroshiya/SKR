package animation;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import character.CombatCapableCharacter;

public class AnimatedSprite implements Runnable, Serializable {

	private static final long serialVersionUID = 5388120283498326586L;
	private SpriteSheet tileSheet;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int FORWARD = 1;
	public static final int BACKWARD = 0;
	private Image pics[][];
	private final long pause;
	private int currentFrame;
	private int direction;
	private final String spriteDir;
	
	public AnimatedSprite(CombatCapableCharacter c){

		pics = new Image[4][4];
		this.currentFrame = 1;
		this.pause = 120;
		this.direction = LEFT;
		this.spriteDir = c.getSpriteDirectory();
		
	}
	
	public void instantiate() throws SlickException{
		try{
			this.tileSheet = new SpriteSheet(this.spriteDir + "sheet.png", 48, 48, new Color(0,0,0));
		}catch (Exception ex){
			//ex.printStackTrace();
		}
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 3; x++) {
				pics[x][y] = tileSheet.getSprite(x,y);
			}
			pics[3][y] = tileSheet.getSprite(1,y);
		}
	}
	
	public synchronized Image getNextFrame() throws SlickException{
		this.nextFrame();
		return this.getImage();
	}

	public synchronized Image getImage() throws SlickException{
		return pics[this.currentFrame][this.direction];
	}
	
	public synchronized void nextFrame(){
		this.currentFrame += this.currentFrame == 3 ? -3 : 1;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	@Override
	public synchronized void run(){
		
		nextFrame();
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nextFrame();
		
	}

	public Long getTimeBetweenFrames() {
		return this.pause;
	}
	
}
