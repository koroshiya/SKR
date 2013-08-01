package com.japanzai.skr;

import java.util.ArrayList;

import character.Character;

public class ComplexDialogue extends Dialogue{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Dialogue> dialogueList;
	private int dialogueIndex;
	private int max;
	
	public ComplexDialogue(){
		
		super();
		instantiate();
		
	}
	
	public ComplexDialogue(ArrayList<String> text, Character c){
		
		super(text, c);
		instantiate();
		
	}
	
	public ComplexDialogue(Line dialogue){
		
		super(dialogue);
		instantiate();
		
	}
	
	public ComplexDialogue(ArrayList<Dialogue> dialogue) {
		
		super();
		instantiate();
		addDialogues(dialogue);
		
	}
	
	public void instantiate(){
		this.dialogueList = new ArrayList<Dialogue>();
		this.dialogueIndex = 0;
		this.max = this.dialogueList.size() - 1;
	}
	
	public void addDialogue(Dialogue d){
		this.dialogueList.add(d);
		this.max++;
	}
	
	public void addDialogues(ArrayList<Dialogue> dialogues){
		for (Dialogue d : dialogues){
			addDialogue(d);
		}
	}
	
	public Dialogue getDialogue(){
		return this.dialogueList.get(dialogueIndex);
	}
	
	public void setDialogueIndex(int index){
		if (index >= 0 && index <= max){
			this.dialogueIndex = index;
		}
	}
	
	public void answer(boolean answer){
		dialogueIndex = answer ? 0 : 1;
	}

}
