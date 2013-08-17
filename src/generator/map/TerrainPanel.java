package generator.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TerrainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final String[] supportedImages = {".png", ".jpg", ".jpeg", ".gif"};
	private final Dimension[] supportedSizes = {new Dimension(48, 48)};
	private ArrayList<ImageIcon> images;
	private ArrayList<String> names;
	private ArrayList<JLabel> labels;
	private MainFrame parent;
	private ImageIcon background;
	
	public TerrainPanel(File imageDir, MainFrame parent) {
		
		this.parent = parent;
		images = new ArrayList<ImageIcon>();
		labels = new ArrayList<JLabel>();
		names = new ArrayList<String>();
		
		parseDir(imageDir);
		
		this.setLayout(new FlowLayout());

		TerrainListener ml = new TerrainListener(this);
		for (ImageIcon i : images){
			JLabel lbl = new JLabel(i);
			lbl.addMouseListener(ml);
			labels.add(lbl);
			this.add(lbl);
		}
		
		JLabel blank = new JLabel("Blank");
		blank.addMouseListener(ml);
		labels.add(blank);
		this.add(blank);
		
		JLabel bgTile = new JLabel("<-Background");
		BackgroundListener tl = new BackgroundListener(this);
		bgTile.addMouseListener(tl);
		this.add(bgTile);
		
		this.add(new Parameters(5));

		BackgroundListener bgl = new BackgroundListener(this);
		JButton applyBackground = new JButton("Apply Background");
		applyBackground.addActionListener(bgl);
		JButton removeBackground = new JButton("Remove Background");
		removeBackground.addActionListener(bgl);

		this.add(applyBackground);
		this.add(removeBackground);
		
		Dimension size = new Dimension(300, 600);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setSize(size);
		
	}
	
	private void parseDir(File dir){
		
		System.out.println("Parsing directory: " + dir.getAbsolutePath());
		for (File f : dir.listFiles()){
			if (isSupportedImage(f)){
				System.out.println("Found: " + f.getName());
				ImageIcon i = new ImageIcon(f.getAbsolutePath());
				images.add(i);
				names.add(f.getName());
			}
		}
		
	}
	
	public String getNameOfImage(ImageIcon img){

		for (int i = 0; i < images.size(); i++){
			
			if (images.get(i) == img){
				
				return names.get(i);
				
			}
			
		}
		
		return "null";
		
	}
	
	private boolean isSupportedImage(File f){
		
		for (String s : supportedImages){
			
			if (f.getName().endsWith(s)){
				ImageIcon i = new ImageIcon(f.getAbsolutePath());
				if (isSupportedSize(i)){return true;}
				System.out.println(f.getName() + " is in a supported format, but not 48x48px in size.");
				return false;
			}
			
		}
		
		return false;
		
	}
	
	private boolean isSupportedSize(ImageIcon i){
		
		Dimension d = new Dimension(i.getIconWidth(), i.getIconHeight());
		
		for (Dimension dim : supportedSizes){
			if (d.getHeight() == dim.getHeight() && 
					d.getWidth() == dim.getWidth()){
				return true;
			}
		}
		
		return false;
		
	}

	public void startApplying(JLabel btn) {

		Border b = btn.getBorder() == null ? BorderFactory.createLineBorder(Color.BLACK, 2) : null;
		for (JLabel lbl : labels){
			lbl.setBorder(null);
		}
		btn.setBorder(b);
		parent.startApplying((ImageIcon) btn.getIcon());
		
	}

	public void applyBackground() {
		
		parent.getPreview().applyBackground(background);
		
	}

	public void removeBackground() {

		parent.getPreview().removeBackground(background);
		
	}

	public void setBackground(JLabel lbl) {
		
		parent.getPreview().setTile(lbl);
		this.background = (ImageIcon) lbl.getIcon();
		
	}
	
	
}
