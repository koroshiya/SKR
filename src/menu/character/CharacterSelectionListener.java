package menu.character;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.japanzai.skr.Party;

import character.PlayableCharacter;

public class CharacterSelectionListener implements MouseListener {

	private CharacterProfileWindow parent;
	
	public CharacterSelectionListener(CharacterProfileWindow characterProfileWindow) {
		
		parent = characterProfileWindow;
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		JLabel lbl = (JLabel) arg0.getSource();
		PlayableCharacter c = Party.getCharacterByName(lbl.getName());
		parent.setCharacter(c);
		//System.out.print(lbl.getName());
		
	}

}
