package com.japanzai.skr;

import java.util.ArrayList;

import character.EnemyCharacter;

public class Opponents {

	private static ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
	
	public static void addEnemy(EnemyCharacter e){
		
		for (EnemyCharacter ex : enemies){
			if (e.getName().equals(ex.getName())){
				return;
			}
		}
		
		enemies.add(e);
		
	}
	
	public static EnemyCharacter getEnemy(int index){
		
		if (index >= 0 && index < enemies.size()){
			return enemies.get(index);
		}
		
		return null;
		
	}

	public static EnemyCharacter getEnemy(String name){
		
		for (EnemyCharacter ex : enemies){
			if (ex.getNickName().equals(name)){
				return ex;
			}
		}
		
		return null;
		
	}
	
	public static EnemyCharacter getEnemyInstance(int index, int level){
		
		EnemyCharacter ex = getEnemy(index);
		
		if (ex != null){
			ex.create();
			
			if (ex.getLevel() < level){
				ex.setLevel(level, null);	
			}
		}
		
		return ex;
		
	}
	
	public static EnemyCharacter getEnemyInstance(String name, int level){
		
		EnemyCharacter ex = getEnemy(name);
		
		if (ex != null){
			ex.create();
			
			if (ex.getLevel() < level){
				ex.setLevel(level, null);	
			}
		}
		
		return ex;
		
	}
	
	public static ArrayList<EnemyCharacter> getEnemies(){
		return enemies;
	}
	
}
