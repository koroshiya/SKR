package battle;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import screen.BattleConsole;
import screen.GameScreen;
import screen.MessageBox;

import character.CombatCapableCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Party;

public class Battle extends BasicGameState{
	
	private ArrayList<EnemyCharacter> enemies;
	private GameScreen parent;
	private BattleMenu battleMenu;
	
	private boolean running;
	
	private PlayableCharacter currentCharacter;	
	
	/**
	 * TODO: test furybreak
	 * */
	
	public Battle(ArrayList<EnemyCharacter> enemies, GameScreen parent){
		
		this.enemies = enemies;
		this.parent = parent;
		
		BattleConsole battleConsole = new BattleConsole();		
		this.battleMenu = new BattleMenu(enemies, this);
		BattlePane pane = new BattlePane(enemies);
		BattleParticipants battleParticipants = new BattleParticipants(enemies);
		//this.battleParticipants = new BattleParticipants(enemies);
		
		/*this.add(battleConsole);
		this.add(pane);
		this.add(battleParticipants);
		this.add(this.battleMenu);*/
		
		for (PlayableCharacter c : Party.getCharactersInParty()){
			c.startBattle();
		}
			
		for (EnemyCharacter c : enemies){
			c.startBattle();
		}
		
		this.running = true;
		
	}
	
	public void start() throws SlickException{
		
		//make arraylist of all players and enemies, then order by speed?

		
		/*if (enemies.get(0) instanceof BossCharacter){
			BossCharacter bc = (BossCharacter) enemies.get(0);
			parent.WriteOnMap(bc.getDialogue());
		}*/
		
		if (running){
			battleMenu.resetDefaultInterface();
		}else{
			battleMenu.setDefaultInterface(this.currentCharacter);
		}
		
		do {
			
			for (PlayableCharacter c : Party.getCharactersInParty()){
				if (c.isAlive()){
					running = true;
					takeTurn(c);
					if (partyWon() || partyLost()){break;}
					else if (!running){return;}
				}
			}
				
			if (partyWon() || partyLost()){break;}
			
			for (EnemyCharacter c : enemies){
				if (c.isAlive()){
					takeTurn(c);
					if (partyWon() || partyLost()){break;}
				}
			}
			
		}while (!(partyWon() || partyLost()));
		
		if (partyWon()){
			MessageBox.InfoBox("Victory", "Winner!");
		}else if (partyLost()){
			MessageBox.InfoBox("You lose...", "What a shame...");
			//TODO: if party member not participating still alive, return to map and swap character in/out party
			//TODO: else, end program //TODO: implement game over screen
			if (Party.getCharactersAlive(true).size() == 0){
				parent.gameover();
			}else {
				Party.formValidParty();
			}
		}else {
			MessageBox.InfoBox("Loser", "Pfft");
		}
		
		end();
		//System.exit(0);
		
	}
	
	public void end(){
		parent.swapToMap();
	}
	
	private boolean partyLost(){
		
		for (PlayableCharacter c : Party.getCharactersInParty()){
			if (c.isAlive()){
				return false;
			}
		}

		return true;
	}
	
	private boolean partyWon(){

		for (EnemyCharacter c : enemies){
			if (c.isAlive()){
				return false;
			}
		}
		
		return true;
	}
	
	private synchronized void takeTurn(CombatCapableCharacter c){
		run(c);
	}
	
	public void run(CombatCapableCharacter c){
		if (c.getGauge() >= 10){ //add check for qued? allow for overlap
			running = false;
			BattleConsole.writeConsole("\n" + c.getName() + "'s turn");
			if (c instanceof PlayableCharacter){
				PlayableCharacter character = (PlayableCharacter) c;
				this.currentCharacter = character;
				battleMenu.setDefaultInterface(this.currentCharacter);
			}else {
				EnemyCharacter character = (EnemyCharacter) c;
				character.invokeAI();
			}

			c.getSprite().updateUI();

		}else {
			c.incrementGauge();
		}
	}
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
		
}
