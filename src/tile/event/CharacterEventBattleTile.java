package tile.event;

import java.util.ArrayList;

import screen.GameScreen;
import slickgamestate.Battle;
import slickgamestate.SlickSKR;

import character.EnemyCharacter;
import character.NonPlayableCharacter;

public class CharacterEventBattleTile extends CharacterEventTile{
	
	private final ArrayList<EnemyCharacter> enemies;
	
	public CharacterEventBattleTile(String spritePath, NonPlayableCharacter npc, EnemyCharacter enemy, int x, int y) {

		this(spritePath, npc, new ArrayList<EnemyCharacter>(), x, y);
		this.enemies.add(enemy);
		
	}
	
	public CharacterEventBattleTile(String spritePath, NonPlayableCharacter npc, ArrayList<EnemyCharacter> enemies, int x, int y) {
		
		super(spritePath, npc, SlickSKR.BATTLE, x, y);
		this.enemies = enemies;
		
	}
	
	@Override
	public void interact(GameScreen parent) {
		Battle battle = ((Battle)parent.getState(SlickSKR.BATTLE));
		battle.setEnemies(enemies);
		super.interact(parent);
	}

}
