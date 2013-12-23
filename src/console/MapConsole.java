package console;

import interfaces.InteractableObject;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import screen.GameScreen;
import screen.SlickListener;
import slickgamestate.SlickGameState;
import slickgamestate.SlickSKR;
import tile.ChestTile;
import console.dialogue.ComplexDialogue;
import console.dialogue.Dialogue;
import controls.SlickImageRectangle;
import controls.SlickRectangle;

public class MapConsole extends SlickListener {
	
	private SlickImageRectangle background;
	private SlickImageRectangle[] rects = new SlickImageRectangle[4];
	
	private Dialogue dialogue;
	private InteractableObject npc = null;
	
	private GameScreen parent;

	private final int INTERACT = Input.KEY_A;
	private final int BACK = Input.KEY_W;
	private final int YES = Input.KEY_S;
	private final int NO = Input.KEY_D;
	
	private final String[] tags = {"Back [W]", "Next [A]", "Yes [S]", "No [D]"};
	
	public MapConsole(ArrayList<Dialogue> dialogues, Dialogue dialogue, GameScreen battleScreen) {
			
		super();
		
		this.dialogue = dialogue;
		this.parent = battleScreen;
		//TODO: set background image for console?
		//TODO: set aside space at bottom for char info, map, etc.
		//BattleMenuListener listener = new BattleMenuListener();

		background = new SlickImageRectangle(-1, 506, 817, 120, "", false, "/res/buttons/4x1/brown.png", false);
		
		final int buttonWidth = 150;
		final int buttonHeight = 25;
		final int startX = 650;
		int startY = 510;
		for (int i = 0; i < rects.length; i++){
			rects[i] = new SlickImageRectangle(startX, startY, buttonWidth, buttonHeight, tags[i], false, "/res/buttons/6x1/blue.png", true);
			startY += buttonHeight + 2;
		}
		rects[1].setEnabled(true);
			
	}
	
	public void paint(Graphics g){
		paint(g, 0, 0);
	}
	
	public void paint(Graphics g, int offX, int offY){
		background.paintCache(g, offX, offY);
		for (SlickRectangle rect : rects){
			rect.paintCache(g, offX, offY);
			rect.paintCenter(g,true, offX, offY);
		}
		//g.drawImage(this.dialogue.getCache(), 10 + offX, 515 + offY);
		this.dialogue.getCache().draw(10 + offX, 515 + offY, 100 * SlickSKR.scaleSize, 100 * SlickSKR.scaleSize);
		
		final int x = 140 + offX;
		int y = 518 + offY;
		
		if (!(npc instanceof ChestTile)){
			g.drawString(this.dialogue.speak(), x, y);
		}else{
			for (String s : this.dialogue.getDialog()){
				g.drawString(s, x, y);
				y += 14;
			}
		}
	}
	
	public SlickRectangle[] getRects(){return this.rects;}
	
	public void speak(Dialogue d) {d.increment();}

	private int converse(Dialogue d) {
		
		boolean moreDialogue = d.moreDialogue();
		rects[0].setEnabled(this.dialogue.canGoBack());
		
		if (!moreDialogue){
			d.reset();
			return -1;
		}
			
		speak(d);
		return isQuestion() ? -2 : 0;
		
	}
	
	public int converse() {return converse(this.dialogue);}
	
	public void setInteractiveTile(InteractableObject npc){this.npc = npc;}
		
	public void setQuestion(boolean question){
		rects[0].setEnabled(this.dialogue.canGoBack());
		rects[1].setEnabled(!question);
		rects[2].setEnabled(question);
		rects[3].setEnabled(question);
		SlickGameState.setFlush(true, true); //TODO: in future, consider only flushing dCache
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
	public void processMouseClick(int arg0, int x, int y) {

		for (SlickRectangle rect : rects){
			if (rect.isWithinBounds(x, y)){
				process(rect.getTag());
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		this.processMouseClick(1, arg1, arg2);
	}
	
	public void process(String tag) {
		if (tag.equals(tags[0])){
			back();
		}else if (tag.equals(tags[1])){
			next();
		}else if (tag.equals(tags[2])){
			answer(true);
		} else if (tag.equals(tags[3])){
			answer(false);
		}
	}
	
	@Override
	public void keyReleased(int code, char arg1) {
		if (code == INTERACT){
			next();
		}else if (code == BACK){
			back();
		}else if (code == YES){
			answer(true);
		}else if (code == NO){
			answer(false);
		}
	}
	
	private void answer(boolean answer) {
		
		if (!isTalking()){
			answerQuestion(answer);
			next();
		}
		
	}
	
	private void next() {
		
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
	
	private void back() {
		Dialogue d = getDialogue();
		if (d.canGoBack()){
			d.back();
			setQuestion(false);
			next();
		}
	}
	
}
