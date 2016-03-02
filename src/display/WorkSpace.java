package display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.CommandsModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.IPane;
import pane.MasterPane;
import pane.SPane;
import parser.CommandParser;
import view.CoordinateView;
import view.HistoryPaneView;
import view.TurtleView;
import view.VariableView;
import view.HistoryPaneView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import addons.Features;
import constants.UIConstants;

public class WorkSpace extends Screen{

	private Features featureMaker;
	private CommandParser parser;
	private TextArea inputText;
	private String myLang;
	
	private Canvas canvas;
	private TurtleView turtleView;
	private TurtleModel turtleModel;
	private HistoryPaneView hpv;
	private ModelMap modelMap;
	
	public void setLang(String language){
		myLang = language;
	}
	
//	public WorkSpace(String l){
//		myLang = l;
//	}
	
	@Override
	public void setUpScene() {
		
		getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		
		featureMaker = new Features();
		
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.GRAY));
		
		setCanvas();
		
		setTurtle();
		
		setCommandPane();
		
		setHistoryPane();
		
		setVariablePane();
		
		setHelpButton();
		
		setBackgroundColor();

		setColorPicker();
		
		setPenPicker();
		
		setTurtleCoordsBox();
	}
	
	private void setColorPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setValue(Color.CORAL);
		
		
		cp.setOnAction(event -> sceneChange(cp.getValue()));
		cp.setLayoutX(250);
		cp.setLayoutY(0);
		getRoot().getChildren().add(cp);
	}
	
	private void setPenPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setValue(Color.CORAL);
		
		
		cp.setOnAction(event -> penChange(cp.getValue()));
		cp.setLayoutX(400);
		cp.setLayoutY(0);
		getRoot().getChildren().add(cp);
		
	}

	private void penChange(Color value) {
		// TODO Auto-generated method stub
		turtleView.setColor(value);
	}
	
	private void sceneChange(Color c) {
		// TODO Auto-generated method stub
		//turtleView.getGC().setFill(c);
		//canvas.getGraphicsContext2D() = new
//		canvas = new Canvas();
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//		gc.setFill(c);
//		System.out.println(gc.getFill());
//		canvas.getGraphicsContext2D().setFill(c);
//		getRoot().getChildren().add(canvas);
		getScene().setFill(c);
	}

	private void setCanvas(){
		canvas = featureMaker.makeCanvas(UIConstants.BORDER_WIDTH,UIConstants.BORDER_WIDTH,UIConstants.CANVAS_SIZE,UIConstants.CANVAS_SIZE, Color.WHITE);
		getRoot().getChildren().add(canvas);	
	}
	
	private void setTurtle(){
		double turtleInitialX = UIConstants.CANVAS_SIZE/2+UIConstants.BORDER_WIDTH;
		double turtleInitialY = turtleInitialX;
		double turtleInitialHeading = UIConstants.INITIAL_HEADING;
		
		ImageView turtleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		turtleModel = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
		turtleView = new TurtleView(turtleImage, getRoot(), canvas.getGraphicsContext2D(), Color.BLACK);
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();
		
        modelMap = new ModelMap();
        modelMap.setTurtle(turtleModel);
        CommandsModel commandsModel = new CommandsModel();
        modelMap.setCommands(commandsModel);
        parser = new CommandParser(modelMap);
        //parser.addPatterns(UIConstants.RSRC_LANG + myLang);
        parser.addPatterns("resources/languages/English");
        parser.addPatterns("resources/languages/Syntax");
	}
	
	private void setCommandPane(){
		HBox commandLine = new HBox();
		inputText = new TextArea();
		inputText.setMaxWidth(UIConstants.WIDTH/2 - 50);
		inputText.setMaxHeight(UIConstants.HEIGHT/4);
		commandLine.getChildren().add(inputText);
		Button inputButton = featureMaker.makeB("Go", event -> readInput(parser, inputText));
		commandLine.getChildren().add(inputButton);
		commandLine.setLayoutX(UIConstants.WIDTH/2);
		commandLine.setLayoutY(UIConstants.HEIGHT- UIConstants.RECT_X);
		getRoot().getChildren().add(commandLine);
	}
	
	private void setHistoryPane(){
		SPane history = new SPane(UIConstants.WIDTH/2, UIConstants.BORDER_WIDTH);
		history.myPane.setMinWidth(400);
		history.myPane.setMinHeight(UIConstants.CANVAS_SIZE);
		history.myPane.setMaxSize(UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE-UIConstants.BORDER_WIDTH);
		getRoot().getChildren().addAll(history.myRoot);
		HistoryPaneModel hpm = new HistoryPaneModel();
		hpv = new HistoryPaneView(history.myBox, inputText);
		hpm.addObserver(hpv);
		hpm.notifyObservers();
		modelMap.setHistory(hpm);
	}
	
	private void setVariablePane(){
		SPane variables = new SPane(UIConstants.BORDER_WIDTH, UIConstants.CANVAS_SIZE + 2*UIConstants.BORDER_WIDTH);
		variables.myPane.setMinSize(UIConstants.WIDTH/2 - UIConstants.BORDER_WIDTH*2, UIConstants.HEIGHT/4);
		variables.myPane.setMaxSize(UIConstants.WIDTH/2 - UIConstants.BORDER_WIDTH*2, UIConstants.HEIGHT/4);
		variables.myPane.setStyle("-fx-background-color: #DAE6F3;");
		variables.myBox.getChildren().add(new Text("Variables"));

		VariableModel varModel = new VariableModel();
		VariableView varView = new VariableView(variables.myBox, inputText);
		varModel.addObserver(varView);
		varModel.notifyObservers();
		
		modelMap.setVariable(varModel);

		getRoot().getChildren().add(variables.myPane);
	}
	
	private void setBackgroundColor(){

	}
	
	private void setPenColor(){
		
	}
	
	private void openHelpPage(){
		Stage myStage = new Stage();
		Group helpRoot = new Group();
		Scene scene = new Scene(helpRoot, UIConstants.WIDTH, UIConstants.HEIGHT);
		myStage.setTitle("Help");
        myStage.setScene(scene);
        myStage.show();
 
		WebView browser = new WebView();
		browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
		helpRoot.getChildren().add(browser);
		browser.getEngine().load(WorkSpace.class.getResource("/references/help.html").toExternalForm());
	}
	
	private void setHelpButton(){
		Button help = featureMaker.makeB("Help", event -> openHelpPage());
		getRoot().getChildren().add(help);
		help.setLayoutX(UIConstants.ZERO);
		help.setLayoutY(UIConstants.ZERO);
	}
	
	private void setTurtleCoordsBox(){
		//duplicate, we already made another HBox elsewhere, can extract
		HBox turtleVars = new HBox();
		turtleVars.setLayoutX(25);
		turtleVars.setLayoutY(475);
		getRoot().getChildren().add(turtleVars);
		
		CoordinateView cv = new CoordinateView(turtleVars, turtleModel);
		turtleModel.addObserver(cv);
		turtleModel.notifyObservers();
	}
	
	private void readInput(CommandParser parser, TextArea input){
		parser.parseText(input.getText());
		input.clear();
	}
	
//	private void readInput(CommandParser parser, TextArea input){
//		//double output = parser.parseText(input.getText());
//		try{
//			parser.parseText(input.getText());
//			input.clear();
//		}
//		catch(String msg){
//			showError("u messed up");
//		}
//	}
	
	protected void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setContentText(message);
		alert.showAndWait();
	}
}
