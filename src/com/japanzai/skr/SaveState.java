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

import org.newdawn.slick.GameContainer;

import character.PlayableCharacter;

public class SaveState {

	public final static String SAVE_DIRECTORY = "/home/mitch/";

	public static boolean save(File f) {

		try{
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//System.out.println("writing: " + Party.getCharacterByIndex(0).getSpriteDirectory() + "avatar.png");
			oos.writeObject(Party.getCharacterByIndex(0).getSpriteDirectory() + "avatar.png");
			oos.writeObject(Inventory.getMoney());
			oos.writeObject(Inventory.getItems());
			oos.writeObject(Party.getCharacters());
			//oos.writeObject(d.bs);
			oos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean save(String s){
		return save(new File(SAVE_DIRECTORY + s));
	}
	
	@SuppressWarnings("unchecked")
	public static boolean load(File f){
		
		try{
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ois.readObject();
			Inventory.setMoney((Integer)ois.readObject());
			Inventory.setItems((ArrayList<Item>)ois.readObject());
			Party.setParty((ArrayList<PlayableCharacter>)ois.readObject());
			ois.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean load(String s){
		return load(new File(s));
	}
	
	public static String[] viewAvatars(GameContainer g){
		File f = new File(SAVE_DIRECTORY);
		File[] fList = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               if (name.lastIndexOf('.')>0){
                  return name.substring(name.lastIndexOf('.')).equals(".sks");
               }
               return false;
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
