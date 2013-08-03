package battle;

import item.ConsumableItem;
import item.Item;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.BattleConsole;
import screen.GameScreen;
import screen.MessageBox;
import screen.SlickGameState;
import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;

import character.BossCharacter;
import character.CombatCapableCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Inventory;
import com.japanzai.skr.Party;
import com.japanzai.skr.SlickSKR;

import controls.SlickRectangle;

public class Battle extends SlickGameState{
	
	private ArrayList<EnemyCharacter> enemies;
	private ArrayList<PlayableCharacter> party;
	private GameScreen parent;
	
	private boolean running;
	
	private PlayableCharacter currentCharacter;
	
	private SlickRectangle[] rects;
	private SlickRectangle[] display; //TODO: change into two-column menu? eg. attack on left, targets on right
	private final String[] commands = {"Attack", "Techniques", "Inventory", "Run", "Back"};
	
	private final int ALLY_MODE = 222;
	private final int ATTACK_MODE = 223;
	private final int ITEM_MODE = 224;
	private final int TECHNIQUE_MODE = 225;
	private final int MENU_MODE = 226;
	private int mode = MENU_MODE;
	
	private Technique tech;
	private Item item;
	
	/**
	 * TODO: test furybreak
	 * TODO: panel down bottom with names and hp bars
	 * ATB gauge
	 * 
	 * */
	
	public Battle(GameScreen parent, ArrayList<EnemyCharacter> enemies){
		
		super(SlickSKR.BATTLE, parent);
		this.parent = parent;
		this.enemies = new ArrayList<EnemyCharacter>();
		this.party = Party.getCharactersInParty();
		rects = new SlickRectangle[0];
		//BattleConsole battleConsole = new BattleConsole(); //TODO: add battleconsole
		this.enemies = enemies;
		
		this.running = true;
		resetDefaultInterface();
		
	}
	
	public void start() throws SlickException{
		
		//make arraylist of all players and enemies, then order by speed?

		
		/*if (enemies.get(0) instanceof BossCharacter){
			BossCharacter bc = (BossCharacter) enemies.get(0);
			parent.WriteOnMap(bc.getDialogue());
		}*/
		
		if (running){
			resetDefaultInterface();
		}else{
			setDefaultInterface(this.currentCharacter);
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
	
	public void end(){parent.swapView(SlickSKR.MAP);}
	
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
				setDefaultInterface(this.currentCharacter);
			}else {
				EnemyCharacter character = (EnemyCharacter) c;
				character.invokeAI();
			}

		}else {
			c.incrementGauge();
		}
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		for (PlayableCharacter c : Party.getCharactersInParty()){
			c.instantiateForBattle();
			c.startBattle();
		}
			
		for (EnemyCharacter c : enemies){
			c.instantiateForBattle();
			c.startBattle();
		}
		
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		this.drawBattlePane(g);
		this.drawBattleParticipants(g);
		for (SlickRectangle rect : rects){rect.paint(g);}
		
	}
	
	public void drawBattlePane(Graphics g){
		final int constY = 100;
		final int partyY = 125;
		
		for (int i = 0; i < this.enemies.size(); i++){
			EnemyCharacter e = this.enemies.get(i);
			if (e.getSprite() == null){
				e.instantiateForBattle();
				e.startBattle();
			}
			g.drawImage(e.getSprite(), 25, partyY + constY * i);
		}
		
		for (int i = 0; i < this.party.size(); i++){
			g.drawImage(this.party.get(i).getSprite(), 727, partyY + constY * i);
		}
	}
	
	public void drawBattleParticipants(Graphics g){
		
		int x = 0;
		int y = 500;
		for (int i = 0; i < enemies.size(); i++){
			EnemyCharacter c = enemies.get(i);
			if (!c.isAlive()){
				//TODO: strikethrough
			}
			g.drawString(c.getStatus(), x, y + i * 25);
		}
		x = 340;
		for (int i = 0; i < party.size(); i++){
			PlayableCharacter c = party.get(i);
			if (!c.isAlive()){
				//TODO: strikethrough
			}
			g.drawString(c.getStatus(), x, y + i * 25);
		}
		
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		try {
			this.processMouseClick(1, x, y);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		for (SlickRectangle rect : display){
			if (rect.isWithinBounds(x, y)){
				//TODO: implement
				System.out.println("BattleMenu test click");
			}
		}
	}
	
	public void setDefaultInterface(PlayableCharacter c){
		
		this.currentCharacter = c;
		//this.mode = this.MENU_MODE;
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 484;
		
		rects = new SlickRectangle[4];
		String attackTag = c.getTemper() < 10 ? commands[0] : "Fury Break!";
		try {
			rects[0] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight, commands[0], false, attackTag);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		for (int i = 1; i < rects.length; i++){
			startY += 2;
			try {
				rects[i] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight ,commands[i], false);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		rects[1].setEnabled(c.getUsableTechniques().size() > 0);
		rects[2].setEnabled(Inventory.getConsumables().size() > 0);
		rects[3].setEnabled(!(this.enemies.get(0) instanceof BossCharacter));
		
		display = rects.clone();
		
	}
	
	public void resetDefaultInterface(){
		
		//this.mode = this.MENU_MODE;
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 484;
		
		rects = new SlickRectangle[4];
		for (int i = 0; i < rects.length; i++){
			try {
				rects[i] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight ,commands[i], false);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			startY += 2;
		}
		
		display = rects.clone();
		
	}
	
	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private void alert(PlayableCharacter c, String origin){
		if (mode == this.ALLY_MODE){
			BattleConsole.writeConsole("That " + origin + " can only be used on " + (c.isAlive() ? "fallen allies" : "the living"));
		}
	}
	
	private boolean test(PlayableCharacter c){
		if (mode == this.ALLY_MODE){
			if (this.tech != null){
				return c.isAlive() != ((HealingTechnique)this.tech).usedOnDead();
			}
			return c.isAlive() != ((ConsumableItem)this.item).usedOnDead();
		}
		return false;
	}
	
	public void process(String command){
		if (mode == this.ALLY_MODE){
			for (PlayableCharacter c : Party.getCharactersInParty()){
				if (c.getName().equals(command)){
					if (test(c)){
						this.currentCharacter.healAlly(c, (HealingTechnique)this.tech, (ConsumableItem)this.item, this.currentCharacter); //add check for appropriate type here
					}else{
						String type = this.tech != null ? "technique" : "item";
						alert(c, type);
					}
				}
			}
			this.item = null;
			this.tech = null;
			resetDefaultInterface();
			try {
				start(); //TODO: check if needed
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
		}else if (mode == this.ATTACK_MODE){
			for (EnemyCharacter e : this.enemies){
				if (e.getName().equals(command)){
					if (this.tech != null){
						this.currentCharacter.attack(e, (CombatTechnique)this.tech);
					}else if (this.currentCharacter.getTemper() == 10){
						this.currentCharacter.attack(e, this.currentCharacter.getFuryBreak());
					}else{
						this.currentCharacter.attack(e);
					}
				}
			}
			resetDefaultInterface();
			try {
				start();
			} catch (SlickException e1) {
				e1.printStackTrace();
			}
		}else if (mode == this.TECHNIQUE_MODE){
			for (Technique t : this.currentCharacter.getUsableTechniques()){
				if (t.getName().equals(command)){
					if (t instanceof CombatTechnique){
						CombatTechnique ct = (CombatTechnique) t;
						//setNewInterface(this.currentCharacter, new BattleAttackListener(enemies, currentCharacter, this.menu, ct));
						this.tech = ct;
						this.mode = this.ATTACK_MODE;
					}else if (t instanceof HealingTechnique){
						HealingTechnique heal = (HealingTechnique) t;
						//setNewInterface(this.currentCharacter, new BattleAllyListener(currentCharacter, this.menu, heal, null));
						this.tech = heal;
						this.mode = this.ALLY_MODE;
					}
				}
			}
		}else if (mode == this.ITEM_MODE){
			for (ConsumableItem t : Inventory.getConsumables()){
				if (t.getName().equals(command)){
					//setNewInterface(this.currentCharacter, new BattleAllyListener(currentCharacter, this.menu, null, t));
					this.mode = this.ALLY_MODE;
					this.item = t;
				}
			}
		}else{ //TODO: turn to else if MENU_MODE?
			if (command.equals(commands[0])){
				//this.battleMenu.setNewInterface(this.currentCharacter, new BattleAttackListener(enemies, currentCharacter, this.battleMenu, null));
			}else if (command.equals(commands[1])){
				//this.battleMenu.setNewInterface(this.currentCharacter, this.currentCharacter.getUsableTechniques(), new BattleTechniqueListener(enemies, currentCharacter, this.battleMenu));
			}else if (command.equals(commands[2])){
				//setNewInterface(this.currentCharacter, Inventory.getConsumables(), new BattleItemListener(currentCharacter, this.battleMenu));
				this.mode = this.ITEM_MODE;
			}else if (command.equals(commands[3])){
				MessageBox.InfoBox("Loser", "Running away?");
				end();
				//System.exit(0);
			}else if (command.equals(commands[4])){
				setDefaultInterface(this.currentCharacter);
			}
		}
	}

	
	public void setEnemies(ArrayList<EnemyCharacter> enemies) {
		this.enemies = enemies;
	}
	
}
