package screen;

import org.newdawn.slick.Input;

import interfaces.SlickEventHandler;

public abstract class SlickListener implements SlickEventHandler{
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseWheelMoved(int arg0) {}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {return false;}

	@Override
	public void setInput(Input arg0) {}

	@Override
	public void keyPressed(int arg0, char arg1) {}
	
}
