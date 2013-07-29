package battle;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.japanzai.skr.Party;

import character.EnemyCharacter;
import character.PlayableCharacter;

public class BattlePane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<JLabel> enemies;
	private ArrayList<JLabel> party;
	
	/**
	 * TODO: panel down bottom with names and hp bars
	 * */
	
	public BattlePane(ArrayList<EnemyCharacter> enemies){

		this.setLayout(new FlowLayout(FlowLayout.LEADING));

		JPanel left = new JPanel();
		left.setLayout(new GridLayout(4, 1));
		left.setPreferredSize(new Dimension(130, 355));
		
		JPanel emptySpace = new JPanel();
		emptySpace.setPreferredSize(new Dimension(480, 355));
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(4, 1));
		right.setPreferredSize(new Dimension(130, 355));
		
		this.enemies = new ArrayList<JLabel>();
		this.party = new ArrayList<JLabel>();
		
		for (EnemyCharacter e : enemies){
			this.enemies.add(e.getSprite());
		}
		
		for (PlayableCharacter e : Party.getCharactersInParty()){
			this.party.add(e.getSprite());
		}
		
		for (JLabel label : this.enemies){
			left.add(label);
		}
		
		for (JLabel label : this.party){
			right.add(label);
		}
		
		this.add(left, FlowLayout.LEFT);
		this.add(emptySpace, FlowLayout.CENTER);
		this.add(right, FlowLayout.RIGHT);
		
		Dimension size = new Dimension(768, 365); //768, 626
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setSize(size);
		
	}

}
