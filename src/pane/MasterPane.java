package pane;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MasterPane {

	private Pane myPane;
	private Group myRoot;
	private VBox myBox;
	
	public MasterPane(int x, int y) {
		setMyPane(new Pane());
		setMyRoot(new Group());
		getMyRoot().getChildren().add(myPane);
		myBox = new VBox();
		getMyPane().setLayoutX(x);
		getMyPane().setLayoutY(y);
		add(myBox);
	}

	public void add(Node item) {
		getMyPane().getChildren().add(item);
	}

	public Pane getMyPane() {
		return myPane;
	}
	
	public Group getMyRoot(){
		return myRoot;
	}

	public void setMyPane(Pane myPane) {
		this.myPane = myPane;
	}
	
	public void setMyRoot(Group r){
		this.myRoot = r;
	}
	
}
