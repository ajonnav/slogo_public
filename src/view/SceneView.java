package view;

import java.util.Observable;

import javafx.scene.Group;
import javafx.scene.Scene;

public class SceneView implements IView{

	private Scene myScene;
	
	public SceneView(Group root, int w, int h) {
		myScene = new Scene(root, w, h);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof SceneModel){
			SceneModel sModel = (SceneModel) o;
			myScene.setColor(sModel.getColor())
		}
		
	}

}
