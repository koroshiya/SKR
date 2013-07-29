package menu.inventory;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class InventoryListener implements MouseListener {
	
	private InventoryWindow parent;
	
	public InventoryListener(InventoryWindow parent) {
		this.parent = parent;
	}

	
	@Override
	public void inputEnded() {}
	

	@Override
	public void inputStarted() {}
	

	@Override
	public boolean isAcceptingInput() {
		return true;
	}
	

	@Override
	public void setInput(Input arg0) {}
	

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}
	

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}
	

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}
	

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {}
	

	@Override
	public void mouseReleased(int arg0, int x, int y) {

		parent.process(x, y);
		
	}


	@Override
	public void mouseWheelMoved(int arg0) {}



}
