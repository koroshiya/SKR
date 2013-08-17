package generator.map;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PreviewPanel extends JScrollPane{

	private static final long serialVersionUID = 1L;
	
	private JLabel[][] map;
	private int x;
	private int y;
	private final int icon_size = 48;
	
	private ImageIcon background;
	private ImageIcon appliedTile;
	private JPanel panel;
	
	public PreviewPanel(int x, int y){

		this.x = x;
		this.y = y;
		
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.getHorizontalScrollBar().setUnitIncrement(20);
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.setDoubleBuffered(true);
		this.setIgnoreRepaint(true);
		this.setOpaque(true);
		this.setWheelScrollingEnabled(true);
		
		panel = new JPanel();
		map = new JLabel[x][y];
		background = null;
		panel.setLayout(new GridLayout(y, x));
		
		Dimension size = new Dimension(750, 750);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setSize(size);

		
		this.setViewportView(this.panel);
		//this.add(this.panel);

		
		PreviewListener ml = new PreviewListener(this);
		
		for (JLabel[] lbl : map){
			for (JLabel label : lbl){
				label = new JLabel("Blank");
				label.addMouseListener(ml);
				
				Dimension lblSize = new Dimension(icon_size, icon_size);
				label.setMinimumSize(lblSize);
				label.setMaximumSize(lblSize);
				label.setPreferredSize(lblSize);
				label.setSize(lblSize);
				
				this.panel.add(label);
			}
		}
		//this.setAutoscrolls(true);
		
	}
	
	public void setTile(JLabel lbl){
		
		//System.out.println(appliedTile.getIconHeight());
		lbl.setIcon(appliedTile);
		lbl.repaint();
		
	}
		
	public ImageIcon getBackgroundImage(){
		return this.background;
	}
	
	public void startApplying(ImageIcon i){
		
		appliedTile = appliedTile == i ? null : i;
		
	}
	
	public void applyBackground(ImageIcon background2) {

		if (background2 != null){
			for (Component comp : this.panel.getComponents()){
				JLabel label = (JLabel) comp;
				if (label.getIcon() == null){
					label.setIcon(background2);
				}
			}
			this.background = background2;
			refresh();
		}
		
	}

	public void removeBackground(ImageIcon background2) {

		if (background2 != null){
			for (Component comp : this.panel.getComponents()){
				JLabel label = (JLabel) comp;
				if (label.getIcon() == background2){
					label.setIcon(null);
				}
				label.repaint();
			}
			this.background = null;
			refresh();
		}
		
	}
	
	public void refresh(){
		for (Component comp : this.panel.getComponents()){
			JLabel label = (JLabel) comp;
			label.repaint();
		}
		this.updateUI();
	}
	
	public void reset(){
		for (Component comp : this.panel.getComponents()){
			JLabel label = (JLabel) comp;
			label.setIcon(null);
			label.repaint();
		}
		this.updateUI();
	}
	
	public int getXValue(){
		return this.x;
	}
	
	public int getYValue(){
		return this.y;
	}
	
	public JLabel[][] getMap(){
		return this.map;
	}
	
	public JLabel getTile(int x, int y, int length){
		
		int row = y * length;
		
		return (JLabel) this.panel.getComponent(row + x);
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		this.updateUI();
	}
	
}
