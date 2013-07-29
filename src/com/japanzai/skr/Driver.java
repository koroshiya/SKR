package com.japanzai.skr;

import item.ConsumableItem;
import item.Item;
import item.Weapon;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.newdawn.slick.SlickException;

import com.japanzai.jreader.Pairing;
import com.japanzai.jreader.dialog.JxDialog;

import map.CharacterTile;
import map.ChestTile;
import map.MapGenerator;
import map.ParentMap;
import map.PresetTile;
import map.RandomTile;
import map.Tile;
import map.TileGenerator;
import map.TransitionTile;

import character.BossCharacter;
import character.EnemyCharacter;
import character.NonPlayableCharacter;
import character.PlayableCharacter;

import screen.GameScreen;
import screen.MessageBox;
import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;

import animation.AnimatedSprite;


 
public class Driver implements Serializable{

	private static final long serialVersionUID = 1L;
	
	ArrayList<FightingStyle> styles = new ArrayList<FightingStyle>();
	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	ArrayList<Gender> genders = new ArrayList<Gender>();
	ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
	ArrayList<EnemyCharacter> bossEnemies = new ArrayList<EnemyCharacter>();
	ArrayList<Technique> techniques = new ArrayList<Technique>();
	private static Driver d;
	
	private GameScreen bs;

	private void instantiateFightingStyles(){
		
		//barbaric, assassin, tank
		FightingStyle street = new FightingStyle(11, 10, 7, 0.1, 5, 0.99, 9, "Street Fighter");
		FightingStyle cop = new FightingStyle(9, 8, 6, 0.15, 8, 1.01, 10, "Modern Samurai");
		FightingStyle wreckless = new FightingStyle(10, 10, 7, 0.08, 5, 0.90, 8, "Wreckless Henchman");
		FightingStyle taekwondo = new FightingStyle(12, 13, 13, 0.2, 6, 0.99, 12, "Tae kwon do");
		FightingStyle tank = new FightingStyle(15, 15, 15, 0.05, 4, 0.90, 6, "Tank");

		//CombatTechnique(int attackStrength, int techniqueAccuracy, String name)
		CombatTechnique rush = new CombatTechnique(1.2, 90, 5, 
				"Rush", "Player rushes their opponent headfirst", 1);
		CombatTechnique frenzy = new CombatTechnique(1.5, 70, 2, 
				"Frenzy", "Player loses their cool and unleashes a wild attack", 2);
		CombatTechnique sureHit = new CombatTechnique(0.8, -1, 4, 
				"Planned Strike", "Player bides their time, waiting for the opportune time to strike", 4);
		HealingTechnique firstAid = new HealingTechnique(10, 4, 
				"First aid", "Player performs first aid on another active party member", 2);

		street.addTechnique(rush);
		cop.addTechnique(rush);
		cop.addTechnique(sureHit);
		cop.addTechnique(firstAid);
		wreckless.addTechnique(rush);
		wreckless.addTechnique(frenzy);
		taekwondo.addTechnique(rush);
		taekwondo.addTechnique(sureHit);
		tank.addTechnique(rush);
		
		styles.add(street);
		styles.add(cop);
		styles.add(wreckless);
		styles.add(taekwondo);
		styles.add(tank);
		
	}
	
	private void instantiateWeapons(){
		
		String strWeaponDir = "/res/item/weapon/sword.png";
		
		Weapon fists = new Weapon("Fists", 0, 10, 10, 0.1, 10, 10, 0.99, 0, false, 0, 0, strWeaponDir);
		Weapon gun = new Weapon("Gun", 3, 10, 10, 0.1, 10, 10, 0.99, 20, true, 0, 0, strWeaponDir);
		Weapon katana = new Weapon("Katana", 4, 10, 10, 0.1, 10, 10, 0.99, 3, false, 0, 0, strWeaponDir);
		Weapon pickaxe = new Weapon("Pickaxe", 1, 10, 10, 0.1, 10, 10, 0.99, 3, false, 0, 0, strWeaponDir);
		Weapon bat = new Weapon("Wooden Bat", 2, 10, 10, 0.1, 10, 10, 0.99, 3, false, 0, 0, strWeaponDir);
		Weapon knife = new Weapon("Sashimi Knife", 5, 10, 10, 0.1, 10, 10, 0.99, 1, true, 0, 0, strWeaponDir);
		Weapon wrench = new Weapon("Wrench", 6, 10, 10, 0.1, 10, 10, 0.99, 1, true, 0, 0, strWeaponDir);
		Weapon log = new Weapon("Log", 7, 10, 10, 0.1, 10, 10, 0.99, 0, false, 0, 0, strWeaponDir);
		
		weapons.add(fists);
		weapons.add(pickaxe);
		weapons.add(bat);
		weapons.add(gun);
		weapons.add(katana);
		weapons.add(knife);
		weapons.add(wrench);
		weapons.add(log);
		
		/*for (Weapon weapon : weapons){
			weapon.increaseQuantity(1);
		}*/
		
	}
	
	private void instantiateGenders(){
		
		Gender female = new Gender(false);
		Gender male = new Gender(true);
		
		genders.add(female);
		genders.add(male);
		
	}
	
	private void instantiatePlayableCharacters(){
		
		String kenSprite = "/res/party/ken/";
		String yuminSprite = "/res/party/yumin/";
		String taeSprite = "/res/party/taesoo/";
		String pickSprite = "/res/party/pickaxe/";
		
		String kenUnique = "<html>Favourite weapon:" + "<br />" + "Metal baseball bat" + "<br />" + "<br />" + 
								"Ken's family was killed by the Hakuryu-Kai when he was 13 years old.</html>";
		String yuminUnique = "<html>Favourite weapon:" + "<br />" + "Dual wielding handgun & katana" + "<br />" + "<br />" + 
								"Yumin's father branded her with a Oshiroi-Bori tatoo on her back.</html>";
		
		PlayableCharacter ken = new PlayableCharacter("Ken", "Kitano", 
				styles.get(0), weapons.get(0), genders.get(1), 
				kenUnique, "Boss", "175cm", "Japanese", null, kenSprite);
		PlayableCharacter yumin = new PlayableCharacter("Yumin", "Yoshizawa", 
				styles.get(1), weapons.get(2), genders.get(0), 
				yuminUnique, "Policewoman", "163cm", "Korean", null, yuminSprite);
		PlayableCharacter pickaxe = new PlayableCharacter("San-Dae", "Yang", 
				styles.get(2), weapons.get(3), genders.get(1), "info", 
				"Henchman", "165cm", "Korean", "Pickaxe", pickSprite);
		PlayableCharacter taesoo = new PlayableCharacter("Tae-Soo", "Park", 
				styles.get(3), weapons.get(0), genders.get(1), 
				"info", "Ken's right-hand man", "182cm", "Korean", null, taeSprite);
		/*PlayableCharacter doheun = new PlayableCharacter("Do-Heun", "Chang", 
				styles.get(4), weapons.get(7), techniques, genders.get(1), 
				"info", "Henchman", "212cm", "Korean", null, kenSprite, i);*/

		PlayableCharacter[] chars = {ken, yumin, pickaxe, taesoo};
		
		for (PlayableCharacter c : chars){
			for (Technique t : c.getFightingStyle().getTechnique(c.getLevel())){
				c.learnTechnique(t);
			}
			Party.toggleCharacterInParty(c);
			Party.addCharacter(c);
		}
		
		//Party.toggleCharacterInParty(doheun);
		//Party.addCharacter(doheun);
		
	}
	
	private void instantiateEnemies(){

		String genericEnemySprite = "/res/enemy/militia/";
		String eye = "/res/enemy/monster/eyeball/";
		String slime = "/res/enemy/monster/slime/";
		String fat = "/res/enemy/human/boss/fatty/";
		ConsumableItem rice = new ConsumableItem("Riceball", 1, 0, 10, null);
							
		EnemyCharacter henchman = new EnemyCharacter("Random", "Flunkie", 
				styles.get(2), weapons.get(0), genders.get(1), 12, "Militia", genericEnemySprite,
				rice, 30, 20, 50);
		EnemyCharacter henchman2 = new EnemyCharacter("Eye", "Ball", 
				styles.get(0), weapons.get(0), genders.get(1), 16, "Eyeball", eye,
				rice, 50, 30, 80);
		EnemyCharacter henchman3 = new EnemyCharacter("Slime", "Hand", 
				styles.get(4), weapons.get(0), genders.get(1), 20, "Slime", slime,
				rice, 60, 40, 95);

		henchman2.setLevel(3, null);
		henchman3.setLevel(6, null);
		
		enemies.add(henchman);
		enemies.add(henchman2);
		enemies.add(henchman3);
		
		BossCharacter fatty = new BossCharacter("Fat", "Bastard", 
						styles.get(4), weapons.get(0), genders.get(1), 60, null, "Fatty", fat,
						rice, 150, 70, 99);
		
		Dialogue d = new Dialogue();
		String str3 = "Ready to face your demise, maggot!?";
		d.addLine(new Line(fatty, str3, 0, false));
		fatty.setDialogue(d);
		
		fatty.setLevel(10, null);
		
		bossEnemies.add(fatty);
		
		Opponents.addEnemy(henchman);
		Opponents.addEnemy(henchman2);
		Opponents.addEnemy(henchman3);
		Opponents.addEnemy(fatty);
		
		
	}
	
	private void instantiateInventory(){
		
		Inventory.addWeapons(weapons);

		String imgRice = "/res/item/food/riceball.png";
		String imgKimchi = "/res/item/food/kimchi.png";
		//String name, int value, int rarity, int potency
		ConsumableItem rice = new ConsumableItem("Riceball", 1, 0, 10, imgRice);
		rice.increaseQuantity(1);
		ConsumableItem kimchi = new ConsumableItem("Kimchi", 5, 0, 15, imgKimchi);
		kimchi.increaseQuantity(1);
		
		Inventory.addItem(rice);
		Inventory.addItem(kimchi);
		

		
	}
	
	private void instantiate(){
		
		instantiateFightingStyles();
		instantiateWeapons();
		instantiateGenders();
		instantiateInventory();
		instantiatePlayableCharacters();
		instantiateEnemies();
		
		//simulateBattle();
		try {
			simulateMap();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException ex) {
			ex.printStackTrace();
			System.out.println("Driver caught error");
		}
		
		
	}
	
	public static void main(String[] args){
			
		restart();
	
	}
	
	private void simulateMap() throws IOException, SlickException{
		
		
		
		ParentMap current = null;
		ParentMap destination = null;
		String grass = ("/res/terrain/grass.png");
		
		Point mapSize = new Point(16 * ParentMap.ICON_SIZE, 13 * ParentMap.ICON_SIZE);
		Point coordinates = new Point(32 * ParentMap.ICON_SIZE, 24 * ParentMap.ICON_SIZE); //Multiple base by pix size?
		Point currentPosition = new Point(8 * ParentMap.ICON_SIZE, 6 * ParentMap.ICON_SIZE);
		AnimatedSprite animatedSprite = new AnimatedSprite(Party.getCharacterByIndex(0));
		

		//SlickSKR sk = null;
		bs = new GameScreen(new SlickSKR());
		current = new ParentMap(coordinates, currentPosition, Party.getCharacterByIndex(0),
									animatedSprite, enemies, bs, mapSize, 97, 
									grass);

		TileGenerator tGenerator = new TileGenerator(basicRandomTiles());
		MapGenerator generator = new MapGenerator(basicPresetTiles(current, destination), tGenerator, 32, 26, "/res/terrain/border/");		
		current.setTiles(generator.generateMap());
		
		Tile[][] deadTile = createDeadMap(32 * ParentMap.ICON_SIZE, 26 * ParentMap.ICON_SIZE);
		Point newCurPos = new Point(20 * ParentMap.ICON_SIZE, 0);
		
		bs.setSKR(new SlickSKR(current));
		//bs.setSKR(sk);
		
		destination = new ParentMap(coordinates, newCurPos, Party.getCharacterByIndex(0),
				animatedSprite, bossEnemies, bs, mapSize, 5, 
				"/res/terrain/mud.png");	
		destination.setTiles(deadTile);

		
		//bs.setFocusable(true);
		//bs.setMap(current);
		bs.start();
		//bs.setBattle(enemies);
		//bs.start();
		//SlickSKR skr = new SlickSKR();
		
	}
	
	private ArrayList<RandomTile> basicRandomTiles() throws SlickException{
		
		String grass = ("/res/terrain/grass.png");
		String rock = ("/res/terrain/rock.png");
		String chestClosed = ("/res/terrain/closedChest.png");
		String chestOpen = ("/res/terrain/openChest.png");
		
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(Inventory.getItem("Riceball"));
		items.add(Inventory.getItem("Kimchi"));
		
		Tile grassTile = new Tile(true, true, grass);
		Tile chestTile = new ChestTile(chestClosed, chestOpen, grass, items);
		Tile rockTile = new Tile(false, false, rock);
		
		RandomTile rt1 = new RandomTile(chestTile, 99);
		RandomTile rt2 = new RandomTile(rockTile, 85);
		RandomTile rt3 = new RandomTile(grassTile, 0);
		
		ArrayList<RandomTile> rTiles = new ArrayList<RandomTile>();
		rTiles.add(rt1);
		rTiles.add(rt2);
		rTiles.add(rt3);
		
		return rTiles;
		
	}
	
	private ArrayList<PresetTile> basicPresetTiles(ParentMap current, ParentMap destination) throws IOException, SlickException{
				
		String imgNPC = ("/res/enemy/militia/");
		String imgKoro = ("/res/enemy/koro/");
		String tilePic = ("/res/terrain/border/borderExitTop.png");
		String grassPic = ("/res/terrain/grass.png");
		
		ArrayList<String> dialogue = new ArrayList<String>();
		dialogue.add("Hello there, Ken.");
		dialogue.add("This game really lacks content, doesn't it?");
		Tile tileNPC = NPC(imgNPC, dialogue);
		PresetTile preTile1 = new PresetTile(tileNPC, 1, 5);
		
		Tile basicTile = new Tile(true, true, grassPic, 16, 12);
		PresetTile preTile2 = new PresetTile(basicTile, 16, 12);
		
		Tile tileKoro = NPC(imgKoro);
		PresetTile preTile3 = new PresetTile(tileKoro, 16, 10);
		
		Tile tTile = new TransitionTile(tilePic, destination, current);
		PresetTile preTile4 = new PresetTile(tTile, 20, 0);
		
		ArrayList<PresetTile> basicPresetTiles = new ArrayList<PresetTile>();
		basicPresetTiles.add(preTile1);
		basicPresetTiles.add(preTile2);
		basicPresetTiles.add(preTile3);
		basicPresetTiles.add(preTile4);
		
		return basicPresetTiles;
		
	}
	
	private Tile[][] createDeadMap(int a, int b) throws SlickException{
		
		int x = (int)Math.floor((double)a / ParentMap.ICON_SIZE);
		int y = (int)Math.floor((double)b / ParentMap.ICON_SIZE);
		
		Tile[][] tile = new Tile[x][y];
		String mud = ("/res/terrain/mud.png");
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(Inventory.getItem("Riceball"));
		items.add(Inventory.getItem("Kimchi"));

		
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){				
				tile[i][j] = new Tile(true, true, mud, i, j);
			}
		}
		
		return tile;
		
	}
	
	private CharacterTile NPC(String imgNPC) throws SlickException{
		
		NonPlayableCharacter npc = new NonPlayableCharacter("Random", "NPC", genders.get(0), null, "NPC", "/res/enemy/koro/");
		//Dialogue dialogue = new Dialogue(d, npc); //Character hasn't been created yet
		
		ComplexDialogue cd = new ComplexDialogue();
		String str1 = "Greetings, Ken. I am your programmer.";
		String str2 = "<html>How does it feel to meet your creator? <br />You aren't scared, are you?</html>";
		cd.addLine(new Line(npc, str1, 0, false));
		cd.addLine(new Line(npc, str2, 1, true));
		
		Dialogue d1 = new Dialogue();
		String str3 = "That's okay. Fear is nothing to be ashamed of.";
		d1.addLine(new Line(npc, str3, 0, false));
		
		Dialogue d2 = new Dialogue();
		String str4 = "The fearless type, huh?";
		String str5 = "Boichi-sama taught you well.";
		d2.addLine(new Line(npc, str4, 0, false));
		d2.addLine(new Line(npc, str5, 1, false));

		cd.addDialogue(d1);
		cd.addDialogue(d2);
		
		npc.setDialogue(cd);
		
		return new CharacterTile(imgNPC, npc);
		
	}
	
	private CharacterTile NPC(String imgNPC, ArrayList<String> d) throws SlickException{
		
		NonPlayableCharacter npc = new NonPlayableCharacter("Random", "NPC", genders.get(0), null, "NPC", "/res/");
		//Dialogue dialogue = new Dialogue(d, npc); //Character hasn't been created yet
		npc.getDialogue().addLines(d, npc);
		
		return new CharacterTile(imgNPC, npc);
		
	}
	
	public static void restart(){
		d = new Driver();
		d.instantiate();
	}

	public static void save(File f) throws IOException{
		
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(Inventory.getMoney());
		oos.writeObject(Inventory.getItems());
		oos.writeObject(Party.getCharacters());
		//oos.writeObject(d.bs);
		
	}
	
	public static void save() throws IOException{
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new SaveFilter());
		//jfc.showSaveDialog(d.bs); //TODO: reimplement
		File f = jfc.getSelectedFile();
		
		if (f != null){
			if (!f.getName().endsWith(".sks")){
				f = new File(f.getAbsolutePath() + ".sks");
			}
			save(f);
		}else {
			save(new File("skr_save.sks"));
		}
		
	}
	
	public static void load(File f) throws IOException, ClassNotFoundException{
		
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Inventory.setMoney((Integer)ois.readObject());
		Inventory.setItems((ArrayList<Item>)ois.readObject());
		Party.setParty((ArrayList<PlayableCharacter>)ois.readObject());
		//d.bs = (BattleScreen)ois.readObject();
		//d.repaint();
		
	}
	
	public static void load() throws IOException, ClassNotFoundException{
		
		//JFileChooser jfc = new JFileChooser();
		//jfc.setFileFilter(new SaveFilter());
		//jfc.showOpenDialog(d.bs);
		//File f = jfc.getSelectedFile();
		ArrayList<Pairing> pairings = new ArrayList<Pairing>();
		File f = new File("");;
		JxDialog jx;
		pairings.add(new Pairing(new ImageIcon(f.getClass().getResource("/images/avatar.png")), ".sks"));
		jx = new JxDialog(pairings);
		f = jx.showDialog();
		
		if (f != null && f.exists() && f.getName().endsWith(".sks")){
			load(f);
		}else {
			load(new File("skr_save.sks"));
		}
	}
	
	public static void quit(){
		
		int choice = MessageBox.ChoiceBox("Any unsaved progress will be lost." + 
											"\n" + "Are you sure you want to quit?", "Quit", null);
		
		if (choice == JOptionPane.YES_OPTION){
			System.exit(0);
		}
		
	}
	
	/*
Name : Ken
Age : 18yo (currently 20ish)


Name : Yumin (real name : Yumi)
Nickname : N/A
Age : 18yo (currently 20ish)
Nationality : Korean (real nationality : Japanese)
Favourite weapon : Handgun + katana
Unique : Oshiroi-Bori tatoo on her back

Name : Do-Heun
Favourite weapon : 4x2 log
Unique : Build, love his car

Name : Tae-Soo
Unique : Tatoo on his back

Nickname : Pickaxe
Favourite weapon : Pickaxe
Unique : Character, hu ?

Name : San-Ki
Nickname : Marin
Surname : Lee
Sex : Male
Height : 180cm
Nationality : Korean
Occupation : Henchman
Favourite weapon : Double wrench (sometime gun)
Unique : Ex Korean soldier (tae-soo's loyal underling)

Name : Ji-Hae
Nickname : Miss Yoo
Surname : Yoo
Sex : Female
Nationality : Korean
Occupation : head of the pleasure district
Unique : Ex Barmaid (2 times rape victim)

Name : Ban Phuong
Surname : Kim
Sex : Male
Nationality : Korean
Occupation : currently boss of an assassin team
Favourite weapon : Muai Thai
Unique : Korean father and Vietnamese mother, Ken's rival

Name : Bae-Dal
Nickname : Master Zen
Surname : Choi
Sex : Male
Nationality : Korean
Occupation : Casino Director
Unique : ex monk

Name : Kae-Lyn
Surname : Kim
Sex : Female
Age : 18
Nationality : Korean
Occupation : Henchman + Weapon specialist
Favourite weapon : Double sashimi knife
Unique : Bust size over 100cm

Name : Ryu
Nickname : Godfather
Surname : Yoshizawa
Sex : Male
Nationality : Japanese
Occupation : White Dragon Clan's Boss
Unique : Yumin's father

Name : Rain
Nickname : Crow
Surname : Fujimi
Sex : Male
Nationality : Japanese
Occupation : White Dragon Clan's hitman and Boss' right hand
Favourite weapon : Double semi-auto handgun + Katana
Unique : Right hand was cut by Yumin

Name : Benito
Nickname : Italian Stallion
Surname : Armani
Sex : Male
Age : 18
Nationality : Italian
Occupation : Henchman
Fighting style: wild, kinda like parkour
Unique : Huge ****
	 * */

}
