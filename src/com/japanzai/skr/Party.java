package com.japanzai.skr;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import character.PlayableCharacter;
import character.PlayableCharacterSave;

public class Party {
	
	private static ArrayList<PlayableCharacter> characters = new ArrayList<PlayableCharacter>();
	
	public static void addCharacter(PlayableCharacter character){
		
		characters.add(character);
		
	}
	
	public static void addCharacters(ArrayList<PlayableCharacter> characterList){
		
		for (PlayableCharacter character : characterList){
			addCharacter(character);
		}
		
	}
	
	public static PlayableCharacter getCharacterByIndex(int index){
		
		if (index < 0 || index >= characters.size()){
			return null;
		}
		
		return characters.get(index);
			
	}
	
	public static PlayableCharacter getCharacterByName(String name){//Char name = fName + " " + lName
		
		for (PlayableCharacter character : characters){
			if (character.getName().equals(name)){
				return character;
			}
		}
		
		return null; //assertion?
			
	}
	
	public static int getCharacterIndexByName(String name){//Char name = fName + " " + lName
		
		int i = -1;
		int total = characters.size();
		while (++i < total){
			if (characters.get(i).equals(name)){
				return i;
			}
		}
		
		return -1; //assertion?
			
	}
	
	/**
	 * @param boolMale True if searching for males, otherwise false
	 * 
	 * @return Returns an ArrayList of PlayableCharacter objects of the specified gender
	 * */
	public static ArrayList<PlayableCharacter> getCharacterByGender(boolean boolMale){
		
		ArrayList<PlayableCharacter> characterList = new ArrayList<PlayableCharacter>();
		
		for (PlayableCharacter character : characters){
			if (character.isMale() == boolMale){
				characterList.add(character);
			}
		}
		
		return characterList;
		
	}
	
	/**
	 * @param boolMale True if searching for living characters, otherwise false
	 * 
	 * @return Returns an ArrayList of PlayableCharacter objects, dependent on whether alive or dead
	 * */
	public static ArrayList<PlayableCharacter> getCharactersAlive(boolean alive){
		
		ArrayList<PlayableCharacter> characterList = new ArrayList<PlayableCharacter>();
		
		for (PlayableCharacter character : characters){
			if (character.isAlive() == alive){
				characterList.add(character);
			}
		}
		
		return characterList;
	
	}
	
	public static ArrayList<PlayableCharacter> getCharactersAliveInParty(boolean alive){
		
		ArrayList<PlayableCharacter> chars = getCharactersAlive(alive);
		ArrayList<PlayableCharacter> living = new ArrayList<PlayableCharacter>();
		
		for (PlayableCharacter c : chars){
			if (c.isInParty()){
				living.add(c);
			}
		}
		
		return living;
		
	}
	
	public static ArrayList<PlayableCharacter> getCharactersInParty(){
		
		ArrayList<PlayableCharacter> characterList = new ArrayList<PlayableCharacter>();
		
		for (PlayableCharacter character : characters){
			if (character.isInParty()){
				characterList.add(character);
			}
		}
		
		//Assertion if characterList.length > 4;
		return characterList;
		
	}
	
	public static ArrayList<PlayableCharacter> getCharacters(){
		return characters;
	}
	
	public static void toggleCharacterInParty(PlayableCharacter c){
		
		boolean inParty = c.isInParty();
		if (!inParty && getCharactersInParty().size() >= 4){
			//party full
			return;
		}else if (inParty && getCharactersInParty().size() < 1){
			//only person in party
			return;
		}
		c.setInParty(!c.isInParty()); //If in party, remove. If not, add.
		
	}
	
	public static void formValidParty(){
		if (getCharactersInParty().size() == 4){
			getCharactersInParty().get(0).setInParty(false);
		}
		for (PlayableCharacter c : getCharacters()){
			if (c.isAlive()){
				c.setInParty(true);
				break;
			}
		}
	}
	
	public static boolean isValidParty(){
		
		int size = getCharactersInParty().size();
		
		if (size > 0 && size < 5){
			for (PlayableCharacter c : getCharactersInParty()){
				if (c.isAlive()){return true;}
			}
		}
		return false;
	}
	
	public static void setParty(ArrayList<PlayableCharacter> newCharacters){
		characters = newCharacters;
	}
	
	public static ArrayList<PlayableCharacterSave> getCharactersSaved(){
		ArrayList<PlayableCharacterSave> saved = new ArrayList<PlayableCharacterSave>();
		for (PlayableCharacter c : characters){
			saved.add(new PlayableCharacterSave(c));
		}
		return saved;
	}
	
	public static void setCharactersSaved(ArrayList<PlayableCharacterSave> saved){
		ArrayList<PlayableCharacter> chars = new ArrayList<PlayableCharacter>();
		for (PlayableCharacterSave s : saved){
			chars.add((PlayableCharacter)s.serialLoad());
		}
		characters = chars;
	}
	
	public static void initialize() throws SlickException {
		
		for (PlayableCharacter c : characters){
			c.instantiate();
		}
		
	}
	
}

