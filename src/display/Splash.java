package display;

import java.io.File;

import preferences.PrefLoader;
import preferences.SaveState;
import addons.Features;
import constants.UIConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Splash extends Screen {

	private Features featMaker;
	private ComboBox<String> languageCBox;
	private Button go;
	private SaveState myState;

	@Override
	public void setUpScene() {
		getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		featMaker = new Features();
		
		setScene(new Scene(getRoot(), UIConstants.SWIDTH, UIConstants.SHEIGHT));

		setLangBox();

		setButton();

		setTitle();

		loadPreferencesButton();
	}

	private void setTitle() {
		Rectangle welRect = new Rectangle(UIConstants.LOAD_BUTTON_Y, UIConstants.RECT_X);
		welRect.setX(UIConstants.VARIABLE_PANE_X);
		welRect.setY(UIConstants.BUTTON_H);
		welRect.setArcHeight(UIConstants.ARC);
		welRect.setArcWidth(UIConstants.ARC);
		getRoot().getChildren().add(welRect);

		Text welText = new Text(getResources().getString(UIConstants.TITLE));
		welText.setLayoutX(UIConstants.WEL_RECT_X);
		welText.setLayoutY(UIConstants.COLOR_SELECTOR_WIDTH);
		getRoot().getChildren().add(welText);
	}

	private void setButton() {
		go = featMaker.makeB(getResources().getString(UIConstants.GO),
				e -> goToWorkSpace((String) languageCBox.getValue()));
		getRoot().getChildren().add(go);
		go.setDisable(true);
		go.setLayoutX(UIConstants.RECT_X);
		go.setLayoutY(UIConstants.GO_BUTTON_Y);
		go.setPrefSize(UIConstants.VARIABLE_PANE_X, UIConstants.USER_X);
	}

	private void loadPreferencesButton(){
		Button preferences = featMaker.makeB(
			getResources().getString("Load"), e -> loadPrefs());
		preferences.setLayoutX(UIConstants.VARIABLE_PANE_X);
		preferences.setLayoutY(UIConstants.LOAD_BUTTON_Y);
		getRoot().getChildren().add(preferences);
	}

	private void loadPrefs() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(getResources().getString("FileSelect"));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter(
						getResources().getString("Files"),
						getResources().getString("srl")));

		File selectedFile = fileChooser.showOpenDialog(getStage());

		PrefLoader loader = new PrefLoader();
		myState = loader.load(selectedFile);
		go.setDisable(false);
	}

	private void goToWorkSpace(String lang) {
		DemoWSpace myW = new DemoWSpace(myState);
		myW.setLang(lang);
		getStage().close();
		myW.begin();
	}

	private void setLangBox() {
		ObservableList<String> options = FXCollections.observableArrayList(
				getResources().getString(UIConstants.CHIN), getResources()
						.getString(UIConstants.ENG),
				getResources().getString(UIConstants.FRA), getResources()
						.getString(UIConstants.GER),
				getResources().getString(UIConstants.ITA), getResources()
						.getString(UIConstants.POR),
				getResources().getString(UIConstants.RUS), getResources()
						.getString(UIConstants.SPA));
		languageCBox = featMaker.makeCBox(options);
		languageCBox.setLayoutX(UIConstants.LOAD_BUTTON_Y);
		languageCBox.setLayoutY(UIConstants.LOAD_BUTTON_Y);
		languageCBox.setValue(getResources().getString(UIConstants.ENG));
		getRoot().getChildren().add(languageCBox);

	}

}
