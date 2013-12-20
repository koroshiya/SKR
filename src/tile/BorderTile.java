package tile;

import org.newdawn.slick.SlickException;

public class BorderTile {
	
	private String borderDirectory;
	
	public BorderTile(String borderDirectory){
		this.borderDirectory = borderDirectory;
	}
	
	public Tile getTile(int i, int j, int maxX, int maxY) throws SlickException{
		String border = borderDirectory;
		if (i == 0){
			if (j == 0){
				border = border + "topLeft.png";
			}else if (j == maxY - 1){
				border = border + "bottomLeft.png";
			}else {
				border = border + "borderLeft.png";
			}
		}else if (i == maxX - 1){
			if (j == 0){
				border = border + "topRight.png";
			}else if (j == maxY - 1){
				border = border + "bottomRight.png";
			}else {
				border = border + "borderRight.png";
			}
		}else if (j == 0){
			border = border + "borderTop.png";
		}else if (j == maxY - 1){
			border = border + "borderBottom.png";
		}else {
			return null;
		}
		
		return new Tile(false, border, i, j);
		
	}
	
}
