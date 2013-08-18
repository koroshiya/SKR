package tile.event;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import screen.GameScreen;
import slickgamestate.Battle;
import slickgamestate.SlickSKR;

import character.EnemyCharacter;
import character.NonPlayableCharacter;

public class CharacterEventBattleTile extends CharacterEventTile{
	
	private final ArrayList<EnemyCharacter> enemies;
	
	public CharacterEventBattleTile(String spritePath, NonPlayableCharacter npc, EnemyCharacter enemy) throws SlickException {

		this(spritePath, npc, new ArrayList<EnemyCharacter>());
		this.enemies.add(enemy);
		
	}
	
	public CharacterEventBattleTile(String spritePath, NonPlayableCharacter npc, ArrayList<EnemyCharacter> enemies) throws SlickException {
		
		super(spritePath, npc, SlickSKR.BATTLE);
		this.enemies = enemies;
		
	}
	
	@Override
	public void interact(GameScreen parent) {
		Battle battle = ((Battle)parent.getState(SlickSKR.BATTLE));
		battle.setEnemies(enemies);
		super.interact(parent);
	}

}
