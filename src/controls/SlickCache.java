package controls;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SlickCache extends Image {
	
	private boolean flush = false;
	private float xPos = 0;
	private float yPos = 0;
	private float initPosX = 0;
	private float initPosY = 0;
	
	public SlickCache(float xPos, float yPos, int width, int height) throws SlickException{
		super(width, height);
		resetPosition(xPos, yPos);
	}
	
	public void resetPosition(float xPos, float yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.initPosX = xPos;
		this.initPosY = yPos;
	}
	
	public void resetPosition(){
		this.initPosX = this.xPos;
		this.initPosY = this.yPos;
	}
	
	public boolean needFlush(){
		return flush;
	}
	
	public void setFlush(boolean needed){
		flush = needed;
	}
	
	public void draw(Graphics g){
		g.drawImage(this, -xPos, -yPos);
	}
	
	public void move(float xDiff, float yDiff){
		xPos = this.initPosX + xDiff;
		yPos = this.initPosY + yDiff;
	}
	
	public float getInitX(){
		return this.initPosX;
	}
	
	public float getInitY(){
		return this.initPosY;
	}

}
