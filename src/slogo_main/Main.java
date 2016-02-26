package slogo_main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.*;
import view.TurtleView;
import model.TurtleModel;
import pane.IPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import addons.Features;

public class Main extends Application {


	public static final String TITLE = "SLogo";
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 500;
	public Features featureMaker;

	public void start (Stage s) {
		featureMaker = new Features();
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.GRAY);
		
		//canvas for turtle view
		Canvas canvas = new Canvas(475, 475);	
		GraphicsContext GC = canvas.getGraphicsContext2D();
		GC.setFill(Color.WHITE);
		GC.fillRect(25,25,475,475);
		root.getChildren().add(canvas);	
		
		double turtleInitialX = 300;
		double turtleInitialY = 300;
		double turtleInitialHeading = 270;
		
		ImageView turtleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		//Image turtleImage = new Image("turtle.png");
		TurtleModel turtleModel = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
		TurtleView turtleView = new TurtleView(turtleImage, root, GC);
		//GC.drawImage(turtleImage, 100, 100);
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();
		
		//command input
		HBox commandLine = makeCommandInput();
		commandLine.setLayoutX(500);
		commandLine.setLayoutY(350);
		root.getChildren().add(commandLine);
		
		//history box
		IPane history = new IPane();
		history.myPane.setLayoutX(WIDTH/2);
		history.myPane.setLayoutY(25);
		history.myPane.setMinWidth(WIDTH/2 -55);
		history.myPane.setMinHeight(HEIGHT/2+50);
		root.getChildren().add(history.myRoot);
		
		FlowPane variables = new FlowPane();
		variables.setStyle("-fx-background-color: DAE6F3;");
		variables.setLayoutX(50);
		variables.setLayoutY(50);
		variables.setMinWidth(WIDTH/2 -55);
		variables.setMinHeight(HEIGHT/2+50);
		Map<String, String> items = new HashMap<String,String>();
		items.put("Turtle X: ",turtleView.getX());
		items.put("Turtle Y: ",turtleView.getY());
		for(String thing : items.keySet()){
			System.out.println(thing);
			Text a = new Text(thing + items.get(thing));
			variables.getChildren().add(a);
		}
		root.getChildren().add(variables);	
		
		root.getScene().setOnKeyPressed(e ->{
			ArrayList<ICommand> commands = new ArrayList<ICommand>();
			commands.add(new PenDownCommand(turtleModel));
			commands.add(new ForwardCommand(turtleModel, 50));
			commands.add(new RightTurnCommand(turtleModel, 90));
			commands.add(new ForwardCommand(turtleModel, 50));
			commands.add(new RightTurnCommand(turtleModel, 90));
			commands.add(new ForwardCommand(turtleModel, 50));
			commands.add(new RightTurnCommand(turtleModel, 90));
			commands.add(new ForwardCommand(turtleModel, 50));
			commands.add(new RightTurnCommand(turtleModel, 90));
			for(int i = 0; i < commands.size(); i++) {
				commands.get(i).execute();
			}
		});
		
		s.setTitle(TITLE);
        s.setScene(scene);
        s.show();
	}


	private HBox makeCommandInput(){
		HBox commandLine = new HBox();
		TextArea inputText = new TextArea();
		inputText.setMaxWidth(WIDTH/2 - 50);
		inputText.setMaxHeight(HEIGHT/4);
		commandLine.getChildren().add(inputText);
		Button inputButton = featureMaker.makeB("Go", event -> readInput());
		commandLine.getChildren().add(inputButton);
		return commandLine;
	}

	
	private void readInput(){
		//make to read the text field
	}
	
}
