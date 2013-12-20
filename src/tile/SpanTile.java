package tile;

public class SpanTile extends Tile {
	
	private final int width;
	private final int height;
	
	public SpanTile(boolean open, String sprite, int width, int height, int x, int y) {
		super(open, sprite, x, y);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){return this.width;}
	
	public int getHeight(){return this.height;}

}
