package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.SPane;
import parser.CommandParser;
import view.CommandsView;
import view.CoordinateView;
import view.DisplayView;
import view.HistoryPaneView;
import view.TurtleView;
import view.VariableView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import addons.Features;
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
        modelMap = new ModelMap();
        setDisplay(colorMap, imageMap);
        setTurtleCoordsBox(modelMap.getDisplay().getTurtleList());
        setInputPane();
        setHistoryPane();
        setHelpButton();
        setUserCommandPane();
    }

    public void setLang (String language) {
        myLang = language;
        parser = new CommandParser(modelMap);
        parser.addPatterns("resources/languages/" + myLang);
        parser.addPatterns("resources/languages/Syntax");
        setVariablePane();
    }
    
    private void setDisplay(Map<Double, String> colorMap, Map<Double, String> imageMap) {
        DisplayModel displayModel = new DisplayModel(colorMap, imageMap);
        DisplayView displayView = new DisplayView(getRoot());
        displayModel.addObserver(displayView);
        modelMap.setDisplay(displayModel);
        displayModel.notifyObservers();
    }
    
    private void setTurtleCoordsBox (List<TurtleModel> turtles) {
        HBox turtleVars = new HBox();
        turtleVars.setLayoutX(UIConstants.COORDINATE_LOCATION);
        turtleVars.setLayoutY(UIConstants.COORDINATE_LOCATION);
        turtleVars.setMaxSize(UIConstants.RECT_X, UIConstants.BORDER_WIDTH);
        getRoot().getChildren().add(turtleVars);
        CoordinateView cv = new CoordinateView(turtleVars, 0, 0, UIConstants.INITIAL_HEADING);
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
        Button inputButton = featureMaker.makeB("Go",
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

    private void setVariablePane () {
        SPane variables = new SPane(UIConstants.VARIABLE_PANE_X, UIConstants.LOWER_PANE_Y);
        variables.myPane.setMinSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
        variables.myPane.setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
        variables.myPane.setStyle("-fx-background-color: #DAE6F3;");
        variables.myBox.getChildren().add(new Text("Variables"));
        VariableModel varModel = new VariableModel();
        VariableView varView = new VariableView(variables.myBox, inputText,
                                                myLang);
        varModel.addObserver(varView);
        varModel.notifyObservers();
        modelMap.setVariable(varModel);
        getRoot().getChildren().add(variables.myPane);
    }

    private void setUserCommandPane () {
        SPane variables = new SPane(25, 25);
        variables.myPane.setMinSize(360, 475);
        variables.myPane.setMaxSize(360, 475);
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
        myStage.setTitle("Help");
        myStage.setScene(scene);
        myStage.show();
        WebView browser = new WebView();
        browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
        helpRoot.getChildren().add(browser);
        browser.getEngine().load(
                                 WorkSpace.class.getResource("/references/help.html")
                                         .toExternalForm());
    }

}
