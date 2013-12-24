package interfaces;

import character.CombatCapableCharacter;

/**
 * Interface by which any technique, item, etc. used for the purpose of healing should abide.
 * Intended to group healing functions together under a common flag.
 * */
public interface HealingCommand {

	public abstract void use(CombatCapableCharacter target);
	
	/**
	 * Potency of the healing command.
	 * -1 revives a target, -2 revives a target to full HP.
	 * Anything else specifies that the command heals that quantity. 
	 * eg. Potency of 10 heals a character by 10HP.
	 * 
	 * @return Amount by which to heal the target of this command.
	 * */
	public abstract int getPotency();
	
	/**
	 * @return True if the tech can be used on the dead (ie. it revives), false if used on the living (ie. it heals)
	 * */
	public abstract boolean usedOnDead();

	/**
	 * Convenience method for calling getName() for the item inheriting from this interface.
	 * 
	 * @return Returns the name of the command.
	 * */
	public abstract String getName();

}
