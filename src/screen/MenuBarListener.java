package screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;

import character.EnemyCharacter;

import com.japanzai.skr.Driver;
import com.japanzai.skr.Opponents;


public class MenuBarListener implements ActionListener {

	private GameScreen parent;
	
	public MenuBarListener(GameScreen battleScreen) {
		
		this.parent = battleScreen;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String tag = arg0.getActionCommand();
		
		if (tag.equals("Save")){
			save();
		}else if (tag.equals("Load")){
			load();
		}else if (tag.equals("Quit")){
			//TODO: After implementing save, display warning
			System.exit(0);
		}else if (tag.equals("Hotkeys")){
			DisplayHotkeys();
		}else if (tag.equals("About")){
			DisplayAboutInfo();
		}else if (tag.equals("Fullscreen")){
			this.parent.setFullScreen();
		}else if (tag.equals("Menu")){
			if (this.parent.isInBattle()){
				GameScreen.WriteOnScreen("You cannot open the menu during battle", "Operation not allowed");
			}else{
				this.parent.swapView();
			}
		}else if (tag.equals("Changelog")){
			MessageBox.Changelog(this.parent);
		}else if (tag.equals("No encounters")){
			JCheckBoxMenuItem noEncounters = (JCheckBoxMenuItem) arg0.getSource();
			GameScreen.setNeverEncounter(noEncounters.isSelected());
		}else if (tag.equals("Encounter every step")){
			JCheckBoxMenuItem allEncounters = (JCheckBoxMenuItem) arg0.getSource();
			GameScreen.setAlwaysEncounter(allEncounters.isSelected());
		}else {
			enemyEncounter(tag);
		}
		
	}
	

	private void load(){
		try {
			Driver.load();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void save(){
		try {
			Driver.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void enemyEncounter(String tag){
		
		EnemyCharacter ex = Opponents.getEnemy(tag);
		if (ex != null){
			parent.encounter(ex);
		}		
		
	}
	
	private void DisplayHotkeys(){
		
		StringBuffer keyPairs = new StringBuffer();

		keyPairs.append("A - Interact" + "\n");
		keyPairs.append("W - Menu" + "\n");
		keyPairs.append("F - Fullscreen" + "\n");
		keyPairs.append("Esc - Quit");
		
		GameScreen.WriteOnScreen(keyPairs, "Hotkeys");
		
	}

	private void DisplayAboutInfo() {
		
		StringBuffer keyPairs = new StringBuffer();

		keyPairs.append("Programming: Koro" + "\n");
		keyPairs.append("Original characters & story: Boichi" + "\n");
		keyPairs.append("Sprites: Koro (oh god no)");
		
		GameScreen.WriteOnScreen(keyPairs, "About SKR Game (yet to be named)");
		
	}

}
