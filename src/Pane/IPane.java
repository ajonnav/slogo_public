package Pane;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class IPane {

	public Pane myPane;
	public Group myRoot;
	
	public IPane() {
		myPane = new Pane();
		myRoot = new Group();
		myRoot.getChildren().add(myPane);
		System.out.println("testing123");
		myPane.setStyle("-fx-padding:20;-fx-background-color:dodgerblue;");
	}
	
	public Pane getPane(){
		return myPane;
	}
	
	public void add(Node item){
		myPane.getChildren().add(item);
	}
	
	

}
