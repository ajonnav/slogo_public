package pane;

import constants.UIConstants;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SPane {
	public ScrollPane myPane;
	public Group myRoot;
	public VBox myBox;
	
	public SPane(int x, int y) {	
		setMyRoot(new Group());
		myBox = new VBox();
		
		getMyRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		
		setMyPane(new ScrollPane(myBox));
		getMyRoot().getChildren().add(myPane);
		getMyPane().setLayoutX(x);
		getMyPane().setLayoutY(y);
		
		myPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myPane.setStyle("-fx-color: red;");
		
	}

	public ScrollPane getMyPane() {
		return myPane;
	}
	
	public Group getMyRoot(){
		return myRoot;
	}

	public void setMyPane(ScrollPane myPane) {
		this.myPane = myPane;
	}
	
	public void setMyRoot(Group r){
		myRoot = r;
	}
}
