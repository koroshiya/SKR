package generator.map;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private TerrainPanel terrain;
	private PreviewPanel preview;
	
	
	public MainFrame(){

		int x;
		int y;
		try{
			x = Integer.parseInt(JOptionPane.showInputDialog("Set width of map in units. Minimum value is 32."));
			y = Integer.parseInt(JOptionPane.showInputDialog("Set height of map in units. Minimum value is 26."));
			if (x < 32){x = 32;}
			if (y < 26){y = 26;}
		}catch (Exception ex){
			JOptionPane.showMessageDialog(this, "Invalid input. Using default values.");
			x = 32;
			y = 26;
		}
		
		Dimension size = new Dimension(1100, 850);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setSize(size);
		
		//File defaultImageDir = new File("/images/");
		File parent = (new File(System.getProperty("user.dir"))).getParentFile();
		String imgDir = parent.getAbsolutePath() + "/images";
		File imageDir = new File(imgDir);
		
		this.setMenuBar();

		preview = new PreviewPanel(x, y);
		terrain = new TerrainPanel(imageDir, this);
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));

		this.getContentPane().add(preview);
		this.getContentPane().add(terrain);
		
	}
	
	private void setMenuBar(){
		
		MenuListener ml = new MenuListener(this);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menu = new JMenu("File");

		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(ml);
		JMenuItem menuItem2 = new JMenuItem("Export");
		menuItem2.addActionListener(ml);

		menu.add(menuItem);
		menu.add(menuItem2);
		menubar.add(menu);
		this.setJMenuBar(menubar);
		
	}
	
	public void start(){
		

		this.setResizable(false);
		
		this.setVisible(true);
		
	}

	public void startApplying(ImageIcon icon) {
		this.preview.startApplying(icon);
	}
	
	public TerrainPanel getTerrain(){
		return this.terrain;
	}

	public PreviewPanel getPreview() {
		return this.preview;
	}
	
	
	
}
