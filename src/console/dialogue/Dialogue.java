package console.dialogue;

import interfaces.Photogenic;

import java.io.Serializable;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slickgamestate.SlickGameState;
import character.Character;

public class Dialogue implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Line> dialogue;
	private String dialogLine;
	private int counter;
	private int max;
	private Image cache;
	
	public Dialogue(ArrayList<Line> dialogue){

		this.counter = -1;
		this.dialogue = dialogue;
		this.max = this.dialogue.size();
		
	}
	
	public Dialogue(ArrayList<String> text, Photogenic c){

		this.counter = -1;
		this.dialogue = new ArrayList<Line>();
		this.max = dialogue.size();
		this.addLines(text, c);
		
	}
	
	public Dialogue(Line dialogue){
		
		this.counter = -1;
		ArrayList<Line> dialogues = new ArrayList<Line>();
		dialogues.add(dialogue);
		this.dialogue = dialogues;
		this.max = dialogues.size();
		
	}
	
	public Dialogue(){
		
		this.counter = -1;
		this.dialogue = new ArrayList<Line>();
		this.max = this.dialogue.size();
		
	}
	
	public void addLine(String text, Character c){
		
		Line line = new Line(c, text, max, false);
		this.addLine(line);		
		
	}
	
	public void addLine(Line line){
		this.dialogue.add(line);
		max++;
	}

	public void addQuestion(String text, Character c){//Add param for dialogue to switch to?
		
		Line line = new Line(c, text, max, true);
		this.addLine(line);
		
	}
	
	public void addLines(ArrayList<String> text, Photogenic c){
		for (String s : text){
			Line line = new Line(c, s, max, false);
			this.addLine(line);
		}
	}
	
	public String speak(){return dialogLine;}
	
	public boolean isQuestion(){
		if (this.counter >= this.max){return false;}
		return this.dialogue.get(this.counter).isQuestion();
	}
		
	public boolean moreDialogue(){return this.counter < this.max - 1;}
	
	public String getAvatar() throws SlickException{return this.dialogue.get(this.counter).getAvatar();}
	
	public Image getCache(){return this.cache;}
	
	public void increment(){setCounter(counter == max ? max : counter + 1);}
	
	public boolean canGoBack(){return this.counter >= 0;}
	
	public void back(){
		if (this.counter == 0){
			setCounter(this.counter - 1);
		}else if (this.counter > 0){
			setCounter(this.counter - 2);
		}
	}
	
	public void reset(){setCounter(-1);}
	
	private void setCounter(int count){
		SlickGameState.setFlush(true, true);
		this.counter = count;
		if (this.counter >= 0 && this.counter < this.max){
			Line line = this.dialogue.get(this.counter);
			dialogLine = line.getText();
			
			try {
				this.cache = new Image(line.getAvatar());
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getCounter() {return this.counter;}
	
	public void addLines(ArrayList<Line> d) {
		for (Line l : d){
			addLine(l);
		}
	}
	
	public ArrayList<String> getDialog() {
		
		ArrayList<String> dialog = new ArrayList<String>();
		for (Line line : this.dialogue){
			dialog.add(line.getText());
		}
		this.counter = this.max - 1;
		return dialog;
		
	}
	
}
