package view;

import java.util.Observer;

import javafx.scene.Group;
import javafx.scene.layout.VBox;

public abstract class View implements Observer{

	public VBox myBox;
	public Group myRoot;
	
	public View() {
		setMyBox(new VBox());
		setMyRoot(new Group());
		getMyRoot().getChildren().add(getMyBox());
		}
	
	public VBox getMyBox(){
		return myBox;
	}
	
	public void setMyBox(VBox vb){
		myBox = vb;
	}
	
	public Group getMyRoot(){
		return myRoot;
	}
	
	public void setMyRoot(Group r){
		myRoot = r;
	}

}
