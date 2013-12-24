package interfaces;

import character.CombatCapableCharacter;

public interface HealingCommand {

	public abstract void use(CombatCapableCharacter target);
	
	public abstract int getPotency();
	
	/**
	 * Returns true if the tech can be used on the dead (ie. it revives)
	 * Returns false if used on the living (ie. it heals)
	 * */
	public abstract boolean usedOnDead();

	public abstract String getName();

}
