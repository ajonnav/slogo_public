package slogo_main;
import java.util.ArrayList;

import command.*;
import view.TurtleView;
import model.TurtleModel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static final String TITLE = "SLogo";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
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
	
	/**
	 * Launches the animation
	 */
	public static void main (String[] args) {
        launch(args);
    } 
}
