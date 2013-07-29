package menu.character;

import javax.swing.border.EmptyBorder;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.japanzai.skr.Party;

import character.PlayableCharacter;

public class CharacterProfileWindow extends BasicGameState {
	
	private PlayableCharacter character;
	private Image lblAvatar;
	
	public CharacterProfileWindow(PlayableCharacter character) throws SlickException{
		
		this.character = character;
		
		//this.setLayout(new GridLayout(1, 1));
		
		lblAvatar = character.getProfilePicture();
		
	}
	
	private void drawCharacterInfoPanel(Graphics g){
				
		//String s = character.getNickName() != null ? " (aka " + character.getNickName() + ")": "";
		
		String lblName = character.getName(); //All info
		String lblHeight = character.getHeight();
		String lblOccupation = character.getOccupation();
		String lblNationality = character.getNationality();
		String lblUnique = character.getUniqueInfo();

		String lblRightHand = "Equipped: " + character.getWeapon().getName();
		//JLabel lblLeftHand = new JLabel(character.getUniqueInfo());
		/*StringBuffer weapons = new StringBuffer();
		weapons.append("Supported weapons: ");
		for (Weapon w : character.getSupportedWeapons()){
			weapons.append(w.getTypeOfWeapon() + ", ");
		}
		weapons.replace(weapons.lastIndexOf(","), weapons.length(), "");
		
		JLabel lblSupported = new JLabel(weapons.toString());
		*/

		String lblLevel = ("Level: " + Integer.toString(character.getLevel()));
		String lblHP = ("HP: " + Integer.toString(character.getHP()));
		String lblStrength = ("Strength: " + Integer.toString(character.getStrength()));
		String lblDefence = ("Defence: " + Integer.toString(character.getDefence()));
		String lblMind = ("Mind: " + Integer.toString(character.getMind()));
		String lblEvasion = ("Evasion: " + Double.toString(character.getEvasion() * 100) + "%");
		String lblAccuracy = ("Accuracy: " + Double.toString(character.getAccuracy() * 100) + "%");
		String lblSpeed = ("Speed: " + Integer.toString(character.getSpeed()));
		
		//panel.setBorder(new EmptyBorder(20, 0, 0, 0));
		//panel.setPreferredSize(new Dimension(350, 400));

		g.drawString(lblName, x, y);
		g.drawString(lblHeight, x, y);
		g.drawString(lblOccupation, x, y);
		g.drawString(lblNationality, x, y);
		g.drawString(lblRightHand, x, y);
		g.drawString(lblLevel, x, y);
		g.drawString(lblHP, x, y);
		g.drawString(lblStrength, x, y);
		g.drawString(lblDefence, x, y);
		g.drawString(lblMind, x, y);
		g.drawString(lblEvasion, x, y);
		g.drawString(lblAccuracy, x, y);
		g.drawString(lblSpeed, x, y);
		g.drawString(lblUnique, x, y);
		
	}
	
	private void drawCharacterPanel(Graphics g){
		
		CharacterSelectionListener l = new CharacterSelectionListener(this);
		
		int y = 10; //value to increment startY
		int startY = 0; 
		
		for (PlayableCharacter c : Party.getCharacters()){
			g.drawString(c.getName(), x, startY);
			label.addMouseListener(l);
			startY += y;
		}
		
	}
	
	public void setCharacter(PlayableCharacter character){
		
		this.character = character;
		
		//this.updateUI();
		
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
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
		drawCharacterInfoPanel(g);
		drawCharacterPanel(g);
		g.drawImage(lblAvatar, x, y, null);
		
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