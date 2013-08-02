package screen;

import interfaces.InteractableObject;
import interfaces.SlickDrawableFrame;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.japanzai.skr.ComplexDialogue;
import com.japanzai.skr.Dialogue;

import controls.SlickRectangle;

public class MapConsole extends SlickListener implements SlickDrawableFrame {
	
	private SlickRectangle background;
	private SlickRectangle[] rects = new SlickRectangle[4];
	
	private Dialogue dialogue;
	private InteractableObject npc = null;
	
	private GameScreen parent;

	private final int INTERACT = Input.KEY_A;
	private final int BACK = Input.KEY_W;
	private final int YES = Input.KEY_S;
	private final int NO = Input.KEY_D;
	
	private final String[] tags = {"Back [W]", "Next [A]", "Yes [S]", "No [D]"};
	
	public MapConsole(ArrayList<Dialogue> dialogues, Dialogue dialogue, GameScreen battleScreen) throws SlickException{
			
		super();
		
		this.dialogue = dialogue;
		this.parent = battleScreen;
		//TODO: set background image for console?
		//TODO: set aside space at bottom for char info, map, etc.
		//BattleMenuListener listener = new BattleMenuListener();

		background = new SlickRectangle(0, 480, 801, 120, "");
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 484;
		for (int i = 0; i < rects.length; i++){
			rects[i] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight, tags[i], false);
			startY += buttonHeight + 2;
		}
		rects[1].setEnabled(true);
			
	}
	
	public void paint(Graphics g){
		background.paint(g);
		for (SlickRectangle rect : rects){
			rect.paint(g);
		}
		g.drawImage(this.dialogue.getCache(), 10, 495);
		g.drawString(this.dialogue.speak(), 140, 490);
	}
		
	public void speak(Dialogue d) throws SlickException{d.increment();}

	private int converse(Dialogue d) throws SlickException {
		
		boolean moreDialogue = d.moreDialogue();
		rects[0].setEnabled(this.dialogue.canGoBack());
		
		if (!moreDialogue){
			d.reset();
			return -1;
		}
			
		speak(d);
		return isQuestion() ? -2 : 0;
		
	}
	
	public int converse() throws SlickException{return converse(this.dialogue);}
	
	public void setInteractiveTile(InteractableObject npc){this.npc = npc;}
		
	public void setQuestion(boolean question){
		rects[0].setEnabled(this.dialogue.canGoBack());
		rects[1].setEnabled(!question);
		rects[2].setEnabled(question);
		rects[3].setEnabled(question);
	}
	
	//If currently talking normally, return true. If asking a question, return false
	public boolean isTalking(){return rects[1].isEnabled();}
	
	public void answerQuestion(boolean answer){
		if (this.dialogue instanceof ComplexDialogue){
			ComplexDialogue d = (ComplexDialogue) this.dialogue;
			this.dialogue.reset();
			d.answer(answer);
			this.dialogue = d.getDialogue();
			setQuestion(false);
		}
	}
	
	public boolean isQuestion(){return this.dialogue.isQuestion();}
	
	public Dialogue getDialogue(){return this.dialogue;}
	
	public void setDialogue(Dialogue dialogue){this.dialogue = dialogue;}
	
	@Override
	public void processMouseClick(int arg0, int x, int y) throws IOException, ClassNotFoundException {

		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				process(rect.getTag());
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		try {
			this.processMouseClick(1, arg1, arg2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void process(String tag) {
		
		try{
			if (tag.equals(tags[0])){
				back();
			}else if (tag.equals(tags[1])){
				next();
			}else if (tag.equals(tags[2])){
				answer(true);
			} else if (tag.equals(tags[3])){
				answer(false);
			}			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		try{
			if (code == INTERACT){
				next();
			}else if (code == BACK){
				back();
			}else if (code == YES){
				answer(true);
			}else if (code == NO){
				answer(false);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void answer(boolean answer) throws SlickException{
		
		if (!isTalking()){
			answerQuestion(answer);
			next();
		}
		
	}
	
	private void next() throws SlickException{
		
		if (!isTalking()){return;}
		
		int state = converse();
		
		if (state == -1){
			parent.removeMapConsole();
			npc.finishInteraction(parent);
			npc = null;
		}else if (state == -2){
			setQuestion(true);
		}
	}
	
	private void back() throws SlickException{
		Dialogue d = getDialogue();
		if (d.canGoBack()){
			d.back();
			setQuestion(false);
			next();
		}
	}
	
}
