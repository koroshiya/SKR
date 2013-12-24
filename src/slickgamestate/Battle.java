package slickgamestate;

import item.ConsumableItem;
import item.Item;

import java.util.ArrayList;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

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
import controls.SlickBlankRectangle;
import controls.SlickRectangle;

public class Battle extends SlickGameState{
	
	private ArrayList<EnemyCharacter> enemies;
	private ArrayList<PlayableCharacter> party;
	private GameScreen parent;
	
	private boolean running;
	private int attackTime = 0;
	
	private PlayableCharacter currentCharacter;
	
	private SlickRectangle[] rects;
	//private SlickRectangle[] display; //TODO: change into two-column menu? eg. attack on left, targets on right
	private final String[] commands;
	
	private final int ALLY_MODE = 222;
	private final int ATTACK_MODE = 223;
	private final int ITEM_MODE = 224;
	private final int TECHNIQUE_MODE = 225;
	private final int MENU_MODE = 226;
	private final int VICTORY_MODE = 228;
	private final int LOSS_MODE = 229;
	private final int RUN_MODE = 230;
	private final String VICTORY = SlickSKR.getValueFromKey("screen.battle.commands.end");
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
	 * TODO: fix battle animations
	 * TODO: add delays
	 * ATB gauge
	 * 
	 * */
	
	public Battle(GameScreen parent, ArrayList<EnemyCharacter> enemies){
		
		super(SlickSKR.BATTLE, parent);
		this.parent = parent;
		this.party = Party.getCharactersInParty();
		rects = new SlickRectangle[0];
		this.enemies = enemies == null ? new ArrayList<EnemyCharacter>() : enemies;
		commands = new String[]{
			SlickSKR.getValueFromKey("screen.battle.commands.attack"),
			SlickSKR.getValueFromKey("screen.battle.commands.techniques"),
			SlickSKR.getValueFromKey("screen.battle.commands.inventory"),
			SlickSKR.getValueFromKey("screen.battle.commands.run"),
			SlickSKR.getValueFromKey("screen.battle.commands.back"),
		};
		
		this.running = true;
		resetDefaultInterface();
		
	}
	
	private void start() {

		attackTime = 120;
		SlickGameState.setFlush(true, false);
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
			//SlickSKR.PlaySFX("weeden/die-or-lose-life.ogg");
			if (Party.getCharactersAlive(true).size() == 0){
				parent.swapView(SlickSKR.GAMEOVER);
			}else {
				Party.formValidParty();
			}
		}
		setTargetBattleEnd();
		
	}
	
	/**
	 * Ends battle, swaps back to map
	 * */
	public void end(){
		BattleConsole.cleanConsole();
		parent.swapView(SlickSKR.MAP);
	}
	
	/**
	 * @return True if all party characters participating in battle have been defeated
	 * */
	private boolean partyLost(){
		
		for (PlayableCharacter c : Party.getCharactersInParty()){
			if (c.isAlive()){
				return false;
			}
		}

		return true;
	}
	
	/**
	 * @return True if all enemy characters have been defeated
	 * */
	private boolean partyWon(){

		for (EnemyCharacter c : enemies){
			if (c.isAlive()){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @param c Character whose turn it is
	 */
	private synchronized void takeTurn(CombatCapableCharacter c){run(c);}
	
	/**
	 * Gives the player an opportunity to attack, heal, run, etc.
	 * 
	 * @param c Character whose turn it is
	 */
	public void run(CombatCapableCharacter c){
		if (c.getGauge() >= 10){ //add check for qued? allow for overlap
			running = false;
			BattleConsole.writeConsole(c.getNickName() + SlickSKR.getValueFromKey("screen.battle.turn"));
			if (c instanceof PlayableCharacter){
				PlayableCharacter character = (PlayableCharacter) c;
				this.currentCharacter = character;
				setDefaultInterface(this.currentCharacter);
			}else {
				EnemyCharacter character = (EnemyCharacter) c;
				character.invokeAI();
				attackTime = 120;
			}

		}else {
			c.incrementGauge();
		}
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2){

		if (attackTime > 0){
			attackTime--;
			if (attackTime == 0){
				SlickGameState.setFlush(true, false);
			}
		}
		super.checkCursor(arg0, rects);
		
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1){

		this.party = Party.getCharactersInParty();
		if (this.enemies.get(0) instanceof BossCharacter){
			BossCharacter boss = (BossCharacter) this.enemies.get(0);
			if (boss.getBattleMusic() != ""){
				SlickSKR.PlayMusic(boss.getBattleMusic());
			}else{
				SlickSKR.PlayMusic("other/public/boss1.ogg");
			}
		}else{
			SlickSKR.PlayMusic("other/public/battle.ogg");
		}
		
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
		
		this.start();
		attackTime = 0;
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) {
		
		if (this.attackTime > 0 || SlickGameState.needFlush()){
			this.drawBattlePane(g);
		}
		
		if (SlickGameState.needFlush()){
			
			this.drawBattleParticipants(g);
			for (SlickRectangle rect : rects){rect.paintCenter(g);}
			BattleConsole.paint(g);
			String message = "";
			if (mode == VICTORY_MODE){
				message = SlickSKR.getValueFromKey("screen.battle.victory");
			}else if (mode == LOSS_MODE){
				message = SlickSKR.getValueFromKey("screen.battle.defeat");
			}else if (mode == RUN_MODE){
				message = SlickSKR.getValueFromKey("screen.battle.escape");
			}
			if (!message.equals("")){
				SlickSKR.loadFont("Ubuntu-B.ttf", 48).drawString(300, 250, message);
			}
			SlickGameState.capture(g);
		}else{
			SlickGameState.drawCache(g);
		}
		
	}
	
	/**
	 * Draws all enemy and player sprites. Potentially animations, usually static figures.
	 * 
	 * @param g Graphics context within which to draw the battle pane
	 */
	public void drawBattlePane(Graphics g){
		int i = -1;
		int total = this.enemies.size();
		CombatCapableCharacter c;
		int inc;
		while (++i < total){
			c = this.enemies.get(i);
			inc = c instanceof BossCharacter ? SlickSKR.scaled_icon_size * 2 : SlickSKR.scaled_icon_size;
			if (c.isAttacking()){
				c.getAnimatedFrame().draw(0, partyY + constY * i, 0 + inc, (partyY + constY * i) + inc, 
										0, 0, c.getAnimatedFrame().getWidth(), c.getAnimatedFrame().getHeight());
				//c.getAnimatedFrame().draw(0, partyY + constY * i);
			}else{
				//c.getSprite().draw(0, partyY + constY * i);
				c.getBattleIcon().draw(0, partyY + constY * i, 0 + inc, (partyY + constY * i) + inc, 
						0, 0, c.getBattleIcon().getWidth(), c.getBattleIcon().getHeight());
			}
		}
		
		i = -1;
		total = this.party.size();
		while (++i < total){
			c = this.party.get(i);
			if (c.isAttacking()){
				c.getAnimatedFrame().draw(727, partyY + constY * i);
			}else{
				c.getBattleIcon().draw(727, partyY + constY * i);
			}
		}
	}
	
	public void drawBattleParticipants(Graphics g){

		int i = -1;
		int total = this.enemies.size();
		while (++i < total){
			drawChar(g, enemies.get(i), i, 0);
		}
		
		i = -1;
		total = this.party.size();
		while (++i < total){
			drawChar(g, party.get(i), i, 340);
		}
		
	}
	
	private void drawChar(Graphics g, CombatCapableCharacter c, int i, int x){
		int tempY = 500 + i * 25;
		String nick = c.getNickName();
		Font f = g.getFont();
		if (!c.isAlive()){
			final int textx = x + f.getWidth(nick);
			final int texty = tempY + f.getHeight(nick) / 2;
			g.drawLine(x, texty, textx, texty);
		}
		g.drawString(nick, x, tempY);
		g.drawString(c.getStatus(), x + 150, tempY);
	}
	
	@Override
	public void mouseReleased(int arg0, int x, int y) {
		this.processMouseClick(1, x, y);
	}
	
	@Override
	public void processMouseClick(int clickCount, int x, int y) {
		
		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				process(rect.getTag());
				SlickGameState.setFlush(true, false);
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
		String attackTag = this.currentCharacter.getTemper() < 10 ? commands[0] : SlickSKR.getValueFromKey("screen.battle.commands.fury");
		rects[0] = new SlickBlankRectangle(startX, startY, buttonWidth, buttonHeight, commands[0], false, attackTag, true);
		
		int i = 0; //NOTE: not a mistake; should be 0
		int total = rects.length;
		while (++i < total){
			rects[i] = new SlickBlankRectangle(startX, startY + buttonHeight * i, buttonWidth, buttonHeight, commands[i], false);
		}
		
		rects[0].setEnabled(true);
		rects[1].setEnabled(this.currentCharacter.getUsableTechniques().size() > 0);
		rects[2].setEnabled(Inventory.getConsumables().size() > 0);
		if (this.enemies.size() > 0){
			rects[3].setEnabled(!(this.enemies.get(0) instanceof BossCharacter));
		}
	}
	
	private SlickRectangle setMenuItem(int i) {
		return new SlickBlankRectangle(startX, startY + buttonHeight * i, buttonWidth, buttonHeight, "", true);
	}
	
	public void resetDefaultInterface(){
		
		if (this.currentCharacter != null){
			setMenu();
		}
		
	}
	
	@Override
	public void keyReleased(int arg0, char arg1) {}
	
	private void alert(PlayableCharacter c, String origin){
		if (mode == this.ALLY_MODE){
			BattleConsole.writeConsole(
				SlickSKR.getValueFromKey("screen.battle.alert.fragment.start") + " " + 
				origin + " " + SlickSKR.getValueFromKey("screen.battle.alert.fragment.middle") + " " + 
				SlickSKR.getValueFromKey("screen.battle.alert.fragment." + (c.isAlive() ? "dead" : "alive"))
			);
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
	
	public void process(String command){
		Log.debug(command);
		if (mode == this.ALLY_MODE){
			Log.debug("ALLY MODE");
			processAllyMode(command);
		}else if (mode == this.ATTACK_MODE){
			Log.debug("ATTACK MODE");
			processAttackMode(command);
		}else if (mode == this.TECHNIQUE_MODE){
			Log.debug("TECHNIQUE MODE");
			processTechniqueMode(command);
		}else if (mode == this.ITEM_MODE){
			Log.debug("ITEM MODE");
			processItemMode(command);
		}else if (mode == this.VICTORY_MODE || mode == this.RUN_MODE || mode == this.LOSS_MODE){
			Log.debug("VICTORY MODE");
			end();
		}else{ //TODO: turn to else if MENU_MODE?
			processMenuMode(command);
		}
	}
	
	/**
	 * Directs and coordinates a player attack towards an opponent.
	 * 
	 * @param command String containing the ID of the opponent to attack.
	 * */
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
			BattleConsole.writeConsole(SlickSKR.getValueFromKey("screen.battle.alert.cannotattackdeceased"));
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
			start();
		}
		//resetDefaultInterface(); //TODO: make blank?
		
	}
	
	/**
	 * Uses a healing item or technique on an ally.
	 * 
	 * @param command Name of the ally to heal.
	 * */
	private void processAllyMode(String command) {
		for (PlayableCharacter c : Party.getCharactersInParty()){
			if (c.getName().equals(command)){
				if (isItemApplicable(c)){
					this.currentCharacter.healAlly(c, (HealingTechnique)this.tech, (ConsumableItem)this.item, this.currentCharacter); //add check for appropriate type here
					start();
				}else{
					String type = SlickSKR.getValueFromKey("screen.battle.use." + (this.tech != null ? "technique" : "item"));
					alert(c, type);
				}
				break;
			}
		}
		this.item = null;
		this.tech = null;
		this.mode = this.MENU_MODE;
		resetDefaultInterface();
	}
	
	private void processTechniqueMode(String command) {
		for (Technique t : this.currentCharacter.getUsableTechniques()){
			if (t.getName().equals(command)){
				if (t instanceof CombatTechnique){
					this.tech = t;
					setTargetEnemies();
				}else if (t instanceof HealingTechnique){
					this.tech = t;
					setTargetAllies();
				}else{
					Log.debug(SlickSKR.getValueFromKey("screen.battle.alert.cannotusetechnique"));
				}
				break;
			}
		}
	}
	
	private void processItemMode(String command) {
		for (ConsumableItem t : Inventory.getConsumables()){
			if (t.getName().equals(command)){
				this.item = t;
				setTargetAllies();
				break;
			}
		}
	}
	
	private void processMenuMode(String command) {
		if (command.equals(commands[0])){
			Log.debug("attack");
			setTargetEnemies();
		}else if (command.equals(commands[1])){
			Log.debug("techniques");
			setTargetTechniques();
		}else if (command.equals(commands[2])){
			Log.debug("items");
			setTargetItems();
		}else if (command.equals(commands[3])){
			mode = RUN_MODE;
			//SlickSKR.PlaySFX("other/attrib/game-over.ogg"); //TODO: new music for running away
			setTargetBattleEnd();
		}else if (command.equals(commands[4])){
			setDefaultInterface(this.currentCharacter);
		}
	}
	
	public void setEnemies(ArrayList<EnemyCharacter> enemies) {
		this.enemies = enemies;
	}
	
	private void setTargetEnemies() {
		this.mode = this.ATTACK_MODE;
		int i = -1;
		int total = enemies.size();
		rects = new SlickRectangle[total];
		while (++i < total){
			rects[i] = setMenuItem(i);
			if (this.enemies.get(i).isAlive()){
				rects[i].setText(Integer.toString(i), this.enemies.get(i).getName());
			}else{
				rects[i].setText("");
			}
		}
	}
	
	private void setTargetAllies() {
		this.mode = this.ALLY_MODE;
		int i = -1;
		int total = party.size();
		rects = new SlickRectangle[total];
		while (++i < total){
			rects[i] = setMenuItem(i);
			rects[i].setText(party.get(i).getName());
		}
	}

	private void setTargetBattleEnd() {
		rects = new SlickRectangle[4];
		int i = -1;
		int total = rects.length;
		while (++i < total){
			rects[i] = this.setMenuItem(i);
		}
		rects[0].setText(VICTORY);
	}

	private void setTargetTechniques() {
		mode = this.TECHNIQUE_MODE;
		ArrayList<Technique> techs = this.currentCharacter.getTechniques();
		rects = new SlickRectangle[techs.size()];
		int i = -1;
		int total = techs.size();
		while (++i < total){
			rects[i] = setMenuItem(i);
			rects[i].setText(techs.get(i).getName());
		}
	}
	
	private void setTargetItems() {
		this.mode = this.ITEM_MODE;
		ArrayList<Item> items = Inventory.getConsumablesAsItems();
		int i = -1;
		int total = items.size();
		rects = new SlickRectangle[total];
		while (++i < total){
			rects[i] = setMenuItem(i);
			rects[i].setText(items.get(i).getName());
		}
	}
	
}
