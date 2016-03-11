package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import constants.UIConstants;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DisplayModel;
import model.TurtleModel;
import pane.SPane;

public class TurtleIDView extends ScrollView {
	private TextArea myTA;
	private VBox myHB;
	private Map<Double, VBox> myMap;
	
	public TurtleIDView(TextArea ta) {
		getMyPane().setLayoutX(UIConstants.TURTLE_PANE_X);
		getMyPane().setLayoutY(UIConstants.LOWER_PANE_Y);
		getMyPane().setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
		getMyPane().setMaxSize(400, UIConstants.LOWER_PANE_HEIGHT);
		getMyBox().getChildren().add(new Text("Turtles"));
		myTA = ta;
		myHB = getMyBox();
		myMap = new HashMap<Double,VBox>();
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof DisplayModel){
			DisplayModel myT = (DisplayModel) o;
			getMyBox().getChildren().clear();
			for(int i = 0; i < myT.getTurtleList().size(); i++){
				TurtleModel tm = myT.getTurtleList().get(i);
				VBox myBox = new VBox();
				Hyperlink item = new Hyperlink("Turtle " + Integer.toString(i+1) + " : " + Boolean.toString(tm.isActive()));
				item.setOnAction(e -> {
					myTA.appendText("tell ");
				});
				Text info = new Text("\t" +"(" + Double.toString(Math.round(tm.getPositionX())) + "," + Double.toString(Math.round(tm.getPositionY())) + ")");
				myBox.getChildren().add(item);
				myBox.getChildren().add(info);
				myHB.getChildren().add(myBox);
			}
		}
	}

}
