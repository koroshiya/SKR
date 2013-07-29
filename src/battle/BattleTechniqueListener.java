package battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import character.EnemyCharacter;
import character.PlayableCharacter;

import java.util.ArrayList;

import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;

public class BattleTechniqueListener implements ActionListener{

	private ArrayList<EnemyCharacter> enemies;
	private PlayableCharacter currentCharacter;
	private BattleMenu menu;
	
	public BattleTechniqueListener(ArrayList<EnemyCharacter> enemies,
								PlayableCharacter currentCharacter,
								BattleMenu menu){
		
		this.enemies = enemies;
		this.currentCharacter = currentCharacter;
		this.menu = menu;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String s = ae.getActionCommand();
		//System.out.println(s);
		
		for (Technique t : this.currentCharacter.getUsableTechniques()){
			if (t.getName().equals(s)){
				if (t instanceof CombatTechnique){
					CombatTechnique ct = (CombatTechnique) t;
					menu.setNewInterface(this.currentCharacter, 
							new BattleAttackListener(enemies, 
							currentCharacter, this.menu, ct));
				}else if (t instanceof HealingTechnique){
					HealingTechnique heal = (HealingTechnique) t;
					menu.setNewInterface(this.currentCharacter,
							new BattleAllyListener(currentCharacter, 
									this.menu, heal, null));
				}
			}
		}
		
		
	}

}
