package interfaces;

import character.CombatCapableCharacter;

/**
 * Command that can be used a specific number of times.
 * eg. A spell that can only be used if you have enough MP,
 * an item of which you have a limited quantity, etc.
 * */
public interface QuantitativeCommand {
	
	public abstract String getName();
	
	/**
	 * Tests if the command can be used or not.
	 * 
	 * @return True if the command can be used, otherwise false.
	 * */
	public abstract boolean canUse();
	
	public abstract void use(CombatCapableCharacter ccc);

}
