package interfaces;

import java.io.IOException;

import org.newdawn.slick.MouseListener;

public interface SlickEventHandler extends MouseListener{
	
	public abstract void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException;
	
}
