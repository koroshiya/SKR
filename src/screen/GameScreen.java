package screen;

import interfaces.InteractableObject;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import slickgamestate.Battle;
import slickgamestate.MapScreen;

import map.ParentMap;

import character.EnemyCharacter;

import com.japanzai.skr.Driver;
import com.japanzai.skr.SlickSKR;

import console.MapConsole;
import console.dialogue.Dialogue;

import java.util.ArrayList;

public class GameScreen extends AppGameContainer{
	
	public GameScreen(SlickSKR skr) throws SlickException{
		
		super(skr);
		
	}
	
	public void setSKR(SlickSKR skr){
		super.game = skr;
		this.setVSync(true);
		this.setSmoothDeltas(true);
		this.setUpdateOnlyWhenVisible(true);
		this.setAlwaysRender(true);
		//this.setShowFPS(false);
		//TODO: Set animated cursor
		//TODO: Set icon
		
		try{
			this.setDisplayMode(800, 600, false); //TODO: Change to true for fullscreen
		}catch (SlickException ex){
			ex.printStackTrace();
		}
	}
	
	public void swapView(int i){((StateBasedGame)super.game).enterState(i);}
	
	public int getStateIndex(){return ((StateBasedGame)super.game).getCurrentStateID();}
	
	public GameState getState(){return ((StateBasedGame)super.game).getCurrentState();}
	
	public GameState getState(int i){return ((StateBasedGame)super.game).getState(i);}
	
	public void setBattle(ArrayList<EnemyCharacter> enemies){
		
		//try {
			//((StateBasedGame)this.game).addState(new Battle(this, enemies));
			//((Battle)this.getState(SlickSKR.BATTLE)) = new Battle();
			((Battle)this.getState(SlickSKR.BATTLE)).setEnemies(enemies);
		//} catch (SlickException e) {
		//	e.printStackTrace();
		//}
		((StateBasedGame)super.game).enterState(SlickSKR.BATTLE);
		
	}
	
	public void setFullScreen(){
				
		try {
			this.setFullscreen(!this.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
				
	}
		
	public boolean isInBattle(){return getState() instanceof Battle;}
	
	public void WriteOnMap(Dialogue dialogue, InteractableObject npc) throws SlickException{
		
		GameState comp = getState();
		
		if (comp instanceof MapScreen){
			
			MapConsole console = new MapConsole(null, dialogue, this);
			((MapScreen)comp).setMapConsole(console);
			console.setInteractiveTile(npc);
			console.converse();
			
		}
		
	}
	
	public boolean isEncounter(ParentMap map){
		
		if (!SlickSKR.DEBUG_MODE && map.randomEncounter()){
			return true;
		}
		return false;
		
	}
	
	public void encounter(ParentMap map){encounter(map.getEnemyFormation());}
	
	public void encounter(EnemyCharacter enemy){
		
		ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
		enemies.add(enemy);
		encounter(enemies);
		
	}
	
	public void encounter(ArrayList<EnemyCharacter> enemies){
		
		setBattle(enemies);
		swapView(1);
		
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
		((MapScreen)this.getState()).setMap(map);
	}
	
	public void removeMapConsole(){
		
		GameState comp = getState();
		
		if (comp instanceof MapScreen){
			((MapScreen) comp).removeMapConsole();
		
		}
	}

	
	public static void WriteOnScreen(String message, String title) {
		MessageBox.InfoBox(message, title);
	}
		
}
