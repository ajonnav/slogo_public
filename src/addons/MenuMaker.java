package addons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuMaker {
	
	private MenuBar menu;
	
	public MenuMaker() {
		setMenu(new MenuBar());
	}
	
	public Menu addMenu(String title){
		Menu mb  = new Menu(title);
		getMenu().getMenus().add(mb);
		return mb;
		
	}
	
	public MenuItem addMenuItem(String title, EventHandler<ActionEvent> event, Menu root){
		MenuItem back = new MenuItem(title);
		back.setOnAction(event);
		root.getItems().add(back);
		return back;
	}

	public MenuBar getMenu() {
		return menu;
	}

	public void setMenu(MenuBar menu) {
		this.menu = menu;
	}

}
