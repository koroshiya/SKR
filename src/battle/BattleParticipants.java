package battle;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.japanzai.skr.Party;

import character.EnemyCharacter;
import character.PlayableCharacter;

public class BattleParticipants extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private ArrayList<JLabel> opponents;
	private ArrayList<JLabel> allies;
	
	/**
	 * TODO: panel down bottom with names and hp bars
	 * TODO: add separate private JLabels for each party member
	 * TODO: add refresh method
	 * */
	
	public BattleParticipants(ArrayList<EnemyCharacter> enemies){

		//this.setLayout(new FlowLayout(FlowLayout.LEADING));

		JPanel left = new JPanel();
		left.setLayout(new GridLayout(4, 1));
		left.setPreferredSize(new Dimension(220, 110));
		
		JPanel emptySpace = new JPanel();
		emptySpace.setPreferredSize(new Dimension(120, 110));
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(4, 1));
		right.setPreferredSize(new Dimension(220, 110));		//768, 626
		
		/*for (int i = 0; i < enemies.size(); i++){
			EnemyCharacter e = enemies.get(i);
			enemyArray[i] = new JLabel(e.getName() + "    " + e.getCurrentHP() + "/" + e.getHP()); //can't have 2 references	
			left.add(enemyArray[i]);
		}
		
		
		for (int i = 0; i < Party.getCharactersInParty().size(); i++){
			PlayableCharacter e = Party.getCharactersInParty().get(i);
			allyArray[i] = new JLabel(e.getName() + "    " + e.getCurrentHP() + "/" + e.getHP()); //can't have 2 references	
			right.add(allyArray[i]);
		}*/
		
		this.opponents = new ArrayList<JLabel>();
		for (EnemyCharacter e : enemies){
			this.opponents.add(e.getStatus()); //can't have 2 references
		}

		this.allies = new ArrayList<JLabel>();
		for (PlayableCharacter e : Party.getCharactersInParty()){
			this.allies.add(e.getStatus());
		}
		
		for (JLabel label : this.opponents){
			left.add(label);
		}
		
		for (JLabel label : this.allies){
			right.add(label);
		}
		
		this.panel = new JPanel();
		Dimension size = new Dimension(585, 120);
		this.panel.setMinimumSize(size);
		this.panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.panel.add(left, FlowLayout.LEFT);
		this.panel.add(emptySpace, FlowLayout.CENTER);
		this.panel.add(right, FlowLayout.RIGHT);
		
		
		size = new Dimension(585, 120);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		//this.setPreferredSize(size);
		//this.setSize(size);
		
		this.setViewportView(this.panel);
		//this.getViewport().add(this.panel);
		
	}

}
