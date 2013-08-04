package slickgamestate.menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;
import slickgamestate.SlickGameState;

import character.PlayableCharacter;

import com.japanzai.skr.Driver;
import com.japanzai.skr.Party;
import com.japanzai.skr.SlickSKR;

import controls.SlickRectangle;

public class MenuMainWindow extends SlickGameState{
		
	private SlickRectangle[] menuItems;
	private SlickRectangle[] menuCharacters;
	private String[] commands = {"Inventory [I]", "Equipment [E]", "Characters [C]", 
									"Backlog [B]", "Save [S]", "Load [L]", "Exit [W]", "Quit [Esc]"};
	private int[] keys = {Input.KEY_I, Input.KEY_E, Input.KEY_C, Input.KEY_B, 
							Input.KEY_S, Input.KEY_L, Input.KEY_W, Input.KEY_ESCAPE};
	
	ArrayList<PlayableCharacter> characters;

	public MenuMainWindow(int state, GameScreen parent){
		
		super(state, parent);
		
	}
	
	public void menuItemPane(Graphics g) throws SlickException{
			
		final float x = 350;
		final float y = 50;
				
		int textx;		
		int texty;
		
		String s = "";
		for (int i = 0; i < menuItems.length; i++){
			s = (String) menuItems[i].getTag();
			g.draw(menuItems[i]);
			textx = g.getFont().getWidth(s);
			texty = g.getFont().getHeight(s);
			g.drawString(s, x + (450 - textx) / 2, i * y + texty);
		}
					
	}
	
	public void characterPane(Graphics g){
		
		float x = 160;
		float y = 0;
		int inc;
		int baseInc = 13;
		for (int i = 0; i < menuCharacters.length; i++){
			PlayableCharacter c = characters.get(i);
			inc = baseInc;
			g.setColor(Color.lightGray);
			g.draw(menuCharacters[i]);
			g.drawImage(c.getCache(), 0, y);
			g.drawString(c.getName(), x, y + inc);
			inc += baseInc;
			g.drawString(c.getOccupation(), x, y + inc);
			inc += baseInc;
			g.drawString(c.getCurrentHP() + "/" + c.getHP() + "HP", x, y + inc);
			inc += baseInc;
			g.drawString("Level " + c.getLevel(), x, y + inc);
			inc += baseInc;
			g.drawString(c.getExperienceToNextLevel() + "xp until next level", x, y + inc);
			inc += baseInc;
			g.drawString(c.isInParty() ? "In party" : "Not in party", x, y + inc);
			inc += baseInc;
			//JLabel label = new MenuItemLabel(tag);
			//			window.addMouseListener(ml);
			//this.panel.add(window);
			y += 150f;
		}
	}	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		
		final float x = 350;
		final float y = 50;

		String s = "";
		menuItems = new SlickRectangle[commands.length];
		for (int i = 0; i < commands.length; i++){
			s = (String) commands[i];
			menuItems[i] = new SlickRectangle(x, i * y, 450, y, s);
		}
		
		characters = Party.getCharacters();
		menuCharacters = new SlickRectangle[characters.size()];
		for (int i = 0; i < menuCharacters.length; i++){
			characters.get(i).instantiate();
			menuCharacters[i] = new SlickRectangle(0, 150 * i, x, 150, Integer.toString(i));
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		
		menuItemPane(g);
		characterPane(g);
		
	}

	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		for (SlickRectangle rect : menuItems){
			if (rect.isWithinBounds(x, y)){
				this.processMenuItem(rect.getTag(), clickCount);
				break;
			}
		}
		for (SlickRectangle rect : menuCharacters){
			if (rect.isWithinBounds(x, y)){
				this.processMenuItem(rect.getTag(), clickCount);
				break;
			}
		}
		
	}
	
	private void processMenuItem(String s, int clickCount) throws IOException, ClassNotFoundException{
		
		if (clickCount == 2){
			try{
				int i = Integer.parseInt(s);
				if (i >= 0 && i < characters.size()){
					PlayableCharacter c = characters.get(i);
					if (c.isInParty()){
						boolean charAlive = false;
						for (PlayableCharacter ch : characters){
							if (!c.getName().equals(ch.getName())){
								if (ch.isAlive() && ch.isInParty()){
									charAlive = true;
									break;
								}
							}
						}
						if (!charAlive){
							GameScreen.WriteOnScreen("Can't remove last living member from party", "Operation not allowed");
							return;
						}
					}
					characters.get(i).toggleInParty();
				}
			}catch (NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}else if (s.equals(commands[6])){
			parent.swapView(SlickSKR.MAP);
		}else if (s.equals(commands[2])){
			parent.swapView(SlickSKR.CHARACTER);
		}else if (s.equals(commands[0])){
			parent.swapView(SlickSKR.INVENTORY);
		}else if (s.equals(commands[7])){
			Driver.quit();
		}else if (s.equals(commands[4])){
			Driver.save();
		}else if (s.equals(commands[5])){
			Driver.load();
		}
		
	}
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		
		try {
			processMouseClick(arg3, arg1, arg2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		System.out.println("MainMenuWindow: " + code);
		try {
			if (code == (keys[6])){
				parent.swapView(SlickSKR.MAP);
			}else if (code == (keys[2])){
				parent.swapView(SlickSKR.CHARACTER);
			}else if (code == (keys[0])){
				parent.swapView(SlickSKR.INVENTORY);
			}else if (code == (keys[7])){
				Driver.quit();
			}else if (code == (keys[4])){
				Driver.save();
			}else if (code == (keys[5])){
				Driver.load();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
