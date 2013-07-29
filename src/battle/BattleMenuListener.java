package battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import screen.MessageBox;

import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Inventory;

public class BattleMenuListener implements ActionListener {

	private String strAttack;
	private String strTechnique;
	private String strInventory;
	private String strRun;
	private String strBack;
	
	private PlayableCharacter currentCharacter;
	private ArrayList<EnemyCharacter> enemies;
	
	private Battle battle;
	private BattleMenu battleMenu;
	
	public BattleMenuListener(String strAttack, String strTechnique, 
				String strInventory, String strRun, String strBack,
				PlayableCharacter currentCharacter, Battle battle,
				BattleMenu battleMenu, ArrayList<EnemyCharacter> enemies){
		
		this.strAttack = strAttack;
		this.strTechnique = strTechnique;
		this.strInventory = strInventory;
		this.strRun = strRun;
		this.strBack = strBack;
		
		this.currentCharacter = currentCharacter;
		this.enemies = enemies;
		
		this.battle = battle;
		this.battleMenu = battleMenu;
		
	}
	
	public void actionPerformed(ActionEvent ae){
		
		String s = ae.getActionCommand();
		
		if (s.equals(strAttack)){
			this.battleMenu.setNewInterface(this.currentCharacter, 
					new BattleAttackListener(enemies, currentCharacter, this.battleMenu, null));
		}else if (s.equals(strTechnique)){
			this.battleMenu.setNewInterface(this.currentCharacter, this.currentCharacter.getUsableTechniques(),
						new BattleTechniqueListener(enemies, currentCharacter, this.battleMenu));
		}else if (s.equals(strInventory)){
			this.battleMenu.setNewInterface(this.currentCharacter, Inventory.getConsumables(),
					new BattleItemListener(currentCharacter, this.battleMenu));
		}else if (s.equals(strRun)){
			MessageBox.InfoBox("Loser", "Running away?");
			this.battle.end();
			//System.exit(0);
		}else if (s.equals(strBack)){
			this.battleMenu.setDefaultInterface(this.currentCharacter);
		}
		
	}
	
}
