package launcher;

import java.awt.Button;
import java.io.File;
import java.util.ResourceBundle;

import constants.UIConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class Window {
	private Stage myStage;
	private Scene myScene;
	private Group myRoot;
	private ResourceBundle myResources;

	public Window() {
		myStage = new Stage();
		myStage.setTitle(UIConstants.TITLE);
		myRoot = new Group();
		//myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + BundleConstants.SCREEN_LANGUAGE);
		setUpScene();
	}



	public abstract void setUpScene();

	protected void close() {
		myStage.close();
	}

	public void begin() {
		myStage.setScene(myScene);
		myStage.show();
	}

	protected Group getGroup() {
		return myRoot;
	}

	protected void setScene(Scene scene) {
		myScene = scene;
	}
	protected Button addB(String name, EventHandler<MouseEvent> event) {
		Button b = new Button(name);
		//b.setOnMouseClicked(event);
		return b;
	}
	
	protected MenuItem addMenuItem(String name, EventHandler<ActionEvent> event, Menu root) {
		MenuItem back = new MenuItem(name);
		back.setOnAction(event);
		root.getItems().add(back);
		return back;
	}
	protected Stage getStage() {
		return myStage;
	}

	protected void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myResources.getString("Error"));
		alert.setContentText(message);
		alert.showAndWait();
	}

	// transitions to a different display screen once a simulation is chosen
//	public void goToDisplay(File file) {
//		try {
//			close();
//		} catch (ErroneousFileException e) {
//			showError(e.getMessage());
//		}
//	}
//
//	protected File getFileFromFileChooser() {
//		FileChooser f = new FileChooser();
//		f.setTitle(myResources.getString("OpenFile"));
//		File selectedFile = f.showOpenDialog(getStage());
//		if (selectedFile == null) {
//			throw new ErroneousFileException(myResources.getString("NoFile"));
//		}
//
//		return selectedFile;
//	}

	protected ResourceBundle getResources() {
		return myResources;
	}
}

