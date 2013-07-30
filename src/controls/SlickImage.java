package controls;

import interfaces.SlickEventHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

public class SlickImage extends Image implements MouseListener {
	
	private final SlickEventHandler parent;
	private final String tag;
	
	public SlickImage(String location, SlickEventHandler parent, String tag) throws SlickException{
		super(location);
		this.parent = parent;
		this.tag = tag;
	}
	
	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {return false;}

	@Override
	public void setInput(Input arg0) {}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		
		parent.process(tag);
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseWheelMoved(int arg0) {}

}
