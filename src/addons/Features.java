package addons;

import model.TurtleModel;
import view.TurtleView;

import java.util.ResourceBundle;

import constants.UIConstants;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Features {

	private ResourceBundle myBundle;
	
	public Button makeB(String property, EventHandler<ActionEvent> action) {
		myBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.SCREEN_LANG);
		
		Button myButton = new Button();
		myButton = new Button();
		myButton.setText(property);
		myButton.setOnAction(action);
		return myButton;
	}
	
	public Canvas makeCanvas(int xLoc, int yLoc, int width, int height, Color color){
		Canvas canvas = new Canvas(width, height);	
		GraphicsContext GC = canvas.getGraphicsContext2D();
		GC.setFill(color);
		GC.fillRect(xLoc,yLoc,width,height);
		return canvas;
	}

	public ComboBox makeCBox(ObservableList<String> choices){
		ComboBox myCBox = new ComboBox(choices);
		return myCBox;
	}
	
	private MenuBar makeMenu() {
		MenuBar menu = new MenuBar();
		return menu;

	}
	
	protected Menu makeMenu(String name, MenuBar parent){
		Menu item = new Menu(name);
		parent.getMenus().add(item);
		return item;
	}
	
	protected void makeMenuItem(String name, EventHandler<ActionEvent> event, Menu root) {
		MenuItem back = new MenuItem(name);
		back.setOnAction(event);
		root.getItems().add(back);
	}
}
