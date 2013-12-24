package character;

import java.io.Serializable;

import interfaces.Photogenic;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;

import slickgamestate.SlickSKR;

import com.japanzai.skr.Gender;

public abstract class Character implements Photogenic, Serializable{
	
	private static final long serialVersionUID = -265227303212544557L;
	
	private String firstName;
	private String lastName;
	private String nickName;
	
	private Gender gender;
	
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int FORWARD = 1;
	public static final int BACKWARD = 0;
	private int direction;
	protected final String spriteDirectory;
	private Animation anim[][];
	private int cFrame = 0;
	
	public Character(String firstName, String lastName, 
							Gender gender, String nickName,
							String spriteDirectory){
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.nickName = nickName;
		this.spriteDirectory = spriteDirectory;
		this.direction = LEFT;
		anim = new Animation[4][2];
		
	}
	
	public void instantiate(){
		int sizeX = 0;
		try{
			sizeX = new Image(spriteDirectory + "avatar.png").getWidth();
		}catch (SlickException ex){
			ex.printStackTrace();
		}
		SpriteSheet tileSheet = null;
		try{
			tileSheet = new SpriteSheet(this.spriteDirectory + "sheet.png", sizeX, sizeX, new Color(0,0,0));
		}catch (Exception ex){
			Log.error("SpriteSheet died");
			ex.printStackTrace();
		}
		if (tileSheet.getVerticalCount() == 0){
			Log.error("break");
		}
		for (int y = 0; y < tileSheet.getVerticalCount(); y++) {
			anim[y][0] = new Animation(false);
			anim[y][1] = new Animation(false);
			
			anim[y][0].addFrame(tileSheet.getSprite(0,y).getScaledCopy(SlickSKR.scaleSize), 120 / 2);
			anim[y][0].addFrame(tileSheet.getSprite(1,y).getScaledCopy(SlickSKR.scaleSize), 120 / 2);
			anim[y][1].addFrame(tileSheet.getSprite(2,y).getScaledCopy(SlickSKR.scaleSize), 120 / 2);
			anim[y][1].addFrame(tileSheet.getSprite(1,y).getScaledCopy(SlickSKR.scaleSize), 120 / 2);
			
			for (int i = 0; i < 2; i++){
				anim[y][i].setCurrentFrame(1);
				anim[y][i].setAutoUpdate(true);
				anim[y][i].setLooping(false);
				anim[y][i].stop();
			}
		}
	}
	
	public boolean isMale(){return this.gender.isMale();}
	
	public String getName(){return this.firstName + " " + this.lastName;}
	
	public String getFirstName(){return this.firstName;}
	
	public String getLastName(){return this.lastName;}
	
	public String getNickName(){
		return this.nickName != null ? this.nickName : this.firstName;
	}
	
	public Gender getGender(){return this.gender;}
	
	public String getSpriteDirectory(){
		return this.spriteDirectory;
	}
	
	public String getAvatar(){
		return (this.spriteDirectory + "avatar.png");
	}
	
	@Override
	public synchronized void draw(Graphics g, int x, int y, int targetHeight){
		SlickSKR.drawImageScaled(g, x, y, targetHeight, this.anim[this.direction][cFrame].getCurrentFrame());
	}

	public synchronized void draw(Graphics g, float x, float y, int targetHeight){
		SlickSKR.drawImageScaled(g, x, y, targetHeight, this.anim[this.direction][cFrame].getCurrentFrame());
	}

	public synchronized void drawAvatar(Graphics g, float x, float y, int targetHeight){
		SlickSKR.drawImageScaled(g, x, y, targetHeight, this.spriteDirectory + "avatar.png");
	}
	
	public synchronized Image getBattleIcon(){
		return anim[LEFT][cFrame].getCurrentFrame();
	}
	
	public synchronized Image getBattleIconEnemy(){
		return anim[RIGHT][cFrame].getCurrentFrame();
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	public synchronized void run(){
		cFrame = cFrame == 0 ? 1 : 0;
		this.anim[this.direction][cFrame].restart();
		//anim[this.direction].setCurrentFrame(1);
	}
	
	public void restartFrame(){
		anim[this.direction][cFrame].setCurrentFrame(1);
	}

	public int getTimeBetweenFrames() {
		return 120;
	}
	
}
