package screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import org.newdawn.slick.SlickException;

import com.japanzai.skr.Dialogue;

public class ConsoleMenuListener implements ActionListener, KeyListener {

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
			console.speak();
			next();
		}
	}
	
	private void answer(boolean answer) throws SlickException{
		
		if (console.isTalking()){return;}
		
		console.answerQuestion(answer);
		next();
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		int code = arg0.getKeyCode();
		//System.out.println(code);
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
	public void keyTyped(KeyEvent arg0) {}

}
