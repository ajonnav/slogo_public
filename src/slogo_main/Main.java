package slogo_main;

import javafx.application.Application;
import javafx.stage.Stage;

import display.Splash;

public class Main extends Application {

	public void start (Stage s) {
		Splash splashscreen = new Splash();
		splashscreen.begin();
	}
}
