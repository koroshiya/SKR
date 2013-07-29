package screen;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageBox{
	
	public static void InfoBox(Object message, String title, JFrame frame){
		
		//frame.setAlwaysOnTop(false);
		JOptionPane.showMessageDialog(frame, message, title, 1);
		//frame.setAlwaysOnTop(OnTop);
		/**TODO: Add Icon*/
	}
	
	public static int ChoiceBox(Object message, String title, JFrame frame){
		
		//frame.setAlwaysOnTop(false);
		return JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION);
		//frame.setAlwaysOnTop(OnTop);
		/**TODO: Add Icon*/
	}
	
	public static Object InputBox(Object message, String title, JFrame frame){
		//frame.setAlwaysOnTop(false);
		Object obj = JOptionPane.showInputDialog(frame, message, title, 1);
		//frame.setAlwaysOnTop(OnTop);
		return obj;
		/**TODO: Add Icon*/
	}
	
	public static void ErrorBox(Object message, String title, JFrame frame){
		//frame.setAlwaysOnTop(false);
		System.err.println(message);
		JOptionPane.showMessageDialog(frame, message, title, 2);
		//frame.setAlwaysOnTop(OnTop);
	}
	
	public static void Changelog(JFrame frame){
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(400, 300));

		textArea.setText(getChanges().toString());
		JOptionPane.showMessageDialog(null, scrollPane, "Changelog", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	private static StringBuffer getChanges(){

		StringBuffer changes = new StringBuffer();
		
		//TODO: Finish InventoryPane stuff
		//TODO: Try out http://www.lwjgl.org/
		//TODO: Add wait between attacks
		//TODO: Add animation to attacks
		//Memo: isKeyDown can be used to check for ctrl in hotkeys

		changes.append("v0.15");
		changes.append("\n");
		changes.append("Implemented Slick2D graphics engine. Completely rewriting UI");
		changes.append("\n");
		changes.append("Implemented Slick2D A* algorithm. No further alterations required.");
		changes.append("\n");
		changes.append("");
		changes.append("\n");
		changes.append("\n");
		
		changes.append("v0.14");
		changes.append("\n");
		changes.append("Implemented basic save/load mechanism (experience, items, money)");
		changes.append("\n");
		changes.append("Implemented JxFileDialog - Custom file chooser (currently load only)");
		changes.append("\n");
		changes.append("Improvements made to A* algorithm. Still not perfect");
		changes.append("\n");
		changes.append("\n");
		
		changes.append("v0.13");
		changes.append("\n");
		changes.append("Improvements to A* algorithm. Still not perfect");
		changes.append("\n");
		changes.append("Added Game Over screen and option to restart upon Game Over");
		changes.append("\n");
		changes.append("Added first instance of a boss character");
		changes.append("\n");
		changes.append("Character displayed on map is now first active character. eg. If Ken is removed from the party, Yumin will be displayed");
		changes.append("\n");
		changes.append("Losing with at least one living character outside party now means survival");
		changes.append("\n");
		changes.append("Can no longer remove last living character from party");
		changes.append("\n");
		changes.append("Drastically improved graphic performance for maps");
		changes.append("\n");
		changes.append("Improved character movement graphic on map");
		changes.append("\n");
		changes.append("Fixed MouseListener bug from ParentMap transition");
		changes.append("\n");
		changes.append("Point & click movement now invokes random encounters too");
		changes.append("\n");
		changes.append("Added global enemy class and enemy encounter debugging options");
		changes.append("\n");
		changes.append("Implemented technique learning system; techniques are learnt by class and level");
		changes.append("\n");
		changes.append("Further improvements to menu + general improvements");
		changes.append("\n");
		changes.append("\n");
		
		changes.append("v0.12");
		changes.append("\n");
		changes.append("Implemented A* algorithm. Not perfect.");
		changes.append("\n");
		changes.append("Added new enemies and character sprites");
		changes.append("\n");
		changes.append("Fixed battle encounter system");
		changes.append("\n");
		changes.append("Added default map background; grass and such can now be behind objects");
		changes.append("\n");
		changes.append("\n");
		
		changes.append("v0.11");
		changes.append("\n");
		changes.append("In the menu, you now need to double click on a character to remove/add them to/from the party");
		changes.append("\n");
		changes.append("Implemented color scheme for party state");
		changes.append("\n");
		changes.append("Overhauled Tile and Map generation algorithms and implemented several new classes to that end");
		changes.append("\n");
		changes.append("Overhauled Character avatar and profile pic implementation");
		changes.append("\n");
		changes.append("Added character profile pages and made the Character menu item functional");
		changes.append("\n");
		changes.append("\n");
		
		changes.append("v0.10");
		changes.append("\n");
		changes.append("NPCs face speaker");
		changes.append("\n");
		changes.append("Mouseclick can be used to move");
		changes.append("\n");
		changes.append("Changelog implemented");
		changes.append("\n");
		changes.append("Encounter rate now map-dependent");
		changes.append("\n");
		changes.append("Encounters are randomly generated instead of set");
		changes.append("\n");
		changes.append("Borders and transitions implemented");
		changes.append("\n");
		changes.append("\n");
		
		changes.append("v0.09");
		changes.append("\n");
		changes.append("Map dialogue window now docked");
		changes.append("\n");
		changes.append("New NPC added - To be removed later or used for tutorials");
		changes.append("\n");
		changes.append("Dialogue options implemented (Yes, No, Back)");
		changes.append("\n");		
		
		return changes;
		
		/*
		 * Add to main menu entry
		 * Swap to secondary menu when clicking on character

		 * Implement adjacent maps and boundaries expressed by fences and gates; entry/exit points coded in
		 * //if tile[i][j] = x, then mapLeft
		 * Code in arraylist of enemies to randomly generate, as well as items to give, pairings, size of party, etc.
		 * //Encounter rate dependent on tile type?
		 * Code in one-time conversations, items, etc.; two Dialogue classes per certain NPC?

		 * -Battle gif/animation?
		 * -Load gif/animation?
		 * -Level up learn technique? //public void levelUp(Character c){c.getTechniques().add} //+message on screen
		 * -Background image for map dialogue
		 * -Image for outside of map, or use base tile repeated? eg. grass for map, stone for town, etc.
		 * -Shop interface? Implement money?

		 * **Make single instance

		 * *Investigate save data and save state
		 * */
		
	}

	
	public static void InfoBox(String message, String title) {
		
		JOptionPane.showMessageDialog(null, message, title, 1);
		
	}
	
}
