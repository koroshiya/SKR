package character;

import java.io.Serializable;

public class Status implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int statHP;
	private int statStrength;
	private int statDefence;
	private int statMind;
	private double statEvasion; //evasion as a ratio.
	private double statAccuracy; //accuracy as a ratio. eg. 100% hit rate
	private int statSpeed;
	
	public Status (
					int statHP, int statStrength, int statDefence,
					int statMind, double statEvasion, double statAccuracy,
					int statSpeed
					){
		
		setStats (
				statHP, statStrength, statDefence,
				statMind, statEvasion, statAccuracy,
				statSpeed
				);
		
	}
	
	public Status(Status baseStats) {
		setStats(baseStats);
	}

	public void setStats (
					int statHP, int statStrength, int statDefence,
					int statMind, double statEvasion, double statAccuracy,
					int statSpeed
					){
		
		this.statHP = statHP;
		this.statStrength = statStrength;
		this.statDefence = statDefence;
		this.statMind = statMind;
		this.statEvasion = statEvasion;
		this.statAccuracy = statAccuracy;
		this.statSpeed = statSpeed;
		
	}
	
	public void setStats (Status newStatus){
		
		this.statHP = newStatus.getHP();
		this.statStrength = newStatus.getStrength();
		this.statDefence = newStatus.getDefence();
		this.statMind = newStatus.getMind();
		this.statEvasion = newStatus.getEvasion();
		this.statAccuracy = newStatus.getAccuracy();
		this.statSpeed = newStatus.getSpeed();
		
	}

	public int getHP(){return this.statHP;}
	
	public int getStrength(){return this.statStrength;}
	
	public int getDefence(){return this.statDefence;}
	
	public int getMind(){return this.statMind;}
	
	public double getEvasion(){return this.statEvasion;}
	
	public double getAccuracy(){return this.statAccuracy;}

	public int getSpeed(){return this.statSpeed;}
	
	public void setHP(int currentHP){this.statHP = currentHP;}
	
	public void setStrength(int currentStrength){this.statStrength = currentStrength;}
	
	public void setDefence(int currentDefence){this.statDefence = currentDefence;}
	
	public void setMind(int currentMind){this.statMind = currentMind;}
	
	public void setEvasion(double currentEvasion){this.statEvasion = currentEvasion;}
	
	public void setAccuracy(double currentAccuracy){this.statAccuracy = currentAccuracy;}
	
	public void setSpeed(int currentSpeed){this.statSpeed = currentSpeed;}
	
}
