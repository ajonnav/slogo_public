package display;

import java.awt.image.BufferedImage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javax.imageio.ImageIO;
import model.CommandsModel;
import model.HistoryPaneModel;
import model.ModelMap;
import model.TurtleModel;
import model.VariableModel;
import pane.SPane;
import parser.CommandParser;
import view.CoordinateView;
import view.HistoryPaneView;
import view.TurtleView;
import view.VariableView;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import addons.Features;
import addons.MenuMaker;
import constants.UIConstants;

public class WorkSpace extends Screen {

	private Features featureMaker;
	private CommandParser parser;
	private TextArea inputText;
	private String myLang;
	private ImageView profileImage;
	private TurtleModel turtleModel;
	private MenuMaker mm;

	private Canvas canvas;
	private TurtleView turtleView;
	// private TurtleModel turtleModel;
	private HistoryPaneView hpv;

	//private Map<String, Observable> modelMap;

	private ImageView selectedFile;

	 public WorkSpace(String l){
	 myLang = l;
	 parser = new CommandParser(modelMap);
	 setLang(myLang);
	 }

	public WorkSpace() {
	}
	private ModelMap modelMap;
	
//	public void setLang(String language){
//		myLang = language;
//
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

		setColorPicker();

		setPenPicker();

		setButtons();

		setTurtleCoords();

	}

	private void setColorPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setValue(Color.CORAL);
		cp.setOnAction(event -> sceneChange(cp.getValue(), cp));
		cp.setLayoutX(250);
		cp.setLayoutY(50);
		getRoot().getChildren().add(cp);
	}

	private void setPenPicker() {
		ColorPicker cp = new ColorPicker();
		cp.setValue(Color.CORAL);
		cp.setOnAction(event -> penChange(cp.getValue(), cp));
		cp.setLayoutX(400);
		cp.setLayoutY(50);
		getRoot().getChildren().add(cp);

	}

	private void penChange(Color value, ColorPicker cp) {
		turtleView.setColor(value);
	}

	public void setLang(String language) {
		System.out.println("is set lang first?");
		myLang = language;
		parser = new CommandParser(modelMap);
		// System.out.println("resources/languages/" + myLang);
		parser.addPatterns("resources/languages/" + myLang);

	}

	private void setTurtleCoords() {
		// duplicate, we already made another HBox elsewhere, can extract
		HBox turtleVars = new HBox();
		turtleVars.setLayoutX(25);
		turtleVars.setLayoutY(475);
		getRoot().getChildren().add(turtleVars);

		CoordinateView cv = new CoordinateView(turtleVars, turtleModel);
		turtleModel.addObserver(cv);
		turtleModel.notifyObservers();
	}

	private void sceneChange(Color c, ColorPicker cp) {
		// turtleView.getGC().setFill(c);
		// canvas.getGraphicsContext2D() = new
		canvas = new Canvas();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(c);
		canvas.getGraphicsContext2D().setFill(c);
		 //getRoot().getChildren().add(canvas);
		//getScene().setFill(c);
	}

	private void setCanvas() {
		canvas = featureMaker.makeCanvas(UIConstants.BORDER_WIDTH, UIConstants.BORDER_WIDTH, UIConstants.CANVAS_SIZE,
				UIConstants.CANVAS_SIZE, Color.WHITE);
		getRoot().getChildren().add(canvas);
	}

	protected void setButtons() {
		Button pick = new Button("Select a new image");
		pick.setOnAction(event -> changeImage());
		pick.setLayoutX(300);
		pick.setLayoutY(150);
		getRoot().getChildren().add(pick);
	}

	public void changeImage() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose Image");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
					new ExtensionFilter("All Files", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(getStage());
			System.out.println(getClass().getClassLoader().getResourceAsStream(selectedFile.getName()));
			// profileImage = new ImageView(new
			// Image(getClass().getClassLoader().getResourceAsStream(selectedFile.getName())));
			if (selectedFile != null) {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				ImageView iv = new ImageView(image);
				setFile(iv);
				// profileImage.setImage(image);
			}
		} catch (Exception e) {
			System.out.println("FAIL");
			System.err.println(e);
		}

	}

	private void setFile(ImageView thing) {
		turtleView = new TurtleView(thing, getRoot(), canvas.getGraphicsContext2D(), Color.BLACK);
		turtleView.getImage().setX(turtleModel.getPositionX());
		turtleView.getImage().setY(turtleModel.getPositionY());
		System.out.println(turtleView.getX());
		turtleModel.addObserver(turtleView);
		turtleModel.notifyObservers();

	}

	private void setTurtle() {
		double turtleInitialX = UIConstants.CANVAS_SIZE / 2;
		double turtleInitialY = turtleInitialX;
		double turtleInitialHeading = UIConstants.INITIAL_HEADING;

		ImageView turtleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		turtleModel = new TurtleModel(turtleInitialX, turtleInitialY, turtleInitialHeading);
		turtleView = new TurtleView(turtleImage, getRoot(), canvas.getGraphicsContext2D(), Color.BLACK);
		System.out.println(turtleView.getX());
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

	private void setCommandPane() {
		HBox commandLine = new HBox();
		inputText = new TextArea();
		inputText.setMaxWidth(UIConstants.WIDTH / 2 - 50);
		inputText.setMaxHeight(UIConstants.HEIGHT / 4);
		commandLine.getChildren().add(inputText);
		Button inputButton = featureMaker.makeB("Go", event -> readInput(parser, inputText));
		commandLine.getChildren().add(inputButton);
		commandLine.setLayoutX(UIConstants.WIDTH / 2);
		commandLine.setLayoutY(UIConstants.HEIGHT - UIConstants.RECT_X);
		getRoot().getChildren().add(commandLine);
	}

	private void setHistoryPane() {
		SPane history = new SPane(UIConstants.WIDTH / 2, UIConstants.BORDER_WIDTH);
		history.myPane.setMinWidth(400);
		history.myPane.setMinHeight(UIConstants.CANVAS_SIZE - UIConstants.BORDER_WIDTH);
		history.myPane.setMaxSize(UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE - UIConstants.BORDER_WIDTH);
		getRoot().getChildren().addAll(history.myRoot);
		HistoryPaneModel hpm = new HistoryPaneModel();
		hpv = new HistoryPaneView(history.myBox, inputText);
		hpm.addObserver(hpv);
		hpm.notifyObservers();
		modelMap.setHistory(hpm);
	}

	private void setVariablePane() {
		SPane variables = new SPane(UIConstants.BORDER_WIDTH, UIConstants.CANVAS_SIZE + UIConstants.BORDER_WIDTH);
		variables.myPane.setMinSize(UIConstants.WIDTH / 2 - UIConstants.BORDER_WIDTH * 2, UIConstants.HEIGHT / 4);
		variables.myPane.setMaxSize(UIConstants.WIDTH / 2 - UIConstants.BORDER_WIDTH * 2, UIConstants.HEIGHT / 4);
		variables.myPane.setStyle("-fx-background-color: #DAE6F3;");
		variables.myBox.getChildren().add(new Text("Variables"));

		VariableModel varModel = new VariableModel();
		VariableView varView = new VariableView(variables.myBox);
		varModel.addObserver(varView);
		varModel.notifyObservers();
		
		modelMap.setVariable(varModel);
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
		browser.getEngine().load(WorkSpace.class.getResource("/references/help.html").toExternalForm());
	}

	private void setHelpButton() {
		Button help = featureMaker.makeB("Help", event -> openHelpPage());
		getRoot().getChildren().add(help);
		help.setLayoutX(UIConstants.ZERO);
		help.setLayoutY(UIConstants.ZERO);
	}

	private void readInput(CommandParser parser, TextArea input) {
		parser.parseText(input.getText());
		input.clear();
	}

	// private void readInput(CommandParser parser, TextArea input){
	// //double output = parser.parseText(input.getText());
	// try{
	// parser.parseText(input.getText());
	// input.clear();
	// }
	// catch(String msg){
	// showError("u messed up");
	// }
	// }

	protected void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setContentText(message);
		alert.showAndWait();
	}
}
