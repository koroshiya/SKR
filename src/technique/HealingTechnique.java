package technique;

import java.io.Serializable;

import character.CombatCapableCharacter;

public class HealingTechnique extends Technique implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int healingPotency;

	public HealingTechnique(int healingPotency, int uses, String name, String description, int level){
	
		super(-1, uses, name, description, level);
		this.healingPotency = healingPotency;
	
	}
	
	public void use(CombatCapableCharacter target){
		
		super.use();
		
		if (this.healingPotency == -1){
			target.revive();
		}else if (this.healingPotency == -2){
			target.revive(100);
		}else{
			target.heal(this.healingPotency);
		}
		
	}
	
	public int getPotency(){return this.healingPotency;}
	
	/**
	 * Returns true if the tech can be used on the dead (ie. it revives)
	 * Returns false if used on the living (ie. it heals)
	 * */
	public boolean usedOnDead(){return this.healingPotency < 0;}

}
