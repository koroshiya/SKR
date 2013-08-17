package generator.map;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;


public class PairPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public PairPanel(JComponent left, JComponent right){
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.add(left);
		this.add(right);
		
	}
	
	public Component getFieldLeft(){
		return this.getComponent(0);
	}
	
	public Component getFieldRight(){
		return this.getComponent(1);
	}
	
}
