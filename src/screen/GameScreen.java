package screen;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import map.MovementListener;
import map.ParentMap;
import menu.MenuItemListener;
import menu.MenuMainWindow;
import menu.character.CharacterProfileWindow;
import menu.inventory.InventoryWindow;

import animation.AnimatedSprite;
import battle.Battle;
import character.EnemyCharacter;
import character.PlayableCharacter;

import com.japanzai.skr.Dialogue;
import com.japanzai.skr.Driver;
import com.japanzai.skr.MapScreen;
import com.japanzai.skr.Opponents;
import com.japanzai.skr.Party;
import com.japanzai.skr.SlickSKR;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameScreen extends AppGameContainer{
	
	private JPanel panel;
	private JPanel layer;
	
	private static JCheckBoxMenuItem always;
	private static JCheckBoxMenuItem never;
	
	private static JFrame frame;
	private static JMenuBar menuBar;

	private final int target;
	
	public GameScreen(SlickSKR skr) throws SlickException{
		
		super(skr);
		this.setVSync(true);
		this.target = this.targetFPS;
		this.setSmoothDeltas(true);
		this.setUpdateOnlyWhenVisible(true);
		//this.setShowFPS(false);
		//TODO: Set animated cursor
		//TODO: Set icon
		
		try{
			this.setDisplayMode(800, 600, false); //TODO: Change to true for fullscreen
			//this.start();
		}catch (SlickException ex){
			ex.printStackTrace();
		}
		
		try{			
			
			
			panel = new JPanel();
			panel.setDoubleBuffered(true);
			//panel.setLayout(cards);
			
			//this.add(panel);
			//frame = this;
			layer = new JPanel();
			layer.setDoubleBuffered(true);

			instantiateMenuBar();
			//frame.pack(); //add insets
			//setResizable(false);
			
		}catch (Exception ex){
			MessageBox.ErrorBox(ex, "Driver error 1", null);
		}
		
	}
	
	public void setSKR(SlickSKR skr){
		super.game = skr;
	}
	
	public void instantiateMenuBar(){
		
		JMenuBar bar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		JMenu menuView = new JMenu("View");
		JMenu menuHelp = new JMenu("Help");
		JMenu menuTesting = new JMenu("TESTING");

		setMenuItem("Save", menuFile);
		setMenuItem("Load", menuFile);
		setMenuItem("Quit", menuFile);
		
		setMenuItem("Fullscreen", menuView);
		setMenuItem("Menu", menuView);

		never = setJCheckBoxMenuItem(menuTesting, "No encounters");
		never.setSelected(true); //Because we're testing //TODO: remove before production, obviously
		always = setJCheckBoxMenuItem(menuTesting, "Encounter every step");
		
		JMenu fight = new JMenu("FIGHT");
		for (EnemyCharacter ex : Opponents.getEnemies()){
			setMenuItem(ex.getNickName(), fight);
		}
		menuTesting.add(fight);
		
		setMenuItem("About", menuHelp);
		setMenuItem("Hotkeys", menuHelp);
		setMenuItem("Changelog", menuHelp);
		
		bar.add(menuFile);
		bar.add(menuView);
		bar.add(menuHelp);
		bar.add(menuTesting);
		
		menuBar = bar;
		//this.setJMenuBar(menuBar);
		
	}
	
	private JCheckBoxMenuItem setJCheckBoxMenuItem(JMenu menu, String tag){
		JCheckBoxMenuItem item = new JCheckBoxMenuItem();
		item.setActionCommand(tag);
		item.setText(tag);
		item.setName(tag);
		item.addActionListener(new MenuBarListener(this));
		menu.add(item);
		return item;
	}
	
	private void setMenuItem(String tag, JMenu menu){
		
		JMenuItem item = new JMenuItem();
		item.setName(tag);
		item.setText(tag);
		item.setActionCommand(tag);
		item.addActionListener(new MenuBarListener(this));
		
		menu.add(item);
		
	}
		
	public void swapToBattle() throws SlickException{
		
		Battle battle = (Battle)panel.getComponent(1);
		battle.start();
		
	}
	
	public void swapToMenu(){
		
		swapView(2);
		//this.setVSync(false);
		//this.setTargetFrameRate(5);
		MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		this.getInput().addKeyListener(ml);
		this.getInput().addMouseListener(ml);
		
	}
	
	public void swapToMap(){
		
		swapView(0);
		
		MovementListener ml = new MovementListener((MapScreen) getState(0));
		
		getInput().addKeyListener(ml);
		getInput().addMouseListener(ml);
		
	}
	
	public void swapToCharacterWindow(){
		swapView(4);
		MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		
		getInput().addKeyListener(ml);
		//getInput().addMouseListener(ml);
	}
	
	public void swapToInventory(){
		
		swapView(3);
		MenuItemListener ml = new MenuItemListener(this, (MenuMainWindow) getState(2));
		
		getInput().addKeyListener(ml);
		//getInput().addMouseListener(ml);
		
	}
	
	public void swapView(int i){

		//this.setTargetFrameRate(this.target);
		//this.setVSync(true);
		((StateBasedGame)super.game).enterState(i);
		removeListeners();
		
	}
	
	public int getStateIndex(){
		return ((StateBasedGame)super.game).getCurrentStateID();
	}
	
	public GameState getState(){
		return ((StateBasedGame)super.game).getCurrentState();
	}
	
	public GameState getState(int i){
		
		return ((StateBasedGame)super.game).getState(i);
		
	}
	
	public void addContent(JComponent comp){
		this.panel.add(comp);
	}
		
	public void setBattle(ArrayList<EnemyCharacter> arrayList){
		
		Battle battle = new Battle(arrayList, this);
		
		for (Component comp : this.panel.getComponents()){
			if (comp instanceof Battle){
				comp = battle;
				return;
			}
		}
		
		addContent(battle);
		
	}
	
	public static void WriteOnScreen(String message, String title){
		MessageBox.InfoBox(message, title, frame);
	}

	public static void WriteOnScreen(StringBuffer itemList, String title) {
		MessageBox.InfoBox(itemList, title, frame);
	}
		
	public void setFullScreen(){
				
		try {
			this.setFullscreen(!this.isFullscreen());
		} catch (SlickException e) {
			e.printStackTrace();
		}
				
	}
		
	public boolean isInBattle(){
		if (panel.getComponents().length < 2){return false;}
		return (panel.getComponent(1) instanceof Battle && panel.getComponent(1).isVisible());
	}
	
	public static JMenuBar getJMenuBarInstance(){
		return menuBar;
	}
	
	public static boolean alwaysEncounter(){
		return always.isSelected();
	}
	
	public static boolean neverEncounter(){
		return never.isSelected();
	}
	
	public static void setAlwaysEncounter(boolean encounter){
		always.setSelected(encounter);
		never.setSelected(false);		
	}
	
	public static void setNeverEncounter(boolean encounter){
		never.setSelected(encounter);
		always.setSelected(false);	
	}
		
	public void WriteOnMap(Dialogue dialogue) throws SlickException{
		
		int mapHeight = 0;
		GameState comp = ((StateBasedGame)super.game).getCurrentState();
		
		if (comp instanceof MapScreen){
						
			MapScreen map = (MapScreen) comp;
			mapHeight = map.getParentMap().getYBoundary();
		
		}else if (panel.getComponents().length > 0){
			Component comp2 = panel.getComponent(1);
			if (comp2.isVisible() && comp2 instanceof Battle){
					
				Battle map = (Battle) comp2;
				mapHeight = map.getHeight();
				
			}
		}
		
		if (mapHeight != 0){
			
			removeListeners();
			MapConsole console = new MapConsole(null, dialogue, this);
			//this.addKeyListener(mapListener);
			//this.addKeyListener(console.getListener()); //TODO: check if needed
			
			this.layer.add(console);
			layer.setPreferredSize(console.getPreferredSize());
			layer.setSize(console.getSize());
			layer.setMaximumSize(console.getMaximumSize());
			layer.setMinimumSize(console.getMinimumSize());
			this.layer.setOpaque(false);
			this.panel.add(this.layer, 0);
			//comp.add(this.layer);
			//this.layer.setLocation(0, map.getHeight() - 50); //map.getHeight() - 50
			this.layer.setBorder(new EmptyBorder(mapHeight - 120, 0, 0, 0));
			this.layer.setVisible(true);
			this.panel.updateUI();
			//this.layer.grabFocus();
				
			console.start();
			console.converse();
		}
		
	}
	
	public void removeMapConsole(){
		
		this.layer.removeAll();
		this.layer.setVisible(false);
		this.panel.remove(0);
		
		removeListeners();
		
	}
	
	public void removeListeners(){
		
		this.getInput().removeAllListeners();
		
	}
	
	
	
	
	
	
	
	
	
	
	public boolean isEncounter(ParentMap map){
		
		if (alwaysEncounter() || !neverEncounter() && map.randomEncounter()){
			return true;
		}
		return false;
		
	}
	
	public void encounter(ParentMap map){
		
		encounter(map.getEnemyFormation());
		
	}
	
	public void encounter(EnemyCharacter enemy){
		
		ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
		enemies.add(enemy);
		encounter(enemies);
		
	}
	
	public void encounter(ArrayList<EnemyCharacter> enemies){
		
		if (panel.getComponent(0).isVisible()){
			setBattle(enemies);
			swapView(1);
		}else {
			System.out.println("Can only do this from the map");
		}
		
	}
			
	public void gameover() throws SlickException{

		swapView(666);
				
		int choice = MessageBox.ChoiceBox("Resume from last save point?", "Game Over", null);
		if (choice == JOptionPane.YES_OPTION){
			Driver.restart();
		}else{
			System.exit(0);
		}
	}

	
	public void setMap(ParentMap map) throws SlickException {
		((MapScreen) getState(0)).setMap(map);
	}

	

	
}
