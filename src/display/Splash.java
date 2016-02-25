package display;


import addons.Features;
import constants.UIConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import slogo_main.Main;

public class Splash extends Screen {
	
	private Features featMaker;

	@Override
	public void setUpScene() {
		getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		
		featMaker = new Features();
		
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.WHITE));
		
		setLangBox();
		
		setButton();
		
		setTitle();
	}
	
	private void setTitle() {
		Rectangle welRect = new Rectangle(600, UIConstants.HEIGHT/5);
		welRect.setX(200);
		welRect.setY(100);
		welRect.setArcHeight(20);
		welRect.setArcWidth(20);
		getRoot().getChildren().add(welRect);
		welRect.getStyleClass().add("welRect");
		
		Text welText = new Text(getResources().getString(UIConstants.TITLE));
		welText.setLayoutX(290);
		welText.setLayoutY(150);
		welText.getStyleClass().add("welText");
		getRoot().getChildren().add(welText);
		
	}

	private void setButton() {
		Button go = featMaker.makeB(getResources().getString(UIConstants.GO), e -> goToMain());
		getRoot().getChildren().add(go);
		go.setLayoutX(600);
		go.setLayoutY(250);
		go.setPrefSize(200, 50);
		
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
		languageCBox.setLayoutX(200);
		languageCBox.setLayoutY(250);
		getRoot().getChildren().add(languageCBox);

	}

}
