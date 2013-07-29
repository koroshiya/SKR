package map;

public class RandomTile {
	
	private Tile t;
	private int chance;
	
	public RandomTile(Tile t, int chance){
		
		this.t = t;
		this.chance = chance;
		
	}
	
	public Tile getTile(){
		
		if (this.t instanceof ChestTile){
			ChestTile chest = (ChestTile) this.t;
			return chest.create();
		}
		
		return t;
	}
	
	public boolean canGenerate(){
		return Math.random() * 100  > this.chance;
	}
	
}
