package screen;

import interfaces.SlickDrawableFrame;
import interfaces.SlickEventHandler;

import javax.swing.JOptionPane;

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

import java.util.ArrayList;

public class GameScreen extends AppGameContainer{
	
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
		
	}
	
	public void setSKR(SlickSKR skr){
		super.game = skr;
	}
			
	public void swapToBattle() throws SlickException{
		
		swapView(SlickSKR.BATTLE);
		//MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		//this.getInput().addKeyListener(ml);
		//this.getInput().addMouseListener(ml);
		
	}
	
	public void swapToMenu(){
		
		swapView(SlickSKR.MENU);
		MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		this.getInput().addKeyListener(ml);
		this.getInput().addMouseListener(ml);
		
	}
	
	public void swapToMap(){
		
		swapView(SlickSKR.MAP);
		
		MovementListener ml = new MovementListener((MapScreen) getState(0));
		
		getInput().addKeyListener(ml);
		getInput().addMouseListener(ml);
		
	}
	
	public void swapToCharacterWindow(){
		swapView(SlickSKR.CHARACTER);
		MenuItemListener ml = new MenuItemListener(this, (SlickEventHandler) getState(4));
		
		getInput().addKeyListener(ml);
		getInput().addMouseListener(ml);
	}
	
	public void swapToInventory(){
		
		swapView(SlickSKR.INVENTORY);
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
			
	public void setBattle(ArrayList<EnemyCharacter> arrayList){
		
		((StateBasedGame)super.game).addState(new Battle(arrayList, this));
		((StateBasedGame)super.game).enterState(6);
		removeListeners();
		
	}
	
	public static void WriteOnScreen(String message, String title){
		MessageBox.InfoBox(message, title, null);
	}

	public static void WriteOnScreen(StringBuffer itemList, String title) {
		MessageBox.InfoBox(itemList, title, null);
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
	
	public void WriteOnMap(Dialogue dialogue) throws SlickException{
		
		GameState comp = ((StateBasedGame)super.game).getCurrentState();
		
		if (comp instanceof MapScreen){
						
			removeListeners();
			MapConsole console = new MapConsole(null, dialogue, this);
			
			((MapScreen)comp).setMapConsole(console);
			getInput().addMouseListener(console);
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

	
	public void removeMapConsole(){
		
		GameState comp = ((StateBasedGame)super.game).getCurrentState();
		
		if (comp instanceof MapScreen){
			
			((MapScreen) comp).removeMapConsole();
			//TODO: re-implement handlersa
		
		}
	}
	

	
}
