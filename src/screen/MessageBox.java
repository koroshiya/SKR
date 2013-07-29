package screen;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

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
		
	public static void InfoBox(String message, String title) {
		
		JOptionPane.showMessageDialog(null, message, title, 1);
		
	}
	
}
