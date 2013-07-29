package menu.character;
import javax.swing.*;

import screen.GameScreen;

import menu.MenuItemListener;

import com.japanzai.skr.Party;

import character.PlayableCharacter;

import java.awt.*;
import java.util.ArrayList;

public class CharacterMenu extends JScrollPane{

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	
	public CharacterMenu(MenuItemListener ml){

		ArrayList<PlayableCharacter> characters = Party.getCharacters();

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.panel = new JPanel();
		this.panel.setLayout(new GridLayout(characters.size(), 1));
		this.setViewportView(this.panel);
		
		/**
		 * TODO: create window for these + menu and such
		 * TODO: highlight members currently in party
		 * */
		
		for (PlayableCharacter c : characters){
			CharacterProfilePane window = new CharacterProfilePane(c);
			window.addMouseListener(ml);
			this.panel.add(window);
		}
		
		GameScreen.setComponentSize(340, 600, this);
		
	}

}
