package technique;

import java.io.Serializable;


public class FuryBreak extends CombatTechnique implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FuryBreak(int attackStrength, String name, String description, int level){
	
		super(2, -1, -1, name, description, level); //"Player unleashes a focused attack without holding back"
	
	}

}
