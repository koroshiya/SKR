package com.japanzai.skr;

import java.io.Serializable;

import interfaces.Photogenic;

import org.newdawn.slick.SlickException;

public class Line implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Photogenic c;
	private String text;
	private int lineNumber;
	private boolean boolQuestion;
	
	public Line(Photogenic c, String text, int lineNumber, boolean boolQuestion){
		
		this.c = c;
		this.text = text;
		this.lineNumber = lineNumber;
		this.boolQuestion = boolQuestion;
		
	}
	
	public String getAvatar() throws SlickException{
		return this.c.getAvatar();
	}
	
	public String getText(){
		return this.text;
	}
	
	public int getLineNumber(){
		return this.lineNumber;
	}
	
	public boolean isQuestion(){
		return this.boolQuestion;
	}
	
}
