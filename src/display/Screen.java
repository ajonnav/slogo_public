package display;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Screen {

	private Scene myScene;
	private Group myRoot;
	private Stage myStage;
	
	
	public Screen() {
		myStage = new Stage();
		myStage.setTitle("WELCOME TO SLOGO");
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
}
