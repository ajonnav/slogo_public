package view;

import java.util.Observable;

import constants.UIConstants;
import javafx.scene.text.Text;
import model.HistoryPaneModel;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;


public class HistoryPaneView extends ScrollView {
	
    public TextArea myTA;
    
    public HistoryPaneView (TextArea ta) {
        myTA = ta;
        getMyPane().setLayoutX(UIConstants.HISTORY_PANE_X);
        getMyPane().setLayoutY(UIConstants.BORDER_WIDTH);
        getMyPane().setMinSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
		getMyPane().setMaxSize(UIConstants.UPPER_PANE_WIDTH, UIConstants.UPPER_PANE_HEIGHT);
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof HistoryPaneModel) {
            HistoryPaneModel hpm = (HistoryPaneModel) o;
            getMyBox().getChildren().clear();
            int x = 1;
            for (String item : hpm.getImmutableHistoryList()) {
            	if(x % 2 != 0) {
            		Hyperlink past = new Hyperlink(">> " + item);
            		past.setOnAction(event -> {
            			myTA.appendText(item);
            		});
            		getMyBox().getChildren().add(past);
            	}
            	else{
            		Text past = new Text("\t" + item);
            		getMyBox().getChildren().add(past);
            	}
            	x++;
            }
        }
    }

}
