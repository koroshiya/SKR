package tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import slickgamestate.SlickSKR;

public class SpanTile extends Tile {
	
	private final int width;
	private final int height;
	
	public SpanTile(boolean open, String sprite, int width, int height, int x, int y, boolean fore) {
		super(open, sprite, x, y, fore);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){return this.width;}
	
	public int getHeight(){return this.height;}

	@Override
	public void draw(Graphics g, int x, int y, int targetHeight){
		if (this.sprite != ""){
			try {
				new Image(this.sprite).getScaledCopy(SlickSKR.scaleSize).draw(x, y);
			} catch (SlickException e) {
				e.printStackTrace();
			} catch (NullPointerException npe){
				Log.error("Resource " + this.sprite + " does not exist");
			}
		}
	}

}
