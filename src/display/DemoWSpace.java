// This entire file is part of my masterpiece.
// Michael Figueiras

/*
 * This workspace needed to be refactored because it had poor duplicate code in terms
 * of initializing the color and image maps. I realized they were overwriting the maps
 * loaded in through the user's file, so it was clear that this code needed to be replaced. In addition,
 * I used reflection to initialize the methods that set up the model and views. This helped improve
 * the dependencies that were discussed in my analysis. I also moved the language feature to the Screen class
 * since both Splash and DemoWSpace keep track of the user language. 
 * This adheres better to the inheritance hierarchy of the display package and encapsulates this data
 * more appropriately.  
 */
package display;

import model.CommandsModel;
import model.DisplayModel;
import model.HistoryModel;
import model.ModelMap;
import model.VariableModel;
import parser.CommandParser;
import preferences.saveState;
import view.CommandsView;
import view.DisplayView;
import view.HistoryPaneView;
import view.InputView;
import view.TurtleIDView;
import view.VariableView;
import view.View;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import addons.WMenu;
import constants.UIConstants;

public class DemoWSpace extends Screen {

	private CommandParser parser;
	private TextArea inputText;
	private HistoryPaneView hpv;
	private ModelMap modelMap;
	private saveState myState;

	private VariableView varView;
	private TurtleIDView turtleView;
	private InputView myIV;
	private CommandsView commandView;

	public DemoWSpace(saveState myS) {
		myState = myS;
		modelMap = new ModelMap();
		parser = new CommandParser(modelMap);
	}

	@Override
	public void setUpScene() {
		setScene(new Scene(getRoot(), UIConstants.WIDTH, UIConstants.HEIGHT, Color.LIGHTBLUE));
		inputText = new TextArea();
		parser.addPatterns(UIConstants.RSRC_LANG + getMyLang());
		parser.addPatterns(UIConstants.RSRC_LANG + UIConstants.SYNTAX);
		for (String s : getResources().getString(UIConstants.INIT).split("/")) {
			try {
				getClass().getDeclaredMethod(s).invoke(this);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * Initializes the turtle display's front end and back end relationship
	 */
	private void setDisplay() {
		DisplayModel displayModel = new DisplayModel(myState.getColorMap(), myState.getImages());
		DisplayView displayView = new DisplayView(getRoot());
		displayModel.addObserver(displayView);
		modelMap.setDisplay(displayModel);
		modelMap.getDisplay().setBackgroundColorIndex(myState.getBackColorIndex());
		displayModel.setToAnimate(true);
		displayModel.notifyObservers();
		setTurtlePane(displayModel);
		displayModel.updateView();
	}

	/*
	 * Initializes the MenuBar for the workspace screen
	 */
	public void setBar() {
		WMenu workspaceMenu = new WMenu(getRoot(), modelMap, getMyLang());
		workspaceMenu.setStage(getStage());
		workspaceMenu.setViews(Arrays.asList(varView, hpv, turtleView, commandView, myIV));
		try {
			workspaceMenu.makeFileMenu();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		getRoot().getChildren().add(workspaceMenu.getMyMenu());
	}

	/*
	 * Establishes the observer and observable relationship for a model and view
	 */
	public void establishRelationship(Observable myModel, View myView) {
		myModel.addObserver(myView);
		myModel.notifyObservers();
		getRoot().getChildren().add(myView.getMyRoot());
	}

	/*
	 * Sets the Pane for the user input text area
	 */
	private void setInputPane() {
		myIV = new InputView(parser, inputText);
		getRoot().getChildren().add(myIV.getMyRoot());
	}

	/*
	 * Sets the Pane for the user history
	 */
	private void setHistoryPane() {
		HistoryModel hpm = new HistoryModel();
		hpv = new HistoryPaneView(inputText);
		modelMap.setHistory(hpm);
		establishRelationship(hpm, hpv);
		initializeHistory(hpm, myState.getHistory());
		hpm.updateView();
	}

	private void initializeHistory(HistoryModel hpm, List<String> history) {
		for (String n : myState.getHistory()) {
			hpm.addToHistory(n);
		}
	}

	/*
	 * Sets the Pane for the current user-defined variables in the environment
	 */
	private void setVariablePane() {
		VariableModel varModel = new VariableModel();
		varView = new VariableView(inputText, getMyLang());
		modelMap.setVariable(varModel);
		establishRelationship(varModel, varView);
		initializeVariables(varModel, myState.getVariables());
		varModel.updateView();
	}

	private void initializeVariables(VariableModel vpm, Map<String, Double> vars) {
		for (String n : vars.keySet()) {
			vpm.setVariable(n, vars.get(n));
		}
	}

	/*
	 * Sets the Pane for the current user-defined methods in the environment
	 */
	private void setUserCommandPane() {
		CommandsModel commandModel = new CommandsModel();
		commandView = new CommandsView(inputText);
		modelMap.setCommands(commandModel);
		establishRelationship(commandModel, commandView);
		commandModel.updateView();
	}

	/*
	 * Sets the Pane for the current status of the various turtles on the
	 * display
	 */
	private void setTurtlePane(DisplayModel dm) {
		turtleView = new TurtleIDView();
		getRoot().getChildren().add(turtleView.getMyRoot());
		dm.addObserver(turtleView);
	}
}