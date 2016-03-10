package addons;

import java.util.ResourceBundle;

import constants.UIConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class WMenu {

	private ResourceBundle myResources;
	
	public WMenu() {
		MenuMaker menuMaker = new MenuMaker();
		MenuBar myMenu = menuMaker.getMenu();
		myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.EXTRAS);
		
		makeFileMenu();
		
	
		Menu toggleMenu = menuMaker.addMenu(getResources().getString("Toggle"));
		menuMaker.addMenuItem(getResources().getString("cToggle"), e -> noVars(userVariables), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("hToggle"), e -> noVars(userHistory), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("tToggle"), e -> noVars(userTurtles), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("vToggle"), e -> noVars(userMethods), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("uToggle"), e -> noVars(userInput), toggleMenu);
		Menu editMenu = menuMaker.addMenu(getResources().getString("EditCommand"));
		menuMaker.addMenuItem(getResources().getString("penStatus"), e -> setPenUpDown(), editMenu);
		getRoot().getChildren().add(myMenu);
	}

	public void makeFileMenu(){
		Menu fileMenu = menuMaker.addMenu(getResources().getString("FileCommand"));
		menuMaker.addMenuItem(getResources().getString("HelpTitle"), e -> openHelpPage(), fileMenu);
		menuMaker.addMenuItem(getResources().getString("NewCommand"), e -> switchWS(), fileMenu);
		menuMaker.addMenuItem(getResources().getString("SaveCommand"), e -> setPrefs(), fileMenu);
	}
	
	public void makeToggleMenu(){
		
	}
}
