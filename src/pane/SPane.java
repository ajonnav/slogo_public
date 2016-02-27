package pane;
 
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public class SPane {
	public ScrollPane myPane;
	public Group myRoot;
 	public VBox myBox;
 	
	public SPane(int x, int y) {
 		setMyRoot(new Group());
 		myBox = new VBox();
 		setMyPane(new ScrollPane(myBox));
 		getMyRoot().getChildren().add(myPane);
 		getMyPane().setLayoutX(x);
 		getMyPane().setLayoutY(y);
 		myPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
 		myPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
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