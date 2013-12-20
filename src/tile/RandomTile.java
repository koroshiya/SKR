package tile;


public class RandomTile {
	
	private Tile t;
	private int chance;
	
	public RandomTile(Tile t, int chance){
		
		this.t = t;
		this.chance = chance;
		
	}
	
	public Tile getTile(int x, int y){
		
		if (this.t instanceof ChestTile){
			ChestTile chest = (ChestTile) this.t;
			return chest.create(x, y);
		}
		
		t.setXCoordinate(x);
		t.setYCoordinate(y);
		return t;
	}
	
	public boolean canGenerate(){
		return Math.random() * 100  > this.chance;
	}
	
}
