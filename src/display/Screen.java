package display;

import java.util.ResourceBundle;

import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Screen {

	private Scene myScene;
	private Group myRoot;
	private Stage myStage;
	private ResourceBundle myBundle;
	
	
	public Screen() {
		myBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.SCREEN_LANG);
		
		myStage = new Stage();
		myStage.setTitle(myBundle.getString(UIConstants.TITLE));
		myRoot = new Group();
		setUpScene();
	}
	
	public abstract void setUpScene();
	
	protected void setScene(Scene s){
		myScene = s;
	}
	
	public void begin(){
		myStage.setScene(myScene);
		myStage.show();
	}
	
	protected Group getRoot(){
		return myRoot;
	}
	
	protected Scene getScene(){
		return myScene;
	}
	protected Stage getStage(){
		return myStage;
	}
	protected ResourceBundle getResources(){
		return myBundle;
	}
}
