package slickgamestate.menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;
import character.PlayableCharacter;

import com.japanzai.skr.Driver;
import com.japanzai.skr.Party;

import controls.SlickRectangle;

public class MenuMainWindow extends SlickGameState{
		
	private SlickRectangle[] menuItems;
	private SlickRectangle[] menuCharacters;
	private final String[] commands;
	private int[] keys = {Input.KEY_I, Input.KEY_E, Input.KEY_C, Input.KEY_B, 
							Input.KEY_S, Input.KEY_L, Input.KEY_W, Input.KEY_ESCAPE};
	private int timer = 0;
	private String message = "";
	private SlickRectangle alert;
	private Image background;
	private TrueTypeFont VICTORY_FONT;
	
	ArrayList<PlayableCharacter> characters;

	public MenuMainWindow(GameScreen parent){
		
		super(SlickSKR.MENU, parent);
		commands = new String[]{
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.inventory"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.equipment"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.characters"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.backlog"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.save"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.load"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.exit"),
			SlickSKR.getValueFromKey("screen.mainmenu.main.commands.quit"),
		};
		
	}
	
	public void menuItemPane(Graphics g) {
		
		int i = -1;
		int total = menuItems.length;
		while (++i < total){
			g.drawImage(menuItems[i].getCache(), menuItems[i].getMinX(), menuItems[i].getMinY());
			menuItems[i].paintCenter(g, true);
		}
		
	}
	
	public void characterPane(Graphics g){
		
		final float x = 155;
		float y = 0;
		int inc;
		final int baseInc = 13;
		for (int i = 0; i < menuCharacters.length; i++){
			g.drawImage(menuCharacters[i].getCache(), menuCharacters[i].getMinX(), menuCharacters[i].getMinY());
			PlayableCharacter c = characters.get(i);
			inc = baseInc;
			g.setColor(Color.white);
			//g.draw(menuCharacters[i]);
			g.drawImage(c.getCache(), 0, y);
			g.drawString(c.getName(), x, y + inc);
			inc += baseInc;
			g.drawString(c.getOccupation(), x, y + inc);
			inc += baseInc;
			g.drawString(c.getCurrentStats().getHP() + "/" + c.getStats().getHP() + SlickSKR.getValueFromKey("screen.mainmenu.main.characterpanel.hp"), x, y + inc);
			inc += baseInc;
			g.drawString(SlickSKR.getValueFromKey("screen.mainmenu.main.characterpanel.level") + " " + c.getLevel(), x, y + inc);
			inc += baseInc;
			g.drawString((c.getExperienceToNextLevel() - c.getExperience()) + SlickSKR.getValueFromKey("screen.mainmenu.main.characterpanel.xptonextlevel"), x, y + inc);
			inc += baseInc;
			g.drawString(SlickSKR.getValueFromKey("screen.mainmenu.main.characterpanel." + (c.isInParty() ? "inparty" : "notinparty")), x, y + inc);
			inc += baseInc;
			y += 155f;
		}
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException{
		
		final float x = 376;
		final float y = 50;

		String s = "";
		int i = -1;
		int total = commands.length;
		menuItems = new SlickRectangle[commands.length];
		while (++i < total){
			s = (String) commands[i];
			menuItems[i] = new SlickRectangle(x, i * y, 450, y, s, "/res/button_onyx_450x50.png");
			menuItems[i].initialize();
		}
		
		characters = Party.getCharacters();
		menuCharacters = new SlickRectangle[characters.size()];
		i = -1;
		total = menuCharacters.length;
		while (++i < total){
			characters.get(i).instantiate();
			menuCharacters[i] = new SlickRectangle(0, 155 * i, x, 150, Integer.toString(i), "/res/button_brown_376x155.png");
			menuCharacters[i].initialize();
		}
		VICTORY_FONT = SlickSKR.loadFont("Ubuntu-B.ttf", 24);
		alert = new SlickRectangle(150, 200, 550, 40, "");
		background = new Image("/res/terrain/refinery.png");
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame arg1){
		//SlickSKR.PlayMusic("other/public/intro.ogg");
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) {

		if (SlickGameState.needFlush()){
			//g.drawImage(background, 0, 0);
			g.fillRect(0,0,gc.getWidth(),gc.getHeight(),background,0,0);
			
			menuItemPane(g);
			characterPane(g);
			SlickGameState.capture(g);
		}else{
			g.drawImage(screenCache, 0, 0);
		}
		if (timer > 0){
			alert.setText(message);
			alert.paintCenter(g, VICTORY_FONT);
			timer--;
			if (timer == 0){
				SlickGameState.setFlush(true);
			}
		}
		
	}

	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		for (SlickRectangle rect : menuItems){
			if (rect.isWithinBounds(x, y)){
				this.processMenuItem(rect.getTag(), clickCount);
				return;
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
		
		timer = 0;
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
							timer = 150;
							message = SlickSKR.getValueFromKey("screen.mainmenu.main.processmenuitem.removelast");
							SlickGameState.setFlush(true);
							return;
						}
					}
					characters.get(i).toggleInParty();
					SlickGameState.setFlush(true);
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
			//Driver.load(); //TODO: reimplement
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
				//Driver.load(); //TODO: reimplement
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
