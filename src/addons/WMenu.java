package addons;

import java.util.ResourceBundle;
import constants.UIConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import preferences.saveState;

public class WMenu {

	private ResourceBundle myResources;
	private MenuMaker menuMaker;
	private MenuBar myMenu;
	private saveState myS;
	
	public WMenu() {
		menuMaker = new MenuMaker();
		myMenu = menuMaker.getMenu();
		myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.EXTRAS);
		
		makeFileMenu();
		makeToggleMenu();
		makeEditMenu();
	}

	public MenuBar getMyMenu(){
		return myMenu;
	}
	public void makeFileMenu(){
		Menu fileMenu = menuMaker.addMenu(myResources.getString("FileCommand"));
		addition("fileMenu", fileMenu);
	}
	
	private void addition(String tag, Menu home) {
		String FM = myResources.getString(tag);
		String[] fileMenus = FM.split("/");
		for(String item : fileMenus) {
			String[] details = item.split(",");
		}

	}
	public void makeToggleMenu(){
		Menu toggleMenu = menuMaker.addMenu(myResources.getString("Toggle"));
		addition("toggleMenu", toggleMenu);
	}
	
	public void makeEditMenu(){
		Menu editMenu = menuMaker.addMenu(myResources.getString("EditCommand"));
	}
}
