package menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;

import character.PlayableCharacter;

import com.japanzai.skr.Driver;
import com.japanzai.skr.Party;

public class MenuMainWindow extends BasicGameState{
	
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
	
	private final int state;
	private final GameScreen parent;

	public MenuMainWindow(int state, GameScreen parent){
		
		this.state = state;
		this.parent = parent;
		
	}
	
	public void menuItemPane(Graphics g){
			
		float x = 350;
		float y = 0;
				
		int textx;		
		int texty;
		
		String s = "";
		for (int i = 0; i < MENU_ITEMS.length; i++){
			s = (String) MENU_ITEMS[i][0];
			Rectangle rect = new Rectangle(x, y, 450f, 50f);
			g.draw(rect);
			textx = g.getFont().getWidth(s);
			texty = g.getFont().getHeight(s);
			g.drawString(s, x + (450 - textx) / 2, y + texty);
			//JLabel label = new MenuItemLabel(tag);
			//label.addMouseListener(new MenuItemListener(this.parent));
			//this.add(label);
			y += 50f;
		}
					
	}
	
	public void characterPane(Graphics g){
		
		ArrayList<PlayableCharacter> characters = Party.getCharacters();
						
		//GameScreen.setComponentSize(340, 600, this);
		
		float x = 160;
		float y = 0;
		int inc;
		int baseInc = 13;
		for (PlayableCharacter c : characters){
			inc = baseInc;
			Rectangle rect = new Rectangle(0, y, 350f, 150f);
			g.setColor(Color.lightGray);
			g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12), false));
			g.draw(rect);
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
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		Party.initialize();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		menuItemPane(g);
		characterPane(g);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.state;
	}

	public void processMouseClick(int arg0, int x, int y) throws IOException, ClassNotFoundException {
		
		if (x <= 350){
			//Character
			
			if (arg0 == 2){
				//CharacterProfilePane pane = (CharacterProfilePane)arg0.getSource();
				//pane.ToggleCharacterInParty();
			}
			
		}else {
			System.out.println("Processing");
			//Menu
			int maxY = 0;
			int baseY = 50;

			maxY = baseY * MENU_ITEMS.length;
			if (y <= maxY){

				for (int i = 0; i < MENU_ITEMS.length; i++){
					if (y <= (Integer)MENU_ITEMS[i][1]){
						processMenuItem((String)MENU_ITEMS[i][0]);
						break;
					}
				}
			}
		}		
	}
	
	private void processMenuItem(String s) throws IOException, ClassNotFoundException{
		
		if (s.equals(EXIT)){
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
