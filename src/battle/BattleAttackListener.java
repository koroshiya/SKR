package battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import character.EnemyCharacter;
import character.PlayableCharacter;

import java.util.ArrayList;

import technique.CombatTechnique;

public class BattleAttackListener implements ActionListener{

	private ArrayList<EnemyCharacter> enemies;
	private PlayableCharacter currentCharacter;
	private BattleMenu menu;
	private CombatTechnique tech;
	
	public BattleAttackListener(ArrayList<EnemyCharacter> enemies,
								PlayableCharacter currentCharacter,
								BattleMenu menu, CombatTechnique tech){
		
		this.enemies = enemies;
		this.currentCharacter = currentCharacter;
		this.menu = menu;
		this.tech = tech;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String s = ae.getActionCommand();
		
		for (EnemyCharacter e : this.enemies){
			if (e.getName().equals(s)){
				if (this.tech != null){
					this.currentCharacter.attack(e, this.tech);
				}else if (this.currentCharacter.getTemper() == 10){
					this.currentCharacter.attack(e, this.currentCharacter.getFuryBreak());
				}else{
					this.currentCharacter.attack(e);
				}
			}
		}
		this.menu.resetDefaultInterface();
		this.menu.start();
		
	}

}
