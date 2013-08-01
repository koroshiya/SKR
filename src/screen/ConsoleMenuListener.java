package screen;

import javax.swing.JButton;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import com.japanzai.skr.Dialogue;

public class ConsoleMenuListener implements MouseListener, KeyListener {

	private MapConsole console;
	private GameScreen parent;	
	
	private final int INTERACT = 65; //keycode to interact //65 = A
	private final int BACK = 87;
	private final int YES = 83;
	private final int NO = 68;
	
	public ConsoleMenuListener(MapConsole mapConsole, GameScreen parent) {
		this.console = mapConsole;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		

		
	}
	
	private void next() throws SlickException{
		
		if (!console.isTalking()){return;}
		
		int state = console.converse();
		
		if (state == -1){
			parent.removeMapConsole();
		}else if (state == -2){
			console.setQuestion(true);
		}
	}
	
	private void back() throws SlickException{
		Dialogue d = console.getDialogue();
		if (d.canGoBack()){
			d.back();
			console.setQuestion(false);
			next();
		}
	}
	
	private void answer(boolean answer) throws SlickException{
		
		if (console.isTalking()){return;}
		
		console.answerQuestion(answer);
		next();
		
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		int code = arg0;
		System.out.println(code);
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

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		if (arg0.getSource() instanceof JButton){
			
			String code = arg0.getActionCommand();
			try{
				if (code.equals("Back")){
					back();
				}else if (code.equals("Next")){
					next();
				}else if (code.equals("Yes")){
					answer(true);
				} else if (code.equals("No")){
					answer(false);
				}			
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {}

	@Override
	public void mouseWheelMoved(int arg0) {}

}
