package screen;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



import com.japanzai.skr.ComplexDialogue;
import com.japanzai.skr.Dialogue;

public class MapConsole extends JPanel{
		
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JButton back;
	private JButton next;
	private JButton yes;
	private JButton no;
	
	private Dialogue dialogue;
	private Image cache;
	
	private GameScreen parent;
	
	private DialogueBox dialogueBox;
	private ConsoleMenuListener listener;
	
	public MapConsole(ArrayList<Dialogue> dialogues, Dialogue dialogue, GameScreen battleScreen){
			
		super();
		
		this.dialogue = dialogue;
		this.parent = battleScreen;
		//TODO: set background image for console?
		//TODO: set aside space at bottom for char info, map, etc.
		//BattleMenuListener listener = new BattleMenuListener();

		dialogueBox = new DialogueBox();
		panel = new JPanel();
		
		Dimension size = new Dimension(134, 110);
		this.panel.setMinimumSize(size);
		this.panel.setMaximumSize(size);
		this.panel.setPreferredSize(size);
		
		panel.setLayout(new GridLayout(4, 1));
		this.listener = new ConsoleMenuListener(this, parent);
		
		back = setJButton("Back", "Back [W]");
		back.setEnabled(false);
		next = setJButton("Next", "Next [A]");
		yes = setJButton("Yes", "Yes [S]");
		yes.setEnabled(false);
		no = setJButton("No", "No [D]");
		no.setEnabled(false);
		
		this.add(dialogueBox);
		panel.add(back);
		panel.add(next);
		panel.add(yes);
		panel.add(no);
		this.add(this.panel);
			
		this.setLayout(new FlowLayout());
		
		size = new Dimension(768, 120);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
			
	}
	
	private JButton setJButton(String tag, String text){

		JButton button = new JButton();
		button.setName(tag);
		button.setActionCommand(tag);
		button.setText(text != null ? text : tag);
		button.addActionListener(this.listener);
		
		return button;
		
	}
	
	public void start(){

		this.setVisible(true);
		
	}
		
	public void speak(Dialogue d) throws SlickException{
		
		System.out.println(d.getAvatar());
		cache = new Image(d.getAvatar());
		display(cache, d.speak());
		d.increment();
		
	}
	
	public void speak() throws SlickException{
		cache = new Image(this.dialogue.getAvatar());
		display(cache, this.dialogue.speak());
	}

	private void display(Image avatar, String speak) {
		
		dialogueBox.displayText(avatar, speak);
		
	}

	private int converse(Dialogue d) throws SlickException {
		
		boolean moreDialogue = d.moreDialogue();
		this.back.setEnabled(this.dialogue.canGoBack());
		
		if (!moreDialogue){
			d.reset();
			return -1;
		}else if (isQuestion()){
			speak(d);
			return -2;
		}
			
		speak(d);
		return 0;
		
	}
	
	public int converse() throws SlickException{
		return converse(this.dialogue);
	}
	
	public ConsoleMenuListener getListener(){
		return this.listener;
	}
		
	public void setQuestion(boolean question){
		this.back.setEnabled(this.dialogue.canGoBack());
		this.next.setEnabled(!question);
		this.yes.setEnabled(question);
		this.no.setEnabled(question);
	}
	
	//If currently talking normally, return true. If asking a question, return false
	public boolean isTalking(){
		return this.next.isEnabled();
	}
	
	public void answerQuestion(boolean answer){
		if (this.dialogue instanceof ComplexDialogue){
			ComplexDialogue d = (ComplexDialogue) this.dialogue;
			this.dialogue.reset();
			d.answer(answer);
			this.dialogue = d.getDialogue();
			setQuestion(false);
			//this.parent.requestFocus();
		}
	}
	
	public boolean isQuestion(){
		return this.dialogue.isQuestion();
	}
	
	public Dialogue getDialogue(){
		return this.dialogue;
	}
	
	public void setDialogue(Dialogue dialogue){
		this.dialogue = dialogue;
	}
	
}
