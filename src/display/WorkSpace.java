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
		cp.setLayoutX(UIConstants.BACKGROUND_PICK_X);
		cp.setLayoutY(UIConstants.ZERO);
		cp.setMinWidth(UIConstants.COLOR_SELECTOR_WIDTH);
		cp.setMinHeight(UIConstants.BORDER_WIDTH);
		getRoot().getChildren().add(cp);
	}

	private void setPenPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setOnAction(event -> penChange(cp.getValue()));
		cp.setLayoutX(UIConstants.PEN_PICK_X);
		cp.setLayoutY(UIConstants.ZERO);
		cp.setMinWidth(UIConstants.COLOR_SELECTOR_WIDTH);
		cp.setMinHeight(UIConstants.BORDER_WIDTH);
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
		layer1 = featureMaker.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
				UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE, Color.GREEN);
		layer2 = featureMaker.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
				UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
				Color.TRANSPARENT);
		getRoot().getChildren().add(layer1);
		getRoot().getChildren().add(layer2);
		layer2.toFront();
	}
	
	protected void setButtons() {
		Button pick = new Button("Select a new image");
		pick.setOnAction(event -> changeImage());
		pick.setLayoutX(UIConstants.IMAGE_SELECT_X);
		pick.setLayoutY(UIConstants.ZERO);
		pick.setMinWidth(UIConstants.IMAGE_SELECT_WIDTH);
		pick.setMinHeight(UIConstants.BORDER_WIDTH);
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
				turtleView.setImage(image);
			}
		} catch (Exception e) {
			System.out.println("FAIL");
			System.err.println(e);
		}
	}


	private void setTurtle(){
		double turtleInitialX = 0;
		double turtleInitialY = 0;
		double turtleInitialHeading = UIConstants.INITIAL_HEADING;
		ImageView turtleImage = new ImageView(new Image(getClass()
				.getClassLoader().getResourceAsStream("turtle.png")));
		turtleModel = new TurtleModel(turtleInitialX, turtleInitialY,
				turtleInitialHeading);
		turtleView = new TurtleView(turtleImage, getRoot(),
				layer2.getGraphicsContext2D(), Color.BLACK);
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();
		turtleModel.penDown();

		modelMap = new ModelMap();
		modelMap.setTurtle(turtleModel);
		CommandsModel commandsModel = new CommandsModel();
		modelMap.setCommands(commandsModel);
	}

	private void setInputPane() {
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

	private void setHistoryPane() {
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

	private void setVariablePane() {
		SPane variables = new SPane(UIConstants.VARIABLE_PANE_X, UIConstants.LOWER_PANE_Y);
		variables.myPane.setMinSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
		variables.myPane.setMaxSize(UIConstants.LOWER_PANE_WIDTH, UIConstants.LOWER_PANE_HEIGHT);
		variables.myPane.setStyle("-fx-background-color: #DAE6F3;");

		//SPane variables = new SPane(100, 525);
		//variables.myPane.setMinSize(450, 200);
		//variables.myPane.setMaxSize(450, 200);
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
		CommandsView varView = new CommandsView(variables.myBox, inputText);
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
		help.setMaxSize(UIConstants.BUTTON_H, UIConstants.BORDER_WIDTH);
	}

	private void setTurtleCoordsBox() {
		// duplicate, we already made another HBox elsewhere, can extract
		HBox turtleVars = new HBox();
		turtleVars.setLayoutX(UIConstants.COORDINATE_LOCATION);
		turtleVars.setLayoutY(UIConstants.COORDINATE_LOCATION);
		turtleVars.setMaxSize(UIConstants.RECT_X, UIConstants.BORDER_WIDTH);
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
