package display;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.TurtleModel;
import model.VariableModel;
import pane.IPane;
import pane.MasterPane;
import parser.CommandParser;
import view.TurtleView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import addons.Features;
import constants.UIConstants;

public class WorkSpace extends Screen{

	private Features featureMaker;
	private CommandParser parser;
	
	private Canvas canvas;
	private TurtleView turtleView;
	
	@Override
	public void setUpScene() {
		getRoot().getStylesheets().add(UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);
		
		featureMaker = new Features();
		
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.GRAY));
		
		setHelpButton();
		
		setCanvas();
		
		setTurtle();
		
		setCommandPane();
		
		setHistoryPane();
		
		setVariablePane();
	}
	
	private void setCanvas(){
		canvas = featureMaker.makeCanvas(UIConstants.BORDER_WIDTH,UIConstants.BORDER_WIDTH,UIConstants.CANVAS_SIZE,UIConstants.CANVAS_SIZE, Color.WHITE);
		getRoot().getChildren().add(canvas);	
	}
	
	private void setTurtle(){
		double turtleInitialX = UIConstants.INITIAL_X;
		double turtleInitialY = UIConstants.INITIAL_Y;
		double turtleInitialHeading = UIConstants.INITIAL_HEADING;
		
		ImageView turtleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		TurtleModel turtleModel = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
		turtleView = new TurtleView(turtleImage, getRoot(), canvas.getGraphicsContext2D());
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();
		
        Map<String, Observable> modelMap = new HashMap<String, Observable>();
        VariableModel variableModel = new VariableModel();
        modelMap.put("turtle", turtleModel);
        modelMap.put("variable", variableModel);
        parser = new CommandParser(modelMap);
        parser.addPatterns("resources/languages/English");
        parser.addPatterns("resources/languages/Syntax");
	}
	
	private void setCommandPane(){
		HBox commandLine = new HBox();
		TextArea inputText = new TextArea();
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
		MasterPane history = new MasterPane(UIConstants.WIDTH/2, UIConstants.BORDER_WIDTH);
		history.myPane.setMinWidth(UIConstants.CANVAS_SIZE);
		history.myPane.setMinHeight(UIConstants.CANVAS_SIZE-UIConstants.BORDER_WIDTH);
		history.myPane.setStyle("-fx-background-color: DAE6F3;");
		getRoot().getChildren().addAll(history.myRoot);
	}
	
	private void setVariablePane(){
		FlowPane variables = new FlowPane();
		variables.setStyle("-fx-background-color: DAE6F3;");
		variables.setLayoutX(UIConstants.BORDER_WIDTH);
		variables.setLayoutY(UIConstants.CANVAS_SIZE + UIConstants.BORDER_WIDTH);
		variables.setMinWidth(UIConstants.WIDTH/2 - UIConstants.BORDER_WIDTH*2);
		variables.setMinHeight(UIConstants.HEIGHT/4);
		Map<String, String> items = new HashMap<String,String>();
		items.put("Turtle X: ", turtleView.getX());
		items.put("Turtle Y: ", turtleView.getY());
		for(String thing : items.keySet()){
			System.out.println(thing);
			Text a = new Text(thing + items.get(thing));
			variables.getChildren().add(a);
		}
		getRoot().getChildren().add(variables);
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
		browser.getEngine().load("http://www.cs.duke.edu/courses/compsci308/spring16/assign/03_slogo/commands.php");
	}
	
	private void setHelpButton(){
		Button help = featureMaker.makeB("Help", event -> openHelpPage());
		getRoot().getChildren().add(help);
		help.setLayoutX(UIConstants.ZERO);
		help.setLayoutY(UIConstants.ZERO);
	}
	
	private void readInput(CommandParser parser, TextArea input){
		parser.parseText(input.getText());
		input.clear();
	}
}
