package character;

import item.Item;
import item.Weapon;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Math;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.japanzai.skr.FightingStyle;
import com.japanzai.skr.Gender;
import com.japanzai.skr.Inventory;

import console.BattleConsole;
import slickgamestate.SlickSKR;
import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;

public abstract class CombatCapableCharacter extends Character implements Serializable{
	
	private static final long serialVersionUID = 6024399234511553080L;
	
	private FightingStyle style;
	private Weapon weapon; //Weapon type. eg. Fists, pickaxe, etc.   //Weapon leftHand and rightHand?
	private ArrayList<Technique> techniques;
	
	private String dead;
	private String alive;
	
	protected int level;
	
	private Status status;
	private Status currentStatus;
	
	private double gauge;
	private final String propertyValue;
	protected Animation attack;
	
	public CombatCapableCharacter(String property, FightingStyle style, Weapon weapon, Gender gender, int level){
		
		this(property, style, weapon, gender);
		setLevel(level);
		
	}
	
	public CombatCapableCharacter (String property, FightingStyle style, Weapon weapon, Gender gender){
						
		super(
			SlickSKR.getValueFromKey(property + "name.first"), 
			SlickSKR.getValueFromKey(property + "name.last"), 
			gender, 
			SlickSKR.getValueFromKey(property + "name.nickname"), 
			SlickSKR.getValueFromKey(property + "sprite")
		);
		this.propertyValue = property;
		this.style = style;
		this.level = 1; //Change way it's set?

		this.status = new Status(style.getBaseStats());
		this.currentStatus = new Status(style.getBaseStats());
		
		this.weapon = weapon;
		this.techniques = new ArrayList<Technique>();
		
	}
	
	/**
	 * Attacks the CombatCapableCharacter specified.
	 * 
	 * @param opponent CombatCapableCharacter to attack.
	 * */
	public void attack(CombatCapableCharacter opponent){
		
		double accuracy = this.currentStatus.getAccuracy() * this.weapon.getAccuracy();
		boolean hit = calcAccuracy(accuracy) && !opponent.calcEvade();
		
		dealDamage(hit, this.weapon.attack(), opponent);
		
		if (this instanceof PlayableCharacter){resetGauge();}
		
	}
	
	/**
	 * Uses a combat technique on the CombatCapableCharacter specified.
	 * 
	 * @param opponent CombatCapableCharacter to attack.
	 * @param tech Attacking technique to use on the character specified.
	 * */
	public void attack(CombatCapableCharacter opponent, CombatTechnique tech){
		
		boolean hit;
		tech.use(opponent);
		
		if (tech.getAlwaysHits()){
			hit = true;
		}else{
			double accuracy = this.currentStatus.getAccuracy() * tech.getAccuracy();
			hit = calcAccuracy(accuracy) && !opponent.calcEvade();
		}
		
		int damage = (int) Math.ceil(tech.getStrength() * weapon.getStrength());
		BattleConsole.writeConsole("");
		BattleConsole.writeConsole(getName() + " used " + tech.getName());
		dealDamage(hit, damage, opponent);
		
		if (this instanceof PlayableCharacter){resetGauge();}
		
	}
	
	/**
	 * Attempts to deal damage to the character specified.
	 * 
	 * @param hit Whether or not the attack hit.
	 * @param str Strength of the attack.
	 * @param opponent Target of the attack.
	 */
	private void dealDamage(boolean hit, int str, CombatCapableCharacter opponent){
		
		System.out.println("cFrame: " + attack.getFrame());
		attack.restart();
		System.out.println("cFrame: " + attack.getFrame());
		if (hit){
			int attackStrength = str + this.currentStatus.getStrength();
			int damageTaken = opponent.takeDamage(attackStrength, this.currentStatus.getStrength());
			BattleConsole.writeConsole(getName() + " dealt " + damageTaken + " to " + opponent.getName());
			SlickSKR.PlaySFX(this.weapon.getSFXHit());
			if (!opponent.isAlive()){
				BattleConsole.writeConsole(getName() + " defeated " + opponent.getName());
				
				if (opponent instanceof EnemyCharacter || opponent instanceof BossCharacter){
					EnemyCharacter vanquishedOpponent = (EnemyCharacter) opponent;
					vanquishOpponent(vanquishedOpponent);
				}
			}
		}else {
			SlickSKR.PlaySFX(this.weapon.getSFXMiss());
			BattleConsole.writeConsole(getName() + " missed " + opponent.getName());
		}
		
	}
	
	/**
	 * Vanquishes the opponent specified.
	 * In doing so receives experience, money, potentially items.
	 * 
	 * @param vanquishedOpponent Opponent to vanquish.
	 * */
	private void vanquishOpponent(EnemyCharacter vanquishedOpponent){
		PlayableCharacter victor = (PlayableCharacter) this;
		victor.gainExperience(vanquishedOpponent.getExperienceGivenWhenDefeated());
		
		int money = vanquishedOpponent.getMoney();
		BattleConsole.writeConsole("Obtained " + money + " gold from " + vanquishedOpponent.getName());
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
	
	/**
	 * Attempts to evade an attack.
	 * 
	 * @return True if evasion was successful, otherwise false.
	 * */
	public boolean calcEvade(){return calculate(this.currentStatus.getEvasion());}
	
	public boolean calcParry(){
		//Add ability/chance to parry? Or just leave evasion?
		return false;
		
	}
	
	/**
	 * Calculates the odds of an event occurring and attempts to invoke it.
	 * 
	 * @param chance Likelihood of an event occurring. 0-1, with 1 being likely, 0 meaning never.
	 * 
	 * @return True if event occurred, otherwise false.
	 * */
	private boolean calculate(double chance){
				
		double result = Math.random() * 100;
		result *= (1 + chance);
		return result >= 100;
		
	}
	
	public int takeDamage(int damage, int attackPower){
		
		double ratio = (double)attackPower / (double)this.currentStatus.getDefence();
		int damageTaken = (int) Math.ceil((double)damage * ratio);
		
		if (this.currentStatus.getHP() <= damageTaken){
			this.currentStatus.setHP(0);
			//TODO: SlickSKR.PlaySFX("weeden/die-or-lose-life.ogg");
		}else{
			this.currentStatus.setHP(this.currentStatus.getHP() - damageTaken);
			//TODO: sound of getting hit
		}
		
		return damageTaken;
		
	}
	
	/**
	 * Initializes temporary stats, resets temper gauge, resets number of uses for all techniques, etc.
	 * */
	public void startBattle(){
		
		if (!(this instanceof PlayableCharacter)){
			this.currentStatus.setHP(this.status.getHP());
		}
		this.currentStatus.setStrength(this.status.getStrength());
		this.currentStatus.setDefence(this.status.getDefence());
		this.currentStatus.setMind(this.status.getMind());
		this.currentStatus.setEvasion(this.status.getEvasion());
		this.currentStatus.setAccuracy(this.status.getAccuracy());
		this.currentStatus.setSpeed(this.status.getSpeed());
		this.gauge = 0;
		
		if (this.techniques != null){
			for (Technique t : techniques){
				t.startBattle();
			}
		}
		
	}
	
	//TODO: Status ailments?
	
	/**
	 * Sets the character's level as specified.
	 * Character still receives bonuses for each level gained.
	 * 
	 * @param level Level to set character at.
	 * */
	public void setLevel(int level){
		int difference = level - this.level;
		if (difference <= 0){return;}
		for (int i = 1; i <= difference; i++){levelUp();}
	}
	
	/**
	 * Increments the character's level, increases stats, learns new techniques, etc. 
	 * */
	public void levelUp(){
	
		this.level++;
		BattleConsole.cleanConsole(); //TODO: remove cleanConsole declaration?
		BattleConsole.writeConsole(getName() + " went up to level " + this.level); //TODO: replace with brief onscreen alert?
		//TODO: level up sfx
		
		int curHP = this.status.getHP();
		
		this.status.setStats(
			this.status.getHP() + levelUpBonus() + getGender().getHPBonus() + style.getHPBonus(),  //Bigger bonus for HP?
			this.status.getStrength() + levelUpBonus() + getGender().getStrengthBonus() + style.getStrengthBonus(),
			this.status.getDefence() + levelUpBonus() + getGender().getDefenceBonus() + style.getDefenceBonus(),
			this.status.getMind() + levelUpBonus() + getGender().getMindBonus() + style.getMindBonus(),
			this.status.getAccuracy() + slightBonus(),
			this.status.getEvasion() + slightBonus(),
			this.status.getSpeed() + levelUpBonus() + getGender().getSpeedBonus() + style.getSpeedBonus()
		);

		curHP = this.status.getHP() - curHP;
		this.currentStatus.setHP(this.currentStatus.getHP() + curHP);
		
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
	
	public boolean isAlive(){return this.currentStatus.getHP() != 0;}
	
	public void revive(){revive(50);}
	
	public void revive(int percentHP){
		this.currentStatus.setHP((int) Math.ceil(this.status.getHP() * ((double)percentHP / 100)));
		SlickSKR.PlaySFX("other/public/revive.ogg");
	}
	
	public void heal(int amount){
		
		this.currentStatus.setHP(this.currentStatus.getHP() + amount);
		BattleConsole.writeConsole(this.getName() + " recovered " + amount + " HP");
		if (this.currentStatus.getHP() > this.status.getHP()){this.currentStatus.setHP(this.status.getHP());}
		//TODO: healing sfx
		
	}
	
	public FightingStyle getFightingStyle(){return this.style;}
	
	public String getPropertyValue(){return this.propertyValue;}
	
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
	
	public Status getStats(){return this.status;}
	
	public Status getCurrentStats(){return this.currentStatus;}
	
	public double getGauge(){return this.gauge;}
	
	public void resetGauge(){this.gauge = 0;}
	
	public void incrementGauge(){
		
		double amount = (double)this.currentStatus.getSpeed() / 20;
		this.gauge += amount;
		if (this.gauge > 10){this.gauge = 10;}
		
	}
	
	public String getSprite(){return this.isAlive() ? this.alive : this.dead;}
	
	public abstract Image getBattleIcon();
	
	public String getStatus(){return this.currentStatus.getHP() + "/" + this.status.getHP();}
	//this.status.setFont(this.currentHP == 0 ? this.strike : this.genericFont);
	
	public abstract void instantiateForBattle();
	
	public void instantiate(){
		super.instantiate();
		int sizeX = 48;
		try {
			sizeX = new Image(spriteDirectory + "avatar.png").getWidth();
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		attack = new Animation();
		try {
			SpriteSheet tileSheet = new SpriteSheet(this.getSpriteDirectory() + "attack.png", sizeX, sizeX, new Color(0,0,0));
			for (int y = 0; y < tileSheet.getVerticalCount(); y++) {
				for (int x = 0; x < tileSheet.getHorizontalCount(); x++) {
					try{
						attack.addFrame(tileSheet.getSprite(x,y).getScaledCopy(SlickSKR.scaleSize), 120 / tileSheet.getHorizontalCount());
					}catch (Exception e){
						break;
					}
				}
				attack.setAutoUpdate(true);
				attack.setLooping(false);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		System.out.println("Frame Count: " + this.attack.getFrame());
	}
	
	public void instantiateSuper(){
		super.instantiate();
	}
	
	public boolean isAttacking(){
		return this.attack.getFrame() != this.attack.getFrameCount();
	}
	
	public Image getAnimatedFrame(){
		return this.attack.getCurrentFrame();
	}
	
	public int getCurrentFrameNumber(){
		return this.attack.getFrame();
	}

	public void setGauge(double gauge) {
		this.gauge = gauge;
	}

	public void setTechniques(ArrayList<Technique> techniques) {
		this.techniques = techniques;
	}
	
}