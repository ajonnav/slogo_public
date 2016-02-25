package addons;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Features {

	
	public Button makeB(String property, EventHandler<ActionEvent> action) {
		Button myButton = new Button();
		myButton = new Button();
		myButton.setText(property);
		myButton.setOnAction(action);
		return myButton;
	}
	
	public ComboBox makeCBox(ObservableList<String> choices){
		ComboBox myCBox = new ComboBox(choices);
		return myCBox;
	}
}
