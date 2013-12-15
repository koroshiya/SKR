package character;

import java.io.File;

import interfaces.Photogenic;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import slickgamestate.MapScreen;

import com.japanzai.skr.Gender;

public abstract class Character implements Photogenic{
	
	private String firstName;
	private String lastName;
	private String nickName;
	
	private Gender gender;
	
	protected Image cache;
	
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int FORWARD = 1;
	public static final int BACKWARD = 0;
	private int direction;
	private final String spriteDirectory;
	private Animation anim[];
	
	public Character(String firstName, String lastName, 
							Gender gender, String nickName,
							String spriteDirectory){
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.nickName = nickName;
		this.spriteDirectory = spriteDirectory;
		this.direction = LEFT;
		
	}
	
	public void instantiate(int sizeX){
		try{
			if (new File(spriteDirectory + "avatar.png").exists()){
				this.cache = new Image(spriteDirectory + "avatar.png");
			}
		}catch (SlickException ex){
			ex.printStackTrace();
		}
		SpriteSheet tileSheet;
		try{
			tileSheet = new SpriteSheet(this.spriteDirectory + "sheet.png", sizeX, sizeX, new Color(0,0,0));
		}catch (Exception ex){
			ex.printStackTrace();
			return;
		}
		anim = new Animation[4];
		for (int y = 0; y < 4; y++) {
			anim[y] = new Animation(false);
			for (int x = 0; x < 4; x++) {
				try{
					anim[y].addFrame(tileSheet.getSprite(x,y), 30);
				}catch (Exception e){
					break;
				}
			}
			anim[y].setCurrentFrame(1);
			anim[y].setAutoUpdate(true);
			anim[y].setLooping(false);
			anim[y].stop();
		}
	}

	public void instantiate(){instantiate(MapScreen.ICON_SIZE);}
	
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
	public Image getCache() {
		return this.cache;
	}
	
	public synchronized void draw(int x, int y){
		this.anim[this.direction].draw(x, y);
	}

	public synchronized void draw(float x, float y){
		this.anim[this.direction].draw(x, y);
	}
	
	public synchronized Image getBattleIcon(){
		return anim[LEFT].getCurrentFrame();
	}
	
	public synchronized Image getBattleIconEnemy(){
		return anim[RIGHT].getCurrentFrame();
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	public synchronized void run(){
		this.anim[this.direction].restart();
		//anim[this.direction].setCurrentFrame(1);
	}
	
	public void restartFrame(){
		anim[this.direction].setCurrentFrame(1);
	}

	public int getTimeBetweenFrames() {
		return 120;
	}
	
}
