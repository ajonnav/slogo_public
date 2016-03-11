package addons;

import java.io.File;
import java.util.ResourceBundle;

import constants.UIConstants;
import display.DemoWSpace;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import preferences.PrefLoader;
import preferences.saveState;

public class NewWorkspaceTransition {

	private Stage myS;
	private FileChooser myFC;
	private ResourceBundle myBundle;
	
	public NewWorkspaceTransition(Stage myStage) {
		myS = myStage;
        myFC = new FileChooser();
        myBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.SCREEN_LANG);
        myFC.setTitle(myBundle.getString("FileSelect"));
        myFC.getExtensionFilters().addAll(
                        new ExtensionFilter(
                                        myBundle.getString("Files"),
                                        myBundle.getString("srl")));

	}
	
	/*
	 * opens a pop up to choose a file and start a new workspace
	 */
	public void execute(String language){
		File selectedFile = myFC.showOpenDialog(myS);
        PrefLoader loader = new PrefLoader();
        saveState myState = loader.load(selectedFile);
        DemoWSpace myW = new DemoWSpace(myState);
        myW.setLang(language);
        myW.begin();
	}

}
