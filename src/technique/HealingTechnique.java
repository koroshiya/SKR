package technique;

import interfaces.HealingCommand;

import java.io.Serializable;

import character.CombatCapableCharacter;

public class HealingTechnique extends Technique implements Serializable, HealingCommand{

	private static final long serialVersionUID = 1252726534762143981L;
	
	private int healingPotency;

	public HealingTechnique(int healingPotency, int uses, String name, String description, int level){
	
		super(-1, uses, name, description, level);
		this.healingPotency = healingPotency;
	
	}
	
	@Override
	public void use(CombatCapableCharacter target){
		
		super.use(target);
		
		if (this.healingPotency == -1){
			target.revive();
		}else if (this.healingPotency == -2){
			target.revive(100);
		}else{
			target.heal(this.healingPotency);
		}
		
	}
	
	@Override
	public int getPotency(){return this.healingPotency;}
	
	@Override
	public boolean usedOnDead(){return this.healingPotency < 0;}

}
