package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuItemLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	public MenuItemLabel(String name){
		
		super();
		super.setIcon(new ImageIcon(getClass().getResource("/images/menuGradient.png")));
		this.setName(name);
		//this.setText(name);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		Dimension size = new Dimension(400, 50);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		
		Font f = new Font(this.getFont().getFontName(), 24, 24);
		this.setFont(f);
		
	}
	
	@Override
	public void paint(Graphics g){
		
		super.paint(g);
		
		if (g instanceof Graphics2D){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		
		int x = g.getFontMetrics().stringWidth(this.getName());
		int y = g.getFontMetrics().getHeight();
		//System.out.println((this.getHeight() - y) / 2);
		
		g.drawString(this.getName(), (this.getWidth() - x) / 2, y + 3);
		//g.drawImage(this.sprite.getSprite(), Math.round(this.sprite.getX()), Math.round(this.sprite.getY()), null);
		
	}
	
}
