package generator.map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class TerrainListener implements MouseListener{

	private TerrainPanel parent;
	
	public TerrainListener(TerrainPanel parent){
		
		this.parent = parent;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		Object comp = e.getSource();
		JLabel btn = (JLabel) comp;
		
		parent.startApplying(btn);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	
}
