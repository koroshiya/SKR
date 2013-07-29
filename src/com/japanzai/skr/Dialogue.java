package com.japanzai.skr;

import java.io.Serializable;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import character.Character;

public class Dialogue implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Line> dialogue;
	private int counter;
	private int max;
	
	public Dialogue(ArrayList<Line> dialogue){

		this.counter = 0;
		this.dialogue = dialogue;
		this.max = this.dialogue.size();
		
	}
	
	public Dialogue(ArrayList<String> text, Character c){

		this.counter = 0;
		this.max = 0;
		this.dialogue = new ArrayList<Line>();
		this.addLines(text, c);
		
	}
	
	public Dialogue(Line dialogue){
		
		this.counter = 0;
		ArrayList<Line> dialogues = new ArrayList<Line>();
		dialogues.add(dialogue);
		this.dialogue = dialogues;
		this.max = 1;
		
	}
	
	public Dialogue(){
		
		this.counter = 0;
		this.max = 0;
		this.dialogue = new ArrayList<Line>();
		
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
	
	public void addLines(ArrayList<String> text, Character c){
		for (String s : text){
			Line line = new Line(c, s, max, false);
			this.addLine(line);
		}
	}
	
	public String speak(){
		
		//System.out.println(this.counter);
		return this.dialogue.get(this.counter).getText();
		
	}
	
	public boolean isQuestion(){
		return this.dialogue.get(this.counter).isQuestion();
	}
		
	public boolean moreDialogue(){
		
		return this.counter != this.max;
		
	}
	
	public String getAvatar() throws SlickException{
		return this.dialogue.get(this.counter).getAvatar();
	}
	
	public void increment(){
		
		if (this.counter == this.max){this.counter = 0;}
		else {this.counter++;}
		
	}
	
	public boolean canGoBack(){
		return this.counter > 0;
	}
	
	public void back(){
		if (this.counter > 0){
			this.counter-= 2;
		}
	}
	
	public void reset(){
		this.counter = 0;
	}

	
	public int getCounter() {
		return this.counter;
	}
	
	public void addLines(ArrayList<Line> d) {
		for (Line l : d){
			addLine(l);
		}
	}
	

	
}
