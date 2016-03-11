package addons;

import constants.UIConstants;
import display.DemoWSpace;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/*
 * Opens a WebView to see the specifications of SLOGO commands
 */
public class HelpBrowser {

	private Stage myStage;
	private Scene scene;
	private WebView browser;

	public HelpBrowser() {
		myStage = new Stage();
		Group helpRoot = new Group();
		scene = new Scene(helpRoot, UIConstants.WIDTH, UIConstants.HEIGHT);
		browser = new WebView();
		helpRoot.getChildren().add(browser);

	}

	public void execute() {
		myStage.setScene(scene);
		browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
		browser.getEngine().load(DemoWSpace.class.getResource("/references/help.html").toExternalForm());
		myStage.show();
	}

}
