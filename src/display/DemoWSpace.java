package display;

import model.CommandsModel;

import model.DisplayModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import parser.CommandParser;
import preferences.PrefLoader;
import preferences.PrefWriter;
import preferences.saveState;
import view.CommandsView;
import view.CoordinateView;
import view.DisplayView;
import view.HistoryPaneView;
import view.InputView;
import view.TurtleIDView;
import view.VariableView;
import view.View;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import addons.MenuMaker;
import addons.WMenu;
import constants.UIConstants;

public class DemoWSpace extends Screen {

	private CommandParser parser;
	private TextArea inputText;
	private String myLang;
	private HistoryPaneView hpv;
	private ModelMap modelMap;
	private saveState myState;

	
	private VariableView varView;
	private TurtleIDView turtleView;
	private InputView myIV;
	private CommandsView commandView;

	public DemoWSpace(saveState myS) {
		myState = myS;
		modelMap = new ModelMap();
	}

	@Override
	public void setUpScene() {
		//getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + "demo.css");
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.LIGHTBLUE));
		inputText = new TextArea();
	  }


	public void switchWS() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(getResources().getString("FileSelect"));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter(
						getResources().getString("Files"),
						getResources().getString("srl")));

		File selectedFile = fileChooser.showOpenDialog(getStage());

		PrefLoader loader = new PrefLoader();
		saveState myState = loader.load(selectedFile);
		
		DemoWSpace myW = new DemoWSpace(myState);
		myW.setLang(myLang);
		myW.begin();
	}

	public void setBar() {
		WMenu workspaceMenu = new WMenu();
		
		MenuMaker menuMaker = new MenuMaker();
		MenuBar myMenu = menuMaker.getMenu();
		Menu fileMenu = menuMaker.addMenu(getResources().getString("FileCommand"));
		menuMaker.addMenuItem(getResources().getString("HelpTitle"), e -> openHelpPage(), fileMenu);
		menuMaker.addMenuItem(getResources().getString("NewCommand"), e -> switchWS(), fileMenu);
		menuMaker.addMenuItem(getResources().getString("SaveCommand"), e -> setPrefs(), fileMenu);
		Menu toggleMenu = menuMaker.addMenu(getResources().getString("Toggle"));
		menuMaker.addMenuItem(getResources().getString("cToggle"), e -> noVars(varView), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("hToggle"), e -> noVars(hpv), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("tToggle"), e -> noVars(turtleView), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("vToggle"), e -> noVars(commandView), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("uToggle"), e -> noVars(myIV), toggleMenu);
		Menu editMenu = menuMaker.addMenu(getResources().getString("EditCommand"));
		menuMaker.addMenuItem(getResources().getString("penStatus"), e -> setPenUpDown(), editMenu);
		getRoot().getChildren().add(workspaceMenu.getMyMenu());
	}

	public void setLang(String language) {
		this.myLang = language;
		parser = new CommandParser(modelMap);
		parser.addPatterns(UIConstants.RSRC_LANG + myLang);
		parser.addPatterns(UIConstants.RSRC_LANG + UIConstants.SYNTAX);
		setVariablePane();

		setInputPane();
		setDisplay();
		setHistoryPane();
		setUserCommandPane();
		setBar();
		setTurtlePane(modelMap.getDisplay().getFrame(modelMap.getDisplay().getNumFrames()-1));
		setTurtleCoordsBox(modelMap.getDisplay().getFrame(modelMap.getDisplay().getNumFrames()-1));
	}


	/*
	 * Initializes the turtle display's front end and back end relationship
	 */
	private void setDisplay() {
		DisplayModel displayModel = new DisplayModel(myState.colorMap, myState.images);
		DisplayView displayView = new DisplayView(getRoot());
		displayModel.addObserver(displayView);
		modelMap.setDisplay(displayModel);
		modelMap.getDisplay().setBackgroundColorIndex(myState.backColorIndex);
		displayModel.notifyObservers();
	}

	/*
	 * Hides/shows a user view from the Scene
	 */
	private void noVars(View variables) {
		if (getRoot().getChildren().contains(variables.getMyRoot())) {
			getRoot().getChildren().remove(variables.getMyRoot());
		} else {
			getRoot().getChildren().add(variables.getMyRoot());
		}
	}

	public void establishRelationship(Observable myModel, View myView){
		myModel.addObserver(myView);
		myModel.notifyObservers();
		getRoot().getChildren().add(myView.getMyRoot());
	}

	private void setTurtleCoordsBox(List<TurtleModel> turtles) {
		CoordinateView cv = new CoordinateView(1, 0, 0, UIConstants.INITIAL_HEADING);
		getRoot().getChildren().add(cv.getMyHBox());
		for (int i = 0; i < turtles.size(); i++) {
			turtles.get(i).addObserver(cv);
			turtles.get(i).notifyObservers();
		}
	}

	/*
	 * Sets the Pane for the user input text area
	 */
	private void setInputPane() {
		myIV  = new InputView(parser, inputText);
		getRoot().getChildren().add(myIV.getMyRoot());
	}

	/*
	 * Sets the Pane for the user history
	 */
	private void setHistoryPane() {
		HistoryPaneModel hpm = new HistoryPaneModel();
		hpv = new HistoryPaneView(inputText);
		modelMap.setHistory(hpm);
		establishRelationship(hpm, hpv);
	}

	/*
	 * Sets the Pane for the current user-defined variables in the environment
	 */
	private void setVariablePane() {
		VariableModel varModel = new VariableModel();
		varView = new VariableView(inputText, getMyLang());
		modelMap.setVariable(varModel);
		establishRelationship(varModel, varView);
	}

	/*
	 * Sets the Pane for the current user-defined methods in the environment
	 */
	private void setUserCommandPane() {
		CommandsModel commandModel = new CommandsModel();
		commandView = new CommandsView(inputText);
		modelMap.setCommands(commandModel);
		establishRelationship(commandModel, commandView);
	}

	/*
	 * Sets the Pane for the current status of the various turtles on the
	 * display
	 */
	private void setTurtlePane(List<TurtleModel> tm) {
		turtleView = new TurtleIDView(inputText);
		getRoot().getChildren().add(turtleView.getMyRoot());
		for (int i = 0; i < tm.size(); i++) {
			tm.get(i).addObserver(turtleView);
			tm.get(i).notifyObservers();
		}
	}

	/*
	 * Opens an new window with a help page for reference
	 */
	private void openHelpPage() {
		Stage myStage = new Stage();
		Group helpRoot = new Group();
		Scene scene = new Scene(helpRoot, UIConstants.WIDTH, UIConstants.HEIGHT);
		myStage.setTitle(getResources().getString("HelpTitle"));
		myStage.setScene(scene);
		myStage.show();
		WebView browser = new WebView();
		browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
		helpRoot.getChildren().add(browser);
		browser.getEngine().load(WorkSpace.class.getResource("/references/help.html").toExternalForm());
	}

	private void setPrefs() {
		String newTitle = newTextInput("File Name", "Save File", "Enter New File Name", "File:");
		PrefWriter setter = new PrefWriter(modelMap, "image.png", myLang);
		setter.writeToSrl();
	}

	private String newTextInput(String holder, String title, String header, String prompt) {
		TextInputDialog dialog = new TextInputDialog(holder);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(prompt);
		Optional<String> input = dialog.showAndWait();
		if (input.isPresent()) {
			String newTitle = input.get();
			return newTitle;
		} else {
			return null;
		}
	}

	private void setPenUpDown() {

	}

	private void setPenThicknessInputField() {

	}

	private void setPenThickness() {

	}

	private void setPenStyleBox() {

	}

	private void setPenStyle() {

	}

	public String getMyLang() {
		return myLang;
	}
}
