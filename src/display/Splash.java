package display;

import java.util.ResourceBundle;
import java.io.File;
import preferences.PrefLoader;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Splash extends Screen {

	private Features featMaker;
	private ComboBox<String> languageCBox;
	private Button go;

	@Override
	public void setUpScene() {
		getRoot().getStylesheets().add(
				UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);

		featMaker = new Features();

		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT,
				Color.GRAY));

		setLangBox();

		setButton();

		setTitle();

		loadPreferencesButton();
	}

	private void setTitle() {
		Rectangle welRect = new Rectangle(500, 200);
		welRect.setX(400);
		welRect.setY(200);
		welRect.setArcHeight(UIConstants.ARC);
		welRect.setArcWidth(UIConstants.ARC);
		getRoot().getChildren().add(welRect);
		welRect.getStyleClass().add(
				getResources().getString("welRect"));

		Text welText = new Text(getResources().getString(UIConstants.TITLE));
		welText.setLayoutX(435);
		welText.setLayoutY(300);
		welText.getStyleClass().add(
				getResources().getString("welText"));
		getRoot().getChildren().add(welText);
	}

	private void setButton() {
		go = featMaker.makeB(getResources().getString(UIConstants.GO),
				e -> goToWorkSpace((String) languageCBox.getValue()));
		getRoot().getChildren().add(go);
		go.setDisable(true);
		go.setLayoutX(600);
		go.setLayoutY(425);
		go.setPrefSize(100, 50);
	}

	private void loadPreferencesButton() {
		Button preferences = featMaker.makeB(
				getResources().getString("Load"), e -> loadPrefs());
		preferences.setLayoutX(50);
		preferences.setLayoutY(50);
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
		loader.load(selectedFile);
		go.setDisable(false);
	}

	private void goToWorkSpace(String lang) {
		WorkSpace myW = new WorkSpace();

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
		languageCBox.setLayoutX(592);
		languageCBox.setLayoutY(400);
		languageCBox.setValue(getResources().getString(UIConstants.ENG));
		getRoot().getChildren().add(languageCBox);

	}

}
