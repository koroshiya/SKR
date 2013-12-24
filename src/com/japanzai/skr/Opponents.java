package com.japanzai.skr;

import java.util.ArrayList;

import org.newdawn.slick.util.Log;

import character.EnemyCharacter;

/**
 * Represents a set of possible enemies that may be encountered
 * and provides methods for retrieving/creating such enemies.
 * eg. Enemies can be retrieved by name, index, created for battle, etc.
 * */
public class Opponents {

	private static ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
	
	/**
	 * @param e Enemy to add to the list of available opponents.
	 * */
	public static void addEnemy(EnemyCharacter e){
		
		for (EnemyCharacter ex : enemies){
			if (e.getName().equals(ex.getName())){
				return;
			}
		}
		
		enemies.add(e);
		
	}
	
	/**
	 * @param index Index of the enemy to retrieve.
	 * */
	public static EnemyCharacter getEnemy(int index){
		
		if (index >= 0 && index < enemies.size()){
			return enemies.get(index);
		}
		
		Log.error("Enemy at index " + index + " doesn't exist.");
		return null;
		
	}

	/**
	 * @param name Name of the enemy to search for.
	 * 
	 * @return Enemy whose name was specified.
	 * */
	public static EnemyCharacter getEnemy(String name){
		
		for (EnemyCharacter ex : enemies){
			if (ex.getNickName().equals(name)){
				return ex;
			}
		}

		Log.error("Enemy of name " + name + " doesn't exist.");
		return null;
		
	}

	/**
	 * @return -1 if not found
	 * */
	public static int getEnemyIndex(String name){
		
		int i = -1;
		int total = enemies.size();
		while (++i < total){
			if (enemies.get(i).getNickName().equals(name)){
				return i;
			}
		}

		Log.error("Enemy of name " + name + " doesn't exist.");
		return -1;
		
	}
	
	/**
	 * Creates a new instance of a specified enemy.
	 * 
	 * @param index Index of the enemy to create.
	 * @param level Level at which to create the enemy.
	 * */
	public static EnemyCharacter getEnemyInstance(int index, int level){
		
		EnemyCharacter ex = getEnemy(index);
		
		if (ex != null){
			ex.create();
			
			if (ex.getLevel() < level){
				ex.setLevel(level);	
			}
		}
		
		return ex;
		
	}
	
	public static EnemyCharacter getEnemyInstance(String name, int level){
		return getEnemyInstance(getEnemyIndex(name), level);
	}
	
	public static ArrayList<EnemyCharacter> getEnemies(){return enemies;}
	
}
