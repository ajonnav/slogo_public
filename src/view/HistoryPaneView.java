package view;

import java.util.Observable;
import constants.UIConstants;
import javafx.scene.text.Text;
import model.ViewableHistoryModel;
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
        setMyName(getResources().getString("Hist"));
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof ViewableHistoryModel) {
            ViewableHistoryModel hpm1 = (ViewableHistoryModel) o;
            getMyBox().getChildren().clear();
            int x = 1;
            for (String item : hpm1.getImmutableHistoryList()) {
                if (x % UIConstants.TWO != 0) {
                    Hyperlink past = new Hyperlink(">> " + item);
                    past.setOnAction(event -> {
                        myTA.appendText(item);
                    });
                    getMyBox().getChildren().add(past);
                }
                else {
                    Text past = new Text("\t" + item);
                    getMyBox().getChildren().add(past);
                }
                x++;
            }
        }
    }

}
