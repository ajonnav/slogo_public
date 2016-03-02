package display;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import addons.Features;
import constants.UIConstants;

public class WorkSpace extends Screen {

	private Features featureMaker;
	private CommandParser parser;
	private TextArea inputText;
	private String myLang;
	private TurtleModel turtleModel;
	private Canvas layer1;
	private Canvas layer2;
	private TurtleView turtleView;
	private HistoryPaneView hpv;
	private ModelMap modelMap;


	public WorkSpace(String l) {
		myLang = l;
		parser = new CommandParser(modelMap);
		setLang(myLang);
	}

	public WorkSpace() {
	}


	// public void setLang(String language){
	// myLang = language;
	//
	// }


	@Override
	public void setUpScene() {

		getRoot().getStylesheets().add(
				UIConstants.DEFAULT_RESOURCE + UIConstants.SPLASH_CSS);

		featureMaker = new Features();

		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT,
				Color.GRAY));

		setCanvas();

		setTurtle();

		setInputPane();

		setHistoryPane();

		setHelpButton();

		setColorPicker();

		setPenPicker();

		setButtons();

		setUserCommandPane();

		setTurtleCoordsBox();

	}

	private void setColorPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setOnAction(event -> sceneChange(cp.getValue()));
		cp.setLayoutX(100);
		cp.setLayoutY(0);
		cp.setMinWidth(150);
		cp.setMinHeight(25);
		getRoot().getChildren().add(cp);
	}

	private void setPenPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setOnAction(event -> penChange(cp.getValue()));
		cp.setLayoutX(250);
		cp.setLayoutY(0);
		cp.setMinWidth(150);
		cp.setMinHeight(25);
		getRoot().getChildren().add(cp);
	}

	private void penChange(Color value) {
		turtleView.setColor(value);
	}

	public void setLang(String language) {
		myLang = language;
		parser = new CommandParser(modelMap);
		parser.addPatterns("resources/languages/" + myLang);
		parser.addPatterns("resources/languages/Syntax");
		setVariablePane();
	}


	private void sceneChange(Color c) {
		layer1 = featureMaker.makeCanvas(410, UIConstants.BORDER_WIDTH,
				UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE, c);
		getRoot().getChildren().add(layer1);
		layer2.toFront();
		turtleView.getImage().toFront();
	}

	private void setCanvas() {
		layer1 = featureMaker.makeCanvas(410, UIConstants.BORDER_WIDTH,
				UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE, Color.GREEN);
		layer2 = featureMaker.makeCanvas(410, UIConstants.BORDER_WIDTH,
				UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
				Color.TRANSPARENT);
		getRoot().getChildren().add(layer1);
		getRoot().getChildren().add(layer2);
		layer2.toFront();
	}
	
	protected void setButtons() {
		Button pick = new Button("Select a new image");
		pick.setOnAction(event -> changeImage());
		pick.setLayoutX(400);
		pick.setLayoutY(0);
		pick.setMinWidth(100);
		pick.setMinHeight(25);
		getRoot().getChildren().add(pick);
	}

	public void changeImage() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose Image");
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Image Files", "*.png", "*.jpg",
							"*.gif"), new ExtensionFilter("All Files", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(getStage());
			if (selectedFile != null) {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				ImageView iv = new ImageView(image);
				setFile(iv);
			}
		} catch (Exception e) {
			System.out.println("FAIL");
			System.err.println(e);
		}
	}

	private void setFile(ImageView thing) {
//<<<<<<< HEAD
//		turtleView = new TurtleView(thing, getRoot(), layer2.getGraphicsContext2D(), Color.BLACK);
		System.out.println("WTF");
//		System.out.println(turtleModel.getPositionX());
//		System.out.println(turtleModel.getPositionY());
		System.out.println(thing.getFitWidth());
		System.out.println(thing.getFitHeight());
		System.out.println("HELP");
//		turtleView.getImage().setX(turtleModel.getPositionX() - thing.getFitWidth() / 2);
//		turtleView.getImage().setY(turtleModel.getPositionY() - thing.getFitHeight() / 2);
//=======
		turtleView = new TurtleView(thing, getRoot(),
				layer2.getGraphicsContext2D(), Color.BLACK);
		turtleView.getImage().setX(
				turtleModel.getPositionX() - thing.getFitWidth() / 2);
		turtleView.getImage().setY(
				turtleModel.getPositionY() - thing.getFitHeight() / 2);
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();
	}

	private void setTurtle(){
		double turtleInitialX = UIConstants.INITIAL_X;
		double turtleInitialY = UIConstants.INITIAL_Y;
		double turtleInitialHeading = UIConstants.INITIAL_HEADING;

		ImageView turtleImage = new ImageView(new Image(getClass()
				.getClassLoader().getResourceAsStream("turtle.png")));
		turtleModel = new TurtleModel(turtleInitialX, turtleInitialY,
				turtleInitialHeading);
		turtleView = new TurtleView(turtleImage, getRoot(),
				layer2.getGraphicsContext2D(), Color.BLACK);
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();

		modelMap = new ModelMap();
		modelMap.setTurtle(turtleModel);
		CommandsModel commandsModel = new CommandsModel();
		modelMap.setCommands(commandsModel);
	}

	private void setInputPane() {
		HBox commandLine = new HBox();
		inputText = new TextArea();
		inputText.setMinWidth(450);
		inputText.setMinHeight(200);
		commandLine.getChildren().add(inputText);
		Button inputButton = featureMaker.makeB("Go",
				event -> readInput(parser, inputText));
		commandLine.getChildren().add(inputButton);
		commandLine.setLayoutX(600);
		commandLine.setLayoutY(525);
		getRoot().getChildren().add(commandLine);
	}

	private void setHistoryPane() {
		SPane history = new SPane(900, 25);
		history.myPane.setMinWidth(360);
		history.myPane.setMinHeight(475);
		history.myPane.setMaxSize(UIConstants.CANVAS_SIZE,
				UIConstants.CANVAS_SIZE - UIConstants.BORDER_WIDTH);
		getRoot().getChildren().addAll(history.myRoot);
		HistoryPaneModel hpm = new HistoryPaneModel();
		hpv = new HistoryPaneView(history.myBox, inputText);
		hpm.addObserver(hpv);
		hpm.notifyObservers();
		modelMap.setHistory(hpm);
	}

	private void setVariablePane() {
		SPane variables = new SPane(100, 525);
		variables.myPane.setMinSize(450, 200);
		variables.myPane.setMaxSize(450, 200);
		variables.myBox.getChildren().add(new Text("Variables"));

		VariableModel varModel = new VariableModel();
		VariableView varView = new VariableView(variables.myBox, inputText,
				myLang);
		varModel.addObserver(varView);
		varModel.notifyObservers();

		modelMap.setVariable(varModel);
		getRoot().getChildren().add(variables.myPane);
	}
	
	private void setUserCommandPane() {
		SPane variables = new SPane(25, 25);
		variables.myPane.setMinSize(360, 475);
		variables.myPane.setMaxSize(360, 475);
		variables.myBox.getChildren().add(new Text("User Commands"));

		CommandsModel varModel = new CommandsModel();
		CommandsView varView = new CommandsView(variables.myBox);
		varModel.addObserver(varView);
		varModel.notifyObservers();

		modelMap.setCommands(varModel);
		getRoot().getChildren().add(variables.myPane);
	}
	

	private void openHelpPage() {
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

	private void setHelpButton() {
		Button help = featureMaker.makeB("Help", event -> openHelpPage());
		getRoot().getChildren().add(help);
		help.setLayoutX(UIConstants.ZERO);
		help.setLayoutY(UIConstants.ZERO);
		help.setMaxSize(75, 25);
	}

	private void setTurtleCoordsBox() {
		// duplicate, we already made another HBox elsewhere, can extract
		HBox turtleVars = new HBox();
		turtleVars.setLayoutX(500);
		turtleVars.setLayoutY(500);
		turtleVars.setMaxSize(200, 25);
		getRoot().getChildren().add(turtleVars);
		
		CoordinateView cv = new CoordinateView(turtleVars, turtleModel);
		turtleModel.addObserver(cv);
		turtleModel.notifyObservers();
	}

	private void readInput(CommandParser parser, TextArea input) {
		parser.parseText(input.getText());
		input.clear();
	}

}
