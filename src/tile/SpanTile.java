package tile;

public class SpanTile extends Tile {
	
	private final int width;
	private final int height;
	
	public SpanTile(boolean open, boolean reachable, String sprite, int width, int height) {
		super(open, reachable, sprite);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){return this.width;}
	
	public int getHeight(){return this.height;}

}
