package screen;

public class ControlScreen {

	private final GameScreen parent;
	
	public ControlScreen(GameScreen battleScreen) {
		
		this.parent = battleScreen;
		
	}	
	
	private void DisplayHotkeys(){
		
		StringBuffer keyPairs = new StringBuffer();

		keyPairs.append("A - Interact" + "\n");
		keyPairs.append("W - Menu" + "\n");
		keyPairs.append("F - Fullscreen" + "\n");
		keyPairs.append("Esc - Quit");
		
		GameScreen.WriteOnScreen(keyPairs, "Hotkeys");
		
	}

	private void DisplayAboutInfo() {
		
		StringBuffer keyPairs = new StringBuffer();

		keyPairs.append("Programming: Koro" + "\n");
		keyPairs.append("Original characters & story: Boichi" + "\n");
		keyPairs.append("Sprites: Koro (oh god no)");
		
		GameScreen.WriteOnScreen(keyPairs, "About SKR Game (yet to be named)");
		
	}

}
