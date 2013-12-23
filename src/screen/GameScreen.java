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
import slickgamestate.state.StateTemplate;
import map.ParentMap;
import character.EnemyCharacter;
import console.MapConsole;
import console.dialogue.Dialogue;

import java.util.ArrayList;

/**
 * @author Koroshiya
 * This class serves as a container for the actual game being played.
 * It manages the swapping of states, transitions, and provides methods via which states
 * can interact with one another or check the status of the game.
 */
public class GameScreen extends AppGameContainer{
	
	private StateBasedGame game; //Game this container is wrapped around.
	
	/**
	 * Instantiates an instance of this class and sets the game for it to manage henceforth.
	 * Game can be swapped later via setSKR, but any SlickSKR passed in must be valid.
	 * 
	 * @param skr Game to be contained by this class.
	 * */
	public GameScreen(SlickSKR skr) throws SlickException{
		
		super(skr.getGame());
		game = skr;
		this.setIcon("/res/icon.png");
	}
	
	/**
	 * Sets the game for this class to manage henceforth.
	 * 
	 * @param skr Game to be contained by this class.
	 * */
	public void setSKR(SlickSKR skr){
		super.game = skr.getGame();
		game = skr;
		this.setVSync(true);
		final int refreshrate = 60;
		this.setTargetFrameRate(refreshrate);
		SlickSKR.setRefreshRate(refreshrate);
		this.setSmoothDeltas(true);
		this.setUpdateOnlyWhenVisible(true);
		this.setAlwaysRender(true);
		this.setMaximumLogicUpdateInterval(10);
		this.setVerbose(false);
		//this.setShowFPS(false);
		
		try{
			this.setDisplayMode(SlickSKR.size.x, SlickSKR.size.y, false); //TODO: Change to true for fullscreen
		}catch (SlickException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Swaps from one state to another.
	 * eg. From a battle to the map or vice versa.
	 * This method is also responsible for transitions, or the lack thereof.
	 * 
	 * @param i ID of the game state to change to, as defined by the constants in SlickSKR.
	 * */
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
	
	/**
	 * Swaps from one state to another.
	 * eg. From a battle to the map or vice versa.
	 * This method is also responsible for transitions, or the lack thereof.
	 * 
	 * @param i ID of the game state to change to, as defined by the constants in SlickSKR.
	 * @param transIn Transition to use when leaving the current state.
	 * @param transOut Transition to use when entering the specified state.
	 * */
	public void swapView(int i, Transition transIn, Transition transOut){
		game.enterState(i, transIn, transOut);
	}
	
	/**
	 * Returns the ID of the current state of the game.
	 * 
	 * @return ID of the state currently active.
	 * */
	public int getStateIndex(){return game.getCurrentStateID();}
	
	/**
	 * Returns the current state of the game.
	 * 
	 * @return Game state currently active.
	 * */
	public GameState getState(){return game.getCurrentState();}
	
	/**
	 * Retrieves the game state specified by the input ID.
	 * 
	 * @param id ID of the state to retrieve.
	 * 
	 * @return Game state specified by the input ID.
	 * */
	public GameState getState(int id){return game.getState(id);}
	
	/**
	 * Sets up and forces the character into a battle.
	 * 
	 * @param enemies Enemy formation with which the character should do battle.
	 * */
	public void setBattle(ArrayList<EnemyCharacter> enemies){
		
		((Battle)this.getState(SlickSKR.BATTLE)).setEnemies(enemies);
		game.enterState(SlickSKR.BATTLE);
		
	}
	
	/**
	 * Attempts to set the game into fullscreen mode.
	 * */
	public void setFullScreen(){
				
		try {
			this.setFullscreen(!this.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
				
	}
		
	/**
	 * Checks if the character is currently in a battle.
	 * 
	 * @return True if the character is currently in a battle.
	 * */
	public boolean isInBattle(){return getState() instanceof Battle;}
	
	/**
	 * Displays a conversation/dialogue on the map via a console at the bottom of the screen.
	 * 
	 * @param dialogue Dialogue for which to display text and iterate through.
	 * @param npc Object, such as a character or treasure chest, with which to interact.
	 * */
	public void WriteOnMap(Dialogue dialogue, InteractableObject npc) {
		
		GameState comp = getState();
		
		if (comp instanceof MapScreen){
			
			MapConsole console = new MapConsole(null, dialogue, this);
			((MapScreen)comp).setMapConsole(console);
			console.setInteractiveTile(npc);
			console.converse();
			
		}
		
	}
	
	/**
	 * Tests whether random encounters are enabled.
	 * If they are, chances a random encounter.
	 * Returns true if a random encounter is induced.
	 * 
	 * @param map Map on which to chance a random encounter.
	 * 
	 * @return True if random encounter induced, otherwise false.
	 * */
	@SuppressWarnings("unused")
	public boolean isEncounter(ParentMap map){
		return !SlickSKR.NO_ENCOUNTERS && map.randomEncounter();
	}
	
	/**
	 * Induces a random encounter.
	 * 
	 * @param map Map for which to form a random enemy formation.
	 * */
	public void encounter(ParentMap map){encounter(map.getEnemyFormation());}
	
	/**
	 * Induces a random encounter, but with a pre-defined enemy (so not really "random" as such).
	 * 
	 * @param enemy Opponent to face. As a single enemy, this is often a BossCharacter.
	 * */
	public void encounter(EnemyCharacter enemy){
		
		ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
		enemies.add(enemy);
		encounter(enemies);
		
	}
	
	/**
	 * Induces a random encounter, but with a pre-defined set of enemies (so not really "random" as such).
	 * 
	 * @param enemies Opponents to face.
	 * */
	public void encounter(ArrayList<EnemyCharacter> enemies){
		
		setBattle(enemies);
		swapView(1);
		
	}

	/**
	 * Swaps the currently displayed map.
	 * 
	 * @param x X coordinate at which to place the character.
	 * @param y Y coordinate at which to place the character.
	 * */
	public void setMap(ParentMap map, float x, float y) {
		((MapScreen)this.getState()).setMap(map, x, y);
	}
	
	/**
	 * Removes the map console, if present, from the screen.
	 * Used to dismiss character dialogues, item notifications, etc.
	 * */
	public void removeMapConsole(){
		
		GameState comp = getState();
		
		if (comp instanceof MapScreen){
			((MapScreen) comp).removeMapConsole();
		
		}
	}
		
}
