package generator.map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;


public class BackgroundListener implements ActionListener, MouseListener{

	private TerrainPanel terrain;
	
	public BackgroundListener(TerrainPanel terrain){
		
		this.terrain = terrain;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object o = arg0.getSource();
		JButton btn = (JButton) o;
		
		if (btn.getText().equals("Apply Background")){
			terrain.applyBackground();
		}else {
			terrain.removeBackground();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		Object o = arg0.getSource();
		JLabel lbl = (JLabel) o;
		
		this.terrain.setBackground(lbl);
		
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
