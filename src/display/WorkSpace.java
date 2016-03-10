package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.SPane;
import parser.CommandParser;
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
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import addons.Features;
import addons.MenuMaker;
import constants.UIConstants;


public class WorkSpace extends Screen {

    private Features featureMaker;
    private CommandParser parser;
    private TextArea inputText;
    private String myLang;
    private HistoryPaneView hpv;
    private ModelMap modelMap;
    private Map<Double, String> imageMap;
    private Map<Double, String> colorMap;
    
    private SPane userHistory;
    private SPane userMethods;
    private SPane userVariables;
    private SPane userTurtles;
    private SPane userInput;
    
    private Boolean down = false;
    private saveState myState;

    @Override
    public void setUpScene () {
        //getRoot().getStylesheets().add(
          //                             UIConstants.DEFAULT_RESOURCE + "demo.css");
        featureMaker = new Features();
        setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT,
                           Color.LIGHTBLUE));
        colorMap = new HashMap<Double, String>();
        colorMap.put(1.0, "#849775");
        colorMap.put(2.0, "#1518b4");
        colorMap.put(3.0, "#5df45d");
        colorMap.put(4.0, "#7182a7");
        colorMap.put(5.0, "#b73547");
        colorMap.put(6.0, "#849775");
        colorMap.put(7.0, "#1518b4");
        colorMap.put(8.0, "#5df45d");
        colorMap.put(9.0, "#7182a7");
        colorMap.put(10.0, "#b73547");
        colorMap.put(11.0, "#b73547");
        colorMap.put(12.0, "#b73547");
        imageMap = new HashMap<Double, String>();
        imageMap.put(1.0, "black.png");
        imageMap.put(2.0, "blue.png");
        imageMap.put(3.0, "green.png");
        imageMap.put(4.0, "red.png");
        imageMap.put(5.0, "turtle.png");
        imageMap.put(6.0, "black.png");
        imageMap.put(7.0, "blue.png");
        imageMap.put(8.0, "green.png");
        imageMap.put(9.0, "red.png");
        imageMap.put(10.0, "turtle.png");
        imageMap.put(11.0, "red.png");
        imageMap.put(12.0, "turtle.png");
        modelMap = new ModelMap();
        setDisplay(colorMap, imageMap);
        setTurtleCoordsBox(modelMap.getDisplay().getNextTurtleList());
        setInputPane();
        setTurtlePane(modelMap.getDisplay().getNextTurtleList());
        setHistoryPane();
        setUserCommandPane();
        setBar();
    }
    

	public void switchWS(){
    	WorkSpace ws = new WorkSpace();
    	ws.setLang(myLang);
    	ws.begin();
    }
    
    public void setBar(){
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
    
    public void setLang (String language) {
    	this.myLang = language;
        parser = new CommandParser(modelMap);
        parser.addPatterns(UIConstants.RSRC_LANG + myLang);
        parser.addPatterns(UIConstants.RSRC_LANG + UIConstants.SYNTAX);
        setVariablePane();
    }
    
    private void setDisplay(Map<Double, String> colorMap, Map<Double, String> imageMap) {
        DisplayModel displayModel = new DisplayModel(colorMap, imageMap);
        DisplayView displayView = new DisplayView(getRoot());
        displayModel.addObserver(displayView);
        modelMap.setDisplay(displayModel);
        displayModel.setToAnimate(true);
        displayModel.notifyObservers();
    }
    
    /*
     * Hides/shows a user view from the Scene
     */
    private void noVars(SPane variables){
    	if(getRoot().getChildren().contains(variables.myPane)){
    		getRoot().getChildren().remove(variables.myPane);
    	}
    	else{
    		getRoot().getChildren().add(variables.myPane);
    	}
    }
    
    private void setTurtleCoordsBox (List<TurtleModel> turtles) {
        HBox turtleVars = new HBox();
        turtleVars.setLayoutX(UIConstants.COORDINATE_LOCATION_X);
        turtleVars.setLayoutY(UIConstants.COORDINATE_LOCATION_Y);
        turtleVars.setMaxSize(UIConstants.RECT_X, UIConstants.BORDER_WIDTH);
        getRoot().getChildren().add(turtleVars);
        CoordinateView cv = new CoordinateView(turtleVars, 1, 0, 0, UIConstants.INITIAL_HEADING);
        for(int i = 0; i < turtles.size(); i++) {
            turtles.get(i).addObserver(cv);
            turtles.get(i).notifyObservers();
        }
    }

    /*
     * Sets the Pane for the user input text area
     */
    private void setInputPane () {
    	userInput = new SPane(UIConstants.RECT_W, UIConstants.LOWER_PANE_Y);
        inputText = new TextArea();
        inputText.setMinSize(UIConstants.LOWER_PANE_WIDTH, 120);
        inputText.setMaxSize(UIConstants.LOWER_PANE_WIDTH, 120);
        userInput.myBox.getChildren().add(inputText);
        Button inputButton = featureMaker.makeB(getResources().getString("GoCommand"),
                                                event -> readInput(parser, inputText));
        userInput.myBox.getChildren().add(inputButton);
        getRoot().getChildren().add(userInput.myPane);
    }
    
    private void readInput (CommandParser parser, TextArea input) {
        parser.parseText(input.getText());
        input.clear();
    }

    /*
     * Sets the Pane for the user history
     */
    private void setHistoryPane () {
        userHistory = new SPane(UIConstants.HISTORY_PANE_X, UIConstants.BORDER_WIDTH);
        userHistory.myPane.setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        userHistory.myPane.setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        getRoot().getChildren().add(userHistory.myPane);
        HistoryPaneModel hpm = new HistoryPaneModel();
        hpv = new HistoryPaneView(userHistory.myBox, inputText);
        hpm.addObserver(hpv);
        hpm.notifyObservers();
        modelMap.setHistory(hpm);
    }
    
    /*
     * Sets the Pane for the current user-defined variables in the environment
     */
    private void setVariablePane () {
    	userVariables = new SPane(25, UIConstants.LOWER_PANE_Y);
        userVariables.myPane.setMinSize(250, UIConstants.LOWER_PANE_HEIGHT);
        userVariables.myPane.setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);

        VariableModel varModel = new VariableModel();
        VariableView varView = new VariableView(userVariables.myBox, new VBox(),inputText,
                                                getMyLang());
        varModel.addObserver(varView);
        varModel.notifyObservers();
        modelMap.setVariable(varModel);
        getRoot().getChildren().add(userVariables.myPane);
    }

    /*
     * Sets the Pane for the current user-defined methods in the environment
     */
    private void setUserCommandPane () {
        userMethods = new SPane(UIConstants.BORDER_WIDTH, UIConstants.METHODS_Y);
        userMethods.myPane.setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        userMethods.myPane.setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        userMethods.myBox.getChildren().add(new Text(getResources().getString("UCommands")));

        CommandsModel varModel = new CommandsModel();
        CommandsView varView = new CommandsView(userMethods.myBox, inputText);
        varModel.addObserver(varView);
        varModel.notifyObservers();
        modelMap.setCommands(varModel);
        getRoot().getChildren().add(userMethods.myPane);
    }
    
    /*
     * Sets the Pane for the current status of the various turtles on the display
     */
    private void setTurtlePane(List<TurtleModel> tm) {
    	//use display model
        userTurtles = new SPane(UIConstants.TURTLE_PANE_X, UIConstants.LOWER_PANE_Y);
        userTurtles.myPane.setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
        userTurtles.myPane.setMaxSize(400, UIConstants.LOWER_PANE_HEIGHT);
        userTurtles.myBox.getChildren().add(new Text(getResources().getString("Tur")));
        getRoot().getChildren().add(userTurtles.myPane);
        TurtleIDView cv = new TurtleIDView(inputText, userTurtles.myBox);
        for(int i = 0; i < tm.size(); i++) {
            tm.get(i).addObserver(cv);
            tm.get(i).notifyObservers();
        }
	}
    
    /*
     * Opens an new window with a help page for reference
     */
    private void openHelpPage () {
        Stage myStage = new Stage();
        Group helpRoot = new Group();
        Scene scene = new Scene(helpRoot, UIConstants.WIDTH, UIConstants.HEIGHT);
        myStage.setTitle(getResources().getString("HelpTitle"));
        myStage.setScene(scene);
        myStage.show();
        WebView browser = new WebView();
        browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
        helpRoot.getChildren().add(browser);
        browser.getEngine().load(
                                 WorkSpace.class.getResource("/references/help.html")
                                         .toExternalForm());
    }
    
    private void setPrefs(){
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
    
    private void setPenUpDown(){
    	if(down == true){
    		parser.parseText("pu");
    		down = false;
    	}
    	else{
    		parser.parseText("pd");
    		down = true;
    	}
    		
    }
    
    private void setPenThicknessInputField(){
    	
    }
    
    private void setPenThickness(){
    	
    }
    
    private void setPenStyleBox(){
    	
    }
    
    private void setPenStyle(){
    	
    }

	public String getMyLang() {
		return myLang;
	}
}
