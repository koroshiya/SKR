package battle;

import item.ConsumableItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import character.PlayableCharacter;

import com.japanzai.skr.Party;

import screen.BattleConsole;
import technique.HealingTechnique;

public class BattleAllyListener implements ActionListener{

	private PlayableCharacter currentCharacter;
	private BattleMenu menu;
	private HealingTechnique tech;
	private ConsumableItem item;
	
	public BattleAllyListener(PlayableCharacter currentCharacter,
								BattleMenu menu, HealingTechnique tech,
								ConsumableItem item){
		
		this.currentCharacter = currentCharacter;
		this.menu = menu;
		this.tech = tech;
		this.item = item;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String s = ae.getActionCommand();
		
		for (PlayableCharacter c : Party.getCharactersInParty()){
			if (c.getName().equals(s)){
				if (test(c)){
					this.currentCharacter.healAlly(c, this.tech, this.item, this.currentCharacter); //add check for appropriate type here
				}else{
					String type = this.tech != null ? "technique" : "item";
					alert(c, type);
				}
			}
		}
		this.menu.resetDefaultInterface();
		this.menu.start();
		
	}
	
	private boolean test(PlayableCharacter c){
		
		if (this.tech != null){
			return c.isAlive() != this.tech.usedOnDead();
		}
		return c.isAlive() != this.item.usedOnDead();
		
	}	
	
	private void alert(PlayableCharacter c, String origin){
		if (c.isAlive()){
			BattleConsole.writeConsole("That " + origin + " can only be used on fallen allies");
		}else{
			BattleConsole.writeConsole("That " + origin + " can only be used on the living");
		}
	}

}
