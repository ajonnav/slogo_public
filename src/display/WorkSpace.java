package display;

import model.CommandsModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.SPane;
import parser.CommandParser;
import view.CommandsView;
import view.CoordinateView;
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
import java.util.HashMap;
import java.util.Map;
import addons.Features;
import constants.UIConstants;


public class WorkSpace extends Screen {

    private Features featureMaker;
    private CommandParser parser;
    private TextArea inputText;
    private String myLang;
    private TurtleView turtleView;
    private HistoryPaneView hpv;
    private ModelMap modelMap;

    @Override
    public void setUpScene () {
        getRoot().getStylesheets().add(
                                       UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
        featureMaker = new Features();
        setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT,
                           Color.GRAY));
        setTurtle();
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

    private void setTurtle () {
        double turtleInitialHeading = UIConstants.INITIAL_HEADING;
        String turtleImage = "turtle.png";
        Map<Double, String> colorMap = new HashMap<Double, String>();
        colorMap.put(1.0, "#849775");
        colorMap.put(2.0, "#1518b4");
        colorMap.put(3.0, "#5df45d");
        colorMap.put(4.0, "#7182a7");
        colorMap.put(5.0, "#b73547");
        Map<Double, String> imageMap = new HashMap<Double, String>();
        imageMap.put(1.0, "black.png");
        imageMap.put(2.0, "blue.png");
        imageMap.put(3.0, "green.png");
        imageMap.put(4.0, "red.png");
        imageMap.put(5.0, "turtle.png");
        TurtleModel turtleModel = new TurtleModel(0, 0, turtleInitialHeading, colorMap, imageMap);
        turtleView = new TurtleView(turtleImage, getRoot(), Color.BLACK, turtleModel);
        turtleModel.addObserver(turtleView);
        turtleModel.notifyObservers();
        turtleModel.penDown();
        modelMap = new ModelMap();
        modelMap.setTurtle(turtleModel);
        setTurtleCoordsBox(turtleModel);
    }
    
    private void setTurtleCoordsBox (TurtleModel turtleModel) {
        HBox turtleVars = new HBox();
        turtleVars.setLayoutX(UIConstants.COORDINATE_LOCATION);
        turtleVars.setLayoutY(UIConstants.COORDINATE_LOCATION);
        turtleVars.setMaxSize(UIConstants.RECT_X, UIConstants.BORDER_WIDTH);
        getRoot().getChildren().add(turtleVars);
        CoordinateView cv = new CoordinateView(turtleVars, turtleModel);
        turtleModel.addObserver(cv);
        turtleModel.notifyObservers();
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
