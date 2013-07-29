package screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {

	private JLabel label;
	
	public GameOver(){
		
		ImageIcon i = new ImageIcon(getClass().getResource("/images/gameover.png"));
		label = new JLabel(i);
		
		Dimension d = new Dimension(800, 600);
		
		this.setLayout(new BorderLayout());
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		this.setSize(d);
		
		
		label.setLayout(new BorderLayout());
		label.setMinimumSize(d);
		label.setMaximumSize(d);
		label.setPreferredSize(d);
		label.setSize(d);
		
		//this.add(label);
		
		this.repaint();
		this.invalidate();
		this.validate();
		this.updateUI();
		
	}
	
	@Override
	public void paint(Graphics g){
		
		super.paint(g);
		g.drawImage(((ImageIcon)label.getIcon()).getImage(), 0, 0, null);
		
	}
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1,
			org.newdawn.slick.Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
