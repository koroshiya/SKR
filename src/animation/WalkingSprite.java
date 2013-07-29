package animation;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;
import java.util.ArrayList;

public class WalkingSprite implements Runnable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> pic;
	private ArrayList<Image> pics;
	private long pause;
	private int currentFrame;
	
	public WalkingSprite(String path) throws SlickException {

		this.pic = new ArrayList<String>();
		this.pic.add((path + "1.png"));
		this.pic.add((path + "2.png")); //new ImageIcon(getClass().getResource(path + "2.png"))
		this.pic.add((path + "3.png"));
		this.pic.add((path + "2.png"));
		this.currentFrame = 1;
		this.pause = 120;
		
	}
	
	public void initialize() throws SlickException{
		
		this.pics = new ArrayList<Image>();
		
		for (String s : pic){
			pics.add(new Image(s));
		}
		
	}
	
	public Image getImage(int i) throws SlickException{
		return (pics.get(i));
	}
	
	public synchronized Image getNextFrame() throws SlickException{
		this.currentFrame += this.currentFrame == 3 ? -3 : 1;
		return this.getImage();
	}
	
	public synchronized void nextFrame(){
		this.currentFrame += this.currentFrame == 3 ? -3 : 1;
	}
	
	public synchronized Image getImage() throws SlickException{
		return (pics.get(currentFrame));
	}
	
	public long getTimeBetweenFrames(){
		return pause;
	}

	@Override
	public synchronized void run(){
		
		nextFrame();
		System.out.println("t: " + this.currentFrame);
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nextFrame();
		System.out.println("t: " + this.currentFrame);
		
	}
	
	public void pause(){
		try {
			Thread.sleep(this.pause);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCurrentFrame(){
		return this.currentFrame;
	}

}
