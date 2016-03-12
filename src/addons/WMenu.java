package addons;

import java.util.List;
import java.util.ResourceBundle;
import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import model.ModelMap;
import preferences.PrefWriter;
import view.View;

public class WMenu {

	private ResourceBundle myResources;
	private MenuMaker menuMaker;
	private MenuBar myMenu;
	private Group myRoot;
	private ModelMap modelMap;
	private String myLang;
	private Stage myStage;
	private Features featureMaker;

	public WMenu(Group root, ModelMap myMap, String language) {
		myRoot = root;
		modelMap = myMap;
		myLang = language;
		menuMaker = new MenuMaker();
		featureMaker = new Features();
		myMenu = menuMaker.getMenu();
		myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.EXTRAS);

	}

	public void setStage(Stage s) {
		myStage = s;
	}


	public MenuBar getMyMenu() {
		return myMenu;
	}

	/*
	 * Makes the File Menu by initializing each of its Menu Items and methods
	 */
	public void makeFileMenu() throws NoSuchMethodException {
		Menu fileMenu = menuMaker.addMenu(myResources.getString("FileCommand"));
		String[] fileM = myResources.getString("fileMenu").split("/");
		for (String pair : fileM) {
			String[] combo = pair.split(",");
			try {
				menuMaker.addMenuItem(combo[0], e -> {
					try {
						getClass().getDeclaredMethod(combo[1]).invoke(this);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} , fileMenu);
			} catch (IllegalArgumentException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * Creates MenuItems for each view so that they can be toggled on or off the
	 * screen
	 */
	public void setViews(List<View> myList) {
		String[] tMenu = myResources.getString("toggleMenu").split("/");
		Menu toggleMenu = menuMaker.addMenu(myResources.getString("Toggle"));
		int i = 0;
		for (View aView : myList) {
			menuMaker.addMenuItem(tMenu[i], e -> noVars(aView), toggleMenu);
			i++;
		}
	}


	/*
	 * Hides/shows a user view from the Scene
	 */
	private void noVars(View variables) {
		if (myRoot.getChildren().contains(variables.getMyRoot())) {
			myRoot.getChildren().remove(variables.getMyRoot());
		} else {
			myRoot.getChildren().add(variables.getMyRoot());
		}
	}

	/*
	 * Initializes a new workspace and opens it up in a separate window
	 */
	private void switchWS() {
		NewWorkspaceTransition myTransition = new NewWorkspaceTransition(myStage);
		myTransition.execute(myLang);
	}

	/*
	 * Initializes a new window with information about Slogo commands
	 */
	private void openHelpPage() {
		HelpBrowser hb = new HelpBrowser();
		hb.execute();
	}

	/*
	 * Creates and saves a user-titled file with the specifications of the
	 * current workspace
	 */
	private void setPrefs() {
		String newTitle = featureMaker.newTextInput(myResources.getString("FileName"),
				myResources.getString("SaveFile"), myResources.getString("EnterNFN"), myResources.getString("FI"));
		PrefWriter setter = new PrefWriter(modelMap, newTitle, myLang);
		setter.writeToSrl();
	}

}
