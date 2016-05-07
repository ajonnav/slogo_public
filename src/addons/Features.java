package addons;

import java.util.Map;
import java.util.Optional;
import constants.UIConstants;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Features {

	public Button makeB(String property, EventHandler<ActionEvent> action) {

		Button myButton = new Button(property);
		myButton.setOnAction(action);
		return myButton;
	}

	public Canvas makeCanvas(double xLoc, double yLoc, double width, double height, Color color) {
		Canvas canvas = new Canvas(width, height);
		canvas.setTranslateX(xLoc);
		canvas.setTranslateY(yLoc);
		GraphicsContext GC = canvas.getGraphicsContext2D();
		canvas.setTranslateX(xLoc);
		canvas.setTranslateY(yLoc);
		GC.setFill(color);
		GC.fillRect(0, 0, width, height);
		return canvas;
	}

	public void changeCanvasColor(Canvas canvas, Color color) {
		canvas.getGraphicsContext2D().setFill(color);
	}

	public ComboBox<String> makeCBox(ObservableList<String> choices) {
		return new ComboBox<>(choices);
	}

	public ComboBox<HBox> makeColorPicker(double layoutX, double layoutY, double width, double height) {
		ComboBox<HBox> cb = new ComboBox<>();
		cb.setLayoutX(layoutX);
		cb.setLayoutY(layoutY);
		cb.setMinWidth(width);
		cb.setMinHeight(height);
		return cb;
	}

	public void updateComboBoxOptions(ComboBox<HBox> cb, Map<Double, String> map) {
		cb.getItems().clear();
		for (Double s : map.keySet()) {
			HBox myHB = new HBox(UIConstants.PADDING);
			myHB.getChildren().add(makeRect(Color.web(map.get(s))));
			myHB.getChildren().add(makeText(Integer.toString(s.intValue())));
			cb.getItems().add(myHB);
		}
	}

	public void updateComboBoxOptionsImage(ComboBox<HBox> cb, Map<Double, String> map) {
		cb.getItems().clear();
		for (Double s : map.keySet()) {
			HBox myHB = new HBox(UIConstants.PADDING);
			ImageView myImage = new ImageView();
			myImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(map.get(s))));
			myImage.setFitWidth(UIConstants.ARC);
			myImage.setFitHeight(UIConstants.ARC);
			myHB.getChildren().add(myImage);
			myHB.getChildren().add(makeText(Integer.toString(s.intValue())));
			cb.getItems().add(myHB);
		}
		HBox tempB = new HBox();
        tempB.getChildren().add(new Text(UIConstants.IMAGE_TURT));
		cb.setValue(tempB);
	}

	protected Menu makeMenu(String name, MenuBar parent) {
		Menu item = new Menu(name);
		parent.getMenus().add(item);
		return item;
	}

	protected void makeMenuItem(String name, EventHandler<ActionEvent> event, Menu root) {
		MenuItem back = new MenuItem(name);
		back.setOnAction(event);
		root.getItems().add(back);
	}

	public Text makeText(String input) {
		return new Text(input);
	}

	public Rectangle makeRect(Color input) {
		return new Rectangle(UIConstants.RECTANGLE_W, UIConstants.RECTANGLE_H, input);
	}

	public String newTextInput(String holder, String title, String header, String prompt) {
		TextInputDialog dialog = new TextInputDialog(holder);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(prompt);
		Optional<String> input = dialog.showAndWait();
		if (input.isPresent()) {
			return input.get();
		} else {
			return null;
		}
	}
}
