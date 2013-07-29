package technique;

import java.io.Serializable;

public abstract class Technique implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int techniqueAccuracy; //-1 = alwaysHits, otherwise should be value between 0 and 100
	private int uses; //Uses per battle
	private int tempUses; //Uses this battle
	private String name;
	private String description;
	private int level;
	
	public Technique(int techniqueAccuracy, int uses, String name, String description, int level){
		
		this.techniqueAccuracy = techniqueAccuracy;
		this.name = name;
		this.uses = uses;
		this.level = level;
		
	}
	
	public boolean getAlwaysHits(){
		return this.techniqueAccuracy == -1;
	}
	
	public int getAccuracy(){return this.techniqueAccuracy;}
	
	public String getName(){return this.name;}
	
	public String getDescription(){return this.description;}
	
	public int getUsesLeft(){return this.tempUses;}
	
	public int getMaxUses(){return this.uses;}
	
	//Called at the start of a battle
	public void startBattle(){this.tempUses = this.uses;}
	
	public void use(){
		if (uses > 0){
			this.tempUses--;
		}
	}
	
	public boolean canUse(){return this.tempUses != 0;}

	public int getLevelRequired(){
		
		return this.level;
		
	}
	
}
