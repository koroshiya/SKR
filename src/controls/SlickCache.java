package controls;

public class SlickCache {
	
	private boolean flush = false;
	protected float xPos = 0;
	protected float yPos = 0;
	private float initPosX = 0;
	private float initPosY = 0;
	private final int width;
	private final int height;
	
	public SlickCache(float xPos, float yPos, int width, int height){
		resetPosition(xPos, yPos);
		this.width = width;
		this.height = height;
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
		//Log.error("initPosX: " + initPosX);
	}
	
	public boolean needFlush(){
		return flush;
	}
	
	public void setFlush(boolean needed){
		flush = needed;
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
	
	public float getXPos(){
		return this.xPos;
	}
	
	public float getYPos(){
		return this.yPos;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public SlickCacheSave save(){
		return new SlickCacheSave(this);
	}

}
