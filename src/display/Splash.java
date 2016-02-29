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

public class Splash extends Screen {
	
	private Features featMaker;
	private ComboBox languageCBox;

	@Override
	public void setUpScene() {
		getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		
		featMaker = new Features();
		
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.GRAY));
		
		setLangBox();
		
		setButton();
		
		setTitle();
		
	}
	
	private void setTitle() {
		Rectangle welRect = new Rectangle(UIConstants.RECT_W, UIConstants.HEIGHT/5);
		welRect.setX(UIConstants.RECT_X);
		welRect.setY(UIConstants.HUNDRED);
		welRect.setArcHeight(UIConstants.ARC);
		welRect.setArcWidth(UIConstants.ARC);
		getRoot().getChildren().add(welRect);
		welRect.getStyleClass().add("welRect");
		
		Text welText = new Text(getResources().getString(UIConstants.TITLE));
		welText.setLayoutX(290);
		welText.setLayoutY(150);
		welText.getStyleClass().add("welText");
		getRoot().getChildren().add(welText);
		
	}

	private void setButton() {
		Button go = featMaker.makeB(getResources().getString(UIConstants.GO), e -> goToWorkSpace((String) languageCBox.getValue()));
		getRoot().getChildren().add(go);
		go.setLayoutX(UIConstants.RECT_W);
		go.setLayoutY(UIConstants.BUTTON_Y);
		go.setPrefSize(UIConstants.RECT_X, UIConstants.BUTTON_H);
	}

	private void goToWorkSpace(String lang) {
		WorkSpace myW = new WorkSpace();
		myW.setLang(lang);
		System.out.println("do i get here?");
		getStage().close();
		myW.begin();
	}

	private void setLangBox(){
		ObservableList<String> options = FXCollections.observableArrayList(
			        getResources().getString(UIConstants.CHIN),
			        getResources().getString(UIConstants.ENG),
			        getResources().getString(UIConstants.FRA),
			        getResources().getString(UIConstants.GER),
			        getResources().getString(UIConstants.ITA),
			        getResources().getString(UIConstants.POR),
			        getResources().getString(UIConstants.RUS),
			        getResources().getString(UIConstants.SPA),
			        getResources().getString(UIConstants.SYN)
			    );
		languageCBox = featMaker.makeCBox(options);
		languageCBox.setLayoutX(UIConstants.RECT_X);
		languageCBox.setLayoutY(UIConstants.BUTTON_Y);
		languageCBox.setValue(getResources().getString(UIConstants.ENG));
		getRoot().getChildren().add(languageCBox);

	}

}
