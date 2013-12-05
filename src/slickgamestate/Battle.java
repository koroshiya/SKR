package slickgamestate;

import item.ConsumableItem;
import item.Item;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.GameScreen;
import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;
import character.BossCharacter;
import character.CombatCapableCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Inventory;
import com.japanzai.skr.Party;

import console.BattleConsole;
import controls.SlickRectangle;

public class Battle extends SlickGameState{
	
	private ArrayList<EnemyCharacter> enemies;
	private ArrayList<PlayableCharacter> party;
	private GameScreen parent;
	
	private boolean running;
	
	private PlayableCharacter currentCharacter;
	
	private SlickRectangle[] rects;
	//private SlickRectangle[] display; //TODO: change into two-column menu? eg. attack on left, targets on right
	private final String[] commands = {"Attack", "Techniques", "Inventory", "Run", "Back"};
	
	private final int ALLY_MODE = 222;
	private final int ATTACK_MODE = 223;
	private final int ITEM_MODE = 224;
	private final int TECHNIQUE_MODE = 225;
	private final int MENU_MODE = 226;
	private final int VICTORY_MODE = 228;
	private final int LOSS_MODE = 229;
	private final int RUN_MODE = 230;
	private final String VICTORY = "End battle";
	private Font VICTORY_FONT;
	private int mode = MENU_MODE;
	
	private Technique tech;
	private Item item;

	final int buttonWidth = 150;
	final int buttonHeight = 25;
	final int startX = 650;
	final int startY = 484;
	
	final int constY = 100;
	final int partyY = 125;
	
	/**
	 * TODO: test furybreak
	 * TODO: panel down bottom with names and hp bars
	 * ATB gauge
	 * 
	 * */
	
	public Battle(GameScreen parent, ArrayList<EnemyCharacter> enemies){
		
		super(SlickSKR.BATTLE, parent);
		this.parent = parent;
		this.party = Party.getCharactersInParty();
		rects = new SlickRectangle[0];
		this.enemies = enemies == null ? new ArrayList<EnemyCharacter>() : enemies;
		
		this.running = true;
		resetDefaultInterface();
		
	}
	
	private void start() throws SlickException{
		
		//TODO: make arraylist of all players and enemies, then order by speed?
		
		/*if (enemies.get(0) instanceof BossCharacter){
			BossCharacter bc = (BossCharacter) enemies.get(0);
			parent.WriteOnMap(bc.getDialogue());
		}*/
		if (this.enemies.size() == 0){return;}
		mode = this.MENU_MODE;
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
			mode = VICTORY_MODE;
			SlickSKR.PlaySFX("other/public/decide.ogg");
		}else if (partyLost()){
			mode = LOSS_MODE;
			SlickSKR.PlaySFX("weeden/die-or-lose-life.ogg");
			if (Party.getCharactersAlive(true).size() == 0){
				parent.gameover();
			}else {
				Party.formValidParty();
			}
		}
		setTargetBattleEnd();
		
	}
	
	public void end(){
		BattleConsole.cleanConsole();
		parent.swapView(SlickSKR.MAP);
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
			BattleConsole.writeConsole(c.getName() + "'s turn");
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
		VICTORY_FONT = SlickSKR.loadFont("Ubuntu-B.ttf", 48);
	}
	
	public void enter(GameContainer arg0, StateBasedGame arg1){

		this.party = Party.getCharactersInParty();
		SlickSKR.PlayMusic("other/public/battle.ogg");
		
		for (PlayableCharacter c : this.party){
			c.instantiateForBattle();
			c.startBattle();
		}
			
		for (EnemyCharacter c : enemies){
			c.instantiateForBattle();
			c.startBattle();
		}
		
		this.currentCharacter = this.party.get(0);
		
		BattleConsole.cleanConsole();
		resetDefaultInterface();
		
		try {
			this.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		g.setFont(SlickSKR.DEFAULT_FONT);
		
		this.drawBattlePane(g);
		this.drawBattleParticipants(g);
		for (SlickRectangle rect : rects){rect.paintCenter(g);}
		BattleConsole.paint(g);
		String message = "";
		if (mode == VICTORY_MODE){
			message = "Victory";
		}else if (mode == LOSS_MODE){
			message = "Defeated";
		}else if (mode == RUN_MODE){
			message = "Coward";
		}
		if (!message.equals("")){
			VICTORY_FONT.drawString(300, 250, message);
		}
		
	}
	
	public void drawBattlePane(Graphics g){
		
		for (int i = 0; i < this.enemies.size(); i++){
			this.enemies.get(i).getSprite().draw(25, partyY + constY * i);
		}
		
		for (int i = 0; i < this.party.size(); i++){
			this.party.get(i).getSprite().draw(727, partyY + constY * i);
		}
	}
	
	public void drawBattleParticipants(Graphics g){
		
		for (int i = 0; i < enemies.size(); i++){
			drawChar(g, enemies.get(i), i, 0);
		}
		for (int i = 0; i < party.size(); i++){
			drawChar(g, party.get(i), i, 340);
		}
		
	}
	
	private void drawChar(Graphics g, CombatCapableCharacter c, int i, int x){
		int tempY = 500 + i * 25;
		String nick = c.getNickName();
		if (!c.isAlive()){
			
			final int textx = SlickSKR.DEFAULT_FONT.getWidth(nick);
			final int texty = SlickSKR.DEFAULT_FONT.getHeight(nick) / 2;
			g.drawLine(x, tempY + texty, x + textx, tempY + texty);
		}
		g.drawString(nick, x, tempY);
		g.drawString(c.getStatus(), x + 150, tempY);
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
		
		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				try {
					process(rect.getTag());
				} catch (SlickException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public void setDefaultInterface(PlayableCharacter c){
		
		this.currentCharacter = c;
		//this.mode = this.MENU_MODE;
		
		setMenu();
		//display = rects.clone();
		
	}
	
	private void setMenu(){
		
		rects = new SlickRectangle[4];
		String attackTag = this.currentCharacter.getTemper() < 10 ? commands[0] : "Fury Break!";
		try {
			rects[0] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight, commands[0], false, attackTag);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		for (int i = 1; i < rects.length; i++){
			try {
				rects[i] = new SlickRectangle(startX, startY + buttonHeight * i, buttonWidth, buttonHeight, commands[i], false);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		rects[0].setEnabled(true);
		rects[1].setEnabled(this.currentCharacter.getUsableTechniques().size() > 0);
		rects[2].setEnabled(Inventory.getConsumables().size() > 0);
		if (this.enemies.size() > 0){
			rects[3].setEnabled(!(this.enemies.get(0) instanceof BossCharacter));
		}
	}
	
	private SlickRectangle setMenuItem(int i) throws SlickException{
		return new SlickRectangle(startX, startY + buttonHeight * i, buttonWidth, buttonHeight, "");
	}
	
	public void resetDefaultInterface(){
		
		if (this.currentCharacter != null){
			setMenu();
		}
		
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
	
	private boolean isItemApplicable(PlayableCharacter c){
		if (mode == this.ALLY_MODE){
			if (this.tech != null){
				return c.isAlive() != ((HealingTechnique)this.tech).usedOnDead();
			}
			return c.isAlive() != ((ConsumableItem)this.item).usedOnDead();
		}
		return false;
	}
	
	public void process(String command) throws SlickException{
		System.out.println(command);
		if (mode == this.ALLY_MODE){
			System.out.println("ALLY MODE");
			processAllyMode(command);
		}else if (mode == this.ATTACK_MODE){
			System.out.println("ATTACK MODE");
			processAttackMode(command);
		}else if (mode == this.TECHNIQUE_MODE){
			System.out.println("TECHNIQUE MODE");
			processTechniqueMode(command);
		}else if (mode == this.ITEM_MODE){
			System.out.println("ITEM MODE");
			processItemMode(command);
		}else if (mode == this.VICTORY_MODE || mode == this.RUN_MODE || mode == this.LOSS_MODE){
			System.out.println("VICTORY MODE");
			end();
		}else{ //TODO: turn to else if MENU_MODE?
			processMenuMode(command);
		}
	}
	
	private void processAttackMode(String command) {
		if (command.equals("")){return;}
		int i;
		try{
			i = Integer.parseInt(command);
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
			return;
		}
		EnemyCharacter e = this.enemies.get(i);
		boolean alive = e.isAlive();
		if (!alive){
			BattleConsole.writeConsole("Can't attack deceased opponents");
		}else{
			if (this.tech != null){
				this.currentCharacter.attack(e, (CombatTechnique)this.tech);
			}else if (this.currentCharacter.getTemper() == 10){
				this.currentCharacter.attack(e, this.currentCharacter.getFuryBreak());
			}else{
				this.currentCharacter.attack(e);
			}
		}
		this.tech = null;
		this.mode = this.MENU_MODE;
		if (alive){
			try {
				start();
			} catch (SlickException e1) {
				e1.printStackTrace();
			}
		}
		//resetDefaultInterface(); //TODO: make blank?
		
	}
	
	private void processAllyMode(String command) {
		for (PlayableCharacter c : Party.getCharactersInParty()){
			if (c.getName().equals(command)){
				if (isItemApplicable(c)){
					this.currentCharacter.healAlly(c, (HealingTechnique)this.tech, (ConsumableItem)this.item, this.currentCharacter); //add check for appropriate type here
					try {
						start();
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}else{
					String type = this.tech != null ? "technique" : "item";
					alert(c, type);
				}
				break;
			}
		}
		this.item = null;
		this.tech = null;
		this.mode = this.MENU_MODE;
		resetDefaultInterface();
		/*try {
			start();
		} catch (SlickException e) {
			e.printStackTrace();
		}*/
	}
	
	private void processTechniqueMode(String command) throws SlickException{
		for (Technique t : this.currentCharacter.getUsableTechniques()){
			if (t.getName().equals(command)){
				if (t instanceof CombatTechnique){
					this.tech = t;
					setTargetEnemies();
				}else if (t instanceof HealingTechnique){
					this.tech = t;
					setTargetAllies();
				}else{
					System.out.println("This technique cannot be used during battle");
				}
				break;
			}
		}
	}
	
	private void processItemMode(String command) throws SlickException{
		for (ConsumableItem t : Inventory.getConsumables()){
			if (t.getName().equals(command)){
				this.item = t;
				setTargetAllies();
				break;
			}
		}
	}
	
	private void processMenuMode(String command) throws SlickException{
		if (command.equals(commands[0])){
			System.out.println("attack");
			setTargetEnemies();
		}else if (command.equals(commands[1])){
			System.out.println("techniques");
			setTargetTechniques();
		}else if (command.equals(commands[2])){
			System.out.println("items");
			setTargetItems();
		}else if (command.equals(commands[3])){
			mode = RUN_MODE;
			SlickSKR.PlaySFX("other/attrib/game-over.ogg");
			setTargetBattleEnd();
		}else if (command.equals(commands[4])){
			setDefaultInterface(this.currentCharacter);
		}
	}
	
	public void setEnemies(ArrayList<EnemyCharacter> enemies) {
		this.enemies = enemies;
	}
	
	private void setTargetEnemies() throws SlickException{
		this.mode = this.ATTACK_MODE;
		rects = new SlickRectangle[this.enemies.size()];
		for (int i = 0; i < this.enemies.size(); i++){
			rects[i] = setMenuItem(i);
			if (this.enemies.get(i).isAlive()){
				rects[i].setText(Integer.toString(i), this.enemies.get(i).getName());
			}else{
				rects[i].setText("");
			}
		}
	}
	
	private void setTargetAllies() throws SlickException{
		this.mode = this.ALLY_MODE;
		rects = new SlickRectangle[party.size()];
		for (int i = 0; i < party.size(); i++){
			rects[i] = setMenuItem(i);
			rects[i].setText(party.get(i).getName());
		}
	}

	private void setTargetBattleEnd() throws SlickException{
		rects = new SlickRectangle[4];
		for (int i = 0; i < rects.length; i++){
			rects[i] = this.setMenuItem(i);
		}
		rects[0].setText(VICTORY);
	}

	private void setTargetTechniques() throws SlickException{mode = this.TECHNIQUE_MODE;
		ArrayList<Technique> techs = this.currentCharacter.getTechniques();
		rects = new SlickRectangle[techs.size()];
		for (int i = 0; i < techs.size(); i++){
			rects[i] = setMenuItem(i);
			rects[i].setText(techs.get(i).getName());
		}
	}
	
	private void setTargetItems() throws SlickException{
		this.mode = this.ITEM_MODE;
		ArrayList<Item> items = Inventory.getConsumablesAsItems();
		rects = new SlickRectangle[items.size()];
		for (int i = 0; i < items.size(); i++){
			rects[i] = setMenuItem(i);
			rects[i].setText(items.get(i).getName());
		}
	}
	
}
