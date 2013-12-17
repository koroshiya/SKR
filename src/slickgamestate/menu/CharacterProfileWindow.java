package slickgamestate.menu;

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
	
	public CharacterProfileWindow(GameScreen gameScreen){
		super(SlickSKR.CHARACTER, gameScreen);
		labels = new String[13];
	}
	
	/**
	 * Draws each of the labels pertaining to a character's information,
	 * such as their stats and background info.
	 * 
	 * @param g Graphics context in which to draw the information
	 * */
	private void drawCharacterInfoPanel(Graphics g){

		final int x = 455;
		final int y = 15;
		
		int i = -1;
		int total = labels.length;
		while (++i < total){
			g.drawString(labels[i], x, y + y * i);
		}
		
		i = -1;
		ArrayList<String> list = character.getUniqueInfo();
		total = list.size();
		while (++i < total){
			g.drawString(list.get(i), x, y * (15 + i));
		}
		
		
	}
	
	/**
	 * Draws each of the character's avatars such that the character in focus
	 * can be swapped by clicking on another character's avatar.
	 * 
	 * @param g Graphics context in which to draw the avatars
	 * */
	private void drawCharacterPanel(Graphics g){
		
		ArrayList<PlayableCharacter> characters = Party.getCharacters();
		int i = -1;
		int total = partyMembers.length;
		while (++i < total){
			characters.get(i).drawScaled(g, (int)partyMembers[i].getX(), (int)partyMembers[i].getY(), 100, 100);
		}
		
	}
	
	/**
	 * Sets the character for whom the focus of the window should be.
	 * Sets the profile picture, stats, info, etc. accordingly.
	 * 
	 * @param character PlayableCharacter from party to set.
	 * */
	public void setCharacter(PlayableCharacter character){
		
		this.character = character;
		try {
			lblAvatar = new Image(this.character.getProfilePicture());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		labels[0] = character.getName(); //All info
		labels[1] = character.getHeight();
		labels[2] = character.getOccupation();
		labels[3] = character.getNationality();

		int i = -1;
		String[] arr = {
			"equipped", "level", "hp", "strength", "defence",
			"mind", "evasion", "accuracy", "speed"
		};
		int total = arr.length;
		while(++i < total){
			labels[i+4] = SlickSKR.getValueFromKey("screen.characterprofilewindow.label." + arr[i]) + ": ";
		}
		//JLabel lblLeftHand = new JLabel(character.getUniqueInfo());
		/*StringBuffer weapons = new StringBuffer();
		weapons.append("Supported weapons: ");
		for (Weapon w : character.getSupportedWeapons()){
			weapons.append(w.getTypeOfWeapon() + ", ");
		}
		weapons.replace(weapons.lastIndexOf(","), weapons.length(), "");
		
		JLabel lblSupported = new JLabel(weapons.toString());
		*/

		labels[4] += character.getWeapon().getName();
		labels[5] += Integer.toString(character.getLevel());
		labels[6] += Integer.toString(character.getStats().getHP());
		labels[7] += Integer.toString(character.getStats().getStrength());
		labels[8] += Integer.toString(character.getStats().getDefence());
		labels[9] += Integer.toString(character.getStats().getMind());
		labels[10] += Double.toString(character.getStats().getEvasion() * 100) + "%";
		labels[11] += Double.toString(character.getStats().getAccuracy() * 100) + "%";
		labels[12] += Integer.toString(character.getStats().getSpeed());
		
	}
	
	/**
	 * Returns the character currently in focus.
	 * 
	 * @return Character whose information, picture, etc. is currently being displayed.
	 * */
	public PlayableCharacter getCharacter(){return this.character;}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) {
		
		ArrayList<PlayableCharacter> characters = Party.getCharacters();
		this.setCharacter(characters.get(0));
		for (PlayableCharacter p : characters){
			p.instantiate();
		}
		int total = characters.size();
		partyMembers = new SlickRectangle[total];
		
		int row = 0;
		int col = 1;
		final int startY = 400;
		final int x = 455;
		
		int i = -1;
		while (++i < total){
			partyMembers[i] = new SlickRectangle(x + row, startY + 102 * col, 100, 100, Integer.toString(i));
			row = row == 0 ? 102 : 0;
			col = i > 1 ? 1 : 0;
		}
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
		
		if (SlickGameState.needFlush()){
			drawCharacterInfoPanel(g);
			drawCharacterPanel(g);
			g.drawImage(lblAvatar, 0, 0);
			SlickGameState.capture(g);
		}else{
			SlickGameState.drawCache(g);
		}
		
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) {
		
		for (SlickRectangle rect : partyMembers){
			if (rect.isWithinBounds(x, y)){
				SlickGameState.setFlush(true, false);
				this.processMenuItem(rect.getTag(), clickCount);
				break;
			}
		}
		
	}

	private void processMenuItem(String tag, int clickCount) {
		
		try{
			int i = Integer.parseInt(tag);
			if (i >= 0 && i < partyMembers.length){
				this.setCharacter(Party.getCharacterByIndex(i));
			}
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y){
		processMouseClick(1, x, y);
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		if (code == Input.KEY_W){
			parent.swapView(SlickSKR.MENU);
		}else if (code == Input.KEY_LEFT){
			previousCharacter();
		}else if (code == Input.KEY_RIGHT){
			nextCharacter();
		}
		
	}
	
	private void nextCharacter(){
		int i = Party.getCharacterIndexByName(this.character.getName());
		i = (i < Party.getCharacters().size() - 1) ? i + 1 : 0;
		this.setCharacter(Party.getCharacterByIndex(i));
	}
	
	private void previousCharacter(){
		int i = Party.getCharacterIndexByName(this.character.getName());
		i = (i > 0) ? i - 1 : Party.getCharacters().size() - 1;
		this.setCharacter(Party.getCharacterByIndex(i));
	}
	
}