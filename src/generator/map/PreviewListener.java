package generator.map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;


public class PreviewListener implements MouseListener{
	
	private PreviewPanel parent;
	
	public PreviewListener(PreviewPanel parent){
		
		this.parent = parent;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		Object o = arg0.getSource();
		JLabel lbl = (JLabel) o;
		
		parent.setTile(lbl);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	
	
}
