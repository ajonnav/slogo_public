package addons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Features {

	
	public Button makeB(String property, EventHandler<ActionEvent> action) {
		Button myButton = new Button();
		myButton = new Button();
		myButton.setText(property);
		myButton.setOnAction(action);
		return myButton;
	}
}
