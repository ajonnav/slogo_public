package view;

import java.util.Observer;
import java.util.ResourceBundle;
import constants.ResourceConstants;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class View implements Observer{

	public VBox myBox;
	public Group myRoot;
	public String myName;
	public ResourceBundle myBundle;
	
	public View() {
		setMyBox(new VBox());
		setMyRoot(new Group());
		getMyRoot().getChildren().add(getMyBox());
		myBundle = ResourceBundle.getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.EXTRAS);
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

	public void setMyName(String s){
		myName = s;
	}
	
	public String getMyName(){
		return myName;
	}
	
	public ResourceBundle getResources(){
		return myBundle;
	}
	
	public void refresh(){
		getMyBox().getChildren().clear();
		getMyBox().getChildren().add(new Text(getMyName()));
	}
}
