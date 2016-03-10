package pane;

import java.util.List;

import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.TurtleModel;

public abstract class SuperPane{

	public ScrollPane myPane;
	public Group myRoot;
	public VBox myBox;
	
	public SuperPane(double x, double y) {	
		setMyRoot(new Group());
		myBox = new VBox();
		
		getMyRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		
		setMyPane(new ScrollPane(myBox));
		getMyRoot().getChildren().add(myPane);
		getMyPane().setLayoutX(x);
		getMyPane().setLayoutY(y);
		
		myPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		myPane.setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
        myPane.setMaxSize(400, UIConstants.LOWER_PANE_HEIGHT);
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

	public void addTurtles(List<TurtleModel> tm) {
		for(TurtleModel item : tm){
			Text info = new Text(Boolean.toString(item.isActive()));
			myBox.getChildren().add(info);
		}
	}
}

