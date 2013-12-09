package animation;

import java.io.Serializable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import slickgamestate.MapScreen;

import character.Character;

public class AnimatedSprite implements Serializable {

	private static final long serialVersionUID = 5388120283498326586L;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int FORWARD = 1;
	public static final int BACKWARD = 0;
	private int direction;
	private final String spriteDir;
	private Animation anim[];
	
	public AnimatedSprite(Character c){

		this.direction = LEFT;
		this.spriteDir = c.getSpriteDirectory();
		
	}
	
	public void instantiate(int sizeX){
		SpriteSheet tileSheet;
		try{
			tileSheet = new SpriteSheet(this.spriteDir + "sheet.png", sizeX, sizeX, new Color(0,0,0));
		}catch (Exception ex){
			ex.printStackTrace();
			return;
		}
		anim = new Animation[4];
		for (int y = 0; y < 4; y++) {
			anim[y] = new Animation(false);
			for (int x = 0; x < 2; x++) {
				anim[y].addFrame(tileSheet.getSprite(x,y), 60);
			}
			anim[y].setCurrentFrame(1);
			anim[y].setAutoUpdate(true);
			anim[y].setLooping(false);
			anim[y].stop();
		}
	}
	
	public void instantiate(){instantiate(MapScreen.ICON_SIZE);}

	public synchronized void draw(int x, int y){
		this.anim[this.direction].draw(x, y);
	}

	public synchronized void draw(float x, float y){
		this.anim[this.direction].draw(x, y);
	}
	
	public synchronized Image getBattleIcon(){
		return anim[AnimatedSprite.LEFT].getCurrentFrame();
	}
	
	public synchronized Image getBattleIconEnemy(){
		return anim[AnimatedSprite.RIGHT].getCurrentFrame();
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	public synchronized void run(){
		this.anim[this.direction].restart();
	}

	public int getTimeBetweenFrames() {
		return 120;
	}
	
}
