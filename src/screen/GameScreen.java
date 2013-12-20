package screen;

import interfaces.InteractableObject;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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
import slickgamestate.state.StateTemplate;
import map.ParentMap;
import character.EnemyCharacter;
import console.MapConsole;
import console.dialogue.Dialogue;

import java.util.ArrayList;

public class GameScreen extends AppGameContainer{
	
	private StateBasedGame game;
	
	public GameScreen(SlickSKR skr) throws SlickException{
		
		super(skr.getGame());
		game = skr;
		this.setIcon("/res/icon.png");
	}
	
	public void setSKR(SlickSKR skr){
		super.game = skr.getGame();
		game = skr;
		this.setVSync(true);
		this.setTargetFrameRate(60);
		this.setSmoothDeltas(true);
		this.setUpdateOnlyWhenVisible(true);
		this.setAlwaysRender(true);
		this.setMaximumLogicUpdateInterval(10);
		
		//this.setShowFPS(false);
		
		try{
			this.setDisplayMode(SlickSKR.size.x, SlickSKR.size.y, false); //TODO: Change to true for fullscreen
		}catch (SlickException ex){
			ex.printStackTrace();
		}
	}
	
	public void swapView(int i){
		SlickGameState.setFlush(true, false);
		int curr = this.getState().getID();
		if (i == SlickSKR.SAVE || i == SlickSKR.LOAD){
			((StateTemplate)this.getState(i)).setBack(curr);
		}
		if (SlickSKR.NO_TRANSITIONS){
			game.enterState(i);
			return;
		}
		
		Transition transIn;
		Transition transOut;
		if (i == SlickSKR.BATTLE || curr == SlickSKR.BATTLE || (i == SlickSKR.MAP && curr == SlickSKR.MAP)){
			transIn = new FadeOutTransition(Color.black, 400);
			transOut = new FadeInTransition(Color.black, 400);
		}else if (i == SlickSKR.MENU || curr == SlickSKR.MENU){
			game.enterState(i);
			return;
		}else if (i == SlickSKR.GAMEOVER || curr == SlickSKR.GAMEOVER){
			transIn = new FadeOutTransition(Color.black, 200);
			transOut = new FadeInTransition(Color.black, 200);
		}else if (curr == SlickSKR.MAINMENU){
			transIn = new FadeOutTransition(Color.white, 800);
			transOut = new FadeInTransition(Color.white, 800);
		}else{
			game.enterState(i);
			return;
		}
		game.enterState(i, transIn, transOut);
		if (i == SlickSKR.BATTLE){SlickSKR.PlaySFX("other/public/battle_start.ogg");}
	}
	
	public void swapView(int i, Transition transIn, Transition transOut){
		game.enterState(i, transIn, transOut);
	}
	
	public int getStateIndex(){return game.getCurrentStateID();}
	
	public GameState getState(){return game.getCurrentState();}
	
	public GameState getState(int i){return game.getState(i);}
	
	public void setBattle(ArrayList<EnemyCharacter> enemies){
		
		((Battle)this.getState(SlickSKR.BATTLE)).setEnemies(enemies);
		game.enterState(SlickSKR.BATTLE);
		
	}
	
	public void setFullScreen(){
				
		try {
			this.setFullscreen(!this.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
				
	}
		
	public boolean isInBattle(){return getState() instanceof Battle;}
	
	public void WriteOnMap(Dialogue dialogue, InteractableObject npc) {
		
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

	public void setMap(ParentMap map, float startX, float startY) {
		((MapScreen)this.getState()).setMap(map, startX, startY);
	}
	
	public void removeMapConsole(){
		
		GameState comp = getState();
		
		if (comp instanceof MapScreen){
			((MapScreen) comp).removeMapConsole();
		
		}
	}
		
}
