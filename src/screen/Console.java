package screen;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class Console extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private static JTextArea area;
	
	private static boolean instantiated;
	
	public Console(){

		area = new JTextArea();
		area.setEditable(false);
		area.setBackground(Color.BLACK);
		area.setForeground(Color.WHITE);
		
		((DefaultCaret)(area.getCaret())).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); //Automatically scroll to the bottom on change

		this.setViewportView(area);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		instantiated = true;
		
	}
	
	public static boolean isInstantiated(){return instantiated;}

	public static void cleanConsole(){
		area.setText("");
	}
	
	public static void writeConsole(String message){
		
		area.append(message + "\n");
		
	}

}
