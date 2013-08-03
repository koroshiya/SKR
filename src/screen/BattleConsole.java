package screen;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import controls.SlickRectangle;

public class BattleConsole {
	
	private static ArrayList<String> backlog = new ArrayList<String>();
	private static SlickRectangle console = null;

	public static void cleanConsole(){
		try {
			console = new SlickRectangle(0, 0, 801, 100, "");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		backlog.clear();
	}
	
	public static void writeConsole(String message){
		
		if (console == null){
			cleanConsole();
		}
		backlog.add(message);
		
	}
	
	public static void paint(Graphics g){
		g.draw(console);
		int size = backlog.size();
		for (int i = 0; i < size && i < 6; i++){
			g.drawString(backlog.get(size - i - 1), 25, 10 + i * 10);
		}
		
	}

}
