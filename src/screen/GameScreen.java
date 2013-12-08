package screen;

import interfaces.InteractableObject;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

import slickgamestate.Battle;
import slickgamestate.MapScreen;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;
import map.ParentMap;
import character.EnemyCharacter;
import console.MapConsole;
import console.dialogue.Dialogue;

import java.util.ArrayList;

public class GameScreen extends AppGameContainer{
	
	public GameScreen(SlickSKR skr) throws SlickException{
		
		super(skr);
		this.setIcon("/res/icon.png");
		
	}
	
	public void setSKR(SlickSKR skr){
		super.game = skr;
		this.setVSync(true);
		this.setTargetFrameRate(60);
		this.setSmoothDeltas(true);
		this.setUpdateOnlyWhenVisible(true);
		this.setAlwaysRender(true);
		this.setMaximumLogicUpdateInterval(10);
		//this.setShowFPS(false);
		//TODO: Set animated cursor
		//TODO: Set icon
		
		try{
			this.setDisplayMode(800, 600, false); //TODO: Change to true for fullscreen
		}catch (SlickException ex){
			ex.printStackTrace();
		}
	}
	
	public void swapView(int i){
		SlickGameState.flush();
		if (SlickSKR.NO_TRANSITIONS){
			((StateBasedGame)super.game).enterState(i);
			return;
		}
		
		int curr = this.getState().getID();
		Transition transIn;
		Transition transOut;
		if (i == SlickSKR.BATTLE || curr == SlickSKR.BATTLE || (i == SlickSKR.MAP && curr == SlickSKR.MAP)){
			transIn = new FadeOutTransition(Color.black, 400);
			transOut = new FadeInTransition(Color.black, 400);
		}else if (i == SlickSKR.MENU || curr == SlickSKR.MENU){
			((StateBasedGame)super.game).enterState(i);
			return;
		}else if (i == SlickSKR.GAMEOVER || curr == SlickSKR.GAMEOVER){
			transIn = new FadeOutTransition(Color.black, 200);
			transOut = new FadeInTransition(Color.black, 200);
		}else if (curr == SlickSKR.MAINMENU){
			transIn = new FadeOutTransition(Color.white, 800);
			transOut = new FadeInTransition(Color.white, 800);
		}else{
			((StateBasedGame)super.game).enterState(i);
			return;
		}
		((StateBasedGame)super.game).enterState(i, transIn, transOut);
		if (i == SlickSKR.BATTLE){SlickSKR.PlaySFX("other/public/battle_start.ogg");}
	}
	
	public void swapView(int i, Transition transIn, Transition transOut){
		((StateBasedGame)super.game).enterState(i, transIn, transOut);
	}
	
	public int getStateIndex(){return ((StateBasedGame)super.game).getCurrentStateID();}
	
	public GameState getState(){return ((StateBasedGame)super.game).getCurrentState();}
	
	public GameState getState(int i){return ((StateBasedGame)super.game).getState(i);}
	
	public void setBattle(ArrayList<EnemyCharacter> enemies){
		
		((Battle)this.getState(SlickSKR.BATTLE)).setEnemies(enemies);
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
		
		if (!SlickSKR.NO_ENCOUNTERS && map.randomEncounter()){
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

		swapView(SlickSKR.GAMEOVER);
				
		/*int choice = MessageBox.ChoiceBox("Resume from last save point?", "Game Over", null);
		if (choice == JOptionPane.YES_OPTION){
			Driver.restart();
		}else{
			System.exit(0);
		}*/
	}

	public void setMap(ParentMap map) {
		((MapScreen)this.getState()).setMap(map);
	}
	
	public void removeMapConsole(){
		
		GameState comp = getState();
		
		if (comp instanceof MapScreen){
			((MapScreen) comp).removeMapConsole();
		
		}
	}
		
}
