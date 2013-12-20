package console;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import controls.SlickBlankRectangle;

public class BattleConsole {
	
	private static ArrayList<String> backlog = new ArrayList<String>();
	private static SlickBlankRectangle console = null;

	public static void cleanConsole(){
		if (console == null){
			console = new SlickBlankRectangle(0, 0, 817, 100, "");
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
		int i = -1;
		int size = backlog.size();
		if (size > 5){
			size = 5;
		}
		while (++i < size){
			g.drawString(backlog.get(size - i - 1), 25, 15 + i * 15);
		}
		
	}

}
