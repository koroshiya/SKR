package technique;

import interfaces.QuantitativeCommand;

import java.io.Serializable;

import character.CombatCapableCharacter;

public abstract class Technique implements Serializable, QuantitativeCommand{
	
	private static final long serialVersionUID = -6370393877800303999L;
	
	private final int techniqueAccuracy;
	private int uses;
	private int tempUses;
	private final String name;
	private final String description;
	private final int level;
	
	/**
	 * @param techniqueAccuracy Accuracy of this technique, expressed as a number between 0 and 100.
	 * 			0 never hits, 100 rarely misses, -1 never misses.
	 * @param uses Number of times this technique can be used per battle.
	 * @param name Name of this technique.
	 * @param description Description of this technique.
	 * @param level Level required to learn/use this technique.
	 */
	public Technique(int techniqueAccuracy, int uses, String name, String description, int level){
		
		this.techniqueAccuracy = techniqueAccuracy;
		this.uses = uses;
		this.name = name;
		this.description = description;
		this.level = level;
		
	}
	
	/**
	 * Checks if this technique will always hit its mark.
	 * Usually used for self-targeting techniques, such as healing techniques.
	 * 
	 * @return True if technique never misses, otherwise false.
	 * */
	public boolean getAlwaysHits(){return this.techniqueAccuracy == -1;}
	
	/**
	 * Returns the accuracy of this technique.
	 * 
	 * @return Accuracy of this technique, expressed as a number between 0 and 100, 
	 * where a higher value means a greater chance of the technique being successful.
	 * */
	public int getAccuracy(){return this.techniqueAccuracy;}
	
	@Override
	public String getName(){return this.name;}
	
	public String getDescription(){return this.description;}
	
	/**
	 * @return How many more times this technique can be used before next battle.
	 * */
	public int getUsesLeft(){return this.tempUses;}
	
	/**
	 * Checks for the number of times this technique can be used per battle.
	 * 
	 * @return How many times this technique can be used per battle.
	 * */
	public int getMaxUses(){return this.uses;}
	
	/**
	 * Called at the start of a battle.
	 * Resets the number of temporary uses (ie. max number of uses of 
	 * this technique over the span of one battle) to its default number of uses.
	 * */
	public void startBattle(){this.tempUses = this.uses;}
	
	@Override
	/**
	 * ccc can be null.
	 * */
	public void use(CombatCapableCharacter ccc){this.tempUses--;}
	
	@Override
	public boolean canUse(){return this.tempUses > 0;}

	/**
	 * Specified the level required for this technique to be learned/used.
	 * 
	 * @return Level required for this technique to be learned/used.
	 * */
	public int getLevelRequired(){return this.level;}
	
}
