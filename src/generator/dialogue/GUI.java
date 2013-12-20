package generator.dialogue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.newdawn.slick.util.Log;

public class GUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public static final String[] commands = {"Add line", "Add question", "End dialogue", "Remove line", "Remove all", "Add newline"};
	private JButton[] buttons;
	private JTextArea textInput;
	private JTextField textEntry;
	private boolean question = false;
	private final JPanel panel = new JPanel();

	private ArrayList<String> dialogue = new ArrayList<String>();
	private final File outputFile;

	public GUI(String path){
		
		outputFile = new File(path + ".txt");
		if (outputFile.exists()){
			if (!outputFile.canWrite()){
				Log.error("Can't write to specified file");
				return;
			}
		}else{
			if (!outputFile.getParentFile().canWrite()){
				Log.error("Can't write to specified file");
				return;
			}
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instantiateGUI();
		
		this.setVisible(true);
	}
	
	public void instantiateGUI(){
		
		panel.setLayout(new BorderLayout());
		
		textInput = new JTextArea();
		textInput.setEditable(false);
		JPanel panelTop = new JPanel();
		this.setLayout(new FlowLayout());
		this.setMinimumSize(new Dimension(550, 400));
		this.setResizable(false);
		panel.setMinimumSize(new Dimension(350, 300));
		panel.setPreferredSize(new Dimension(350, 300));
		panel.add(new JScrollPane(textInput), BorderLayout.CENTER);
		this.add(panel);
		
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new GridLayout(commands.length - 1, 0));
		rightPane.setMaximumSize(new Dimension(100, 400));
		
		ButtonListener listener = new ButtonListener(this);
		
		buttons = new JButton[commands.length];
		for (int i = 1; i < buttons.length; i++){
			buttons[i] = setJButton(commands[i], listener);
			rightPane.add(buttons[i]);
		}
		textEntry = new JTextField(36);
		
		this.add(rightPane);
		this.add(panelTop);
		this.add(textEntry);
		buttons[0] = setJButton(commands[0], listener);
		this.add(buttons[0]);
		
		this.setVisible(true);
		
	}
	
	private JButton setJButton(String text, ButtonListener listener){
		JButton btn = new JButton(text);
		btn.addActionListener(listener);
		btn.setPreferredSize(new Dimension(160, 25));
		return btn;
	}

	
	public void add(){
		
		String newText = textEntry.getText();
		if (!newText.equals("")){	
			String curText = textInput.getText();
			this.dialogue.add(newText);
			
			if (!curText.equals("")){
				curText += "\n";
			}
			curText += textEntry.getText();
			
			textInput.setText(curText);
			textEntry.setText("");
		}
	}

	public void addQuestion() {
		
		add();
		this.question = true;
		end();
		
	}
	
	public void addNewline(){
		textEntry.setText(textEntry.getText() + "\\n");
	}
	
	public void end(){
		if (write()){
			System.exit(0);
		}
	}
	
	public boolean write(){
		ArrayList<String> writeOut = new ArrayList<String>();
		int max = this.dialogue.size();
		if (question){
			writeOut.add("ComplexDialogue cd = new ComplexDialogue();");
			max--;
		}else{
			writeOut.add("Dialogue cd = new Dialogue();");
		}
		for (int i = 0; i < max; i++){
			writeOut.add("cd.addLine(new Line(npc, \"" + this.dialogue.get(i) + "\", " + i + ", false));");
		}
		if (question){
			writeOut.add("cd.addLine(new Line(npc, \"" + this.dialogue.get(max) + "\", " + max + ", true));");
		}
		return export(writeOut);
	}
	
	private boolean export(ArrayList<String> outputText){
		
		try {
			boolean exists = outputFile.exists();
			if (!exists){
				outputFile.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, exists));
			for (String line : outputText){
				writer.write(line + "\n");
			}
			writer.close();
			Log.error("Map successfully written to: " + outputFile.getAbsolutePath());
			return true;
		} catch (IOException e) {
			Log.error("Couldn't write to file - Export aborted");
			return false;
		}
		
	}
	
	public void clearLast() {
		this.dialogue.remove((this.dialogue.size() - 1));
		String[] lines = textInput.getText().split("\\n");
		textInput.setText("");
		StringBuffer remaining = new StringBuffer();
		
		if (lines.length > 1){
			for (int i = 0; i < lines.length - 1; i++){
				remaining.append(lines[i]);
				if (i < lines.length - 2){
					remaining.append("\n");
				}
			}
		}
		
		textInput.setText(remaining.toString());
	}

	public void clearAll() {
		this.dialogue.clear();
		this.textInput.setText("");
	}
	
}
