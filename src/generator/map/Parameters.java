package generator.map;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.newdawn.slick.util.Log;


public class Parameters extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private PairPanel name;
	private PairPanel enc;

	public Parameters(int length){
		

		Dimension size = new Dimension(300, 600);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setSize(size);
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		name = new PairPanel(new JLabel("Map name (alphanumeric): "), new JFileNameField(8));
		enc = new PairPanel(new JLabel("Encounter rate (0-100)"), new JNumberField(length));
		//this.add(name);
		//this.add(enc);
		
	}
	
	public boolean parse(){
		
		String fileName = ((JFileNameField)name.getFieldRight()).getText();
		

		
		
		return true;
		
	}
	
	public int getEncounterRate(){
		
		int encRate;
		
		try{
			
			String strEnc = ((JNumberField)name.getFieldRight()).getText();
			
			if (strEnc.length() > 3){
				strEnc.substring(0, 3);
			}
			
			encRate = Integer.parseInt(strEnc);
			
		}catch (Exception ex){
			Log.error("Error encountered - Setting encounter rate to 97");
			encRate = 97;
		}
		
		return encRate;
		
	}
	
}
