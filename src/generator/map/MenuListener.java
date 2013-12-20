package generator.map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.newdawn.slick.util.Log;

public class MenuListener implements ActionListener{

	private MainFrame parent;
	
	public MenuListener(MainFrame parent){
		
		this.parent = parent;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object o = arg0.getSource();
		
		if (o instanceof JMenuItem){
			
			JMenuItem item = (JMenuItem) o;
			String command = item.getText();
			
			if (command.equals("Export")){
				export();
			}else if (command.equals("New")){
				parent.getPreview().reset();
			}
			
		}
		
	}
	
	private void export(){
		
		String s = JOptionPane.showInputDialog("Enter the name of this map.");
		if (s != null && s.length() > 0){
			File f = new File(System.getProperty("user.home") + File.separator + s + ".txt");
			try {
				f.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				writer.write(outputText(s));
				writer.close();
				Log.error("Map successfully written to: " + f.getAbsolutePath());
			} catch (IOException e) {
				Log.error("Couldn't write to file - Export aborted");
				return;
			}
			
		}else {
			Log.error("Invalid name - Export aborted");
		}
		
	}
	
	private String outputText(String mapName){
		
		StringBuffer buffer = new StringBuffer();
		int x = parent.getPreview().getXValue();
		int y = parent.getPreview().getYValue();
		
		buffer.append(mapName);
		buffer.append(" = new ParentMap(");
		buffer.append("new Point(" + Integer.toString(x * 2) + " * ParentMap.ICON_SIZE, " + Integer.toString(y * 2) + " * ParentMap.ICON_SIZE)");
		buffer.append(", ");
		buffer.append("new Point(8 * ParentMap.ICON_SIZE, 6 * ParentMap.ICON_SIZE)"); //Character initial position
		buffer.append(", ");
		buffer.append("Party.getCharacterByIndex(0)");
		buffer.append(", ");
		buffer.append("new AnimatedSprite(Party.getCharacterByIndex(0))");
		buffer.append(", ");
		buffer.append("enemies"); //TODO: encounter list
		buffer.append(", ");
		buffer.append("bs");
		buffer.append(", ");
		buffer.append("new Point(" + Integer.toString(x) + " * ParentMap.ICON_SIZE, " + Integer.toString(y) + " * ParentMap.ICON_SIZE)"); //TODO: mapsize
		buffer.append(", ");
		buffer.append("97"); //TODO: encounter rate
		buffer.append(", ");
		
		String bg = "grass.png";
		ImageIcon ic = parent.getPreview().getBackgroundImage();
		if (ic != null){
			bg = parent.getTerrain().getNameOfImage(ic);
		}

		buffer.append("ImageIO.read(getClass().getResource(\"/images/terrain/" + bg + "\"))");
		buffer.append(");");
		buffer.append("\n");
		buffer.append("\n");
		
		JLabel[][] map = parent.getPreview().getMap();
		JLabel[] label = map[0];
		TerrainPanel terrain = parent.getTerrain();
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < label.length; j++){
				String tr = terrain.getNameOfImage((ImageIcon) parent.getPreview().getTile(i, j, map.length).getIcon());
				if (!tr.equals(bg)){
					buffer.append("Tile t" + mapName + i + j + " = new Tile(true, true, " + "\"/images/terrain/" + tr + "\"" + ", " + i + ", " + j + ");");
					buffer.append("\n");
					buffer.append("PresetTile p" + mapName + i + j + " = new PresetTile(t" + mapName + i + j + ", " + i + ", " + j + ");");
					buffer.append("\n");
				}
			}
		}
		
		return buffer.toString();
		
	}

}
