package Launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launch extends Application{

	public static void main(String [] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Splash start = new Splash();
		start.begin();
		
	}
	
}
