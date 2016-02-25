package addons;

import model.TurtleModel;
import view.TurtleView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Features {

	
	public Button makeB(String property, EventHandler<ActionEvent> action) {
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
}
