package character;

import interfaces.Photogenic;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.japanzai.skr.Gender;

public abstract class Character implements Photogenic{
	
	private String firstName;
	private String lastName;
	private String nickName;
	
	private Gender gender;
	
	private String avatar;
	protected Image cache;
	
	private String spriteDirectory; //TODO: consider swapping with String; get resource dynamically to save resources
	
	public Character(String firstName, String lastName, 
							Gender gender, String nickName,
							String spriteDirectory){
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.nickName = nickName;
		this.spriteDirectory = spriteDirectory;
		this.avatar = this.spriteDirectory + "avatar.png";
		
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
	public Image getCache() {
		return this.cache;
	}

	@Override
	public void instantiate() throws SlickException {
		this.cache = new Image(this.avatar);
	}
	
}
