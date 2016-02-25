package display;


import addons.Features;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import slogo_main.Main;

public class Splash extends Screen {
	
	private Features featMaker;

	@Override
	public void setUpScene() {
		getRoot().getStylesheets().add("References/splash.css");
		
		featMaker = new Features();
		
		setScene(new Scene(getRoot(), 1000, 500, Color.WHITE));
		
		setLangBox();
		
		setButton();
	}
	
	private void setButton() {
		Button go = featMaker.makeB("GO", e -> goToMain());
		getRoot().getChildren().add(go);
		go.setLayoutX(500);
		go.setLayoutY(250);
		go.setPrefSize(100, 50);
		
	}

	private void goToMain() {
		Main newScreen = new Main();
		getStage().close();
		newScreen.start(getStage());
	}

	private void setLangBox(){
		ObservableList<String> options = FXCollections.observableArrayList(
			        "Chinese",
			        "English",
			        "French",
			        "German",
			        "Italian",
			        "Portuguese",
			        "Russian",
			        "Spanish",
			        "Syntax"
			    );
		ComboBox languageCBox = featMaker.makeCBox(options);
		getRoot().getChildren().add(languageCBox);

	}
	
	
	private void addBoxElem(){
		
	}
}
