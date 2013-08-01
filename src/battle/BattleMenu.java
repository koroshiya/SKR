package battle;

import interfaces.SlickDrawableFrame;
import interfaces.SlickEventHandler;
import item.ConsumableItem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;

import technique.Technique;

import character.BossCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Inventory;
import com.japanzai.skr.Party;

import controls.SlickRectangle;

public class BattleMenu implements SlickDrawableFrame, SlickEventHandler {

	private SlickRectangle[] rects;
	private SlickRectangle[] display; //TODO: change into two-column menu? eg. attack on left, targets on right
	private final String[] commands = {"Attack", "Techniques", "Inventory", "Run"};
	
	private final String strBack = "Back";
	
	private ArrayList<EnemyCharacter> enemies;
	private PlayableCharacter currentCharacter;
	
	private final Battle battle;

	public BattleMenu(ArrayList<EnemyCharacter> enemies, Battle battle){
		
		//BattleMenuListener listener = new BattleMenuListener();
		this.enemies = enemies;
		this.currentCharacter = null;
		this.battle = battle;
		
		resetDefaultInterface();
		
	}
	
	public void start() throws SlickException{
		this.battle.start();
	}
	
	public void setDefaultInterface(PlayableCharacter c){
		
		this.currentCharacter = c;
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, c, 
				battle, this, enemies);
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 484;
		
		rects = new SlickRectangle[4];
		String attackTag = c.getTemper() < 10 ? commands[0] : "Fury Break!";
		rects[0] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight, commands[0], false, attackTag);
		for (int i = 1; i < rects.length; i++){
			startY += 2;
			rects[i] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight ,commands[i], false);
		}

		rects[1].setEnabled(c.getUsableTechniques().size() > 0);
		rects[2].setEnabled(Inventory.getConsumables().size() > 0);
		rects[3].setEnabled(!(this.enemies.get(0) instanceof BossCharacter));
		
		display = rects.clone();
		
	}
	
	public void setNewInterface(PlayableCharacter character, ActionListener act){
		
		this.currentCharacter = character;

		if (act instanceof BattleAllyListener){
			ArrayList<PlayableCharacter> allies = Party.getCharactersInParty();
			for (int i = 0; i < allies.size(); i++){
				PlayableCharacter c = allies.get(i);
				setButton(c.getNickName(), c.getName(), act); //change to getNickName
			}
			
			display = rects.clone(); //TODO: change
		}else{
			for (int i = 0; i < this.enemies.size(); i++){
				EnemyCharacter e = enemies.get(i); //TODO: display targets
				setButton(e.getNickName(), e.getName(), act);
				if (!e.isAlive()){button.setEnabled(false);}
			}
			
			display = rects.clone(); //TODO: change
		}
		
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, character, 
				battle, this, enemies);
		setButton(strBack, null, listener);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void setNewInterface(PlayableCharacter character,
			ArrayList<?> list, ActionListener act) {

		this.currentCharacter = character;
		
		if (list.get(0) instanceof Technique){
			ArrayList<Technique> usableTechniques = (ArrayList<Technique>) list;
			
			for (Technique t : usableTechniques){ //use index instead?
				JButton button = setButton(t.getName(), t.getName(), act);
				if (!t.canUse()){button.setEnabled(false);}
			}
			
			display = rects.clone(); //TODO: change
		}else {
			ArrayList<ConsumableItem> usableItems = (ArrayList<ConsumableItem>) list;
			for (ConsumableItem i : usableItems){ //use index instead?
				JButton button = setButton(i.getName(), i.getName(), act);
				if (!i.canUse()){button.setEnabled(false);}
			}
			
			display = rects.clone(); //TODO: change
		}
		
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, character, 
				battle, this, enemies);
		
		setButton(strBack, strBack, listener);
	
	}
	
	public void resetDefaultInterface(){
		
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, this.currentCharacter, 
				battle, this, enemies);
		
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 484;
		
		rects = new SlickRectangle[4];
		for (int i = 0; i < rects.length; i++){
			rects[i] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight ,commands[i], false);
			startY += 2;
		}
		
		display = rects.clone();
		
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {}

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
	public void mouseWheelMoved(int arg0) {}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {return false;}

	@Override
	public void setInput(Input arg0) {}

	@Override
	public void processMouseClick(int clickCount, int x, int y) throws IOException, ClassNotFoundException {
		
		System.out.println("BattleMenu test click");
		
		for (SlickRectangle rect : display){
			if (rect.isWithinBounds(x, y)){
				//TODO: implement
			}
		}
		
	}

	@Override
	public void paint(Graphics g) {
		for (SlickRectangle rect : rects){
			rect.paint(g);
		}
	}
	
}
