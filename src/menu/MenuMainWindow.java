package menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;
import screen.SlickGameState;

import character.PlayableCharacter;

import com.japanzai.skr.Driver;

import controls.SlickRectangle;

public class MenuMainWindow extends SlickGameState{
	
	public static final String INVENTORY = "Inventory [I]";
	public static final String EQUIPMENT = "Equipment [E]";
	public static final String CHARACTERS = "Characters [C]";
	public static final String BACKLOG = "Backlog";
	public static final String SAVE = "Save [S]";
	public static final String LOAD = "Load [L]";
	public static final String EXIT = "Exit [W]";
	public static final String QUIT = "Quit [Esc]";
	public static final int MENU_ITEM_HEIGHT = 50;
	
	Object[][] MENU_ITEMS = {{INVENTORY, 1 * MENU_ITEM_HEIGHT}, {EQUIPMENT, 2 * MENU_ITEM_HEIGHT}, 
			{CHARACTERS, 3 * MENU_ITEM_HEIGHT}, {BACKLOG, 4 * MENU_ITEM_HEIGHT},
			{SAVE, 5 * MENU_ITEM_HEIGHT}, {LOAD, 6 * MENU_ITEM_HEIGHT}, 
			{EXIT, 7 * MENU_ITEM_HEIGHT}, {QUIT, 8 * MENU_ITEM_HEIGHT}};
	private SlickRectangle[] menuItems;
	private SlickRectangle[] menuCharacters;
	
	ArrayList<PlayableCharacter> characters;

	public MenuMainWindow(int state, GameScreen parent){
		
		super(state, parent);
		
	}
	
	public void menuItemPane(Graphics g) throws SlickException{
			
		float x = 350;
		float y = 0;
				
		int textx;		
		int texty;
		
		String s = "";
		for (int i = 0; i < MENU_ITEMS.length; i++){
			s = (String) MENU_ITEMS[i][0];
			g.draw(menuItems[i]);
			textx = g.getFont().getWidth(s);
			texty = g.getFont().getHeight(s);
			g.drawString(s, x + (450 - textx) / 2, y + texty);
			y += 50f;
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
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
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
					characters.get(i).toggleInParty();
				}
			}catch (NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}else if (s.equals(EXIT)){
			parent.swapToMap();
		}else if (s.equals(CHARACTERS)){
			parent.swapToCharacterWindow();
		}else if (s.equals(INVENTORY)){
			parent.swapToInventory();
		}else if (s.equals(QUIT)){
			Driver.quit();
		}else if (s.equals(SAVE)){
			Driver.save();
		}else if (s.equals(LOAD)){
			Driver.load();
		}
		
	}

}
