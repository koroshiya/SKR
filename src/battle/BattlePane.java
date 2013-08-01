package battle;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screen.SlickGameState;

import com.japanzai.skr.Party;

import character.EnemyCharacter;
import character.PlayableCharacter;

public class BattlePane extends SlickGameState {
	
	private ArrayList<EnemyCharacter> enemies;
	private ArrayList<PlayableCharacter> party;
	
	/**
	 * TODO: panel down bottom with names and hp bars
	 * ATB gauge
	 * */
	
	public BattlePane(ArrayList<EnemyCharacter> enemies){
		
		this.enemies = enemies;
		this.party = Party.getCharactersInParty();
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		final int constY = 50;
		int enemyY = 405;
		
		for (EnemyCharacter e : this.enemies){
			g.drawImage(e.getCache(), 25, enemyY);
			enemyY += constY;
		}
		
		int partyY = 405;
		for (PlayableCharacter e : this.party){
			g.drawImage(e.getCache(), 727, partyY);
			partyY += constY;
		}
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void processMouseClick(int clickCount, int x, int y)
			throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
