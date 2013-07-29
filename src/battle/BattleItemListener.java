package battle;

import item.ConsumableItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import character.PlayableCharacter;

import com.japanzai.skr.Inventory;

public class BattleItemListener implements ActionListener{

	private PlayableCharacter currentCharacter;
	private BattleMenu menu;
	
	public BattleItemListener(PlayableCharacter currentCharacter,
								BattleMenu menu){
		
		this.currentCharacter = currentCharacter;
		this.menu = menu;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String s = ae.getActionCommand();
		//System.out.println(s);
		
		for (ConsumableItem t : Inventory.getConsumables()){
			if (t.getName().equals(s)){
				menu.setNewInterface(this.currentCharacter, 
						new BattleAllyListener(currentCharacter, 
								this.menu, null, t));
			}
		}
		
		
	}

}
