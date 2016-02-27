package slogo_main;
import java.util.HashMap;
import java.util.Map;
import view.TurtleView;
import model.TurtleModel;
import parser.CommandParser;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.Observable;


public class Main extends Application{
	
	public static final String TITLE = "SLogo";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final String WHITESPACE = "\\p{Space}";
	
	
	public void start (Stage s) {
		Group root = new Group();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);
		
		GraphicsContext GC = canvas.getGraphicsContext2D();
		Scene scene = new Scene(root);
		
		double turtleInitialX = 300;
		double turtleInitialY = 300;
		double turtleInitialHeading = 270;
		
		ImageView turtleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		TurtleModel turtleModel = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
		TurtleView turtleView = new TurtleView(turtleImage, root, GC);
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();
                Map<String, Observable> modelMap = new HashMap<String, Observable>();
                modelMap.put("turtle", turtleModel);
		String userInput = "fd repeat 2 [ fd 100 ]";
		CommandParser parser = new CommandParser(modelMap);
		parser.addPatterns("resources/languages/English");
		parser.addPatterns("resources/languages/Syntax");
		root.getScene().setOnKeyPressed(e ->{
		    parser.parseText(userInput);
		});
		s.setTitle(TITLE);
                s.setScene(scene);
                s.show();
	}
	
	/**
	 * Launches the animation
	 */
	public static void main (String[] args) {
        launch(args);
    } 
}
