package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.SPane;
import parser.CommandParser;
import preferences.PrefLoader;
import preferences.PrefSetter;
import preferences.PrefWriter;
import view.CommandsView;
import view.CoordinateView;
import view.DisplayView;
import view.HistoryPaneView;
import view.TurtleIDView;
import view.TurtleView;
import view.VariableView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
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

    @Override
    public void setUpScene () {
        getRoot().getStylesheets().add(
                                       UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
        featureMaker = new Features();
        setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT,
                           Color.GRAY));
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
        modelMap = new ModelMap(colorMap, imageMap);
        setDisplay();
        
        setInputPane();
        setTurtle();
        
        setHistoryPane();
        setUserCommandPane();

        setBar();

        setPenUpDownButton();

    }
    
    private void setTurtlePane(List<TurtleModel> tm) {
        SPane turtleID = new SPane(UIConstants.TURTLE_PANE_X, UIConstants.LOWER_PANE_Y);
        turtleID.myPane.setMinSize(UIConstants.TURTLE_MIN_W, UIConstants.LOWER_PANE_HEIGHT);
        turtleID.myPane.setMaxSize(400, UIConstants.LOWER_PANE_HEIGHT);
        turtleID.myBox.getChildren().add(new Text(getResources().getString("Tur")));
        getRoot().getChildren().add(turtleID.myPane);
        TurtleIDView cv = new TurtleIDView(inputText, turtleID.myBox);
        for(int i = 0; i < tm.size(); i++) {
            tm.get(i).addObserver(cv);
            tm.get(i).notifyObservers();
        }
		
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
    	MenuItem helpItem = menuMaker.addMenuItem(getResources().getString("HelpTitle"), e -> openHelpPage(), fileMenu);
    	MenuItem newItem = menuMaker.addMenuItem(getResources().getString("NewCommand"), e -> switchWS(), fileMenu);
    	getRoot().getChildren().add(myMenu);
    }
    
    public void setLang (String language) {
        setMyLang(language);
        parser = new CommandParser(modelMap);

        parser.addPatterns(UIConstants.RSRC_LANG + myLang);
        parser.addPatterns(UIConstants.RSRC_LANG + UIConstants.SYNTAX);

        parser.addPatterns("resources/languages/" + getMyLang());

        setVariablePane();
        setPreferencesSaveButton();
    }
    
    private void setDisplay() {
        DisplayModel displayModel = new DisplayModel(colorMap);
        DisplayView displayView = new DisplayView(getRoot());
        displayModel.addObserver(displayView);
        modelMap.setDisplay(displayModel);
        displayModel.notifyObservers();
    }
    
    private void setTurtle () {
        String turtleImage = "turtle.png";
        List<TurtleModel> turtles = new ArrayList<TurtleModel>();
        List<TurtleView> turtleViews = new ArrayList<TurtleView>();
        for(int i = 0; i < 3; i++) {
            TurtleModel turtleModel = new TurtleModel(0, 0, UIConstants.INITIAL_HEADING, colorMap, imageMap); 
            TurtleView turtleView = new TurtleView(turtleImage, getRoot());
            turtleModel.addObserver(turtleView);
            turtleModel.notifyObservers();
            turtleModel.penDown();
            turtles.add(turtleModel);
            turtleViews.add(turtleView);
        }
        modelMap.setTurtles(turtles);
        modelMap.setTurtleViews(turtleViews);
        setTurtleCoordsBox(turtles);
        setTurtlePane(turtles);
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

    private void setInputPane () {
        HBox commandLine = new HBox();
        inputText = new TextArea();
        inputText.setMinSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
        inputText.setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
        commandLine.getChildren().add(inputText);
        Button inputButton = featureMaker.makeB(getResources().getString("GoCommand"),
                                                event -> readInput(parser, inputText));
        commandLine.getChildren().add(inputButton);
        commandLine.setLayoutX(UIConstants.RECT_W);
        commandLine.setLayoutY(UIConstants.LOWER_PANE_Y);
        getRoot().getChildren().add(commandLine);
    }
    
    private void readInput (CommandParser parser, TextArea input) {
        parser.parseText(input.getText());
        input.clear();
    }

    private void setHistoryPane () {
        SPane history = new SPane(UIConstants.HISTORY_PANE_X, UIConstants.BORDER_WIDTH);
        history.myPane.setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        history.myPane.setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        getRoot().getChildren().addAll(history.myRoot);
        HistoryPaneModel hpm = new HistoryPaneModel();
        hpv = new HistoryPaneView(history.myBox, inputText);
        hpm.addObserver(hpv);
        hpm.notifyObservers();
        modelMap.setHistory(hpm);
    }
    private void noVars(SPane variables){
    	if(getRoot().getChildren().contains(variables.myPane)){
    		getRoot().getChildren().remove(variables.myPane);
    	}
    	else{
    		getRoot().getChildren().add(variables.myPane);
    	}
    }
    private void setVariablePane () {
    	SPane variables = new SPane(25, UIConstants.LOWER_PANE_Y);
    	Button disappear = featureMaker.makeB("GTFO", e -> noVars(variables));
    	disappear.setLayoutX(40);
    	disappear.setLayoutY(10);
    	getRoot().getChildren().add(disappear);
        //SPane variables = new SPane(25, UIConstants.LOWER_PANE_Y);
        variables.myPane.setMinSize(250, UIConstants.LOWER_PANE_HEIGHT);
        variables.myPane.setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);

        variables.myBox.getChildren().add(new Text(getResources().getString("Var")));

        variables.myPane.setStyle("-fx-background-color: #DAE6F3;");

        VariableModel varModel = new VariableModel();
        VariableView varView = new VariableView(variables.myBox, new VBox(),inputText,
                                                getMyLang());
        varModel.addObserver(varView);
        varModel.notifyObservers();
        modelMap.setVariable(varModel);
        getRoot().getChildren().add(variables.myPane);
    }

    private void setUserCommandPane () {

        SPane variables = new SPane(25, 35);
        variables.myPane.setMinSize(360, 465);
        variables.myPane.setMaxSize(360, 475);
        variables.myBox.getChildren().add(new Text(getResources().getString("UCommands")));

//        SPane variables = new SPane(UIConstants.BORDER_WIDTH, UIConstants.BORDER_WIDTH);
//        variables.myPane.setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
//        variables.myPane.setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
        variables.myBox.getChildren().add(new Text("User Commands"));

        CommandsModel varModel = new CommandsModel();
        CommandsView varView = new CommandsView(variables.myBox, inputText);
        varModel.addObserver(varView);
        varModel.notifyObservers();
        modelMap.setCommands(varModel);
        getRoot().getChildren().add(variables.myPane);
    }

    
    private void setHelpButton () {
        Button help = featureMaker.makeB("Help", event -> openHelpPage());
        getRoot().getChildren().add(help);
        help.setLayoutX(UIConstants.ZERO);
        help.setLayoutY(UIConstants.ZERO);
        help.setMaxSize(UIConstants.BUTTON_H, UIConstants.BORDER_WIDTH);
    }
    
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
    
    private void setPreferencesSaveButton(){
        Button save = featureMaker.makeB("Set State", event -> setPrefs());
        getRoot().getChildren().add(save);
        save.setLayoutX(UIConstants.ZERO);
        save.setLayoutY(UIConstants.HEIGHT-25);
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
    
    private void setPenUpDownButton(){
    	Button penUD = featureMaker.makeB("Pen Up/Down", event -> setPenUpDown());
    	penUD.setLayoutX(150);
    	penUD.setLayoutY(UIConstants.HEIGHT-25);
    	getRoot().getChildren().add(penUD);
    }
    
    private void setPenUpDown(){
    	
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

	public void setMyLang(String myLang) {
		this.myLang = myLang;
	}
}
