package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.HistoryPaneModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.HistoryPaneModel;


public class HistoryPaneView implements IView {

    public List<String> myHist;
    public VBox myVBox;
    public TextArea myTA;

    public HistoryPaneView (VBox vb, TextArea ta) {
        myHist = new ArrayList<>();
        myVBox = vb;
        myTA = ta;
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof HistoryPaneModel) {
            HistoryPaneModel hpm = (HistoryPaneModel) o;
            myHist = new ArrayList(hpm.getImmutableHistoryList());
            myVBox.getChildren().clear();
            //Collections.reverse(myHist);
            int x = 1;
            for (String item : myHist) {
            	if(x % 2 != 0) {
            		Hyperlink past = new Hyperlink(">> " + item);
            		past.setOnAction(event -> {
            			myTA.appendText(item);
            			past.setStyle("-fx-color: black;");
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
