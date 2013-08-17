package generator.map;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;


public class JFileNameField extends JTextField implements KeyListener{

	private static final long serialVersionUID = 1L;

	public JFileNameField(int length){
		super(length);
		this.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		for (char c : this.getText().toCharArray()){
			if (!Character.isLetterOrDigit(c)) {
				String text = this.getText().replaceFirst(Character.toString(c), "");
				this.setText(text);
			}
		}
		if (this.getText().length() > 20){
			this.setText(this.getText().substring(0, 20));
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	

}
