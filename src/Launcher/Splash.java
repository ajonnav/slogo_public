package Launcher;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Splash {


	private Group myRoot;
	private Stage s;
	private Scene myScene;
	private TextField textField;
	
	public Splash() {
		// TODO Auto-generated constructor stub
		myRoot = new Group();
		s = new Stage();
		s.setTitle("TESTING");
	}

	public void setUpScene() {
		// TODO Auto-generated method stub
		myScene = new Scene(myRoot, 500,500);
		textInput();
		
	}
	public void textInput(){
		Label label1 = new Label("Test some text:");
		textField = new TextField ();
		HBox hb = new HBox();
		hb.getChildren().addAll(label1, textField);
		hb.setSpacing(10);
		myRoot.getChildren().add(hb);
		hb.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		
	}
	
	public void parser(){
		System.out.println("User has entered a command");
		textField.getText();
	}
	
	private void handleKeyInput(KeyCode code) {
		switch (code) {
		case ENTER:
			parser();
		default:
		}
	}
	
	public void begin() {
		// TODO Auto-generated method stub
		setUpScene();
		s.setScene(myScene);
		s.show();
	}

}
