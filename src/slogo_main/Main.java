package slogo_main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pane.IPane;
import command.*;
import view.TurtleView;
import model.TurtleModel;
//import parser.CommandParser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import constants.UIConstants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import addons.Features;
import java.util.Observable;

public class Main extends Application {

	public Features featureMaker;
	public static final String WHITESPACE = "\\p{Space}";
	
	public void start (Stage s) {
		featureMaker = new Features();
		Group root = new Group();
		Scene scene = new Scene(root, UIConstants.WIDTH, UIConstants.HEIGHT, Color.GRAY);
		
		//canvas for turtle view
		Canvas canvas = featureMaker.makeCanvas(UIConstants.BORDER_WIDTH,UIConstants.BORDER_WIDTH,UIConstants.CANVAS_SIZE,UIConstants.CANVAS_SIZE, Color.WHITE);
		root.getChildren().add(canvas);	
		
		double turtleInitialX = UIConstants.INITIAL_X;
		double turtleInitialY = UIConstants.INITIAL_Y;
		double turtleInitialHeading = UIConstants.INITIAL_HEADING;
		
		ImageView turtleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		TurtleModel turtleModel = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
		TurtleView turtleView = new TurtleView(turtleImage, root, canvas.getGraphicsContext2D());
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();

		//command input
		HBox commandLine = makeCommandInput(root);
		
		//history box
		makeHistoryPane(root);
		
		//top panel options, help to html file
		Button help = featureMaker.makeB("Help", event -> openHelpPage());
		root.getChildren().add(help);
		help.setLayoutX(UIConstants.ZERO);
		help.setLayoutY(UIConstants.ZERO);
		
		testCommand(root, turtleModel, turtleView);
		
		s.setTitle(UIConstants.TITLE);
        s.setScene(scene);
        s.show();
	}

	private HBox makeCommandInput(Group root){
		HBox commandLine = new HBox();
		TextArea inputText = new TextArea();
		inputText.setMaxWidth(UIConstants.WIDTH/2 - 50);
		inputText.setMaxHeight(UIConstants.HEIGHT/4);
		commandLine.getChildren().add(inputText);
		Button inputButton = featureMaker.makeB("Go", event -> readInput(inputText));
		commandLine.getChildren().add(inputButton);
		commandLine.setLayoutX(UIConstants.WIDTH/2);
		commandLine.setLayoutY(UIConstants.HEIGHT- UIConstants.RECT_X);
		root.getChildren().add(commandLine);
		return commandLine;
	}
	
	private void readInput(TextArea input){
		//make to read the text field
		input.clear();
	}
	
	private void makeHistoryPane(Group root){
		IPane history = new IPane();
		history.myPane.setLayoutX(UIConstants.WIDTH/2);
		history.myPane.setLayoutY(UIConstants.BORDER_WIDTH);
		history.myPane.setMinWidth(UIConstants.CANVAS_SIZE);
		history.myPane.setMinHeight(UIConstants.CANVAS_SIZE-UIConstants.BORDER_WIDTH);
		root.getChildren().add(history.myRoot);
	}
	
	private void testCommand(Group root, TurtleModel turtleModel, TurtleView turtleView){		
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
		root.getChildren().add(variables);	
	/*
                Map<String, Observable> modelMap = new HashMap<String, Observable>();
                modelMap.put("turtle", turtleModel);
		String userInput = "repeat 2 [ fd 50 ]";
		CommandParser parser = new CommandParser(modelMap);
		parser.addPatterns("resources/languages/English");
		parser.addPatterns("resources/languages/Syntax");
		root.getScene().setOnKeyPressed(e ->{
		    parser.parseText(userInput);
		});

	*/
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
}
