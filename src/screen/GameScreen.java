package screen;

import interfaces.SlickEventHandler;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import map.MovementListener;
import map.ParentMap;
import menu.MenuItemListener;
import menu.MenuMainWindow;

import battle.Battle;
import character.EnemyCharacter;

import com.japanzai.skr.Dialogue;
import com.japanzai.skr.Driver;
import com.japanzai.skr.MapScreen;
import com.japanzai.skr.SlickSKR;

import java.awt.Component;
import java.util.ArrayList;

public class GameScreen extends AppGameContainer{
	
	private JPanel panel;
	//private JPanel layer;
	
	private static JCheckBoxMenuItem always;
	private static JCheckBoxMenuItem never;
	
	private static JFrame frame;
	
	public GameScreen(SlickSKR skr) throws SlickException{
		
		super(skr);
		this.setVSync(true);
		this.setSmoothDeltas(true);
		this.setUpdateOnlyWhenVisible(true);
		//this.setShowFPS(false);
		//TODO: Set animated cursor
		//TODO: Set icon
		
		try{
			this.setDisplayMode(800, 600, false); //TODO: Change to true for fullscreen
			//this.start();
		}catch (SlickException ex){
			ex.printStackTrace();
		}
		
		try{			
			
			
			panel = new JPanel();
			panel.setDoubleBuffered(true);
			//panel.setLayout(cards);
			

			//instantiateMenuBar();
			//frame.pack(); //add insets
			//setResizable(false);
			
		}catch (Exception ex){
			MessageBox.ErrorBox(ex, "Driver error 1", null);
		}
		
	}
	
	public void setSKR(SlickSKR skr){
		super.game = skr;
	}
			
	public void swapToBattle() throws SlickException{
		
		Battle battle = (Battle)panel.getComponent(1);
		battle.start();
		
	}
	
	public void swapToMenu(){
		
		swapView(2);
		MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		this.getInput().addKeyListener(ml);
		this.getInput().addMouseListener(ml);
		
	}
	
	public void swapToMap(){
		
		swapView(0);
		
		MovementListener ml = new MovementListener((MapScreen) getState(0));
		
		getInput().addKeyListener(ml);
		getInput().addMouseListener(ml);
		
	}
	
	public void swapToCharacterWindow(){
		swapView(4);
		MenuItemListener ml = new MenuItemListener(this, (SlickEventHandler) getState(4));
		
		getInput().addKeyListener(ml);
		getInput().addMouseListener(ml);
	}
	
	public void swapToInventory(){
		
		swapView(3);
		MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		
		getInput().addKeyListener(ml);
		//getInput().addMouseListener(ml);
		
	}
	
	public void swapView(int i){

		((StateBasedGame)super.game).enterState(i);
		removeListeners();
		
	}
	
	public int getStateIndex(){
		return ((StateBasedGame)super.game).getCurrentStateID();
	}
	
	public GameState getState(){
		return ((StateBasedGame)super.game).getCurrentState();
	}
	
	public GameState getState(int i){
		
		return ((StateBasedGame)super.game).getState(i);
		
	}
	
	public void addContent(JComponent comp){
		this.panel.add(comp);
	}
		
	public void setBattle(ArrayList<EnemyCharacter> arrayList){
		
		((StateBasedGame)super.game).addState(new Battle(arrayList, this));
		((StateBasedGame)super.game).enterState(6);
		removeListeners();
		
	}
	
	public static void WriteOnScreen(String message, String title){
		MessageBox.InfoBox(message, title, frame);
	}

	public static void WriteOnScreen(StringBuffer itemList, String title) {
		MessageBox.InfoBox(itemList, title, frame);
	}
		
	public void setFullScreen(){
				
		try {
			this.setFullscreen(!this.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
				
	}
		
	public boolean isInBattle(){
		return getState() instanceof Battle;
	}
	
	public static void setAlwaysEncounter(boolean encounter){
		always.setSelected(encounter);
		never.setSelected(false);		
	}
	
	public static void setNeverEncounter(boolean encounter){
		never.setSelected(encounter);
		always.setSelected(false);	
	}
		
	public void WriteOnMap(Dialogue dialogue) throws SlickException{
		
		int mapHeight = 0;
		GameState comp = ((StateBasedGame)super.game).getCurrentState();
		
		if (comp instanceof MapScreen){
						
			MapScreen map = (MapScreen) comp;
			mapHeight = map.getParentMap().getYBoundary();
		
		}else if (panel.getComponents().length > 0){
			//Component comp2 = panel.getComponent(1);
			if (isInBattle()){
					
				//Battle map = (Battle) comp2;
				mapHeight = 800;
				//mapHeight = map.getHeight();
				
			}
		}
		
		if (mapHeight != 0){
			
			removeListeners();
			MapConsole console = new MapConsole(null, dialogue, this);
				
			console.start();
			console.converse();
		}
		
	}
	
	public void removeListeners(){
		
		this.getInput().removeAllListeners();
		
	}
	
	
	public boolean isEncounter(ParentMap map){
		
		if (!SlickSKR.DEBUG_MODE && map.randomEncounter()){
			return true;
		}
		return false;
		
	}
	
	public void encounter(ParentMap map){
		
		encounter(map.getEnemyFormation());
		
	}
	
	public void encounter(EnemyCharacter enemy){
		
		ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
		enemies.add(enemy);
		encounter(enemies);
		
	}
	
	public void encounter(ArrayList<EnemyCharacter> enemies){
		
		//if (panel.getComponent(0).isVisible()){
			setBattle(enemies);
			swapView(1);
		//}else {
		//	System.out.println("Can only do this from the map");
		//}
		
	}
			
	public void gameover() throws SlickException{

		swapView(666);
				
		int choice = MessageBox.ChoiceBox("Resume from last save point?", "Game Over", null);
		if (choice == JOptionPane.YES_OPTION){
			Driver.restart();
		}else{
			System.exit(0);
		}
	}

	
	public void setMap(ParentMap map) throws SlickException {
		((MapScreen) getState(0)).setMap(map);
	}

	

	
}
