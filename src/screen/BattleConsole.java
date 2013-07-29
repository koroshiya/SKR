package screen;


import java.awt.Color;

public class BattleConsole extends Console{

	private static final long serialVersionUID = 1L;

	public BattleConsole(){
		
		super();
		
		this.setBackground(Color.BLACK);
		this.setForeground(Color.BLACK);
		
		//BattleMenuListener listener = new BattleMenuListener();
		this.setAutoscrolls(true);
		GameScreen.setComponentSize(768, 120, this);
		
	}
	
		
	/*public void paint(Graphics g){
		
		super.paint(g);
		
		if (g instanceof Graphics2D){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			//Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
			//        new float[] { 3, 1 }, 0);
			//g2.setStroke(stroke);
			g2.setPaint(Color.BLACK);
			
			//((Graphics2D) g).setStroke(new StrokeBorder());
		}
		
		g.setColor(Color.BLACK); //Override with image when tiles have been made
		
		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//g.setColor(Color.WHITE);
		//g.setPaintMode();
		//this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.validate();

	}*/
	
}
