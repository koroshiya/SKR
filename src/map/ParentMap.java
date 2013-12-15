package map;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import screen.GameScreen;
import slickgamestate.MapScreen;
import tile.Tile;
import tile.TransitionTile;
import character.Character;
import character.BossCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

public class ParentMap {
	
	private Point coordinates;
	private double currentPositionx;
	private double currentPositiony;
	private TileMap tileMap;
	private int direction;
	private ArrayList<EnemyCharacter> enemies;
	
	private PlayableCharacter animatedSprite;
	private GameScreen parent;
	
	public static final int LEFT = 37;
	public static final int RIGHT = 39;
	public static final int UP = 38;
	public static final int DOWN = 40;
	
	private boolean locked;
	private int encounterRate;
	 //TODO: turn into constructor
	//TODO: enemy or battle arraylist
	//TODO: dynamically create battle depending on enemy arraylist? mix and match
	private String defaultTile;
	private Image cache;
	private final String themeMusic;
	
	private float xDiff;
	private float yDiff;
	
	public ParentMap(Point coordinates, Point currentPosition, 
			PlayableCharacter animatedSprite, ArrayList<EnemyCharacter> enemies,
			GameScreen parent, Point mapSize, int encounterRate, String grass,
			String theme){

		this.parent = parent;
		this.coordinates = coordinates;
		this.currentPositionx = currentPosition.x;
		this.currentPositiony = currentPosition.y;
		this.tileMap = null;
		this.direction = DOWN;
		this.animatedSprite = animatedSprite;
		this.enemies = enemies;
		this.encounterRate = encounterRate;
		this.defaultTile = grass;
		this.themeMusic = theme;
	
	}
	
	public void instantiateImages() throws SlickException{
		tileMap.instantiate(defaultTile);
		animatedSprite.instantiate();
		this.showSprite();
	}
	
	public void moveToPosition(Point p){moveToPosition(p.x, p.y);}
	
	public synchronized void moveToPosition(double x, double y){

		this.currentPositionx = x;
		this.currentPositiony = y;
		((MapScreen)parent.getState()).resetPosition((float)xDiff, (float)yDiff);
		xDiff = 0;
		yDiff = 0;
		this.getTileByPosition(x/MapScreen.ICON_SIZE, y/MapScreen.ICON_SIZE).stepOn();
		
	}
	
	public boolean canMoveToPosition(double d, double currentPositiony2){
		
		double x = d / MapScreen.ICON_SIZE + this.getCharacterPositionX();
		double y = currentPositiony2 / MapScreen.ICON_SIZE + this.getCharacterPositionY();
		
		if (!tileExists(x, y)){return false;}
		
		if (x <= getXBoundary() && y <= getYBoundary()){
			if (x != this.currentPositionx || y != this.currentPositiony){
				return tileMap.isOpenAndReachable(x, y);
			}
		}
		
		return false;
		
	}
	
	public void tryMoveToTile(int x, int y) {
		
		x += this.currentPositionx / MapScreen.ICON_SIZE;
		y += this.currentPositiony / MapScreen.ICON_SIZE;
		
		if (x < 0 || y < 0 || x >= tileMap.getWidthInTiles() || y >= tileMap.getHeightInTiles()){return;}
		
		int a = (int) (Math.floor(this.currentPositionx / MapScreen.ICON_SIZE) + this.getCharacterPositionX());
		int b = (int) (Math.floor(this.currentPositiony / MapScreen.ICON_SIZE) + this.getCharacterPositionY());

		Point start = new Point(a, b);
		Point finish = new Point(x, y);
		
		AStarPathFinder finder = new AStarPathFinder(tileMap, 60, false);
		Path pt = finder.findPath(null, start.x, start.y, finish.x, finish.y);
		
		if (pt != null){
			ArrayList<Point> path = new ArrayList<Point>();
			Step step;
			for (int i = 0; i < pt.getLength(); i++){
				step = pt.getStep(i);
				path.add(new Point(step.getX(), step.getY()));
			}
			move(path);
		}else{
			findDirection(finish);
			showSprite();
		}
		
	}
	
	public boolean relativeTileExists(int a, int b){
		
		int x = a / MapScreen.ICON_SIZE + this.getCharacterPositionX();
		int y = b / MapScreen.ICON_SIZE + this.getCharacterPositionY();
		
		return tileExists(x, y);
		
	}
	
	public boolean tileExists(double x, double y){
		
		return getTileByPosition(x, y) != null;
		
	}
	
	public Tile getTileByPosition(double x2, double y2){
		
		double x = x2 / MapScreen.ICON_SIZE;
		double y = y2 / MapScreen.ICON_SIZE;
		
		return getTileByIndex(x, y);
		
	}
	
	public Tile getTileByIndex(double x, double y){
		
		return this.tileMap.getTileByIndex(x, y);
		
	}
	
	public int getHeight(){return this.tileMap.getHeightInTiles() * MapScreen.ICON_SIZE;}
	
	public int getWidth(){return this.tileMap.getWidthInTiles() * MapScreen.ICON_SIZE;}
	
	public double getPositionX(){return this.currentPositionx;}
	
	public double getPositionY(){return this.currentPositiony;}
	
	public int getXBoundary(){return this.coordinates.x;}
	
	public int getYBoundary(){return this.coordinates.y;}
	
	public synchronized int getCharacterPositionX(){
		return (int) Math.floor(this.coordinates.getX() / 4 / MapScreen.ICON_SIZE);
	}
	
	public synchronized int getCharacterPositionY(){
		return (int) Math.floor(this.coordinates.getY() / 4 / MapScreen.ICON_SIZE);
	}
	
	public synchronized double getRelativeX(){
		return (this.currentPositionx / MapScreen.ICON_SIZE) + this.getCharacterPositionX();
	}
	
	public synchronized double getRelativeY(){
		return (this.currentPositiony / MapScreen.ICON_SIZE) + this.getCharacterPositionY();
	}
	
	public void setDirection(int dir){
		
		if (dir == LEFT || dir == RIGHT || dir == UP || dir == DOWN){
			this.direction = dir;
			showSprite();
		}
		
	}
	
	public int getDirection(){return this.direction;}
	
	public void moveDown() {
		
		move(this.currentPositionx, this.currentPositiony + MapScreen.ICON_SIZE, DOWN);
		
	}
	
	public void moveUp() {
		
		move(this.currentPositionx, this.currentPositiony - MapScreen.ICON_SIZE, UP);
		
	}
	
	public void moveLeft() {
		
		move(this.currentPositionx - MapScreen.ICON_SIZE, this.currentPositiony, LEFT);
		
	}
	 
	public void moveRight() {
		
		move(this.currentPositionx + MapScreen.ICON_SIZE, this.currentPositiony, RIGHT);
		
	}
	
	public void move(int dir) {
		
		if (dir == LEFT){
			moveLeft();
		}else if (dir == RIGHT){
			moveRight();
		}else if (dir == UP){
			moveUp();
		}else if (dir == DOWN){
			moveDown();
		}
		
	}
	
	private synchronized void move(ArrayList<Point> pts){
		
		if (!isLocked()){
			
			lock();
			
			Thread thread = new Thread(new spriteThread(this, this.animatedSprite, pts));
			thread.start();
				
		}
	}
	
	private synchronized void move(double d, double currentPositiony2, int dir) {

		if (!isLocked()){
		
			lock();
			this.direction = dir;
			showSprite();
			
			if (canMoveToPosition(d, currentPositiony2)){
				
				Thread thread = new Thread(new spriteThread(this, this.animatedSprite, d, currentPositiony2));
				thread.start();

				Tile tile = getTileByPosition(d + (this.getCharacterPositionX() * MapScreen.ICON_SIZE), 
												currentPositiony2 + (this.getCharacterPositionY() * MapScreen.ICON_SIZE));
				if (tile instanceof TransitionTile){
					TransitionTile t = (TransitionTile) tile;
					t.interact(this.getFrame());
				}
	
			}else {
				unlock();
			}
		
		}
				
	}
	
	public void lock(){this.locked = true;}
	
	public void unlock(){this.locked = false;}
	
	public boolean isLocked(){return this.locked;}
	
	public class spriteThread implements Runnable{
		
		private ParentMap map;
		private PlayableCharacter sprite;
		private double x;
		private double y;
		private ArrayList<Point> points;
		
		public spriteThread(ParentMap map, PlayableCharacter sprite, double x, double y){
			this.map = map;
			this.sprite = sprite;
			this.x = x;
			this.y = y;
			this.points = null;
		}
		
		public spriteThread(ParentMap map, PlayableCharacter sprite, ArrayList<Point> points){
			this.map = map;
			this.sprite = sprite;
			this.points = points;
		}

		@Override
		public void run() {
			
			if (this.points != null){
				for (Point p : this.points){
					if (p.x == this.map.getRelativeX() && p.y == this.map.getRelativeY()){
						continue;
					}
					int px = p.x * MapScreen.ICON_SIZE - getCharacterPositionX() * MapScreen.ICON_SIZE;
					int py = p.y * MapScreen.ICON_SIZE - getCharacterPositionY() * MapScreen.ICON_SIZE;
					findDirection(p);
					
					showSprite();
					
					takeStep(px, py);
					Tile tile = getTileByPosition(px + (getCharacterPositionX() * MapScreen.ICON_SIZE), 
							py + (getCharacterPositionY() * MapScreen.ICON_SIZE));
					
					if (tile instanceof TransitionTile){
						TransitionTile t = (TransitionTile) tile;
						t.interact(getFrame());
						break;
					}else if (parent.isEncounter(map)){
						parent.encounter(map);
						break;
					}
				}
			}else {
				takeStep(this.x, this.y);
				
				if (parent.isEncounter(map)){
					parent.encounter(map);
				}
			}

			map.unlock();
			
		}
		
		private void takeStep(double x2, double y2) {
			
			double diffX = 0;
			double diffY = 0;
			
			double diff = (double)MapScreen.ICON_SIZE / (double)60;

			System.out.println("current: " + this.map.currentPositionx);
			System.out.println("destination: " + x2);
			
			if (x2 > this.map.currentPositionx){
				diffX = diff;
			}else if (x2 < this.map.currentPositionx){
				diffX = -diff;
			}else if (y2 > this.map.currentPositiony){
				diffY = diff;
			}else {
				diffY = -diff;
			}
			
			xDiff = 0;
			yDiff = 0;
			
			this.sprite.run();
			
			int a = -1;
			while (++a < 60){
				xDiff += diffX;
				yDiff += diffY;
				pause();
			}

			this.map.animatedSprite.restartFrame();
			this.map.moveToPosition(this.map.currentPositionx + 60 * diffX, this.map.currentPositiony + 60 * diffY);
			
		}
		
		public synchronized void pause(){
			try {
				Thread.sleep(this.sprite.getTimeBetweenFrames() / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void findDirection(Point dest){

		if (this.getRelativeX() < dest.x){
			this.direction = RIGHT;
		}else if (this.getRelativeX() > dest.x){
			this.direction = LEFT;
		}else if (this.getRelativeY() < dest.y){
			this.direction = DOWN;
		}else {
			this.direction = UP;
		}
	}
	
	public void showSprite(){
		
		if (this.direction == LEFT){
			this.animatedSprite.setDirection(Character.LEFT);
		}else if (this.direction == RIGHT){
			this.animatedSprite.setDirection(Character.RIGHT);
		}else if (this.direction == UP){
			this.animatedSprite.setDirection(Character.FORWARD);
		}else {
			this.animatedSprite.setDirection(Character.BACKWARD);
		}
		
	}
		
	public synchronized Image getCache(){return this.cache;}
	
	public synchronized PlayableCharacter getAnimatedSprite(){return this.animatedSprite;}
		
	public ArrayList<EnemyCharacter> getEnemies() {return this.enemies;}
	
	public GameScreen getFrame(){return this.parent;}
	
	public boolean randomEncounter(){
		return Math.random() * 100 > this.encounterRate;
	}
	
	public ArrayList<EnemyCharacter> getEnemyFormation(){
		
		ArrayList<EnemyCharacter> list = new ArrayList<EnemyCharacter>();
		int encRate = this.encounterRate + 50;
		
		
		do{
			double encounter = Math.random() * 100;
			EnemyCharacter ex = null;
			for (EnemyCharacter e : this.enemies){
				if (encounter >= e.getEncounterRate()){
					ex = e.create();
				}
			}
			if (ex == null){
				ex = this.enemies.get(0).create();
			}else if (ex instanceof BossCharacter){
				list.clear();
				list.add(ex);
				break;
			}
			list.add(ex);
			encRate /= 2;
			if (list.size() == 4){break;}
		}while (encRate >= 50);
		
		return list;
		
	}

	public void setTiles(Tile[][] generatedMap) {
		this.tileMap = new TileMap(generatedMap);
	}
	
	public void setAnimatedSprite(PlayableCharacter animatedSprite2) {
		this.animatedSprite = animatedSprite2;
	}
	
	public String getDefaultTile() {return this.defaultTile;}
	
	public TileBasedMap getTiles() {return tileMap;}
	
	public float getXDiff(){return this.xDiff;}
	
	public float getYDiff(){return this.yDiff;}
	
	public double getCurrentPositionX() {return this.currentPositionx;}

	public double getCurrentPositionY() {return this.currentPositiony;}
	
	public String getThemeMusic(){
		return this.themeMusic;
	}
	
}
