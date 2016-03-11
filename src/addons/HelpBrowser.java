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

	public HelpBrowser() {
		Stage myStage = new Stage();
        Group helpRoot = new Group();
        Scene scene = new Scene(helpRoot, UIConstants.WIDTH, UIConstants.HEIGHT);
        myStage.setScene(scene);
        myStage.show();
        WebView browser = new WebView();
        browser.setPrefSize(UIConstants.WIDTH, UIConstants.HEIGHT);
        helpRoot.getChildren().add(browser);
        browser.getEngine().load(DemoWSpace.class.getResource("/references/help.html").toExternalForm());
	}

}
