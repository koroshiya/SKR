package com.japanzai.skr;

import item.ConsumableItem;
import item.Item;
import item.StoreInstance;
import item.Weapon;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import console.dialogue.ComplexDialogue;
import console.dialogue.Dialogue;
import console.dialogue.Line;
import map.ParentMap;
import map.generator.MapGenerator;
import map.generator.TileGenerator;
import character.BossCharacter;
import character.EnemyCharacter;
import character.NonPlayableCharacter;
import character.PlayableCharacter;
import screen.GameScreen;
import slickgamestate.SlickSKR;
import technique.CombatTechnique;
import technique.HealingTechnique;
import technique.Technique;
import tile.CharacterTile;
import tile.ChestTile;
import tile.RandomTile;
import tile.ScaleTile;
import tile.SpanTile;
import tile.SpanTransitionTile;
import tile.Tile;
import tile.TransitionTile;
import tile.event.CharacterEventBattleTile;
import tile.event.CharacterEventStoreTile;
import tile.event.CharacterEventTile;

public class Driver implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final String grass = "/res/terrain/grass8.png";
	private final String mud = "/res/terrain/mud.png";
	
	ArrayList<FightingStyle> styles = new ArrayList<FightingStyle>();
	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	ArrayList<Gender> genders = new ArrayList<Gender>();
	ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
	ArrayList<EnemyCharacter> bossEnemies = new ArrayList<EnemyCharacter>();
	ArrayList<Technique> techniques = new ArrayList<Technique>();
	Properties prop = new Properties();
	private static Driver d;
	
	private GameScreen bs;
	
	private String getValueFromKey(String key){
		String result = prop.getProperty(key);
		return result == null ? "" : result;
	}

	private void instantiateFightingStyles(){
		
		//barbaric, assassin, tank
		FightingStyle street = new FightingStyle(11, 10, 7, 0.1, 5, 0.99, 9, getValueFromKey("character.class.streetfighter"));
		FightingStyle cop = new FightingStyle(9, 8, 6, 0.15, 8, 1.01, 10, getValueFromKey("character.class.modernsamurai"));
		FightingStyle wreckless = new FightingStyle(10, 10, 7, 0.08, 5, 0.90, 8, getValueFromKey("character.class.wrecklesshenchman"));
		FightingStyle taekwondo = new FightingStyle(12, 13, 13, 0.2, 6, 0.99, 12, getValueFromKey("character.class.taekwondo"));
		FightingStyle tank = new FightingStyle(15, 15, 15, 0.05, 4, 0.90, 6, getValueFromKey("character.class.tank"));

		//CombatTechnique(int attackStrength, int techniqueAccuracy, String name)
		String combatTechString = "technique.combat.";
		String healTechString = "technique.heal.";
		CombatTechnique rush = new CombatTechnique(1.2, 90, 5, 
				getValueFromKey(combatTechString + "name.rush"), 
				getValueFromKey(combatTechString + "desc.rush"), 1);
		CombatTechnique frenzy = new CombatTechnique(1.5, 70, 2, 
				getValueFromKey(combatTechString + "name.frenzy"), 
				getValueFromKey(combatTechString + "desc.frenzy"), 2);
		CombatTechnique sureHit = new CombatTechnique(0.8, -1, 4, 
				getValueFromKey(combatTechString + "name.surehit"), 
				getValueFromKey(combatTechString + "desc.surehit"), 4);
		HealingTechnique firstAid = new HealingTechnique(10, 4, 
				getValueFromKey(healTechString + "name.firstaid"), 
				getValueFromKey(healTechString + "desc.firstaid"), 2);

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
		
		String wpName = "weapon.class.name.";
		
		weapons.add(new Weapon(getValueFromKey(wpName + "barehanded"), 0, 10, 10, 0.1, 10, 10, 0.99, 0, false, 0, 0, getValueFromKey("weapon.image.barehanded.url")));
		weapons.add(new Weapon(getValueFromKey(wpName + "firearm"), 1, 10, 10, 0.1, 10, 10, 0.99, 20, true, 0, 0, getValueFromKey("weapon.image.firearm.url")));
		weapons.add(new Weapon(getValueFromKey(wpName + "bat"), 2, 10, 10, 0.1, 10, 10, 0.99, 3, false, 0, 0, getValueFromKey("weapon.image.bat.url")));
		weapons.add(new Weapon(getValueFromKey(wpName + "pickaxe"), 3, 10, 10, 0.1, 10, 10, 0.99, 3, false, 0, 0, getValueFromKey("weapon.image.pickaxe.url")));
		//weapons.add katana
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

		String kenInfo = "character.ken.";
		String yuminInfo = "character.yumin.";
		String pickaxeInfo = "character.pickaxe.";
		String taesooInfo = "character.taesoo.";
		
		//String doUnique = "Favorite weapon:" + "\n" + "4x2 plank" + "\n" + "\n" + "Enormous build. Loves his car.";
		
		ArrayList<Integer> wList = new ArrayList<Integer>();
		int i = -1;
		while (++i < Weapon.TYPE.length){
			wList.add(i);
		}
		
		PlayableCharacter ken = new PlayableCharacter(kenInfo, styles.get(0), weapons.get(0), genders.get(1), wList);
		//PlayableCharacter yumin = new PlayableCharacter(yuminInfo, styles.get(1), weapons.get(2), genders.get(0), wList);
		//PlayableCharacter pickaxe = new PlayableCharacter(pickaxeInfo, styles.get(2), weapons.get(3), genders.get(1), wList);
		//PlayableCharacter taesoo = new PlayableCharacter(taesooInfo, styles.get(3), weapons.get(0), genders.get(1), wList);
		/*PlayableCharacter doheun = new PlayableCharacter("Do-Heun", "Chang", 
				styles.get(4), weapons.get(7), techniques, genders.get(1), 
				"info", "Henchman", "212cm", "Korean", null, kenSprite, i);*/

		PlayableCharacter[] chars = {ken/*, yumin, pickaxe, taesoo*/};
		
		for (PlayableCharacter c : chars){
			for (Technique t : c.getFightingStyle().getTechnique(c.getLevel())){
				c.learnTechnique(t);
			}
			Party.toggleCharacterInParty(c);
			Party.addCharacter(c);
		}
		
	}
	
	private void instantiateEnemies(){

		ConsumableItem rice = new ConsumableItem(getValueFromKey("consumable.riceball.name"), 1, 0, 10, getValueFromKey("consumable.riceball.img"));
		
		EnemyCharacter henchman = new EnemyCharacter("enemy.militia.", styles.get(2), weapons.get(0), genders.get(1), 12, rice, 30, 20, 50);
		EnemyCharacter henchman2 = new EnemyCharacter("enemy.eye.", styles.get(0), weapons.get(0), genders.get(1), 16, rice, 50, 30, 80);
		EnemyCharacter henchman3 = new EnemyCharacter("enemy.slime.", styles.get(4), weapons.get(0), genders.get(1), 20, rice, 60, 40, 95);

		henchman2.setLevel(3);
		henchman3.setLevel(6);
		
		enemies.add(henchman);
		enemies.add(henchman2);
		enemies.add(henchman3);
		
		BossCharacter fatty = new BossCharacter("enemy.fatbastard.", styles.get(4), weapons.get(0), genders.get(1), 60, null, rice, 150, 70, 99);
		
		Dialogue d = new Dialogue();
		String str3 = "Ready to face your demise, maggot!?";
		d.addLine(new Line(fatty, str3, 0, false));
		fatty.setDialogue(d);
		
		fatty.setLevel(10);
		
		bossEnemies.add(fatty);
		
		Opponents.addEnemy(henchman);
		Opponents.addEnemy(henchman2);
		Opponents.addEnemy(henchman3);
		Opponents.addEnemy(fatty);
		
	}
	
	private void instantiateInventory(){
		
		Inventory.addWeapons(weapons);
		
		//String name, int value, int rarity, int potency
		ConsumableItem rice = new ConsumableItem(
			getValueFromKey("consumable.riceball.name"), 
			1, 0, 10, 
			getValueFromKey("consumable.riceball.img")
		);
		ConsumableItem kimchi = new ConsumableItem(
			getValueFromKey("consumable.kimchi.name"), 
			5, 0, 15, 
			getValueFromKey("consumable.kimchi.img")
		);
		
		rice.increaseQuantity(1);
		kimchi.increaseQuantity(1);
		
		Inventory.addItem(rice);
		Inventory.addItem(kimchi);
		Inventory.setMoney(50);
		
	}
	
	private void instantiate(){

		try {
			bs = new GameScreen(new SlickSKR());
		} catch (SlickException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ResourceLoader.getResourceAsStream("/res/script/names_en_US.properties"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
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
			Log.error("Driver caught error");
		}
		
		
	}
	
	public static void main(String[] args){restart();}
	
	private void simulateMap() throws IOException, SlickException{
		
		ParentMap current = null;
		ParentMap destination = null;
		
		Point mapSize = new Point(16 * SlickSKR.scaled_icon_size, 13 * SlickSKR.scaled_icon_size);
		//Point coordinates = new Point(32 * SlickSKR.scaled_icon_size, 24 * SlickSKR.scaled_icon_size); //Multiple base by pix size?

		int flX = SlickSKR.size.x / 2;
		flX -= flX % SlickSKR.scaled_icon_size;
		int flY = SlickSKR.size.y / 2;
		flY -= flY % SlickSKR.scaled_icon_size;
		Point currentPosition = new Point(flX, flY);
		PlayableCharacter animatedSprite = Party.getCharacterByIndex(0);

		//SlickSKR sk = null;
		current = new ParentMap(currentPosition, animatedSprite, enemies, bs, mapSize, 97, grass, "other/public/summeropenfielddusk.ogg");

		Tile[][] deadTile = createDeadMap(32 * SlickSKR.scaled_icon_size, 26 * SlickSKR.scaled_icon_size,destination,current);
		Point newCurPos = new Point(20 * SlickSKR.scaled_icon_size, 0);
		destination = new ParentMap(newCurPos, animatedSprite, bossEnemies, bs, mapSize, 5, mud, "other/public/Cavern.ogg");	
		destination.setTiles(deadTile);
		
		TileGenerator tGenerator = new TileGenerator(basicRandomTiles());
		MapGenerator generator = new MapGenerator(basicPresetTiles(current, destination), tGenerator, 32, 26, "/res/terrain/border/");		
		current.setTiles(generator.generateMap());
		
		bs.setSKR(new SlickSKR(current));
		
		bs.start();
		
	}
	
	private RandomTile[] basicRandomTiles() throws SlickException{

		String grass = ("/res/terrain/grass8.png");
		//String grass = ("/res/terrain/grass3.png");
		//String rock = ("/res/terrain/rock.png");
		String weed = ("/res/terrain/weed02.png");
		String pine = ("/res/terrain/pine-none03.png");
		String shrub = ("/res/terrain/shrub2-05.png");
		String chestClosed = ("/res/terrain/closedChest.png");
		String chestOpen = ("/res/terrain/openChest.png");
		String boxO = ("/res/terrain/box-open.png");
		String boxC = ("/res/terrain/box-closed.png");
		
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(Inventory.getItem("Riceball"));
		items.add(Inventory.getItem("Kimchi"));
		
		Tile grassTile = new ScaleTile(true, grass,0,0,false);
		Tile chestTile = new ChestTile(chestClosed, chestOpen, items, 0, 0, false);
		//Tile rockTile = new Tile(false, false, rock);
		Tile pineTile = new SpanTile(true, pine, 155, 149,0,0,true);
		Tile weedTile = new Tile(true, weed,0,0,true);
		Tile shrubTile = new Tile(true, shrub,0,0,true);
		Tile boxCTile = new ChestTile(boxC, boxO, items, 0, 0, false);
		
		return new RandomTile[]{
			new RandomTile(chestTile, 99),
			new RandomTile(pineTile, 98),
			new RandomTile(shrubTile, 98),
			new RandomTile(weedTile, 98),
			new RandomTile(boxCTile, 98),
			//new RandomTile(rockTile, 98),

			new RandomTile(grassTile, 0)
		};
		
	}
	
	private Tile[] basicPresetTiles(ParentMap current, ParentMap destination) throws IOException, SlickException{

		String imgNPC = ("/res/enemy/militia/");
		String imgKoro = ("/res/enemy/koro/");
		
		ArrayList<String> dialogue = new ArrayList<String>();
		dialogue.add("Hello there, Ken.");
		dialogue.add("This game really lacks content, doesn't it?");
		
		ArrayList<Item> storeItems = new ArrayList<Item>();
		storeItems.add(Inventory.getConsumables().get(0).create(5));
		storeItems.add(Inventory.getConsumables().get(1).create(2));
		StoreInstance store = new StoreInstance(storeItems);
		
		ArrayList<String> dialogue2 = new ArrayList<String>();
		dialogue2.add("PREPARE TO FACE YOUR DEATH, MAGGOT!");

		CharacterEventTile tileNPC = new CharacterEventStoreTile(imgNPC, InteractiveNPC(imgNPC, dialogue), store, 1, 5);
		CharacterEventTile tileEnemy = new CharacterEventBattleTile(imgNPC, InteractiveNPC(imgNPC, dialogue2), this.enemies.get(2).create(), 3, 17);
		Tile basicTile = new Tile(true, "/res/terrain/grass8.png", 16, 12,false);
		Tile tTile = new TransitionTile("/res/terrain/border/borderExitTop.png", destination, current, 20, 16, 20, 0,false);
		//Tile pineTile = new SpanTile(true, "/res/terrain/pine-none03.png", 155, 149, 19, 10,false);
		Tile tentTile = new SpanTransitionTile(destination, current, "/res/terrain/building/tent.png", 228, 216, 3, 3, 10, 20,false);
		/*
		 String sprite, ParentMap map,
			ParentMap currentMap, int startX, int startY, 
			int width, int height, int entranceXIndex, int entranceYIndex
		 * */
		
		return new Tile[]{tileNPC, basicTile, NPC(imgKoro, 16, 10), tTile, tileEnemy, /*pineTile,*/ tentTile};
		
	}
	
	private Tile[][] createDeadMap(int a, int b, ParentMap current, ParentMap destination) throws SlickException{
		
		int x = (int)Math.floor((double)a / SlickSKR.scaled_icon_size);
		int y = (int)Math.floor((double)b / SlickSKR.scaled_icon_size);
		
		Tile[][] tile = new Tile[x][y];
		String mud = ("/res/terrain/mud.png");
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(Inventory.getItem("Riceball"));
		items.add(Inventory.getItem("Kimchi"));
		
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){				
				tile[i][j] = new Tile(true, mud, i, j,false);
			}
		}
		tile[20][16] = new TransitionTile("/res/terrain/weed02.png", current, destination, 20, 0, 20, 16,false);
		
		return tile;
		
	}
	
	private CharacterTile NPC(String imgNPC, int x, int y) throws SlickException{
		
		NonPlayableCharacter npc = new NonPlayableCharacter("Random", "NPC", genders.get(0), null, "NPC", "/res/enemy/koro/");
		//Dialogue dialogue = new Dialogue(d, npc); //Character hasn't been created yet
		
		ComplexDialogue cd = new ComplexDialogue();
		String str1 = "Greetings, Ken. I am your programmer.";
		String str2 = "How does it feel to meet your creator? \nYou aren't scared, are you?";
		cd.addLine(new Line(npc, str1, 0, false));
		cd.addLine(new Line(npc, str2, 1, true));
		
		Dialogue d1 = new Dialogue();
		String str3 = "That's okay. Fear is nothing to be ashamed of.";
		d1.addLine(new Line(npc, str3, 0, false));
		
		ComplexDialogue d2 = new ComplexDialogue();
		String str4 = "The fearless type, huh?";
		d2.addLine(new Line(npc, str4, 0, true));
		
		Dialogue d3 = new Dialogue();
		String str5 = "Boichi-sama taught you well.";
		d3.addLine(new Line(npc, str5, 0, false));

		Dialogue d4 = new Dialogue();
		String str6 = "Modesty is a good trait to possess.";
		d4.addLine(new Line(npc, str6, 0, false));
		
		d2.addDialogue(d3);
		d2.addDialogue(d4);
		
		cd.addDialogue(d1);
		cd.addDialogue(d2);
		
		npc.setDialogue(cd);
		
		return new CharacterTile(imgNPC, npc, x, y);
		
	}
	
	private NonPlayableCharacter InteractiveNPC(String imgNPC, ArrayList<String> d) throws SlickException{
		
		NonPlayableCharacter npc = new NonPlayableCharacter("Random", "NPC", genders.get(0), null, "NPC", "/res/enemy/militia/");
		//Dialogue dialogue = new Dialogue(d, npc); //Character hasn't been created yet
		npc.getDialogue().addLines(d, npc);
		
		return npc;
		
	}
	
	public static void restart(){
		d = new Driver();
		d.instantiate();
	}
	
	public static void quit(){
		
		/*int choice = MessageBox.ChoiceBox("Any unsaved progress will be lost." + 
											"\n" + "Are you sure you want to quit?", "Quit", null);
		
		if (choice == JOptionPane.YES_OPTION){
			System.exit(0);
		}*/
		//TODO: implement check of some kind
		System.exit(0);
		
	}
	
}
