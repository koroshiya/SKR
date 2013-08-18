package generator.dialogue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonListener implements ActionListener{
	
	private final GUI parent;
	
	public ButtonListener(GUI parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object obj = arg0.getSource();
		
		if (obj instanceof JButton){
			
			JButton btn = (JButton) obj;
			String command = btn.getText();
			
			if (command.equals(GUI.commands[0])){
				parent.add();
			}else if (command.equals(GUI.commands[1])){
				parent.addQuestion();
			}else if (command.equals(GUI.commands[2])){
				parent.end();
			}else if (command.equals(GUI.commands[3])){
				parent.clearLast();
			}else if (command.equals(GUI.commands[4])){
				parent.clearAll();
			}else if (command.equals(GUI.commands[5])){
				parent.addNewline();
			}
			
		}
		
	}
	
}
