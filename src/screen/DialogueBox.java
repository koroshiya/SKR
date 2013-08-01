package screen;

import javax.swing.JLabel;

import org.newdawn.slick.Image;

public class DialogueBox {
	
	private Image avatar;
	private JLabel dialogue;
	
	public DialogueBox(){
		
		this.avatar = null; //100 x 110
		//this.avatar.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.dialogue = new JLabel(); //494 x 110
		
		//size = new Dimension(624, 110); //Size of this control
		
	}
	
	public void displayText(Image avatar2, String text){
		this.avatar = avatar2;
		this.dialogue.setText(text);
	}
	
	public Image getCache(){
		return this.avatar;
	}
	
}
