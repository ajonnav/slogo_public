package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import constants.UIConstants;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.HistoryPaneModel;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;


public class HistoryPaneView implements IView {

    public VBox myVBox;
    public TextArea myTA;
    private ResourceBundle myResources;
    
    public HistoryPaneView (VBox vb, TextArea ta) {
        myVBox = vb;
        myTA = ta;
        myResources = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.SCREEN_LANG);
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof HistoryPaneModel) {
            HistoryPaneModel hpm = (HistoryPaneModel) o;
            myVBox.getChildren().clear();
            int x = 1;
            for (String item : hpm.getImmutableHistoryList()) {
            	if(x % 2 != 0) {
            		Hyperlink past = new Hyperlink(">> " + item);
            		past.setOnAction(event -> {
            			myTA.appendText(item);
            			past.setStyle(myResources.getString("black"));
            		});
            		myVBox.getChildren().add(past);
            	}
            	else{
            		Text past = new Text("\t" + item);
            		myVBox.getChildren().add(past);
            	}
            	x++;
            }
        }
    }
}
