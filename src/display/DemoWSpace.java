package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryModel;
import model.IHistoryModel;
import model.IVariableModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.SPane;
import parser.CommandParser;
import preferences.PrefLoader;
import preferences.PrefWriter;
import preferences.saveState;
import view.CommandsView;
import view.CoordinateView;
import view.DisplayView;
import view.HistoryPaneView;
import view.TurtleIDView;
import view.VariableView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import command.Command;
import addons.Features;
import addons.MenuMaker;
import constants.UIConstants;

public class DemoWSpace extends Screen {

	private Features featureMaker;
	private CommandParser parser;
	private TextArea inputText;
	private String myLang;
	private HistoryPaneView hpv;
	private ModelMap modelMap;

	private saveState myState;

	private SPane userHistory;
	private SPane userMethods;
	private SPane userVariables;
	private SPane userTurtles;
	private SPane userInput;

	private Boolean down = false;

	public DemoWSpace(saveState myS) {
		myState = myS;
		modelMap = new ModelMap();
		setDisplay();
		setInputPane();
		setHistoryPane();
		setUserCommandPane();
		setBar();
		setTurtlePane(modelMap.getDisplay().getFrame(modelMap.getDisplay().getNumFrames()-1));
	}

	@Override
	public void setUpScene() {
		//getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + "demo.css");
		featureMaker = new Features();
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.LIGHTBLUE));
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
		MenuMaker menuMaker = new MenuMaker();
		MenuBar myMenu = menuMaker.getMenu();
		Menu fileMenu = menuMaker.addMenu(getResources().getString("FileCommand"));
		menuMaker.addMenuItem(getResources().getString("HelpTitle"), e -> openHelpPage(), fileMenu);
		menuMaker.addMenuItem(getResources().getString("NewCommand"), e -> switchWS(), fileMenu);
		menuMaker.addMenuItem(getResources().getString("SaveCommand"), e -> setPrefs(), fileMenu);
		Menu toggleMenu = menuMaker.addMenu(getResources().getString("Toggle"));
		menuMaker.addMenuItem(getResources().getString("cToggle"), e -> noVars(userVariables), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("hToggle"), e -> noVars(userHistory), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("tToggle"), e -> noVars(userTurtles), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("vToggle"), e -> noVars(userMethods), toggleMenu);
		menuMaker.addMenuItem(getResources().getString("uToggle"), e -> noVars(userInput), toggleMenu);
		Menu editMenu = menuMaker.addMenu(getResources().getString("EditCommand"));
		menuMaker.addMenuItem(getResources().getString("penStatus"), e -> setPenUpDown(), editMenu);
		getRoot().getChildren().add(myMenu);
	}

	public void setLang(String language) {
		this.myLang = language;
		parser = new CommandParser(modelMap);
		parser.addPatterns(UIConstants.RSRC_LANG + myLang);
		parser.addPatterns(UIConstants.RSRC_LANG + UIConstants.SYNTAX);
		setVariablePane();
	}

	/*
	 * reads the input and passes it to the parser to interpret
	 */
	private void readInput(CommandParser parser, TextArea input) {
		parser.parseText(input.getText());
		input.clear();
	}

	/*
	 * Initializes the turtle display's front end and back end relationship
	 */
	private void setDisplay() {
		DisplayModel displayModel = new DisplayModel(myState.getColorMap(), myState.getImages());
		DisplayView displayView = new DisplayView(getRoot());
		displayModel.addObserver(displayView);
		modelMap.setDisplay(displayModel);
		modelMap.getDisplay().setBackgroundColorIndex(myState.getBackColorIndex());
		displayModel.setToAnimate(true);
		displayModel.notifyObservers();
	}

	/*
	 * Hides/shows a user view from the Scene
	 */
	private void noVars(SPane variables) {
		if (getRoot().getChildren().contains(variables.getMyPane())) {
			getRoot().getChildren().remove(variables.getMyPane());
		} else {
			getRoot().getChildren().add(variables.getMyPane());
		}
	}

	/*
	 * Sets the Pane for the user input text area
	 */
	private void setInputPane() {
		userInput = new SPane(UIConstants.RECT_W, UIConstants.LOWER_PANE_Y);
		inputText = new TextArea();
		inputText.setMinSize(UIConstants.LOWER_PANE_WIDTH, 120);
		inputText.setMaxSize(UIConstants.LOWER_PANE_WIDTH, 120);
		userInput.getMyBox().getChildren().add(inputText);
		Button inputButton = featureMaker.makeB(getResources().getString("GoCommand"),
				event -> readInput(parser, inputText));
		inputButton.setMinWidth(UIConstants.LOWER_PANE_WIDTH);
		userInput.getMyBox().getChildren().add(inputButton);
		getRoot().getChildren().add(userInput.getMyPane());
	}

	/*
	 * Sets the Pane for the user history
	 */
	private void setHistoryPane() {
		userHistory = new SPane(UIConstants.HISTORY_PANE_X, UIConstants.BORDER_WIDTH);
		userHistory.getMyPane().setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
		userHistory.getMyPane().setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
		getRoot().getChildren().add(userHistory.getMyPane());
		HistoryModel hpm = new HistoryModel();
		initializeHistory(hpm, myState.getHistory());
		hpv = new HistoryPaneView(userHistory.getMyBox(), inputText);
		hpm.addObserver(hpv);
		hpm.notifyObservers();
		modelMap.setHistory(hpm);
		hpm.updateView();
	}
	
	private void initializeHistory(HistoryModel hpm, List<String> history){
		for(String n: myState.getHistory()){
			hpm.addToHistory(n);
		}
	}
	
	
	/*
	 * Sets the Pane for the current user-defined variables in the environment
	 */
	private void setVariablePane() {
		userVariables = new SPane(25, UIConstants.LOWER_PANE_Y);
		userVariables.getMyPane().setMinSize(250, UIConstants.LOWER_PANE_HEIGHT);
		userVariables.getMyPane().setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);

		VariableModel vpm = new VariableModel();
		initializeVariables(vpm, myState.getVariables());
		VariableView varView = new VariableView(userVariables.getMyBox(), new VBox(), inputText, getMyLang());
		vpm.addObserver(varView);
		vpm.notifyObservers();
		modelMap.setVariable(vpm);
		getRoot().getChildren().add(userVariables.getMyPane());
		vpm.updateView();
	}
	
	private void initializeVariables(VariableModel vpm, Map<String, Double> vars){
		for (String n: vars.keySet()){
			vpm.setVariable(n, vars.get(n));
		}
	}
	
	/*
	 * Sets the Pane for the current user-defined methods in the environment
	 */
	private void setUserCommandPane() {
		userMethods = new SPane(UIConstants.BORDER_WIDTH, UIConstants.METHODS_Y);
		userMethods.getMyPane().setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
		userMethods.getMyPane().setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
		userMethods.getMyBox().getChildren().add(new Text(getResources().getString("UCommands")));

		CommandsModel cpm = new CommandsModel();
		initializeCommands(cpm, myState.getCommands(), myState.getCommandVars());
		CommandsView commandView = new CommandsView(userMethods.getMyBox(), inputText);
		cpm.addObserver(commandView);
		cpm.notifyObservers();
		modelMap.setCommands(cpm);
		getRoot().getChildren().add(userMethods.getMyPane());
		cpm.updateView();
	}

	private void initializeCommands(CommandsModel cpm, Map<String, List<Command>> commands, Map<String, List<Command>> commVars){
		for (String n: commands.keySet()){
			cpm.setCommands(n, commands.get(n));
			cpm.setVariables(n, commVars.get(n));
		}
	}
	
	/*
	 * Sets the Pane for the current status of the various turtles on the
	 * display
	 */
	private void setTurtlePane(List<TurtleModel> tm) {
		// use display model
		userTurtles = new SPane(UIConstants.TURTLE_PANE_X, UIConstants.LOWER_PANE_Y);
		userTurtles.getMyPane().setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
		userTurtles.getMyPane().setMaxSize(400, UIConstants.LOWER_PANE_HEIGHT);
		userTurtles.getMyBox().getChildren().add(new Text(getResources().getString("Tur")));
		getRoot().getChildren().add(userTurtles.getMyPane());
		TurtleIDView cv = new TurtleIDView(inputText, userTurtles.getMyBox());
		for (int i = 0; i < tm.size(); i++) {
			tm.get(i).addObserver(cv);
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
		PrefWriter setter = new PrefWriter(modelMap, newTitle, myLang);
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
		if (down == true) {
			parser.parseText("pu");
			down = false;
		} else {
			parser.parseText("pd");
			down = true;
		}

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
