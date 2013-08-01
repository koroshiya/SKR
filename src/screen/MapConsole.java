package screen;

import interfaces.SlickDrawableFrame;
import interfaces.SlickEventHandler;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;



import com.japanzai.skr.ComplexDialogue;
import com.japanzai.skr.Dialogue;

import controls.SlickRectangle;

public class MapConsole implements SlickDrawableFrame, SlickEventHandler, MouseListener{
	
	private SlickRectangle background;
	private SlickRectangle[] rects = new SlickRectangle[4];
	
	private Dialogue dialogue;
	private Image cache;
	
	private GameScreen parent;
	
	private DialogueBox dialogueBox;
	private ConsoleMenuListener listener;
	
	private final String[] tags = {"Back [W]", "Next [A]", "Yes [S]", "No [D]"};
	
	public MapConsole(ArrayList<Dialogue> dialogues, Dialogue dialogue, GameScreen battleScreen) throws SlickException{
			
		super();
		
		this.dialogue = dialogue;
		this.parent = battleScreen;
		//TODO: set background image for console?
		//TODO: set aside space at bottom for char info, map, etc.
		//BattleMenuListener listener = new BattleMenuListener();

		dialogueBox = new DialogueBox();
		background = new SlickRectangle(0, 480, 801, 120, "");
		
		this.listener = new ConsoleMenuListener(this, parent);
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 484;
		for (int i = 0; i < rects.length; i++){
			rects[i] = new SlickRectangle(startX, startY, buttonWidth, buttonHeight, tags[i], false);
			startY += buttonHeight + 2;
		}
		rects[1].setEnabled(true);
		
		//this.add(dialogueBox); //TODO: implement
		
			
	}
	
	public void paint(Graphics g){
		background.paint(g);
		for (SlickRectangle rect : rects){
			rect.paint(g);
		}
		g.drawImage(cache, 20f, 600f);
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
		rects[0].setEnabled(this.dialogue.canGoBack());
		
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
		rects[0].setEnabled(this.dialogue.canGoBack());
		rects[1].setEnabled(!question);
		rects[2].setEnabled(question);
		rects[3].setEnabled(question);
	}
	
	//If currently talking normally, return true. If asking a question, return false
	public boolean isTalking(){
		return rects[1].isEnabled();
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

	
	@Override
	public void processMouseClick(int arg0, int x, int y) throws IOException, ClassNotFoundException {

		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				System.out.println(rect.getTag());
				break;
			}
		}
		
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		System.out.println("test");
		try {
			this.processMouseClick(1, arg1, arg2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseWheelMoved(int arg0) {}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {return false;}

	@Override
	public void setInput(Input arg0) {}
	
}
