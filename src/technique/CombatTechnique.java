package technique;

import java.io.Serializable;

public class CombatTechnique extends Technique implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double attackStrength; //multiplier

	public CombatTechnique(double attackStrength, int techniqueAccuracy, int uses, 
			String name, String description, int level){
	
		super(techniqueAccuracy, uses, name, description, level);
		this.attackStrength = attackStrength; //tech to attack every enemy at once?
	
	}
	
	public double getStrength(){return this.attackStrength;}

}
