package character;

import item.Item;
import item.Weapon;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Math;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import map.ParentMap;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;
import com.japanzai.skr.Inventory;

import screen.BattleConsole;
import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;

public abstract class CombatCapableCharacter extends Character implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FightingStyle style;
	private Weapon weapon; //Weapon type. eg. Fists, pickaxe, etc.   //Weapon leftHand and rightHand?
	private ArrayList<Technique> techniques;
	
	private Image dead;
	private Image alive;
	/*
	 * TODO: Add sprite imageicon
	 * TODO: Add avatar imageicon
	 * TODO: Add profile pic imageicon
	 * */
	
	private int level;
	
	private int statHP;
	private int statStrength;
	private int statDefence;
	private int statMind;
	private double statEvasion; //evasion as a ratio.
	private double statAccuracy; //accuracy as a ratio. eg. 100% hit rate
	private int statSpeed;
	
	private int currentHP;
	private int currentStrength;
	private int currentDefence;
	private int currentMind;
	private double currentEvasion;
	private double currentAccuracy;
	private int currentSpeed;
	
	private double gauge;
	
	public CombatCapableCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender,
					int level, String nickName, String sprite){
		
		this(firstName, lastName, style, weapon, gender, nickName, sprite);
		setLevel(level, null);
		
	}
	
	public CombatCapableCharacter (String firstName, String lastName, FightingStyle style, 
					Weapon weapon, Gender gender, String nickName, String sprite){
						
		super(firstName, lastName, gender, nickName, sprite);
	
		this.style = style;
		this.level = 1; //Change way it's set?
		
		this.statHP = style.getBaseHP();
		this.statStrength = style.getBaseStrength();
		this.statDefence = style.getBaseDefence();
		this.statEvasion = style.getBaseEvasion();
		this.statMind = style.getBaseMind();
		this.statAccuracy = style.getBaseAccuracy();
		this.statSpeed = style.getBaseSpeed();
		
		this.currentHP = this.statHP;
		
		//this.sprite = new JLabel(this.getName());

		//this.sprite = new JLabel(new ImageIcon("../images/ken/forward1.png"));
		this.weapon = weapon;
		this.techniques = new ArrayList<Technique>();
		
		//@SuppressWarnings("rawtypes")
		//Map attributes = (new JLabel().getFont()).getAttributes();
		//attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		//this.strike = new Font(attributes);
		//this.genericFont = new JLabel().getFont();
		
		//startBattle(); //Just used to set current stat values
		
		//this.status = new JLabel(getName() + "    " + getCurrentHP() + "/" + getHP());
		
	}
	
	public void attack(CombatCapableCharacter opponent){
			
		double accuracy = this.currentAccuracy * this.weapon.getAccuracy();
		boolean hit = calcAccuracy(accuracy) && !opponent.calcEvade();
		
		BattleConsole.writeConsole("CombatCapableCharacter: Attacking");
		dealDamage(hit, this.weapon.attack(), opponent);
		
		if (this instanceof PlayableCharacter){resetGauge();}
		
	}
	
	public void attack(CombatCapableCharacter opponent, CombatTechnique tech){
		
		boolean hit;
		tech.use();
		
		if (tech.getAlwaysHits()){
			hit = true;
		}else{
			double accuracy = this.currentAccuracy * tech.getAccuracy();
			hit = calcAccuracy(accuracy) && !opponent.calcEvade();
		}
		
		int damage = (int) Math.ceil(tech.getStrength() * weapon.getStrength());
		BattleConsole.writeConsole("");
		BattleConsole.writeConsole(getName() + " used " + tech.getName());
		dealDamage(hit, damage, opponent);
		
		if (this instanceof PlayableCharacter){resetGauge();}
		
	}
	
	private void dealDamage(boolean hit, int str, CombatCapableCharacter opponent){
		
		if (hit){
			int attackStrength = str + this.currentStrength;
			int damageTaken = opponent.takeDamage(attackStrength, this.currentStrength);
			BattleConsole.writeConsole(getName() + " dealt " + damageTaken + " to " + opponent.getName());
			if (!opponent.isAlive()){
				BattleConsole.writeConsole(getName() + " defeated " + opponent.getName());
				
				if (opponent instanceof EnemyCharacter || opponent instanceof BossCharacter){
					EnemyCharacter vanquishedOpponent = (EnemyCharacter) opponent;
					vanquishOpponent(vanquishedOpponent);					
				}
			}
		}else {
			BattleConsole.writeConsole(getName() + " missed " + opponent.getName());
		}
	}
	
	private void vanquishOpponent(EnemyCharacter vanquishedOpponent){
		PlayableCharacter victor = (PlayableCharacter) this;
		victor.gainExperience(vanquishedOpponent.getExperienceGivenWhenDefeated());
		
		int money = vanquishedOpponent.getMoney();
		BattleConsole.writeConsole("Obtained " + money + " yen from " + vanquishedOpponent.getName());
		Inventory.receiveMoney(money);
		
		Item item = vanquishedOpponent.getDrop();
		if (item != null){
			BattleConsole.writeConsole(vanquishedOpponent.getName() + " dropped " + item.getName());
			Inventory.addItem(item);
		}
	}
	
	private boolean calcAccuracy(double accuracy){
	
		return calculate(accuracy); 
		//redundant, but just in case changes are made
		//also to keep track of what is being calculated
		
	}
	
	public boolean calcEvade(){
		
		return calculate(this.currentEvasion);
		
	}
	
	public boolean calcParry(){
		//Add ability/chance to parry? Or just leave evasion?
		return false;
		
	}
	
	private boolean calculate(double chance){
				
		double result = Math.random() * 100;
		result *= (1 + chance);
		return result >= 100;
		
	}
	
	public int takeDamage(int damage, int attackPower){
		
		double ratio = (double)attackPower / (double)this.currentDefence;
		int damageTaken = (int) Math.ceil((double)damage * ratio);
		
		
		if (this.currentHP <= damageTaken){
			this.currentHP = 0;
			
			//this.status.setFont(this.strike); //TODO: set this on start if dead?
			
			/**
			 * TODO: fix strikethrough when reviving - same with sprite
			 * TODO: when implementing real sprites, change with dead sprite
			 * */
		}else{
			this.currentHP -= damageTaken;
		}
		
		return damageTaken;
		
	}
	
	public void startBattle(){
		
		if (!(this instanceof PlayableCharacter)){
			this.currentHP = this.statHP;
		}
		this.currentStrength = this.statStrength;
		this.currentDefence = this.statDefence;
		this.currentMind = this.statMind;
		this.currentEvasion = this.statEvasion;
		this.currentAccuracy = this.statAccuracy;
		this.currentSpeed = this.statSpeed;
		this.gauge = 0;
		
		if (this.techniques != null){
			for (Technique t : techniques){
				t.startBattle();
			}
		}
		
	}
	
	//Status ailments?
	//Revive? Attitude? (eg. angry raises critical and/or damage)
	
	public void setLevel(int level, BattleConsole bc){
		int difference = level - this.level;
		if (difference <= 0){return;}
		for (int i = 1; i <= difference; i++){levelUp();}
	}
	
	public void levelUp(){
	
		this.level++;
		BattleConsole.cleanConsole();
		BattleConsole.writeConsole(getName() + " went up to level " + this.level);
		
		int curHP = this.statHP;
		this.statHP += levelUpBonus() + getGender().getHPBonus() + style.getHPBonus(); //Bigger bonus for HP?
		curHP = this.statHP - curHP;
		this.currentHP += curHP;
		
		this.statStrength += levelUpBonus() + getGender().getStrengthBonus() + style.getStrengthBonus();
		this.statDefence += levelUpBonus() + getGender().getDefenceBonus() + style.getDefenceBonus();
		this.statMind += levelUpBonus() + getGender().getMindBonus() + style.getMindBonus();
		this.statAccuracy += slightBonus();
		this.statEvasion += slightBonus();
		this.statSpeed += levelUpBonus() + getGender().getSpeedBonus() + style.getSpeedBonus();
		
		ArrayList<Technique> tech = this.style.getTechnique(this.level);
		for (Technique t : tech){
			learnTechnique(t);
		}
		
	}
	
	/**
	 * @return bonus of 1-2.
	 * Used for normal stats in addition to gender bonus.
	 * */
	private int levelUpBonus(){
	
		double base = Math.random() + Math.ceil((double)this.level / 10);
		return (int) Math.ceil(base);
		
	}
	
	/**
	 * @return bonus of 0 or 1.
	 * Used for stats we don't want soaring. eg. accuracy, evasion, etc.
	 * */
	private int slightBonus(){

		/**
		 * TODO: add level to the equation
		 * */
		double base = Math.random();
		return (int) Math.round(base);
		
	}
	
	//Getters/Setters/Is
	
	public boolean isAlive(){return this.currentHP != 0;}
	
	public void revive(){
		revive(50);
	}
	
	public void revive(int percentHP){
		this.currentHP = (int) Math.ceil(this.statHP * ((double)percentHP / 100));
	}
	
	public void heal(int amount){
		
		this.currentHP += amount;
		BattleConsole.writeConsole(this.getName() + " recovered " + amount + " HP");
		if (this.currentHP > this.statHP){this.currentHP = this.statHP;}
		
	}
	
	public FightingStyle getFightingStyle(){return this.style;}
	
	public Weapon getWeapon(){return this.weapon;}
	
	public void setWeapon(Weapon weapon){
		
		if (this.weapon != null){this.weapon.setEquipped(false);}
		this.weapon = weapon;
		this.weapon.setEquipped(true); //Add inventory check for if Weapon is equipped
		
	}
	
	public ArrayList<Technique> getTechniques(){return this.techniques;}
	
	public ArrayList<CombatTechnique> getCombatTechniques(){
		
		ArrayList<CombatTechnique> combatList = new ArrayList<CombatTechnique>();
		
		for (Technique tech : techniques){
			if (tech instanceof CombatTechnique){
				combatList.add((CombatTechnique) tech);
			}
		}
		
		return combatList;
		
	}
	
	public ArrayList<HealingTechnique> getHealingTechniques(){
		
		ArrayList<HealingTechnique> healList = new ArrayList<HealingTechnique>();
		
		for (Technique tech : techniques){
			if (tech instanceof HealingTechnique){
				healList.add((HealingTechnique) tech);
			}
		}
		
		return healList;
		
	}
	
	public ArrayList<Technique> getUsableTechniques(){
		
		ArrayList<Technique> techList = new ArrayList<Technique>();
		
		for (Technique tech : techniques){
			if (tech.getUsesLeft() != 0){
				techList.add(tech);
			}
		}
		
		return techList;
		
	}
	
	public void learnTechnique(Technique technique){
		this.techniques.add(technique);
	}
	
	public int getLevel(){return this.level;}
	
	public int getHP(){return this.statHP;}
	
	public int getStrength(){return this.statStrength;}
	
	public int getDefence(){return this.statDefence;}
	
	public int getMind(){return this.statMind;}
	
	public double getEvasion(){return this.statEvasion;}
	
	public double getAccuracy(){return this.statAccuracy;}
	
	public int getSpeed(){return this.statSpeed;}
	
	public int getCurrentHP(){return this.currentHP;}
	
	public int getCurrentStrength(){return this.currentStrength;}
	
	public int getCurrentDefence(){return this.currentDefence;}
	
	public int getCurrentMind(){return this.currentMind;}
	
	public double getCurrentEvasion(){return this.currentEvasion;}
	
	public double getCurrentAccuracy(){return this.currentAccuracy;}
	
	public int getCurrentSpeed(){return this.currentSpeed;}

	public void setCurrentHP(int currentHP){this.currentHP = currentHP;}
	
	public void setCurrentStrength(int currentStrength){this.currentStrength = currentStrength;}
	
	public void setCurrentDefence(int currentDefence){this.currentDefence = currentDefence;}
	
	public void setCurrentMind(int currentMind){this.currentMind = currentMind;}
	
	public void setCurrentEvasion(double currentEvasion){this.currentEvasion = currentEvasion;}
	
	public void setCurrentAccuracy(double currentAccuracy){this.currentAccuracy = currentAccuracy;}
	
	public void setCurrentSpeed(int currentSpeed){this.currentSpeed = currentSpeed;}
	
	public double getGauge(){return this.gauge;}
	
	public void resetGauge(){this.gauge = 0;}
	
	public void incrementGauge(){
		
		double amount = (double)this.currentSpeed / 20;
		this.gauge += amount;
		if (this.gauge > 10){this.gauge = 10;}
		
	}
	
	public Image getSprite(){return this.isAlive() ? this.alive : this.dead;}
	
	public Image getSprite(int dir) {
		
		Image f = null;
		
		try{
			if (dir == ParentMap.LEFT){
				f = new Image(this.getSpriteDirectory() + "left2.png");
			}else if (dir == ParentMap.RIGHT){
				f = new Image(this.getSpriteDirectory() + "right2.png");
			}else if (dir == ParentMap.UP){
				//backwards sprite
				f = new Image(this.getSpriteDirectory() + "backwards2.png");
			}else if (dir == ParentMap.DOWN){
				//forwards sprite
				f = new Image(this.getSpriteDirectory() + "forward2.png");
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		return f;
		
	}
	
	public void setAliveIcon(Image icon){this.alive = icon;}
	
	public void setDeadIcon(Image icon){this.dead = icon;}
	
	public String getStatus(){return getCurrentHP() + "/" + getHP();}
	//this.status.setFont(this.currentHP == 0 ? this.strike : this.genericFont);
	
	public abstract void instantiateForBattle();
	
}