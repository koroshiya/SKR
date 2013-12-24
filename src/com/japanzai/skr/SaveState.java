package com.japanzai.skr;

import item.Item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import screen.GameScreen;
import slickgamestate.MapScreen;
import slickgamestate.SlickSKR;
import character.PlayableCharacter;

public class SaveState {

	public final static String SAVE_DIRECTORY = "/home/mitch/";

	/**
	 * Saves the current state to a file on disk.
	 * This method should ALWAYS correspond to load(File).
	 * As one becomes more complex, or changes in any way, so should the other.
	 * 
	 * @param f File within which to store the current game state.
	 * 
	 * @return True if save was successful, otherwise false.
	 * */
	private static boolean save(File f) {

		try{
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//System.out.println("writing: " + Party.getCharacterByIndex(0).getSpriteDirectory() + "avatar.png");
			oos.writeObject(Party.getCharacterByIndex(0).getSpriteDirectory() + "avatar.png");
			oos.writeObject(Inventory.getMoney());
			oos.writeObject(Inventory.getItems());
			oos.writeObject(Party.getCharacters());
			oos.writeObject(MapManager.getMap());
			//oos.writeObject(d.bs);
			oos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * Saves the current state to disk at the location specified.
	 * See save(File) for more details.
	 * 
	 * @param s String representing the file path at which to save this state.
	 * eg. "/home/user/game/gamestate.sks" on Unix, or "C:\\gamestate.sks" on Windows.
	 * 
	 * @return True if save was successful, otherwise false.
	 * */
	public static boolean save(String s){return save(new File(SAVE_DIRECTORY + s));}
	
	@SuppressWarnings("unchecked")
	/**
	 * Loads the saved state from a file on disk.
	 * This method should ALWAYS correspond to save(File).
	 * As one becomes more complex, or changes in any way, so should the other.
	 * */
	private static boolean load(File f, GameScreen gs){
		
		try{
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ois.readObject();
			Inventory.setMoney((Integer)ois.readObject());
			Inventory.setItems((ArrayList<Item>)ois.readObject());
			Party.setParty((ArrayList<PlayableCharacter>)ois.readObject());
			((MapScreen)gs.getState(SlickSKR.MAP)).load(ois.readObject());
			ois.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * Loads the saved state from a file on disk.
	 * For more info, see load(File).
	 * 
	 * @param s Filepath from which to load the save state.
	 * eg. "/home/user/game/gamestate.sks" on Unix, or "C:\\gamestate.sks" on Windows.
	 * 
	 * @return True if save was successful, otherwise false.
	 * */
	public static boolean load(String s, GameScreen gs){return load(new File(s), gs);}
	
	/**
	 * Retrieves the avatars from each available save state as strings.
	 * This is so the separate avatars can be displayed on the load/save screen, or wherever else desired.
	 * 
	 * @return Array of strings representing paths to character avatars.
	 * */
	public static String[] viewAvatars(){
		File f = new File(SAVE_DIRECTORY);
		File[] fList = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               return (name.lastIndexOf('.')>0) && name.substring(name.lastIndexOf('.')).equals(".sks");
            }
         });
		if (fList != null){
			String[] list = new String[fList.length];
			for (int i = 0; i < fList.length; i++){
				try{
					FileInputStream fis = new FileInputStream(fList[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					list [i] = (String) ois.readObject();
					ois.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return list;
		}else{
			return new String[0];
		}

	}

}
