package map;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import screen.GameScreen;
import slickgamestate.MapScreen;
import slickgamestate.SlickSKR;
import tile.Tile;
import tile.TransitionTile;
import character.Character;
import character.BossCharacter;
import character.EnemyCharacter;
import character.PlayableCharacter;

public class ParentMap {
	
	private Point coordinates;
	private float currentPositionx;
	private float currentPositiony;
	private TileMap tileMap;
	private int direction;
	private ArrayList<EnemyCharacter> enemies;
	
	private static PlayableCharacter animatedSprite;
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
	private final String themeMusic;
	
	private float xDiff;
	private float yDiff;
	
	public ParentMap(Point coordinates, Point currentPosition, 
			PlayableCharacter animatedSprite2, ArrayList<EnemyCharacter> enemies,
			GameScreen parent, Point mapSize, int encounterRate, String grass,
			String theme){

		this.parent = parent;
		this.coordinates = coordinates;
		this.currentPositionx = currentPosition.x;
		this.currentPositiony = currentPosition.y;
		this.tileMap = null;
		this.direction = DOWN;
		animatedSprite = animatedSprite2;
		this.enemies = enemies;
		this.encounterRate = encounterRate;
		this.defaultTile = grass;
		this.themeMusic = theme;
	
	}
	
	public void instantiateImages() throws SlickException{
		animatedSprite.instantiate();
		this.tileMap.instantiate();
		this.showSprite();
	}
	
	public void moveToPosition(Point p){moveToPosition(p.x, p.y);}
	
	public synchronized void moveToPosition(float x, float y){

		this.currentPositionx = x;
		this.currentPositiony = y;
		((MapScreen)parent.getState()).resetPosition((float)xDiff, (float)yDiff);
		xDiff = 0;
		yDiff = 0;
		MapScreen.step = new Point((int)(x/SlickSKR.scaled_icon_size), (int)(y/SlickSKR.scaled_icon_size));
		
	}
	
	public boolean canMoveToPosition(float d, float currentPositiony2){
		
		float x = d / SlickSKR.scaled_icon_size + this.getCharacterPositionX();
		float y = currentPositiony2 / SlickSKR.scaled_icon_size + this.getCharacterPositionY();
		
		if (!tileExists(x, y)){return false;}
		
		if (x <= getXBoundary() && y <= getYBoundary()){
			if (x != this.currentPositionx || y != this.currentPositiony){
				return tileMap.isOpenAndReachable(x, y);
			}
		}
		
		return false;
		
	}
	
	public void tryMoveToTile(int x, int y) {
		
		x += this.currentPositionx / SlickSKR.scaled_icon_size;
		y += this.currentPositiony / SlickSKR.scaled_icon_size;
		
		if (x < 0 || y < 0 || x >= tileMap.getWidthInTiles() || y >= tileMap.getHeightInTiles()){return;}
		
		int a = (int) (Math.floor(this.currentPositionx / SlickSKR.scaled_icon_size) + this.getCharacterPositionX());
		int b = (int) (Math.floor(this.currentPositiony / SlickSKR.scaled_icon_size) + this.getCharacterPositionY());

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
		
		int x = a / SlickSKR.scaled_icon_size + this.getCharacterPositionX();
		int y = b / SlickSKR.scaled_icon_size + this.getCharacterPositionY();
		
		return tileExists(x, y);
		
	}
	
	public boolean tileExists(float x, float y){
		return getTileByPosition(x, y) != null;
	}
	
	public Tile getTileByPosition(float x2, float y2){
		
		float x = x2 / SlickSKR.scaled_icon_size;
		float y = y2 / SlickSKR.scaled_icon_size;
		
		return getTileByIndex(x, y);
		
	}
	
	public Tile getTileByIndex(float x, float y){
		return this.tileMap.getTileByIndex(x, y);
	}
	
	public int getHeight(){return this.tileMap.getHeightInTiles() * SlickSKR.scaled_icon_size;}
	
	public int getWidth(){return this.tileMap.getWidthInTiles() * SlickSKR.scaled_icon_size;}
	
	public float getPositionX(){return this.currentPositionx;}
	
	public float getPositionY(){return this.currentPositiony;}
	
	public int getXBoundary(){return this.coordinates.x;}
	
	public int getYBoundary(){return this.coordinates.y;}
	
	public synchronized int getCharacterPositionX(){
		return (int) Math.floor(this.coordinates.getX() / 4 / SlickSKR.scaled_icon_size);
	}
	
	public synchronized int getCharacterPositionY(){
		return (int) Math.floor(this.coordinates.getY() / 4 / SlickSKR.scaled_icon_size);
	}
	
	public synchronized float getRelativeX(){
		return (this.currentPositionx / SlickSKR.scaled_icon_size) + this.getCharacterPositionX();
	}
	
	public synchronized float getRelativeY(){
		return (this.currentPositiony / SlickSKR.scaled_icon_size) + this.getCharacterPositionY();
	}
	
	public void setDirection(int dir){
		
		if (dir == LEFT || dir == RIGHT || dir == UP || dir == DOWN){
			this.direction = dir;
			showSprite();
		}
		
	}
	
	public int getDirection(){return this.direction;}
	
	public void moveDown() {
		
		move(this.currentPositionx, this.currentPositiony + SlickSKR.scaled_icon_size, DOWN);
		
	}
	
	public void moveUp() {
		
		move(this.currentPositionx, this.currentPositiony - SlickSKR.scaled_icon_size, UP);
		
	}
	
	public void moveLeft() {
		
		move(this.currentPositionx - SlickSKR.scaled_icon_size, this.currentPositiony, LEFT);
		
	}
	 
	public void moveRight() {
		
		move(this.currentPositionx + SlickSKR.scaled_icon_size, this.currentPositiony, RIGHT);
		
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
			
			Thread thread = new Thread(new spriteThread(this, animatedSprite, pts));
			thread.start();
				
		}
	}
	
	private synchronized void move(float d, float currentPositiony2, int dir) {

		if (!isLocked()){
		
			lock();
			this.direction = dir;
			showSprite();
			
			if (canMoveToPosition(d, currentPositiony2)){
				
				Thread thread = new Thread(new spriteThread(this, animatedSprite, d, currentPositiony2));
				thread.start();

				Tile tile = getTileByPosition(d + (this.getCharacterPositionX() * SlickSKR.scaled_icon_size), 
												currentPositiony2 + (this.getCharacterPositionY() * SlickSKR.scaled_icon_size));
				if (tile instanceof TransitionTile){
					MapScreen.step = new Point((int)(d + (this.getCharacterPositionX() * SlickSKR.scaled_icon_size)), 
												(int)(currentPositiony2 + (this.getCharacterPositionY() * SlickSKR.scaled_icon_size)));
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
		private float x;
		private float y;
		private ArrayList<Point> points;
		
		public spriteThread(ParentMap map, PlayableCharacter sprite, float x, float y){
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
					int px = p.x * SlickSKR.scaled_icon_size - getCharacterPositionX() * SlickSKR.scaled_icon_size;
					int py = p.y * SlickSKR.scaled_icon_size - getCharacterPositionY() * SlickSKR.scaled_icon_size;
					findDirection(p);
					
					showSprite();
					
					takeStep(px, py);
					Tile tile = getTileByPosition(px + (getCharacterPositionX() * SlickSKR.scaled_icon_size), 
							py + (getCharacterPositionY() * SlickSKR.scaled_icon_size));
					
					if (tile instanceof TransitionTile){
						MapScreen.step = new Point((int)(px + (getCharacterPositionX() * SlickSKR.scaled_icon_size)), 
								(int)(py + (getCharacterPositionY() * SlickSKR.scaled_icon_size)));
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
		
		private void takeStep(float x2, float y2) {
			
			float diffX = 0;
			float diffY = 0;
			
			float diff = (float)SlickSKR.scaled_icon_size / (float)SlickSKR.refreshRate;
			
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
			while (++a < SlickSKR.refreshRate){
				xDiff += diffX;
				yDiff += diffY;
				pause();
			}

			animatedSprite.restartFrame();
			this.map.moveToPosition(this.map.currentPositionx + SlickSKR.refreshRate * diffX, this.map.currentPositiony + SlickSKR.refreshRate * diffY);
			
		}
		
		public synchronized void pause(){
			try {
				Thread.sleep(this.sprite.getTimeBetweenFrames() / SlickSKR.refreshRate);
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
			animatedSprite.setDirection(Character.LEFT);
		}else if (this.direction == RIGHT){
			animatedSprite.setDirection(Character.RIGHT);
		}else if (this.direction == UP){
			animatedSprite.setDirection(Character.FORWARD);
		}else {
			animatedSprite.setDirection(Character.BACKWARD);
		}
		
	}
	
	public synchronized PlayableCharacter getAnimatedSprite(){return animatedSprite;}
		
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
		animatedSprite = animatedSprite2;
	}
	
	public String getDefaultTile() {return this.defaultTile;}
	
	public TileBasedMap getTiles() {return tileMap;}
	
	public float getXDiff(){return this.xDiff;}
	
	public float getYDiff(){return this.yDiff;}
	
	public float getCurrentPositionX() {return this.currentPositionx;}

	public float getCurrentPositionY() {return this.currentPositiony;}
	
	public String getThemeMusic(){return this.themeMusic;}
	
}
