package addons;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Features {
	
	public Button makeB(String property, EventHandler<ActionEvent> action) {

		Button myButton = new Button(property);
		myButton.setOnAction(action);
		return myButton;
	}
	
	public Canvas makeCanvas(double xLoc, double yLoc, double width, double height, Color color){
		Canvas canvas = new Canvas(width, height);	
		canvas.setTranslateX(xLoc);
		canvas.setTranslateY(yLoc);
		GraphicsContext GC = canvas.getGraphicsContext2D();
		canvas.setTranslateX(xLoc);
		canvas.setTranslateY(yLoc);
		GC.setFill(color);
		GC.fillRect(0,0,width,height);
		return canvas;
	}

	public void changeCanvasColor(Canvas canvas, Color color){
		canvas.getGraphicsContext2D().setFill(color);
	}
	
	public ComboBox<String> makeCBox(ObservableList<String> choices){
		ComboBox<String> myCBox = new ComboBox<String>(choices);
		return myCBox;
	}
	
	protected ColorPicker setColorPicker(EventHandler<ActionEvent> event) {
		ColorPicker cp = new ColorPicker();
		cp.setValue(Color.CORAL);
		cp.setOnAction(event);
		cp.setLayoutX(250);
		cp.setLayoutY(50);
		return cp;
	}

	public ColorPicker setPenPicker(EventHandler<ActionEvent> event) {
		ColorPicker cp = new ColorPicker();
		cp.setValue(Color.CORAL);
		cp.setOnAction(event);
		cp.setLayoutX(400);
		cp.setLayoutY(50);
		return cp;

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
