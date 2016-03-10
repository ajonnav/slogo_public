package addons;

import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class Features {

    public Button makeB (String property, EventHandler<ActionEvent> action) {

        Button myButton = new Button(property);
        myButton.setOnAction(action);
        return myButton;
    }

    public Canvas makeCanvas (double xLoc, double yLoc, double width, double height, Color color) {
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

    public void changeCanvasColor (Canvas canvas, Color color) {
        canvas.getGraphicsContext2D().setFill(color);
    }

    public ComboBox<String> makeCBox (ObservableList<String> choices) {
        ComboBox<String> myCBox = new ComboBox<String>(choices);
        return myCBox;
    }

    public ComboBox<HBox> makeColorPicker (double layoutX,
                                             double layoutY,
                                             double width,
                                             double height) {
        ComboBox<HBox> cb = new ComboBox<HBox>();
        SingleSelectionModel<HBox> model = new SingleSelectionModel<HBox>() {
            @Override
            protected HBox getModelItem (int index) {
                return null;
            }

            @Override
            protected int getItemCount () {
                return 0;
            }
        };
        cb.setSelectionModel(model);
        cb.setLayoutX(layoutX);
        cb.setLayoutY(layoutY);
        cb.setMinWidth(width);
        cb.setMinHeight(height);
        return cb;
    }
    
    public void updateComboBoxOptions (ComboBox<HBox> cb, Map<Double, String> map) {
        cb.getItems().clear();
        for (Double s : map.keySet()) {
        	HBox myHB = new HBox(4);
        	myHB.getChildren().add(makeRect(Color.web(map.get(s))));
        	myHB.getChildren().add(makeText(Integer.toString(s.intValue())));
            cb.getItems().add(myHB);
        }
    }
    
    public void updateComboBoxOptionsImage (ComboBox<HBox> cb, Map<Double, String> map) {
        cb.getItems().clear();
        for (Double s : map.keySet()) {
        	HBox myHB = new HBox(4);
        	ImageView myImage = new ImageView();
        	myImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(map.get(s))));
        	myImage.setFitWidth(20);
        	myImage.setFitHeight(20);
        	myHB.getChildren().add(myImage);
        	myHB.getChildren().add(makeText(Integer.toString(s.intValue())));
            cb.getItems().add(myHB);
        }
    }

    public ColorPicker setColorPicker (EventHandler<ActionEvent> event) {
        ColorPicker cp = new ColorPicker();
        cp.setValue(Color.CORAL);
        cp.setOnAction(event);
        cp.setLayoutX(250);
        cp.setLayoutY(50);
        return cp;
    }

    public ColorPicker setPenPicker (EventHandler<ActionEvent> event) {
        ColorPicker cp = new ColorPicker();
        cp.setValue(Color.CORAL);
        cp.setOnAction(event);
        cp.setLayoutX(400);
        cp.setLayoutY(50);
        return cp;

    }

    protected Menu makeMenu (String name, MenuBar parent) {
        Menu item = new Menu(name);
        parent.getMenus().add(item);
        return item;
    }

    protected void makeMenuItem (String name, EventHandler<ActionEvent> event, Menu root) {
        MenuItem back = new MenuItem(name);
        back.setOnAction(event);
        root.getItems().add(back);
    }
    
    public static Text makeText(String input){
    	return new Text(input);
    }
    public static Rectangle makeRect(Color input){
    	return new Rectangle(120, 18 ,input);
    }
}
