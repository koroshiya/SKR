package battle;

import item.ConsumableItem;

import javax.swing.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import technique.Technique;

import character.BossCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Inventory;
import com.japanzai.skr.Party;

public class BattleMenu extends BasicGameState {
	
	private JButton btnAttack;
	private JButton btnTechnique;
	private JButton btnInventory;
	private JButton btnRun;
	
	private final String strAttack = "Attack";
	private final String strTechnique = "Techniques";
	private final String strInventory = "Inventory";
	private final String strRun = "Run";
	private final String strBack = "Back";
	
	private ArrayList<EnemyCharacter> enemies;
	private PlayableCharacter currentCharacter;
	
	private Battle battle;
	
	private JPanel panel;

	public BattleMenu(ArrayList<EnemyCharacter> enemies, Battle battle){
		
		//BattleMenuListener listener = new BattleMenuListener();
		this.enemies = enemies;
		this.currentCharacter = null;
		
		this.battle = battle;
		
		//this.setLayout());
		//this.setLayout(new GridLayout(4, 1));
		//this.setLayout(new FlowLayout());
		//this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//this.setVerticalScrollBar();
	
		
		/*Dimension size = new Dimension(165, 120);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setSize(size);*/
		
		panel = new JPanel();	
		//size = new Dimension(165, 120);
		//this.panel.setMinimumSize(size);
		//this.panel.setMaximumSize(size);
		this.panel.setLayout(new GridLayout(4, 1));	
		
		//resetDefaultInterface();

		//this.setViewportView(panel);
		//this.setResizable(false);
		
	}
	
	private JButton setButton(String displayText, String tag, ActionListener act){
		
		JButton button = new JButton(displayText);
		button.setPreferredSize(new Dimension(120, 29));
		button.setSize(120, 29);
		if (tag != null){
			button.setActionCommand(tag);
		}else{
			button.setActionCommand(displayText);
		}
		button.addActionListener(act);
		this.panel.add(button);
		return button;
		
	}
	
	public void start() throws SlickException{
		this.battle.start();
	}
	
	public void setDefaultInterface(PlayableCharacter c){

		resetComponents();
		this.currentCharacter = c;
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, c, 
				battle, this, enemies);
		
		setPaneCount(4);
		
		String attackTag = c.getTemper() < 10 ? strAttack : "Fury Break!";
		
		btnAttack = setButton(attackTag, strAttack, listener);
		btnTechnique = setButton(strTechnique, null, listener);
		btnInventory = setButton(strInventory, null, listener);
		btnRun = setButton(strRun, null, listener);

		btnTechnique.setEnabled(c.getUsableTechniques().size() > 0);
		btnInventory.setEnabled(Inventory.getConsumables().size() > 0);
		btnRun.setEnabled(!(this.enemies.get(0) instanceof BossCharacter));

		repaint();
		setVisible(true);
		
	}
	
	public void setNewInterface(PlayableCharacter character, 
								ActionListener act){
		
		this.currentCharacter = character;
		resetComponents();

		if (act instanceof BattleAllyListener){
			setPaneCount(Party.getCharactersInParty().size() + 1);
			for (PlayableCharacter c : Party.getCharactersInParty()){ //use index instead?
				setButton(c.getNickName(), c.getName(), act); //change to getNickName
			}
		}else{
			setPaneCount(this.enemies.size() + 1);
			for (EnemyCharacter e : this.enemies){
				JButton button = setButton(e.getNickName(), e.getName(), act);
				if (!e.isAlive()){button.setEnabled(false);}
			}
		}
		
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, character, 
				battle, this, enemies);
		setButton(strBack, null, listener);
		
		repaint();
		setVisible(true);
		
	}
	
	@SuppressWarnings("unchecked")
	public void setNewInterface(PlayableCharacter character,
			ArrayList<?> list, ActionListener act) {

		this.currentCharacter = character;
		resetComponents();
		//this.removeAll();
		setPaneCount(list.size() + 1);	
		
		if (list.get(0) instanceof Technique){
			ArrayList<Technique> usableTechniques = (ArrayList<Technique>) list;
			
			for (Technique t : usableTechniques){ //use index instead?
				JButton button = setButton(t.getName(), t.getName(), act);
				if (!t.canUse()){button.setEnabled(false);}
			}
		}else {
			ArrayList<ConsumableItem> usableItems = (ArrayList<ConsumableItem>) list;
			for (ConsumableItem i : usableItems){ //use index instead?
				JButton button = setButton(i.getName(), i.getName(), act);
				if (!i.canUse()){button.setEnabled(false);}
			}
		}
		
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, character, 
				battle, this, enemies);
		
		setButton(strBack, strBack, listener);
		
		repaint();
		setVisible(true);
	
	}
	
	public void resetDefaultInterface(){
		
		resetComponents();
		//BattleConsole.writeConsole("failure");
		
		BattleMenuListener listener = new BattleMenuListener(strAttack, 
				strTechnique, strInventory, strRun, strBack, this.currentCharacter, 
				battle, this, enemies);
		
		setPaneCount(4);
		
		btnAttack = setButton(strAttack, null, listener);
		btnTechnique = setButton(strTechnique, null, listener);
		btnInventory = setButton(strInventory, null, listener);
		btnRun = setButton(strRun, null, listener);
		
		btnAttack.setEnabled(false);
		btnTechnique.setEnabled(false);
		btnInventory.setEnabled(false);
		btnRun.setEnabled(false);
				
		this.setVisible(true);
		
	}
	
	private void resetComponents(){
		
		setVisible(false);
		
		for (Component comp : this.panel.getComponents()){
			if (comp instanceof JButton){
				this.panel.remove(comp);
			}
		}
		
	}
	
	private void setPaneCount(int count){
		
		int size = count < 4 ? 4 : count;
		this.panel.setLayout(new GridLayout(size, 1));

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
