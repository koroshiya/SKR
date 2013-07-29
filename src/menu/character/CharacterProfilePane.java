package menu.character;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;


import com.japanzai.skr.Party;

import character.PlayableCharacter;

public class CharacterProfilePane extends BasicGameState {
	
	private PlayableCharacter character;
	
	private Image lblAvatar;
	String lblName;
	String lblHP;
	String lblLevel;
	String lblNextLevel;
	String lblType;
	String lblInParty;
	
	public CharacterProfilePane(PlayableCharacter character) throws SlickException{
		
		this.character = character; //Listener for when clicked? //Take to CharacterProfileWindow for character
		
		lblAvatar = character.getCache();
		
		lblName = (character.getName());
		lblHP = (character.getCurrentHP() + "/" + character.getHP() + "HP");
		lblLevel = ("Level " + character.getLevel());
		lblNextLevel = ((character.getExperienceToNextLevel() - character.getExperience()) + "xp until next level");
		lblType = (character.getFightingStyle().getStyle());
		lblInParty = (this.character.isInParty() ? "In party" : "Not in party");
		
		
		//this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		//Dimension size = new Dimension(300, 160);
		
		setInParty(this.character.isInParty());
		
	}
	
	public void ToggleCharacterInParty(){
		
		if (this.character.isInParty()){
			if (Party.getCharactersInParty().size() != 1){
				if ((this.character.isAlive() && Party.getCharactersAliveInParty(true).size() > 1) || 
						(!this.character.isAlive())){
					Party.toggleCharacterInParty(this.character);
				}else {
					GameScreen.WriteOnScreen("Can't remove last living character from party", "Operation not allowed");
				}
			}else {
				GameScreen.WriteOnScreen("Can't remove last character from party", "Operation not allowed");
			}
		}else if (!this.character.isInParty()){
			if (Party.getCharactersInParty().size() < 4){
				Party.toggleCharacterInParty(this.character);
			}else {
				GameScreen.WriteOnScreen("Can't add any more characters to party", "Operation not allowed");
			}
		}
		
		setInParty(this.character.isInParty());
			
	}
	
	public void setInParty(boolean inParty){
		
		lblInParty = (inParty ? "In party" : "Not in party");
		
		/*this.setBackground(inParty ? Color.lightGray : Color.darkGray);
		this.getComponent(1).setBackground(this.getBackground());
		
		lblName.setForeground(inParty ? Color.black : Color.white);
		lblHP.setForeground(lblName.getForeground());
		lblLevel.setForeground(lblName.getForeground());
		lblNextLevel.setForeground(lblName.getForeground());
		lblType.setForeground(lblName.getForeground());
		lblInParty.setForeground(lblName.getForeground());*/
		
	}
	
	public PlayableCharacter getCharacter(){
		return this.character;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
