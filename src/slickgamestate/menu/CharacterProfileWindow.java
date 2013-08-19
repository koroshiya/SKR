package slickgamestate.menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;

import com.japanzai.skr.Party;

import controls.SlickRectangle;
import character.PlayableCharacter;

public class CharacterProfileWindow extends SlickGameState{
	
	private PlayableCharacter character;
	private Image lblAvatar;
	private SlickRectangle[] partyMembers;
	String[] labels;
	
	public CharacterProfileWindow(GameScreen gameScreen) throws SlickException{
		
		super(SlickSKR.CHARACTER, gameScreen);
		
	}
	
	private void drawCharacterInfoPanel(Graphics g){
				
		//String s = character.getNickName() != null ? " (aka " + character.getNickName() + ")": "";

		final int x = 455;
		final int y = 15;
		
		for (int i = 0; i < labels.length; i++){
			g.drawString(labels[i], x, y + y * i);
		}
		
		ArrayList<String> list = character.getUniqueInfo();
		for (int i = 0; i < list.size(); i++){
			g.drawString(list.get(i), x, y * (15 + i));
		}
		
		
	}
	
	private void drawCharacterPanel(Graphics g){
		
		PlayableCharacter c;
		for (int i = 0; i < partyMembers.length; i++){
			c = Party.getCharacters().get(i);
			g.drawImage(c.getCache().getScaledCopy(100, 100), partyMembers[i].getX(), partyMembers[i].getY());
		}
		
	}
	
	public void setCharacter(PlayableCharacter character) throws SlickException{
		
		this.character = character;
		lblAvatar = new Image(this.character.getProfilePicture());
		labels = new String[13];
		
		labels[0] = character.getName(); //All info
		labels[1] = character.getHeight();
		labels[2] = character.getOccupation();
		labels[3] = character.getNationality();

		labels[4] = "Equipped: " + character.getWeapon().getName();
		//JLabel lblLeftHand = new JLabel(character.getUniqueInfo());
		/*StringBuffer weapons = new StringBuffer();
		weapons.append("Supported weapons: ");
		for (Weapon w : character.getSupportedWeapons()){
			weapons.append(w.getTypeOfWeapon() + ", ");
		}
		weapons.replace(weapons.lastIndexOf(","), weapons.length(), "");
		
		JLabel lblSupported = new JLabel(weapons.toString());
		*/

		labels[5] = ("Level: " + Integer.toString(character.getLevel()));
		labels[6] = ("HP: " + Integer.toString(character.getStats().getHP()));
		labels[7] = ("Strength: " + Integer.toString(character.getStats().getStrength()));
		labels[8] = ("Defence: " + Integer.toString(character.getStats().getDefence()));
		labels[9] = ("Mind: " + Integer.toString(character.getStats().getMind()));
		labels[10] = ("Evasion: " + Double.toString(character.getStats().getEvasion() * 100) + "%");
		labels[11] = ("Accuracy: " + Double.toString(character.getStats().getAccuracy() * 100) + "%");
		labels[12] = ("Speed: " + Integer.toString(character.getStats().getSpeed()));
		
	}
	
	public PlayableCharacter getCharacter(){return this.character;}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		this.setCharacter(Party.getCharacterByIndex(0));
		for (PlayableCharacter p : Party.getCharacters()){
			p.instantiate();
		}
		partyMembers = new SlickRectangle[Party.getCharacters().size()];
		
		int row = 0;
		int col = 1;
		final int startY = 400;
		final int x = 455;
		
		for (int i = 0; i < partyMembers.length; i++){
			partyMembers[i] = new SlickRectangle(x + row, startY + 102 * col, 100, 100, Integer.toString(i));
			row = row == 0 ? 102 : 0;
			col = i > 1 ? 1 : 0;
		}
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		g.setFont(SlickSKR.DEFAULT_FONT);
		drawCharacterInfoPanel(g);
		drawCharacterPanel(g);
		g.drawImage(lblAvatar, 0, 0);
		
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		for (SlickRectangle rect : partyMembers){
			if (rect.isWithinBounds(x, y)){
				this.processMenuItem(rect.getTag(), clickCount);
				break;
			}
		}
		
	}

	private void processMenuItem(String tag, int clickCount) {
		
		int i;
		try{
			i = Integer.parseInt(tag);
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
			return;
		}
		
		if (i >= 0 && i < partyMembers.length){
			try {
				this.setCharacter(Party.getCharacterByIndex(i));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y){
		try {
			processMouseClick(1, x, y);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		
		if (code == Input.KEY_W){
			parent.swapView(SlickSKR.MENU);
		}
		
	}
	
}